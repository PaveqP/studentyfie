package com.ludoed.agent.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "agentContacts")
public class AgentContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long agentContactsId;

    @Column(name = "name")
    private String name;

    @Column(name = "link")
    private String link;

    @ManyToOne
    @JoinColumn(name = "agent_id")
    private Agent agent;
}
