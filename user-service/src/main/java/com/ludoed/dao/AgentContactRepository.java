package com.ludoed.dao;

import com.ludoed.model.AgentContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentContactRepository extends JpaRepository<AgentContact, Long> {
}
