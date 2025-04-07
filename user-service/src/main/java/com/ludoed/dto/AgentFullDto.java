package com.ludoed.dto;

import com.ludoed.model.AgentContact;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
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
