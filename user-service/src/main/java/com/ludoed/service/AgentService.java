package com.ludoed.service;

import com.ludoed.dto.AgentFullDto;
import com.ludoed.model.Agent;

import java.util.List;

public interface AgentService {
    AgentFullDto getAgentById(Long agentId);

    List<AgentFullDto> getAllAgents(int from, int size);

    AgentFullDto createAgent(AgentFullDto agent);

    AgentFullDto updateAgent(Long agentId, AgentFullDto agent);

    String deleteAgent(Long agentId);
}
