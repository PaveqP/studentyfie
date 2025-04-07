package com.ludoed.service;

import com.ludoed.dto.StudentFullDto;
import com.ludoed.model.Student;
import com.ludoed.model.StudentSocial;

import java.util.List;

public interface StudentService {
    StudentFullDto getStudentById(Long studentId);

    List<StudentFullDto> getAllStudents(int from, int size);

    StudentFullDto createStudent(StudentFullDto student);

    StudentFullDto updateStudent(Long studentId, StudentFullDto student);

    String deleteStudent(Long studentId);
}
