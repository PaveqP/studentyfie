package com.ludoed.university.service;

import com.ludoed.university.dto.UniversityFullDto;
import com.ludoed.university.dto.UniversityFullDtoInput;
import com.ludoed.university.dto.UniversityFullDtoOutput;

import java.util.List;

public interface UniversityService {
    public UniversityFullDtoOutput createUniversity(UniversityFullDtoInput university);

    public UniversityFullDtoOutput updateUniversity(Long universityId, UniversityFullDtoInput university);

    public UniversityFullDtoOutput getUniversityById(Long universityId);

    public List<UniversityFullDtoOutput> getAllUniversities(int from, int size);

    public String deleteUniversityById(Long universityId);
}
