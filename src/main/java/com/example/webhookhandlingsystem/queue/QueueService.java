package com.example.webhookhandlingsystem.queue;

import com.example.webhookhandlingsystem.model.WebhookData;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class QueueService {

    private BlockingQueue<QueueTask> queue;

    public QueueService(BlockingQueue<QueueTask> queue) {
        this.queue = queue;
    }

    @PostConstruct
    public void startProcessing() {
        CompletableFuture.runAsync(this::processQueue);
    }

    @Async
    public void processQueue() {
        while (true) {
            try {
                QueueTask task = queue.take(); // This blocks until an element is available
                log.info("taskId: {}, data: {}", task.getTaskId(), task.getWebhookData());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void addToQueue(WebhookData webhookData) {
        QueueTask task = QueueTask.builder()
                .taskId(UUID.randomUUID().toString())
                .webhookData(webhookData)
                .build();
        queue.add(task);
    }

}

