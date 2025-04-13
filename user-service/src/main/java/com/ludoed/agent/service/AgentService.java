package com.ludoed.agent.service;

import com.ludoed.agent.dto.AgentFullDto;

import java.util.List;

public interface AgentService {
    AgentFullDto getAgentById(Long agentId);

    List<AgentFullDto> getAllAgents(int from, int size);

    AgentFullDto createAgent(AgentFullDto agent);

    AgentFullDto updateAgent(Long agentId, AgentFullDto agent);

    String deleteAgent(Long agentId);
}
