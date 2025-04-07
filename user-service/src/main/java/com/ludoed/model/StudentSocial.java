package com.ludoed.model;

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
@Table(name = "studentSocials")
public class StudentSocial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long socialsId;

    @Column(name = "name")
    private String name;

    @Column(name = "link")
    private String link;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Long studentId;
}
