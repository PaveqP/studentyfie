package com.ludoed.university.service;

import com.ludoed.university.dto.ExchangeProgramDto;
import com.ludoed.university.dto.ExchangeProgramDtoInput;
import com.ludoed.university.dto.ExchangeProgramDtoOutput;
import com.ludoed.university.dto.UniversityFullDtoInput;
import com.ludoed.university.dto.UniversityFullDtoOutput;
import com.ludoed.university.model.Agent;
import com.ludoed.university.model.AgentContact;
import com.ludoed.agent.model.AgentFullDto;
import com.ludoed.university.dao.AgentContactRepository;
import com.ludoed.university.dao.AgentRepository;
import com.ludoed.agent.mapper.AgentMapper;
import com.ludoed.exception.DuplicatedDataException;
import com.ludoed.exception.NotFoundException;
import com.ludoed.kafka.KafkaAgentClient;
import com.ludoed.university.dao.ExchangeProgramRepository;
import com.ludoed.university.dao.ProgramConditionRepository;
import com.ludoed.university.dao.UniversityGeographicRepository;
import com.ludoed.university.dao.UniversityInfoRepository;
import com.ludoed.university.dao.UniversitySocialsRepository;
import com.ludoed.university.mapper.UniversityMapper;
import com.ludoed.university.model.ExchangeProgram;
import com.ludoed.university.model.ProgramCondition;
import com.ludoed.university.model.UniversityGeographic;
import com.ludoed.university.model.UniversityInfo;
import com.ludoed.university.model.UniversitySocials;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UniversityServiceImpl implements UniversityService {

    private final UniversityInfoRepository universityInfoRepository;

    private final UniversitySocialsRepository universitySocialsRepository;

    private final UniversityGeographicRepository universityGeographicRepository;

    private final ExchangeProgramRepository exchangeProgramRepository;

    private final ProgramConditionRepository programConditionRepository;

    private final AgentRepository agentRepository;

    private final AgentContactRepository contactRepository;

    private final UniversityMapper universityMapper;

    private final AgentMapper agentMapper;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final KafkaAgentClient kafkaAgentClient;

    @Override
    public UniversityFullDtoOutput createUniversity(UniversityFullDtoInput universityDto) {
        if (universityInfoRepository.findByName(universityDto.getName()).isPresent()) {
            throw new DuplicatedDataException("Этот университет уже существует");
        }

        UniversityInfo savedUniversity = universityInfoRepository.save(universityMapper.toUniversityInfo(universityDto));

        if (universityDto.getSocials() != null && !universityDto.getSocials().isEmpty()) {
            List<UniversitySocials> socials = universityDto.getSocials().stream()
                    .peek(u -> u.setUniversityInfo(savedUniversity))
                    .toList();
            universitySocialsRepository.saveAll(socials);
        }

        if (universityDto.getGeographic() != null) {
            universityDto.getGeographic().setUniversityInfo(savedUniversity);
            universityGeographicRepository.save(universityDto.getGeographic());
        }

        if (universityDto.getPrograms() != null && !universityDto.getPrograms().isEmpty()) {
            List<ExchangeProgramDtoInput> programs = universityDto.getPrograms().stream()
                    .map(program -> {
                        ProgramCondition savedCondition = programConditionRepository.save(program.getProgramCondition());

                        AgentFullDto agentFullDto = kafkaAgentClient.requestAgentByEmail(program.getAgentEmail());
                        Agent savedAgent = agentRepository.save(agentMapper.toAgent(agentFullDto));
                        if (agentFullDto.getContacts() != null && !agentFullDto.getContacts().isEmpty()) {
                            List<AgentContact> contacts = agentFullDto.getContacts().stream()
                                    .peek(s -> s.setAgent(savedAgent))
                                    .toList();
                            contactRepository.saveAll(contacts);
                        }

                        ExchangeProgramDtoOutput newProgram = universityMapper.toExchangeProgramDtoOutput(program, agentFullDto, savedCondition);

                        exchangeProgramRepository.save(universityMapper.toExchangeProgram(newProgram, savedUniversity));
                        return universityMapper.
                    })
                    .toList();
        }

        UniversityGeographic geographic = universityDto.getGeographic();
        List<UniversitySocials> socials = universityDto.getSocials();
        List<ExchangeProgram> programs = exchangeProgramRepository.findByUniversityInfoId(savedUniversity.getId());

        return universityMapper.toUniversityFullDto(savedUniversity, geographic, socials, programs);
    }

    @Override
    @Transactional
    public UniversityFullDtoOutput updateUniversity(Long universityId, UniversityFullDtoInput universityDto) {
        UniversityInfo university = universityInfoRepository.findById(universityId)
                .orElseThrow(() -> new NotFoundException("Университета с id = " + universityId + " не существует."));

        Optional.ofNullable(universityDto.getName()).ifPresent(university::setName);
        Optional.ofNullable(universityDto.getDescription()).ifPresent(university::setDescription);
        Optional.ofNullable(universityDto.getAvatar()).ifPresent(university::setAvatar);
        Optional.ofNullable(universityDto.getRating()).ifPresent(university::setRating);

        UniversityInfo updatedUniversity = universityInfoRepository.save(university);

        List<UniversitySocials> updatedSocials = new ArrayList<>();
        if (universityDto.getSocials() != null) {
            universitySocialsRepository.deleteByUniversityInfoId(universityId);
            updatedSocials = universityDto.getSocials().stream()
                    .peek(u -> u.setUniversityInfo(university))
                    .toList();
            universitySocialsRepository.saveAll(updatedSocials);
        }

        UniversityGeographic updatedGeographic = null;
        if (universityDto.getGeographic() != null) {
            universityGeographicRepository.deleteByUniversityInfoId(universityId);
            universityDto.getGeographic().setUniversityInfo(university);
            updatedGeographic = universityGeographicRepository.save(universityDto.getGeographic());
        }

        List<ExchangeProgram> updatedPrograms = new ArrayList<>();
        if (universityDto.getPrograms() != null) {
            // Удаляем старые программы и их условия
            List<ExchangeProgram> existingPrograms = exchangeProgramRepository.findByUniversityInfoId(universityId);
            existingPrograms.forEach(program -> {
                if (program.getProgramCondition() != null) {
                    programConditionRepository.deleteById(program.getProgramCondition().getId());
                }
            });
            exchangeProgramRepository.deleteByUniversityInfoId(universityId);

            updatedPrograms = universityDto.getPrograms().stream()
                    .map(programDto -> {
                        ProgramCondition savedCondition = programConditionRepository.save(programDto.getProgramCondition());

                        ExchangeProgram program = new ExchangeProgram();
                        program.setName(programDto.getName());
                        program.setDescription(programDto.getDescription());
                        program.setRating(programDto.getRating());
                        program.setAgent(programDto.getAgent());
                        program.setProgramCondition(savedCondition);
                        program.setUniversityInfo(university);

                        return exchangeProgramRepository.save(program);
                    })
                    .toList();
        }

        return universityMapper.toUniversityFullDto(
                updatedUniversity,
                updatedGeographic,
                updatedSocials,
                updatedPrograms
        );
    }

    @Override
    public UniversityFullDtoOutput getUniversityById(Long universityId) {
        UniversityInfo university = universityInfoRepository.findById(universityId)
                .orElseThrow(() -> new NotFoundException("Университета с id = " + universityId + " не существует."));

        UniversityGeographic geographic = universityGeographicRepository.findByUniversityInfoId(universityId);
        List<UniversitySocials> socials = universitySocialsRepository.findByUniversityInfoId(universityId);
        List<ExchangeProgram> programs = exchangeProgramRepository.findByUniversityInfoId(universityId);

        return universityMapper.toUniversityFullDto(university, geographic, socials, programs);
    }

    @Override
    public List<UniversityFullDtoOutput> getAllUniversities(int from, int size) {
        PageRequest pageRequest = PageRequest.of(from / size, size, Sort.by(Sort.Direction.ASC, "id"));
        List<UniversityInfo> universityList = universityInfoRepository.findAll(pageRequest).toList();

        if (universityList.isEmpty()) {
            return new ArrayList<>();
        }

        List<Long> universityIds = universityList.stream()
                .map(UniversityInfo::getId)
                .collect(Collectors.toList());

        List<UniversitySocials> allSocials = universitySocialsRepository.findByUniversityInfoIdIn(universityIds);
        List<UniversityGeographic> allGeographic = universityGeographicRepository.findByUniversityInfoIdIn(universityIds);
        List<ExchangeProgram> allPrograms = exchangeProgramRepository.findByUniversityInfoIdIn(universityIds);

        return universityMapper.toUniversityFullDtoList(
                universityList,
                allGeographic,
                allPrograms,
                allSocials
        );
    }

    @Override
    @Transactional
    public String deleteUniversityById(Long universityId) {
        if (universityId == null || universityInfoRepository.findById(universityId).isEmpty()) {
            throw new NotFoundException("Университета с id = " + universityId + " не существует.");
        }
        universityInfoRepository.deleteById(universityId);
        universityGeographicRepository.deleteByUniversityInfoId(universityId);
        universitySocialsRepository.deleteByUniversityInfoId(universityId);
        return "Success";
    }
}
