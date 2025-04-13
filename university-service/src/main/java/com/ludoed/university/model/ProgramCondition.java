package com.ludoed.university.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "program_conditions")
public class ProgramCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "program_conditions_id")
    private Long id;

    @Column(name = "min_course")
    private Integer minCourse;

    @Column(name = "min_rate")
    private Float minRate;
}
