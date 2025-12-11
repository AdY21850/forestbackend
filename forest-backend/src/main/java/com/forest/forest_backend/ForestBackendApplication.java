package com.forest.forest_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
@EnableAsync
@SpringBootApplication
public class ForestBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForestBackendApplication.class, args);
        System.setProperty("java.net.preferIPv4Stack", "true");

        SpringApplication.run(ForestBackendApplication.class, args);
    }

}
