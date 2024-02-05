package com.example.webhookhandlingsystem;

import com.example.webhookhandlingsystem.model.WebhookData;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

@Slf4j
@Component
public class WebhookSignatureUtil {

    private ObjectMapper objectMapper;

    private static final String SECRET_KEY = "my_secret_key";
    private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

    public WebhookSignatureUtil() {
        this.objectMapper = new ObjectMapper();
    }

    public boolean verifySignature(WebhookData data,
                                   String signature) {
        boolean signatureMatches = false;
        try {
            Map<String, Object> dataMap = objectMapper.convertValue(data, new TypeReference<Map<String, Object>>() {});
            String dataString = prepareStringToSign(dataMap);

            Mac sha256Hmac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
            SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), HMAC_SHA256_ALGORITHM);
            sha256Hmac.init(secretKeySpec);

            byte[] computedHash = sha256Hmac.doFinal(dataString.getBytes());
            String computedSignature = bytesToHex(computedHash);

            signatureMatches = computedSignature.equals(signature);
        } catch (Exception e) {
            log.error("exception: ", e);
        }
        return signatureMatches;
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private String prepareStringToSign(Map<String, Object> data) {
        Map<String, Object> flattened = flattenObject(data);
        List<String> sortedKeys = new ArrayList<>(flattened.keySet());
        Collections.sort(sortedKeys);

        StringBuilder sb = new StringBuilder();
        for (String key : sortedKeys) {
            if (sb.length() > 0) {
                sb.append(';');
            }
            Object value = flattened.get(key);
            String jsonValue = stringifyValue(value);
            sb.append(key).append(":").append(jsonValue);
        }
        return sb.toString();
    }

    private String stringifyValue(Object value) {
        if (value == null) {
            return "null";
        } else if (value instanceof String) {
            // Enclose strings in quotes and escape existing quotes
            return "\"" + value.toString().replace("\"", "\\\"") + "\"";
        } else {
            return value.toString();
        }
    }

    private Map<String, Object> flattenObject(Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>();
        flatten(map, "", result);
        return result;
    }

    private void flatten(Map<String, Object> map, String parentKey, Map<String, Object> result) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = parentKey.isEmpty() ? entry.getKey() : parentKey + "." + entry.getKey();
            Object value = entry.getValue();
            if (value instanceof Map) {
                flatten((Map<String, Object>) value, key, result);
            } else {
                result.put(key, value);
            }
        }
    }

}
