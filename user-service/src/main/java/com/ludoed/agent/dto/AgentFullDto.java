package com.ludoed.agent.dto;

import com.ludoed.agent.model.AgentContact;
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

    private Long university;

    private List<AgentContact> contacts;
}
