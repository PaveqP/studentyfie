package com.ludoed.university.dao;

import com.ludoed.university.model.ProgramCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramConditionRepository extends JpaRepository<ProgramCondition, Long> {
}
