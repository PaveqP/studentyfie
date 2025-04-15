package com.ludoed.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic userToUniversityTopic() {
        return TopicBuilder.name("user-to-university")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic universityToUserTopic() {
        return TopicBuilder.name("university-to-user")
                .partitions(3)
                .replicas(1)
                .build();
    }
}
