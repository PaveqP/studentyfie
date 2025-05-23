package com.ludoed.agent.dao;

import com.ludoed.agent.model.AgentContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgentContactRepository extends JpaRepository<AgentContact, Long> {
    List<AgentContact> findByAgentId(Long agentId);

    void deleteByAgentId(Long agentId);
}
