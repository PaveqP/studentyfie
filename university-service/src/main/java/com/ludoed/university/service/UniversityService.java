package com.ludoed.university.service;

import com.ludoed.university.dto.UniversityFullDto;

import java.util.List;

public interface UniversityService {
    public UniversityFullDto createUniversity(UniversityFullDto university);

    public UniversityFullDto updateUniversity(Long universityId, UniversityFullDto university);

    public UniversityFullDto getUniversityById(Long universityId);

    public List<UniversityFullDto> getAllUniversities(int from, int size);

    public String deleteUniversityById(Long universityId);
}
