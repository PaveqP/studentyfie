package com.ludoed.university.dto;

import com.ludoed.university.model.UniversityGeographic;
import com.ludoed.university.model.UniversitySocials;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class UniversityFullDto {

    private Long id;

    private String name;

    private String description;

    private byte[] avatar;

    private Float rating;

    private UniversityGeographic geographic;

    private List<UniversitySocials> socials;
}
