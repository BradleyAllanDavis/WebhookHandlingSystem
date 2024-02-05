package com.example.webhookhandlingsystem;

import com.example.webhookhandlingsystem.model.WebhookRequest;
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

    public WebhookController(WebhookSignatureUtil webhookSignatureUtil) {
        this.webhookSignatureUtil = webhookSignatureUtil;
    }

    @PostMapping("/external1")
    public ResponseEntity<?> handleWebhook(@RequestBody WebhookRequest requestBody) {
        log.info("requestBody: {}", requestBody.toString());
        try {
            if (webhookSignatureUtil.verifySignature(requestBody.getData(), requestBody.getSignature())) {
                log.info("signature matches");
                // TODO: Add to queue
            } else {
                log.warn("signature does not match!");
            }
        } catch (Exception e) {
            log.error("exception: ", e);
        }
        return ResponseEntity.ok()
                .build();
    }

}
