package com.ludoed.dto;

import com.ludoed.model.StudentLearn;
import com.ludoed.model.StudentSocial;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class StudentFullDto {
    private Long studentId;

    private String email;

    private String aboutMe;

    private byte[] resumeFile;

    private String firstName;

    private String surname;

    private String lastName;

    private LocalDate birthDate;

    private StudentLearn learnInfo;

    private byte[] avatar;

    private List<StudentSocial> socials;
}
