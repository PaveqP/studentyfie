package com.ludoed.service;

import com.ludoed.dao.StudentRepository;
import com.ludoed.dao.StudentSocialRepository;
import com.ludoed.dao.StudentsLearnRepository;
import com.ludoed.dto.StudentFullDto;
import com.ludoed.exception.DuplicatedDataException;
import com.ludoed.exception.NotFoundException;
import com.ludoed.mapper.StudentMapper;
import com.ludoed.model.Student;
import com.ludoed.model.StudentSocial;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    private final StudentMapper studentMapper;

    private final StudentSocialRepository studentSocialRepository;

    private final StudentsLearnRepository studentsLearnRepository;

    @Override
    public StudentFullDto getStudentById(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException("Студента с id = {} не существует." + studentId));
        List<StudentSocial> social = studentSocialRepository.findByStudentId(studentId);
        return studentMapper.toStudentFullDto(student, social);
    }

    @Override
    public List<StudentFullDto> getAllStudents(int from, int size) {
        PageRequest pageRequest = PageRequest.of(from / size, size, Sort.by(Sort.Direction.ASC, "id"));
        List<Student> studentList = studentRepository.findAll(pageRequest).toList();
        if (studentList.isEmpty()) {
            return new ArrayList<>();
        }
        List<StudentSocial> socials = new ArrayList<>();
        List<StudentFullDto> students = studentMapper.toStudentFullDto()
        for (Student student : studentList) {
            List<StudentSocial> socialsList = studentSocialRepository.findByStudentId(student.getStudentId());
            student.setSocials(socialsList);
        }
        return studentList.stream().toList();
    }

    @Override
    public StudentFullDto createStudent(StudentFullDto student) {
        if (studentRepository.findAll().contains(student)) {
            throw new DuplicatedDataException("Этот студент уже существует.");
        }
        studentRepository.save(student);
        studentSocialRepository.save(student.getSocials());
        studentsLearnRepository.save(student.getLearnInfo());
        return student;
    }

    @Override
    public StudentFullDto updateStudent(Long studentId, StudentFullDto student) {
        Student updatingStudent = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException("Студента с id = {} не существует." + studentId));
        Optional.ofNullable(student.getEmail()).ifPresent(updatingStudent::setEmail);
        Optional.ofNullable(student.getAboutMe()).ifPresent(updatingStudent::setAboutMe);
        Optional.ofNullable(student.getResumeFile()).ifPresent(updatingStudent::setResumeFile);
        Optional.ofNullable(student.getFirstName()).ifPresent(updatingStudent::setFirstName);
        Optional.ofNullable(student.getSurname()).ifPresent(updatingStudent::setSurname);
        Optional.ofNullable(student.getLastName()).ifPresent(updatingStudent::setLastName);
        Optional.ofNullable(student.getBirthDate()).ifPresent(updatingStudent::setBirthDate);
        Optional.ofNullable(student.getAvatar()).ifPresent(updatingStudent::setAvatar);
        List<StudentSocial> studentSocials = new ArrayList<>(student.getSocials().stream().toList());
        studentRepository.save(updatingStudent);
        if (!studentSocials.isEmpty()) {
            studentSocialRepository.save(studentSocials);
            updatingStudent.setSocials(studentSocials);
        }
        return updatingStudent;
    }

    @Override
    public String deleteStudent(Long studentId) {
        if (studentId == null || studentRepository.findById(studentId).isEmpty()) {
            throw new NotFoundException("Студента с id = {} не существует." + studentId);
        }
        studentRepository.deleteById(studentId);
        studentSocialRepository.deleteByStudentId(studentId);
        return "Success";
    }
}
