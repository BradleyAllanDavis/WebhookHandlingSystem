package com.example.webhookhandlingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class WebhookHandlingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebhookHandlingSystemApplication.class, args);
    }

}
