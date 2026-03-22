package com.smartcv.builder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SmartCVApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartCVApplication.class, args);
    }

}
