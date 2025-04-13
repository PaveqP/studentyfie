package com.ludoed.student.dao;

import com.ludoed.student.model.StudentLearn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentsLearnRepository extends JpaRepository<StudentLearn, Long> {
}
