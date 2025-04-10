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
        List<AgentContact> contacts = new ArrayList<>();
        for (Agent agent : agentList) {
            contacts = agentContactRepository.findByAgentId(agent.getId());
        }
        return agentMapper.toAgentFullDtoList(agentList, contacts);
    }

    @Override
    public AgentFullDto createAgent(AgentFullDto agentDto) {
        if (agentRepository.findAll().contains(agentDto)) {
            throw new DuplicatedDataException("Этот агент уже существует.");
        }

        Agent savedAgent = agentRepository.save(agentMapper.toAgent(agentDto));

        if (agentDto.getContacts() != null && !agentDto.getContacts().isEmpty()) {
            List<AgentContact> contacts = agentDto.getContacts().stream()
                    .peek(s -> s.setAgent(savedAgent))
                    .toList();
            agentContactRepository.saveAll(contacts);
        }

        return agentMapper.toAgentFullDto(savedAgent, agentDto.getContacts());
        //return agentRepository.save(agentDto);
    }

    @Override
    public AgentFullDto updateAgent(Long agentId, AgentFullDto agentDto) {
        Agent agent = agentRepository.findById(agentId)
                .orElseThrow(() -> new NotFoundException("Агента с id = {} не существует." + agentId));
        Optional.ofNullable(agentDto.getAvatar()).ifPresent(agent::setAvatar);
        Optional.ofNullable(agentDto.getEmail()).ifPresent(agent::setEmail);
        Optional.ofNullable(agentDto.getFirstName()).ifPresent(agent::setFirstName);
        Optional.ofNullable(agentDto.getSurname()).ifPresent(agent::setSurname);
        Optional.ofNullable(agentDto.getLastName()).ifPresent(agent::setLastName);
        Optional.ofNullable(agentDto.getUniversity()).ifPresent(agent::setUniversity); //TODO
        Agent updatedAgent = agentRepository.save(agent);
        List<AgentContact> updatedContacts = new ArrayList<>();
        if (agentDto.getContacts() != null) {
            agentContactRepository.deleteByAgentId(agentId);

            updatedContacts = agentDto.getContacts().stream()
                    .peek(s -> s.setAgent(agent))
                    .toList();
            agentContactRepository.saveAll(updatedContacts);
        }
        return agentMapper.toAgentFullDto(updatedAgent, updatedContacts);
    }

    @Override
    public String deleteAgent(Long agentId) {
        if (agentId == null || agentRepository.findById(agentId).isEmpty()) {
            throw new NotFoundException("Агента с id = {} не существует." + agentId);
        }
        agentRepository.deleteById(agentId);
        agentContactRepository.deleteByAgentId(agentId);
        return "Success";
    }
}
