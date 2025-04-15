package com.ludoed.kafka;

import com.ludoed.agent.dto.AgentFullDto;
import com.ludoed.university.UniversityInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UniversityKafkaProducer {

    private final KafkaTemplate<String, AgentFullDto> kafkaTemplate;

    public void sendAgentToKafka(AgentFullDto agentFullDto) {
        kafkaTemplate.send("university-to-user", agentFullDto.getAgentId().toString(), agentFullDto);
        System.out.println("✔️ Sent UniversityFullDto to university-to-user topic: " + agentFullDto);
    }
}

