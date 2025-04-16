package com.ludoed.agent.model;

import com.ludoed.university.model.AgentContact;
import com.ludoed.university.model.UniversityInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class AgentFullDto {

    private Long agentId;

    private byte[] avatar;

    private String email;

    private String firstName;

    private String surname;

    private String lastName;

    private UniversityInfo university;

    private List<AgentContact> contacts;
}
