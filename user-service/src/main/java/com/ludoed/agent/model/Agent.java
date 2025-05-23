package com.ludoed.agent.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "agent")
public class Agent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agent_id")
    private Long id;

    @Column(name = "avatar")
    private byte[] avatar;

    @NotNull
    @Email
    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Column(name = "surname")
    private String surname;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "university_id")
    private Long university; //TODO
}
