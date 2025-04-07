package com.ludoed.service;

import com.ludoed.model.Agent;

import java.util.List;

public interface AgentService {
    Agent getAgentById(Long agentId);

    List<Agent> getAllAgents(int from, int size);

    Agent createAgent(Agent agent);

    Agent updateAgent(Long agentId, Agent agent);

    String deleteAgent(Long agentId);
}
