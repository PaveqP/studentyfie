package com.ludoed.mapper;

import com.ludoed.dto.AgentFullDto;
import com.ludoed.model.Agent;
import com.ludoed.model.AgentContact;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AgentMapper {

    public AgentFullDto toAgentFullDto(Agent agent, List<AgentContact> contacts) {
        return new AgentFullDto(
                agent.getId(),
                agent.getAvatar(),
                agent.getEmail(),
                agent.getFirstName(),
                agent.getSurname(),
                agent.getLastName(),
                agent.getUniversity(),
                contacts
        );
    }

    public Agent toAgent(AgentFullDto agentFullDto) {
        return new Agent(
                agentFullDto.getAgentId(),
                agentFullDto.getAvatar(),
                agentFullDto.getEmail(),
                agentFullDto.getFirstName(),
                agentFullDto.getSurname(),
                agentFullDto.getLastName(),
                agentFullDto.getUniversity()
        );
    }

    public List<AgentFullDto> toAgentFullDtoList(List<Agent> agents, List<AgentContact> contacts) {
        Map<Long, List<AgentContact>> contactsByAgentId = contacts.stream()
                .collect(Collectors.groupingBy(contact -> contact.getAgent().getId()));

        return agents.stream()
                .map(agent -> {
                    List<AgentContact> agentContacts = contactsByAgentId.getOrDefault(
                            agent.getId(), Collections.emptyList());
                    return toAgentFullDto(agent, agentContacts);
                })
                .collect(Collectors.toList());
    }
}
