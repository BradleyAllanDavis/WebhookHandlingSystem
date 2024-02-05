package com.example.webhookhandlingsystem.queue;

import com.example.webhookhandlingsystem.model.WebhookData;
import com.example.webhookhandlingsystem.model.WebhookDataService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class QueueService {

    private BlockingQueue<QueueTask> queue;
    private WebhookDataService webhookDataService;

    public QueueService(BlockingQueue<QueueTask> queue,
                        WebhookDataService webhookDataService) {
        this.queue = queue;
        this.webhookDataService = webhookDataService;
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
                webhookDataService.upsert(task.getWebhookData());
                logUpsertedRecord(task);
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

    private void logUpsertedRecord(QueueTask task) {
        Optional<WebhookData> record = webhookDataService.getById(task.getWebhookData().getId());
        if (record.isPresent()) {
            log.info("upsertedRecord: {}", record.get());
        } else {
            log.warn("no record found!");
        }
    }

}

