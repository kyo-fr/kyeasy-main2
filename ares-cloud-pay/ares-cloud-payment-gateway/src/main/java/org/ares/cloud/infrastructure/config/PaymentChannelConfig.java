package org.ares.cloud.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "payment")
public class PaymentChannelConfig {
    
    private AlipayConfig alipay = new AlipayConfig();
    private WechatPayConfig wechatPay = new WechatPayConfig();
    private BraintreeConfig braintree = new BraintreeConfig();
    
    @Data
    public static class AlipayConfig {
        private String appId;
        private String privateKey;
        private String publicKey;
        private String gatewayUrl;
    }
    
    @Data
    public static class WechatPayConfig {
        private String appId;
        private String mchId;
        private String privateKey;
        private String apiV3Key;
        private String serialNumber;
    }
    
    @Data
    public static class BraintreeConfig {
        private String merchantId;
        private String publicKey;
        private String privateKey;
        private String environment;
    }
} 