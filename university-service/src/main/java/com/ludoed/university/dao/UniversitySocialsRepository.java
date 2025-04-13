package com.ludoed.university.dao;

import com.ludoed.university.model.UniversitySocials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UniversitySocialsRepository extends JpaRepository<UniversitySocials, Long> {
    void deleteByUniversityInfoId(Long universityId);

    List<UniversitySocials> findByUniversityInfoId(Long universityId);

    List<UniversitySocials> findByUniversityInfoIdIn(List<Long> universityIds);
}
