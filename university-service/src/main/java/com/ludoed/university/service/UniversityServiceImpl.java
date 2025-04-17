package com.ludoed.university.service;

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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
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
    @Transactional
    public UniversityFullDtoOutput createUniversity(UniversityFullDtoInput universityDto) {
        // Проверка на дубликат университета
        if (universityInfoRepository.findByName(universityDto.getName()).isPresent()) {
            throw new DuplicatedDataException("Этот университет уже существует");
        }

        // 1. Сохраняем основную информацию об университете
        UniversityInfo universityInfo = universityMapper.toUniversityInfo(universityDto);
        UniversityInfo savedUniversity = universityInfoRepository.save(universityInfo);

        // 2. Сохраняем географические данные
        UniversityGeographic savedGeographic = null;
        if (universityDto.getGeographic() != null) {
            UniversityGeographic geographic = universityDto.getGeographic();
            geographic.setUniversityInfo(savedUniversity);
            savedGeographic = universityGeographicRepository.save(geographic);
        }

        // 3. Сохраняем социальные сети
        List<UniversitySocials> savedSocials = new ArrayList<>();
        if (universityDto.getSocials() != null && !universityDto.getSocials().isEmpty()) {
            universityDto.getSocials().forEach(social -> social.setUniversityInfo(savedUniversity));
            savedSocials = universitySocialsRepository.saveAll(universityDto.getSocials());
        }

        // 4. Обрабатываем программы обмена
        List<ExchangeProgram> savedPrograms = new ArrayList<>();
        if (universityDto.getPrograms() != null && !universityDto.getPrograms().isEmpty()) {
            for (ExchangeProgramDtoInput programDto : universityDto.getPrograms()) {
                // Сохраняем условия программы
                ProgramCondition condition = programConditionRepository.save(programDto.getProgramCondition());

                // Работа с агентом
                AgentFullDto agentDto = kafkaAgentClient.requestAgentByEmail(programDto.getAgentEmail());
                Agent agent = agentRepository.save(agentMapper.toAgent(agentDto));

                // Сохраняем контакты агента
                if (agentDto.getContacts() != null && !agentDto.getContacts().isEmpty()) {
                    List<AgentContact> contacts = agentDto.getContacts().stream()
                            .peek(contactDto -> contactDto.setAgent(agent))
                            .toList();
                    contactRepository.saveAll(contacts);
                }

                // Создаем и сохраняем программу обмена
                ExchangeProgram program = new ExchangeProgram();
                program.setName(programDto.getName());
                program.setDescription(programDto.getDescription());
                program.setRating(programDto.getRating());
                program.setAgent(agent);
                program.setProgramCondition(condition);
                program.setUniversityInfo(savedUniversity);

                savedPrograms.add(exchangeProgramRepository.save(program));
            }
        }

        // 5. Формируем результат
        return universityMapper.toUniversityFullDtoOutput(
                savedUniversity,
                savedGeographic,
                savedSocials,
                savedPrograms.stream()
                        .map(program -> {
                            // Создаем DTO для программы
                            ExchangeProgramDtoOutput programDto = new ExchangeProgramDtoOutput();
                            programDto.setId(program.getId());
                            programDto.setName(program.getName());
                            programDto.setDescription(program.getDescription());
                            programDto.setRating(program.getRating());
                            programDto.setProgramCondition(program.getProgramCondition());

                            // Маппинг агента
                            Agent agent = program.getAgent();
                            AgentFullDto agentDto = new AgentFullDto();
                            agentDto.setAgentId(agent.getId());
                            agentDto.setEmail(agent.getEmail());
                            agentDto.setFirstName(agent.getFirstName());
                            agentDto.setSurname(agent.getSurname());
                            agentDto.setLastName(agent.getLastName());
                            agentDto.setAvatar(agent.getAvatar());
                            agentDto.setUniversity(agent.getUniversity());
                            programDto.setAgent(agentDto);

                            return programDto;
                        })
                        .toList()
        );
    }

    @Override
    @Transactional
    public UniversityFullDtoOutput updateUniversity(Long universityId, UniversityFullDtoInput universityDto) {
        UniversityInfo university = universityInfoRepository.findById(universityId)
                .orElseThrow(() -> new NotFoundException("Университета с id = " + universityId + " не существует."));

        // Обновляем основные данные университета
        Optional.ofNullable(universityDto.getName()).ifPresent(university::setName);
        Optional.ofNullable(universityDto.getDescription()).ifPresent(university::setDescription);
        Optional.ofNullable(universityDto.getAvatar()).ifPresent(university::setAvatar);
        Optional.ofNullable(universityDto.getRating()).ifPresent(university::setRating);
        UniversityInfo updatedUniversity = universityInfoRepository.save(university);

        // Обновляем социальные сети
        List<UniversitySocials> updatedSocials = updateUniversitySocials(universityId, university, universityDto.getSocials());

        // Обновляем географические данные
        UniversityGeographic updatedGeographic = updateUniversityGeographic(universityId, university, universityDto.getGeographic());

        // Обновляем программы обмена
        List<ExchangeProgram> updatedPrograms = updateExchangePrograms(universityId, university, universityDto.getPrograms());

        return universityMapper.toUniversityFullDtoOutput(
                updatedUniversity,
                updatedGeographic,
                updatedSocials,
                updatedPrograms.stream()
                        .map(this::mapToExchangeProgramDtoOutput)
                        .toList()
        );
    }

    private List<UniversitySocials> updateUniversitySocials(Long universityId, UniversityInfo university, List<UniversitySocials> socials) {
        universitySocialsRepository.deleteByUniversityInfoId(universityId);
        if (socials == null || socials.isEmpty()) {
            return Collections.emptyList();
        }
        socials.forEach(s -> s.setUniversityInfo(university));
        return universitySocialsRepository.saveAll(socials);
    }

    private UniversityGeographic updateUniversityGeographic(Long universityId, UniversityInfo university, UniversityGeographic geographic) {
        universityGeographicRepository.deleteByUniversityInfoId(universityId);
        if (geographic == null) {
            return null;
        }
        geographic.setUniversityInfo(university);
        return universityGeographicRepository.save(geographic);
    }

    private List<ExchangeProgram> updateExchangePrograms(Long universityId, UniversityInfo university, List<ExchangeProgramDtoInput> programDtos) {
        // Удаляем старые программы и их условия
        List<ExchangeProgram> existingPrograms = exchangeProgramRepository.findByUniversityInfoId(universityId);
        existingPrograms.forEach(program -> {
            if (program.getProgramCondition() != null) {
                programConditionRepository.deleteById(program.getProgramCondition().getId());
            }
        });
        exchangeProgramRepository.deleteByUniversityInfoId(universityId);

        if (programDtos == null || programDtos.isEmpty()) {
            return Collections.emptyList();
        }

        return programDtos.stream()
                .map(programDto -> {
                    // Сохраняем условия программы
                    ProgramCondition condition = programConditionRepository.save(programDto.getProgramCondition());

                    AgentFullDto agentDto = kafkaAgentClient.requestAgentByEmail(programDto.getAgentEmail());
                    // Получаем или создаем агента
                    Agent agent = agentMapper.toAgent(agentDto);

                    // Создаем и сохраняем программу
                    ExchangeProgram program = new ExchangeProgram();
                    program.setName(programDto.getName());
                    program.setDescription(programDto.getDescription());
                    program.setRating(programDto.getRating());
                    program.setAgent(agent);
                    program.setProgramCondition(condition);
                    program.setUniversityInfo(university);

                    return exchangeProgramRepository.save(program);
                })
                .toList();
    }

    private Agent getOrCreateAgent(String agentEmail) {
        return agentRepository.findByEmail(agentEmail)
                .orElseGet(() -> {
                    AgentFullDto agentDto = kafkaAgentClient.requestAgentByEmail(agentEmail);
                    Agent agent = agentMapper.toAgent(agentDto);
                    Agent savedAgent = agentRepository.save(agent);

                    // Сохраняем контакты агента
                    if (agentDto.getContacts() != null && !agentDto.getContacts().isEmpty()) {
                        List<AgentContact> contacts = agentDto.getContacts().stream()
                                .peek(contactDto -> contactDto.setAgent(savedAgent))
                                .toList();
                        contactRepository.saveAll(contacts);
                    }

                    return savedAgent;
                });
    }

    private ExchangeProgramDtoOutput mapToExchangeProgramDtoOutput(ExchangeProgram program) {
        ExchangeProgramDtoOutput dto = new ExchangeProgramDtoOutput();
        dto.setId(program.getId());
        dto.setName(program.getName());
        dto.setDescription(program.getDescription());
        dto.setRating(program.getRating());
        dto.setProgramCondition(program.getProgramCondition());

        Agent agent = program.getAgent();
        if (agent != null) {
            AgentFullDto agentDto = new AgentFullDto();
            agentDto.setAgentId(agent.getId());
            agentDto.setEmail(agent.getEmail());
            agentDto.setFirstName(agent.getFirstName());
            agentDto.setSurname(agent.getSurname());
            agentDto.setLastName(agent.getLastName());
            agentDto.setAvatar(agent.getAvatar());
            agentDto.setUniversity(agent.getUniversity());
            dto.setAgent(agentDto);
        }

        return dto;
    }

    @Override
    public UniversityFullDtoOutput getUniversityById(Long universityId) {
        UniversityInfo university = universityInfoRepository.findById(universityId)
                .orElseThrow(() -> new NotFoundException("Университета с id = " + universityId + " не существует."));

        UniversityGeographic geographic = universityGeographicRepository.findByUniversityInfoId(universityId);
        List<UniversitySocials> socials = universitySocialsRepository.findByUniversityInfoId(universityId);
        List<ExchangeProgram> programs = exchangeProgramRepository.findByUniversityInfoId(universityId);

        return universityMapper.toUniversityFullDtoOutput(
                university,
                geographic,
                socials,
                programs.stream()
                        .map(this::mapToExchangeProgramDtoOutput)
                        .toList()
        );
    }

    @Override
    public List<UniversityFullDtoOutput> getAllUniversities(int from, int size) {
        PageRequest pageRequest = PageRequest.of(from / size, size, Sort.by(Sort.Direction.ASC, "id"));
        List<UniversityInfo> universityList = universityInfoRepository.findAll(pageRequest).toList();

        if (universityList.isEmpty()) {
            return Collections.emptyList();
        }

        List<Long> universityIds = universityList.stream()
                .map(UniversityInfo::getId)
                .toList();

        Map<Long, UniversityGeographic> geographicMap = universityGeographicRepository.findByUniversityInfoIdIn(universityIds)
                .stream()
                .collect(Collectors.toMap(
                        geo -> geo.getUniversityInfo().getId(),
                        Function.identity()));

        Map<Long, List<UniversitySocials>> socialsMap = universitySocialsRepository.findByUniversityInfoIdIn(universityIds)
                .stream()
                .collect(Collectors.groupingBy(
                        social -> social.getUniversityInfo().getId()));

        Map<Long, List<ExchangeProgram>> programsMap = exchangeProgramRepository.findByUniversityInfoIdIn(universityIds)
                .stream()
                .collect(Collectors.groupingBy(
                        program -> program.getUniversityInfo().getId()));

        return universityList.stream()
                .map(university -> {
                    UniversityGeographic geographic = geographicMap.get(university.getId());
                    List<UniversitySocials> socials = socialsMap.getOrDefault(university.getId(), Collections.emptyList());
                    List<ExchangeProgram> programs = programsMap.getOrDefault(university.getId(), Collections.emptyList());

                    return universityMapper.toUniversityFullDtoOutput(
                            university,
                            geographic,
                            socials,
                            programs.stream()
                                    .map(this::mapToExchangeProgramDtoOutput)
                                    .toList()
                    );
                })
                .toList();
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
