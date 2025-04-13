package com.ludoed.student.dto;

import com.ludoed.student.model.StudentLearn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentShortDto {

    private String email;

    private String aboutMe;

    private byte[] resumeFile;

    private String firstName;

    private String surname;

    private String lastName;

    private LocalDate birthDate;

    private StudentLearn learnInfo;

    private byte[] avatar;
}
