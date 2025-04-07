package com.ludoed.mapper;

import com.ludoed.dto.StudentShortDto;
import com.ludoed.model.Student;
import com.ludoed.model.StudentSocial;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentMapper {
    public Student toStudent(StudentShortDto studentShortDto, List<StudentSocial> social) {
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
                studentShortDto.getAvatar(),
                social
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
}
