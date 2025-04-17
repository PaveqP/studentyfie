package com.ludoed.kafka;

import com.ludoed.agent.model.AgentFullDto;
import com.ludoed.AgentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class KafkaAgentClient {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    // Временное хранилище ответов
    private final ConcurrentHashMap<String, AgentFullDto> responses = new ConcurrentHashMap<>();

    public AgentFullDto requestAgentByEmail(String email) {
        String requestId = UUID.randomUUID().toString();

        AgentRequest request = new AgentRequest(requestId, email);
        kafkaTemplate.send("agent-request", requestId, request);
        System.out.println("Запрос на получение агента отправлен: " + request);

        // Ждём ответа (максимум 5 секунд)
        int timeoutMs = 5000;
        int waited = 0;
        while (!responses.containsKey(requestId) && waited < timeoutMs) {
            try {
                Thread.sleep(100);
                waited += 100;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Ожидание прервано", e);
            }
        }

        AgentFullDto response = responses.remove(requestId);
        if (response == null) {
            throw new RuntimeException("Не удалось получить данные агента по email: " + email);
        }

        return response;
    }

    // Этот метод будет вызван из KafkaListener
    public void handleAgentResponse(String requestId, AgentFullDto dto) {
        responses.put(requestId, dto);
    }
}

