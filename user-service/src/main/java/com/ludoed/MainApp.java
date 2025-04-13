package com.ludoed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.ludoed.agent", "com.ludoed.student"})
@EnableJpaRepositories(basePackages = {"com.ludoed.student.dao", "com.ludoed.agent.dao"})
@EntityScan(basePackages = {"com.ludoed.student.model", "com.ludoed.agent.model"})
public class MainApp {
    public static void main(String[] args) {
        SpringApplication.run(MainApp.class,args);
    }
}
