package com.ludoed.student.mapper;

import com.ludoed.student.dto.StudentFullDto;
import com.ludoed.student.dto.StudentShortDto;
import com.ludoed.student.dto.StudentSocialDto;
import com.ludoed.student.model.Student;
import com.ludoed.student.model.StudentSocial;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class StudentMapper {
    public Student toStudent(StudentFullDto studentFullDto) {
        Student student = new Student();
        student.setEmail(studentFullDto.getEmail());
        student.setAboutMe(studentFullDto.getAboutMe());
        student.setResumeFile(studentFullDto.getResumeFile());
        student.setFirstName(studentFullDto.getFirstName());
        student.setSurname(studentFullDto.getSurname());
        student.setLastName(studentFullDto.getLastName());
        student.setBirthDate(studentFullDto.getBirthDate());
        student.setLearnInfo(studentFullDto.getLearnInfo());
        student.setAvatar(studentFullDto.getAvatar());
        return student;
    }
    public StudentFullDto toStudentFullDto(Student student, List<StudentSocial> social) {
        return new StudentFullDto(
                student.getId(),
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
