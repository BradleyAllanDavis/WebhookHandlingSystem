package com.example.webhookhandlingsystem;

import com.example.webhookhandlingsystem.model.WebhookRequest;
import com.example.webhookhandlingsystem.queue.QueueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/webhook")
public class WebhookController {

    private WebhookSignatureUtil webhookSignatureUtil;
    private QueueService queueService;

    public WebhookController(WebhookSignatureUtil webhookSignatureUtil,
                             QueueService queueService) {
        this.webhookSignatureUtil = webhookSignatureUtil;
        this.queueService = queueService;
    }

    @PostMapping("/external1")
    public ResponseEntity<?> handleWebhook(@RequestBody WebhookRequest requestBody) {
        log.info("requestBody: {}", requestBody.toString());
        try {
            if (webhookSignatureUtil.verifySignature(requestBody.getData(), requestBody.getSignature())) {
                log.info("signature matches");
                queueService.addToQueue(requestBody.getData());
            } else {
                log.warn("signature does not match!");
            }
        } catch (Exception e) {
            log.error("exception handling webhook: ", e);
        }
        return ResponseEntity.ok()
                .build();
    }

}
