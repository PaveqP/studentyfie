package com.ludoed.service;

import com.ludoed.dao.AgentContactRepository;
import com.ludoed.dao.AgentRepository;
import com.ludoed.dto.AgentFullDto;
import com.ludoed.exception.DuplicatedDataException;
import com.ludoed.exception.NotFoundException;
import com.ludoed.mapper.AgentMapper;
import com.ludoed.model.Agent;
import com.ludoed.model.AgentContact;
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

    private final AgentContactRepository agentContactRepository;

    private final AgentMapper agentMapper;

    @Override
    public AgentFullDto getAgentById(Long agentId) {
        Agent agent = agentRepository.findById(agentId)
                .orElseThrow(() -> new NotFoundException("Агента с id = {} не существует." + agentId));
        List<AgentContact> contacts = agentContactRepository.findByAgentId(agentId);
        return agentMapper.toAgentFullDto(agent, contacts);
    }

    @Override
    public List<AgentFullDto> getAllAgents(int from, int size) {
        PageRequest pageRequest = PageRequest.of(from / size, size, Sort.by(Sort.Direction.ASC, "id"));
        List<Agent> agentList = agentRepository.findAll(pageRequest).toList();
        if (agentList.isEmpty()) {
            return new ArrayList<>();
        }
        return agentList.stream().toList();
    }

    @Override
    public AgentFullDto createAgent(AgentFullDto agentDto) {
        if (agentRepository.findAll().contains(agentDto)) {
            throw new DuplicatedDataException("Этот агент уже существует.");
        }
        return agentRepository.save(agentDto);
    }

    @Override
    public AgentFullDto updateAgent(Long agentId, AgentFullDto agentDto) {
        Agent updatingAgent = agentRepository.findById(agentId)
                .orElseThrow(() -> new NotFoundException("Агента с id = {} не существует." + agentId));
        Optional.ofNullable(agentDto.getAvatar()).ifPresent(updatingAgent::setAvatar);
        Optional.ofNullable(agentDto.getEmail()).ifPresent(updatingAgent::setEmail);
        Optional.ofNullable(agentDto.getFirstName()).ifPresent(updatingAgent::setFirstName);
        Optional.ofNullable(agentDto.getSurname()).ifPresent(updatingAgent::setSurname);
        Optional.ofNullable(agentDto.getLastName()).ifPresent(updatingAgent::setLastName);
        Optional.ofNullable(agentDto.getUniversity()).ifPresent(updatingAgent::setUniversity); //TODO

        return updatingAgent;
    }

    @Override
    public String deleteAgent(Long agentId) {
        return null;
    }
}
