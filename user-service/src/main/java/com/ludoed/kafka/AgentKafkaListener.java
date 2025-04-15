package com.ludoed.kafka;

import com.ludoed.agent.dto.AgentFullDto;
import com.ludoed.agent.service.AgentService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgentKafkaListener {

    private final AgentService agentService; // ты должен реализовать save/update

    @KafkaListener(
            topics = "user-to-university",
            groupId = "university-service-group",
            containerFactory = "agentKafkaListenerContainerFactory"
    )
    public void listenAgentFromKafka(AgentFullDto agentDto) {
        System.out.println("📥 Received AgentFullDto from user-to-university topic: " + agentDto);
        agentService.createAgent(agentDto);
    }
}
