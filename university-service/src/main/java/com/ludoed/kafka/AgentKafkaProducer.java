package com.ludoed.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgentKafkaProducer {

    private final KafkaTemplate<String, AgentFullDto> kafkaTemplate;

    public void sendAgentToKafka(AgentFullDto agentDto) {
        kafkaTemplate.send("user-to-university", agentDto.getId().toString(), agentDto);
        System.out.println("✔️ Sent AgentFullDto to user-to-university topic: " + agentDto);
    }
}
