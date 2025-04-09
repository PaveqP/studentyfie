package com.ludoed.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class StudentSocialDto {

    private Long socialsId;

    private String name;

    private String link;
}
