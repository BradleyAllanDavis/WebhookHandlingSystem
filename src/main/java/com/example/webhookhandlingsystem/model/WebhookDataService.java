package com.example.webhookhandlingsystem.model;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class WebhookDataService {

    private final WebhookDataMapper webhookDataMapper;

    public WebhookDataService(WebhookDataMapper webhookDataMapper) {
        this.webhookDataMapper = webhookDataMapper;
    }

    public boolean insert(WebhookData webhookData) {
        int insertedRows = webhookDataMapper.insert(webhookData);
        return insertedRows > 0;
    }

    public Optional<WebhookData> getById(String id) {
        return webhookDataMapper.getById(id);
    }

    public boolean update(WebhookData webhookData) {
        int updatedRows = webhookDataMapper.update(webhookData);
        return updatedRows > 0;
    }

    @Transactional
    public boolean upsert(WebhookData webhookData) {
        boolean result = false;
        try {
            Optional<WebhookData> existingWebhookDataOpt = getById(webhookData.getId());
            if (existingWebhookDataOpt.isPresent() &&
                    webhookData.getUpdated_at() > existingWebhookDataOpt.get().getUpdated_at()) {
                result = update(webhookData);
            } else if (!existingWebhookDataOpt.isPresent()) {
                result = insert(webhookData);
            } else {
                log.warn("received stale update");
            }
        } catch (Exception e) {
            log.error("error during upsert operation", e);
        }
        return result;
    }

}
