package com.ludoed.university.controller;

import com.ludoed.university.dto.UniversityFullDto;
import com.ludoed.university.dto.UniversityFullDtoInput;
import com.ludoed.university.dto.UniversityFullDtoOutput;
import com.ludoed.university.service.UniversityService;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/university")
public class UniversityController {

    private final UniversityService universityService;

    @GetMapping("/{universityId}")
    public UniversityFullDtoOutput getUniversityById(@PathVariable Long universityId) {
        return universityService.getUniversityById(universityId);
    }

    @GetMapping
    public List<UniversityFullDtoOutput> getAllUniversities(@RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                                      @RequestParam(defaultValue = "10") @Positive int size) {
        return universityService.getAllUniversities(from, size);
    }

    @PostMapping
    public UniversityFullDtoOutput createUniversity(@RequestBody UniversityFullDtoInput university) {
        return universityService.createUniversity(university);
    }

    @PatchMapping("/{universityId}")
    public UniversityFullDtoOutput updateUniversity(@PathVariable Long universityId, @RequestBody UniversityFullDtoInput university) {
        return universityService.updateUniversity(universityId, university);
    }

    @DeleteMapping("/{universityId}")
    public String deleteUniversity(@PathVariable Long universityId) {
        return universityService.deleteUniversityById(universityId);
    }
}