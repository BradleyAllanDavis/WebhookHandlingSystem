package com.example.webhookhandlingsystem.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({ "asset", "cents", "id", "status", "type", "updated_at" })
public class WebhookData {

    private String id;
    private String type;
    private String asset;
    private String cents;
    private String status;
    private Long updated_at;

}
