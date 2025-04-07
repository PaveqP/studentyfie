package com.ludoed.controller;

import com.ludoed.dto.StudentFullDto;
import com.ludoed.model.Student;
import com.ludoed.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public StudentFullDto getStudentById(@RequestParam Long studentId) {
        return studentService.getStudentById(studentId);
    }

    @PostMapping
    public StudentFullDto createStudent(@RequestBody StudentFullDto student) {
        return studentService.createStudent(student);
    }
}
