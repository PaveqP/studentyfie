package com.ludoed.service;

import com.ludoed.model.Student;
import com.ludoed.model.StudentSocial;

import java.util.List;

public interface StudentService {
    Student getStudentById(Long studentId);

    List<Student> getAllStudents(int from, int size);

    Student createStudent(Student student);

    Student updateStudent(Long studentId, Student student);

    String deleteStudent(Long studentId);
}
