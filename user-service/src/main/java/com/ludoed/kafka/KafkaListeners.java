package com.ludoed.kafka;

import com.ludoed.agent.dao.AgentContactRepository;
import com.ludoed.agent.dao.AgentRepository;
import com.ludoed.agent.dto.AgentFullDto;
import com.ludoed.agent.mapper.AgentMapper;
import com.ludoed.agent.model.Agent;
import com.ludoed.agent.model.AgentContact;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KafkaListeners {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final AgentRepository agentRepository;

    private final AgentContactRepository agentContactRepository;

    private final AgentMapper agentMapper;

    @KafkaListener(topics = "agent-request", groupId = "user-service")
    public void handleAgentRequest(ConsumerRecord<String, AgentRequest> record) {
        String requestId = record.key();
        String email = record.value().getEmail();

        Agent agent = agentRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Агент не найден"));
        List<AgentContact> contacts = agentContactRepository.findByAgentId(agent.getId());

        AgentFullDto agentDto = agentMapper.toAgentFullDto(agent, contacts);

        kafkaTemplate.send("agent-response", requestId, agentDto);
    }
}
