package com.ludoed.kafka;

import com.ludoed.AgentRequest;
import com.ludoed.agent.dao.AgentContactRepository;
import com.ludoed.agent.dao.AgentRepository;
import com.ludoed.agent.dto.AgentFullDto;
import com.ludoed.agent.mapper.AgentMapper;
import com.ludoed.agent.model.Agent;
import com.ludoed.agent.model.AgentContact;
import com.ludoed.agent.service.AgentServiceImpl;
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

    private final AgentServiceImpl service;

    @KafkaListener(topics = "agent-request", groupId = "user-service", containerFactory = "kafkaListenerContainerFactory")
    public void handleAgentRequest(ConsumerRecord<String, AgentRequest> record) {
        String requestId = record.key();
        String email = record.value().getEmail();
        try {

            AgentFullDto agentDto = service.getAgentByEmail(email);

            kafkaTemplate.send("agent-response", requestId, agentDto);
        } catch (Exception e) {
            System.err.println("Error processing agent request: " + e.getMessage());
            throw e;
        }
    }
}
