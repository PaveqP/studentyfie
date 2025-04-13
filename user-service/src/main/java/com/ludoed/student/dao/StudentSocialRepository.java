package com.ludoed.student.dao;

import com.ludoed.student.model.StudentSocial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentSocialRepository extends JpaRepository<StudentSocial,Long> {
    List<StudentSocial> findByStudentId(Long studentId);

    void deleteByStudentId(Long studentId);

    List<StudentSocial> findByStudentIdIn(List<Long> studentIds);
}
