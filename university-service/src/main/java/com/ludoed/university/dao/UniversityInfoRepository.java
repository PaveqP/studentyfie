package com.ludoed.university.dao;

import com.ludoed.university.model.UniversityInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UniversityInfoRepository extends JpaRepository<UniversityInfo, Long> {
    Optional<Object> findByName(String name);
}
