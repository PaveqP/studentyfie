package com.ludoed.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "studentLearns")
public class StudentLearn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentLearnId;

    @Column(name = "university")
    private String university;

    @Column(name = "course")
    private String course;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "program_id")
    private String program;

    @Column(name = "rating")
    private Float rating;

    @Column(name = "exchange_programs_id")
    private Long exchangeProgramsId; //TODO
}
