package com.ludoed.kafka;

import com.ludoed.agent.dto.AgentFullDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topics.agent-response}")
    private String agentResponseTopic;

    @Value("${kafka.topics.university-request}")
    private String universityRequestTopic;

    public KafkaProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void requestUniversityById(Long universityId) {
        kafkaTemplate.send(universityRequestTopic, universityId);
    }
}

