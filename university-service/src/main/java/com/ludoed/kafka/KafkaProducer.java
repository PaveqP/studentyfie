package com.ludoed.kafka;

import com.ludoed.university.model.AgentRequest;
import com.ludoed.university.model.UniversityInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topics.university-response}")
    private String universityResponseTopic;

    @Value("${kafka.topics.agent-request}")
    private String agentRequestTopic;

    public KafkaProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUniversityFullDto(UniversityInfo info) {
        kafkaTemplate.send(universityResponseTopic, info);
    }

    public void requestAgentByEmail(String email, String requestId) {
        AgentRequest request = new AgentRequest(requestId, email);
        kafkaTemplate.send("agent-request", requestId, request);
        System.out.println("🔄 Запрос на получение агента отправлен: " + request);
    }
}
