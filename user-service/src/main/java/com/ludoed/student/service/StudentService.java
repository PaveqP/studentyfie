package com.ludoed.student.service;

import com.ludoed.student.dto.StudentFullDto;
import com.ludoed.student.model.Student;
import com.ludoed.student.model.StudentSocial;

import java.util.List;

public interface StudentService {
    StudentFullDto getStudentById(Long studentId);

    List<StudentFullDto> getAllStudents(int from, int size);

    StudentFullDto createStudent(StudentFullDto student);

    StudentFullDto updateStudent(Long studentId, StudentFullDto student);

    String deleteStudent(Long studentId);
}
