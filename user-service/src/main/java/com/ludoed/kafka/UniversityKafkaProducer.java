package com.ludoed.kafka;

import com.ludoed.university.UniversityInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UniversityKafkaProducer {

    private final KafkaTemplate<String, UniversityInfo> kafkaTemplate;

    public void sendUniversityToKafka(UniversityInfo university) {
        kafkaTemplate.send("university-to-user", university.getId().toString(), university);
        System.out.println("✔️ Sent UniversityFullDto to university-to-user topic: " + university);
    }
}

