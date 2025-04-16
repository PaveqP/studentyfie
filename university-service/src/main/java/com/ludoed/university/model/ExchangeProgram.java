package com.ludoed.university.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "exchange_programs")
public class ExchangeProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exchange_programs_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "rating")
    private Float rating;

    @Column(name = "agent_email")
    private String agentEmail;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "program_conditions_id")
    private ProgramCondition programCondition;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "uni_info_id")
    private UniversityInfo universityInfo;
}
