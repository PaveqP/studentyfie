package com.ludoed.mapper;

import com.ludoed.dto.StudentFullDto;
import com.ludoed.dto.StudentShortDto;
import com.ludoed.model.Student;
import com.ludoed.model.StudentSocial;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class StudentMapper {
    public Student toStudent(StudentFullDto studentShortDto) {
        return new Student(
                null,
                studentShortDto.getEmail(),
                studentShortDto.getAboutMe(),
                studentShortDto.getResumeFile(),
                studentShortDto.getFirstName(),
                studentShortDto.getSurname(),
                studentShortDto.getLastName(),
                studentShortDto.getBirthDate(),
                studentShortDto.getLearnInfo(),
                studentShortDto.getAvatar()
        );
    }

    public StudentShortDto toStudentShortDto(Student student) {
        return new StudentShortDto(
                student.getEmail(),
                student.getAboutMe(),
                student.getResumeFile(),
                student.getFirstName(),
                student.getSurname(),
                student.getLastName(),
                student.getBirthDate(),
                student.getLearnInfo(),
                student.getAvatar()
        );
    }

    public StudentFullDto toStudentFullDto(Student student, List<StudentSocial> social) {
        return new StudentFullDto(
                null,
                student.getEmail(),
                student.getAboutMe(),
                student.getResumeFile(),
                student.getFirstName(),
                student.getSurname(),
                student.getLastName(),
                student.getBirthDate(),
                student.getLearnInfo(),
                student.getAvatar(),
                social
        );
    }

    public List<StudentFullDto> toStudentFullDtoList(List<Student> students, List<StudentSocial> socials) {
        Map<Long, List<StudentSocial>> socialsByStudentId = socials.stream()
                .collect(Collectors.groupingBy(social -> social.getStudent().getId()));

        return students.stream()
                .map(student -> {
                    List<StudentSocial> studentSocials = socialsByStudentId.getOrDefault(
                            student.getId(), Collections.emptyList());
                    return toStudentFullDto(student, studentSocials);
                })
                .collect(Collectors.toList());
    }
}
