package com.example.webhookhandlingsystem.queue;

import com.example.webhookhandlingsystem.model.WebhookData;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QueueTask {

    private String taskId;

    private WebhookData webhookData;

}
