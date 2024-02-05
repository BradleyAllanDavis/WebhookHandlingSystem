package com.example.webhookhandlingsystem.queue;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Configuration
public class QueueConfig {

    @Bean
    public BlockingQueue<QueueTask> inMemoryQueue() {
        return new LinkedBlockingQueue<>();
    }

}
