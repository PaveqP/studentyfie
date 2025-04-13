package com.ludoed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.ludoed.university", "com.ludoed.programs"})
@EnableJpaRepositories(basePackages = {"com.ludoed.university.dao", "com.ludoed.programs.dao"})
@EntityScan(basePackages = {"com.ludoed.university.model", "com.ludoed.programs.model"})
public class MainApp {
    public static void main(String[] args) {
        SpringApplication.run(MainApp.class,args);
    }
}
