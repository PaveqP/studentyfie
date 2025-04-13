package com.ludoed.student.service;

import com.ludoed.student.dao.StudentRepository;
import com.ludoed.student.dao.StudentSocialRepository;
import com.ludoed.student.dao.StudentsLearnRepository;
import com.ludoed.student.dto.StudentFullDto;
import com.ludoed.exception.DuplicatedDataException;
import com.ludoed.exception.NotFoundException;
import com.ludoed.student.mapper.StudentMapper;
import com.ludoed.student.model.Student;
import com.ludoed.student.model.StudentSocial;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        List<Long> studentIds = studentList.stream()
                .map(Student::getId)
                .collect(Collectors.toList());

        List<StudentSocial> allSocials = studentSocialRepository.findByStudentIdIn(studentIds);

        return studentMapper.toStudentFullDtoList(studentList, allSocials);
    }

    @Override
    public StudentFullDto createStudent(StudentFullDto studentDto) {
        if (studentRepository.findByEmail(studentDto.getEmail()).isPresent()) {
            throw new DuplicatedDataException("Этот студент уже существует.");
        }

        Student savedStudent = studentRepository.save(studentMapper.toStudent(studentDto));

        if (studentDto.getSocials() != null && !studentDto.getSocials().isEmpty()) {
            List<StudentSocial> socials = studentDto.getSocials().stream()
                    .peek(s -> s.setStudent(savedStudent))
                    .toList();
            studentSocialRepository.saveAll(socials);
        }

        if (studentDto.getLearnInfo() != null) {
            studentsLearnRepository.save(studentDto.getLearnInfo());
        }

        return studentMapper.toStudentFullDto(savedStudent, studentDto.getSocials());
    }

    @Transactional
    @Override
    public StudentFullDto updateStudent(Long studentId, StudentFullDto studentDto) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException("Студента с id = {} не существует." + studentId));
        Optional.ofNullable(studentDto.getEmail()).ifPresent(student::setEmail);
        Optional.ofNullable(studentDto.getAboutMe()).ifPresent(student::setAboutMe);
        Optional.ofNullable(studentDto.getResumeFile()).ifPresent(student::setResumeFile);
        Optional.ofNullable(studentDto.getFirstName()).ifPresent(student::setFirstName);
        Optional.ofNullable(studentDto.getSurname()).ifPresent(student::setSurname);
        Optional.ofNullable(studentDto.getLastName()).ifPresent(student::setLastName);
        Optional.ofNullable(studentDto.getBirthDate()).ifPresent(student::setBirthDate);
        Optional.ofNullable(studentDto.getAvatar()).ifPresent(student::setAvatar);
        Student updatedStudent = studentRepository.save(student);
        List<StudentSocial> updatedSocials = new ArrayList<>();
        if (studentDto.getSocials() != null) {
            studentSocialRepository.deleteByStudentId(studentId);

            updatedSocials = studentDto.getSocials().stream()
                    .peek(s -> s.setStudent(student))
                    .toList();
            studentSocialRepository.saveAll(updatedSocials);
        }
        return studentMapper.toStudentFullDto(updatedStudent, updatedSocials);
    }

    @Transactional
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
