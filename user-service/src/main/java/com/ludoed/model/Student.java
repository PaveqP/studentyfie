package com.ludoed.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    @NotNull
    @Email
    @Column(name = "email")
    private String email;

    @Column(name = "about_me")
    private String aboutMe;

    @Lob
    @Column(name = "resume_file")
    private byte[] resumeFile;

    @NotNull
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Column(name = "surname")
    private String surname;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @NotNull
    @OneToOne
    @JoinColumn(name = "student_learn_id")
    private StudentLearns learnInfo;

    @Lob
    @Column(name = "avatar")
    private byte[] avatar;

    @ManyToOne
    @JoinColumn(name = "socials_id")
    private List<StudentSocials> socials;
}
