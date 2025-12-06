package org.ares.cloud.infrastructure.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 支付通知客户端
 */
@Component
@RequiredArgsConstructor
public class NotifyClient {

    private final RestTemplate restTemplate;
    
    /**
     * 发送通知
     */
    public String notify(String merchantId, String orderNo, String notifyParams) {
        String url = String.format("http://merchant-service/api/payment/notify/%s/%s", merchantId, orderNo);
        return restTemplate.postForObject(url, notifyParams, String.class);
    }
} 