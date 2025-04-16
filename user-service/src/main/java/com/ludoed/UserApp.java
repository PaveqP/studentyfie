package com.ludoed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.ludoed")
@EnableJpaRepositories(basePackages = {"com.ludoed.student.dao", "com.ludoed.agent.dao"})
@EntityScan(basePackages = {"com.ludoed.student.model", "com.ludoed.agent.model"})
public class UserApp {
    public static void main(String[] args) {
        SpringApplication.run(UserApp.class,args);
    }
}
