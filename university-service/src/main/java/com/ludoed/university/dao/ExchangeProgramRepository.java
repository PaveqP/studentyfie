package com.ludoed.university.dao;

import com.ludoed.university.model.ExchangeProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExchangeProgramRepository extends JpaRepository<ExchangeProgram, Long> {
    List<ExchangeProgram> findByUniversityInfoId(Long id);

    List<ExchangeProgram> findByUniversityInfoIdIn(List<Long> universityIds);

    void deleteByUniversityInfoId(Long universityId);
}
