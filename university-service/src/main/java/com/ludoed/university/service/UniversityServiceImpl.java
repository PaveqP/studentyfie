package com.ludoed.university.service;

import com.ludoed.exception.DuplicatedDataException;
import com.ludoed.exception.NotFoundException;
import com.ludoed.university.dao.UniversityGeographicRepository;
import com.ludoed.university.dao.UniversityInfoRepository;
import com.ludoed.university.dao.UniversitySocialsRepository;
import com.ludoed.university.dto.UniversityFullDto;
import com.ludoed.university.mapper.UniversityMapper;
import com.ludoed.university.model.UniversityGeographic;
import com.ludoed.university.model.UniversityInfo;
import com.ludoed.university.model.UniversitySocials;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    private final UniversityMapper universityMapper;

    @Override
    public UniversityFullDto createUniversity(UniversityFullDto universityDto) {
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
            universityGeographicRepository.save(universityDto.getGeographic());
        }

        return universityMapper.toUniversityFullDto(savedUniversity, universityDto.getGeographic(), universityDto.getSocials());
    }

    @Override
    @Transactional
    public UniversityFullDto updateUniversity(Long universityId, UniversityFullDto universityDto) {
        UniversityInfo university = universityInfoRepository.findById(universityId)
                .orElseThrow(() -> new NotFoundException("Университета с id = {} не существует." + universityId));
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

        if (universityDto.getGeographic() != null) {
            universityGeographicRepository.deleteByUniversityInfoId(universityId);
            universityGeographicRepository.save(universityDto.getGeographic());
        }

        return universityMapper.toUniversityFullDto(updatedUniversity, universityDto.getGeographic(), updatedSocials);
    }

    @Override
    public UniversityFullDto getUniversityById(Long universityId) {
        UniversityInfo university = universityInfoRepository.findById(universityId)
                .orElseThrow(() -> new NotFoundException("Университета с id = {} не существует." + universityId));
        UniversityGeographic geographic = universityGeographicRepository.findByUniversityInfoId(universityId);
        List<UniversitySocials> socials = universitySocialsRepository.findByUniversityInfoId(universityId);
        return universityMapper.toUniversityFullDto(university, geographic, socials);
    }

    @Override
    public List<UniversityFullDto> getAllUniversities(int from, int size) {
        PageRequest pageRequest = PageRequest.of(from / size, size, Sort.by(Sort.Direction.ASC, "id"));
        List<UniversityInfo> universityList = universityInfoRepository.findAll(pageRequest).toList();
        if (universityList.isEmpty()) {
            return new ArrayList<>();
        }

        List<Long> universityIds = universityList.stream()
                .map(UniversityInfo::getId)
                .collect(Collectors.toList());

        List<UniversitySocials> AllSocials = universitySocialsRepository.findByUniversityInfoIdIn(universityIds);

        List<UniversityGeographic> AllGeographic = universityGeographicRepository.findByUniversityInfoIdIn(universityIds);

        return universityMapper.toUniversityFullDtoList(universityList, AllGeographic, AllSocials);
    }

    @Override
    @Transactional
    public String deleteUniversityById(Long universityId) {
        if (universityId == null || universityInfoRepository.findById(universityId).isEmpty()) {
            throw new NotFoundException("Университета с id = {} не существует." + universityId);
        }
        universityInfoRepository.deleteById(universityId);
        universityGeographicRepository.deleteByUniversityInfoId(universityId);
        universitySocialsRepository.deleteByUniversityInfoId(universityId);
        return "Success";
    }
}
