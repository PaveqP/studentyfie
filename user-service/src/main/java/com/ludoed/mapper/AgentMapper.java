package com.ludoed.mapper;

import com.ludoed.dto.AgentFullDto;
import com.ludoed.model.Agent;
import com.ludoed.model.AgentContact;

import java.util.List;

public class AgentMapper {

    public AgentFullDto toAgentFullDto(Agent agent, List<AgentContact> contacts) {
        return new AgentFullDto(
                null,
                agent.getAvatar(),
                agent.getEmail(),
                agent.getFirstName(),
                agent.getSurname(),
                agent.getLastName(),
                agent.getUniversity(),
                contacts
        );
    }
}
