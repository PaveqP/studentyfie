package com.ludoed.controller;

import com.ludoed.dto.AgentFullDto;
import com.ludoed.service.AgentService;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/agents")
public class AgentController {

    private final AgentService agentService;

    @GetMapping("/{agentId}")
    public AgentFullDto getAgentById(@PathVariable Long agentId) {
        return agentService.getAgentById(agentId);
    }

    @GetMapping
    public List<AgentFullDto> getAllAgent(@RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                          @RequestParam(defaultValue = "10") @Positive int size) {
        return agentService.getAllAgents(from, size);
    }

    @PostMapping
    public AgentFullDto createAgent(@RequestBody AgentFullDto agent) {
        return agentService.createAgent(agent);
    }

    @PatchMapping("/{agentId}")
    public AgentFullDto updateAgent(@PathVariable Long agentId, @RequestBody AgentFullDto agent) {
        return agentService.updateAgent(agentId, agent);
    }

    @DeleteMapping("/{agentId}")
    public String deleteAgent(@PathVariable Long agentId) {
        return agentService.deleteAgent(agentId);
    }
}
