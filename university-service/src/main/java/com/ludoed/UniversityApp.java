package com.ludoed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.ludoed.university")
@EnableJpaRepositories(basePackages = "com.ludoed.university.dao")
@EntityScan(basePackages = "com.ludoed.university.model")
public class UniversityApp {
    public static void main(String[] args) {
        SpringApplication.run(UniversityApp.class,args);
    }
}
