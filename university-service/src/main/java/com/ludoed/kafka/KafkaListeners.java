package com.ludoed.kafka;

import com.ludoed.agent.model.AgentFullDto;
import com.ludoed.university.service.UniversityServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaListeners {

    private final UniversityServiceImpl universityService;

    private final KafkaAgentClient kafkaAgentClient;

    @KafkaListener(topics = "agent-response", groupId = "university-service")
    public void listen(ConsumerRecord<String, AgentFullDto> record) {
        String requestId = record.key();
        AgentFullDto agent = record.value();
        System.out.println("Получен агент из user-service: " + agent);

        kafkaAgentClient.handleAgentResponse(requestId, agent);
    }
}

