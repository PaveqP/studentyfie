package com.ludoed.service;

import com.ludoed.dao.AgentRepository;
import com.ludoed.exception.DuplicatedDataException;
import com.ludoed.exception.NotFoundException;
import com.ludoed.model.Agent;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AgentServiceImpl implements AgentService {

    private final AgentRepository agentRepository;

    @Override
    public Agent getAgentById(Long agentId) {
        return agentRepository.findById(agentId)
                .orElseThrow(() -> new NotFoundException("Агента с id = {} не существует." + agentId));
    }

    @Override
    public List<Agent> getAllAgents(int from, int size) {
        PageRequest pageRequest = PageRequest.of(from / size, size, Sort.by(Sort.Direction.ASC, "id"));
        List<Agent> agentList = agentRepository.findAll(pageRequest).toList();
        if (agentList.isEmpty()) {
            return new ArrayList<>();
        }
        return agentList.stream().toList();
    }

    @Override
    public Agent createAgent(Agent agent) {
        if (agentRepository.findAll().contains(agent)) {
            throw new DuplicatedDataException("Этот агент уже существует.");
        }
        return agentRepository.save(agent);
    }

    @Override
    public Agent updateAgent(Long agentId, Agent agent) {
        Agent updatingAgent = agentRepository.findById(agentId)
                .orElseThrow(() -> new NotFoundException("Агента с id = {} не существует." + agentId));
        Optional.ofNullable(agent.getAvatar()).ifPresent(updatingAgent::setAvatar);
        Optional.ofNullable(agent.getEmail()).ifPresent(updatingAgent::setEmail);
        Optional.ofNullable(agent.getFirstName()).ifPresent(updatingAgent::setFirstName);
        Optional.ofNullable(agent.getSurname()).ifPresent(updatingAgent::setSurname);
        Optional.ofNullable(agent.getLastName()).ifPresent(updatingAgent::setLastName);
        Optional.ofNullable(agent.getUniversity()).ifPresent(updatingAgent::setUniversity); //TODO

        return updatingAgent;
    }

    @Override
    public String deleteAgent(Long agentId) {
        return null;
    }
}
