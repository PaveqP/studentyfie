package com.ludoed.student.controller;

import com.ludoed.student.dto.StudentFullDto;
import com.ludoed.student.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@Tag(name = "Контроллер студентов")
@RequestMapping("/users/students")
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/{studentId}")
    @Operation(summary = "Получение студента", description = "Получение студента по id")
    public StudentFullDto getStudentById(@PathVariable Long studentId) {
        return studentService.getStudentById(studentId);
    }

    @GetMapping
    @Operation(summary = "Получение всех студентов")
    public List<StudentFullDto> getAllStudents(@RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                               @RequestParam(defaultValue = "10") @Positive int size) {
        return studentService.getAllStudents(from, size);
    }

    @PostMapping
    @Operation(summary = "Создание студента")
    public StudentFullDto createStudent(@RequestBody StudentFullDto student) {
        return studentService.createStudent(student);
    }

    @PatchMapping("/{studentId}")
    @Operation(summary = "Изменение студента")
    public StudentFullDto updateStudent(@PathVariable Long studentId, @RequestBody StudentFullDto student) {
        return studentService.updateStudent(studentId, student);
    }

    @DeleteMapping("/{studentId}")
    @Operation(summary = "Удаление студента")
    public String deleteStudent(@PathVariable Long studentId) {
        return studentService.deleteStudent(studentId);
    }
}
