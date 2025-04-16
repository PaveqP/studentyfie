package com.ludoed.university.dto;

import com.ludoed.university.model.ProgramCondition;
import com.ludoed.university.model.UniversityInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ExchangeProgramDtoInput {
    private Long id;

    private String name;

    private String description;

    private Float rating;

    private String agentEmail;

    private ProgramCondition programCondition;

    private UniversityInfo universityInfo;
}
