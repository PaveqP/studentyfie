package com.ludoed.university.dao;

import com.ludoed.university.model.UniversityGeographic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UniversityGeographicRepository extends JpaRepository<UniversityGeographic, Long> {
    void deleteByUniversityInfoId(Long universityId);

    UniversityGeographic findByUniversityInfoId(Long universityId);

    List<UniversityGeographic> findByUniversityInfoIdIn(List<Long> universityIds);
}
