package com.ludoed.kafka;

import com.ludoed.university.dto.UniversityFullDto;
import com.ludoed.university.service.UniversityService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UniversityKafkaListener {

    private final UniversityService universityService; // ты должен реализовать save/update

    @KafkaListener(
            topics = "university-to-user",
            groupId = "user-service-group",
            containerFactory = "universityKafkaListenerContainerFactory"
    )
    public void listenUniversityFromKafka(UniversityFullDto universityDto) {
        System.out.println("📥 Received UniversityFullDto from university-to-user topic: " + universityDto);
        universityService.updateUniversity(universityDto.getId(), universityDto);
    }
}

