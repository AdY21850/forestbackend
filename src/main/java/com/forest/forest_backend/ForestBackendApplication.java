package com.forest.forest_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication(exclude = MailSenderAutoConfiguration.class)
public class ForestBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ForestBackendApplication.class, args);
    }
}
