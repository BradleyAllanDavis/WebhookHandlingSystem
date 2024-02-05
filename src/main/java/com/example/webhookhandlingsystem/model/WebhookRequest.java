package com.example.webhookhandlingsystem.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({ "data", "signature" })
public class WebhookRequest {

    private String signature;
    private WebhookData data;

}
