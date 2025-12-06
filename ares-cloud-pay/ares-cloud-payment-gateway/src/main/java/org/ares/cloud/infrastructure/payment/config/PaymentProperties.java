package org.ares.cloud.infrastructure.payment.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 支付配置属性
 */
@Data
@Component
@ConfigurationProperties(prefix = "payment")
public class PaymentProperties {
    
    /**
     * 支付页面基础地址
     */
    private String pageBaseUrl;
    
    /**
     * 支付宝网关地址
     */
    private String alipayGatewayUrl = "https://openapi.alipay.com/gateway.do";
    
    /**
     * 微信支付网关地址
     */
    private String wechatGatewayUrl = "https://api.mch.weixin.qq.com/v3";
    
    /**
     * 支付结果页面配置
     */
    private PaymentResultPage resultPage = new PaymentResultPage();
    
    @Data
    public static class PaymentResultPage {
        /**
         * 支付结果页面地址
         */
        private String url = "/payment/result";
        
        /**
         * 支付成功页面模板
         */
        private String successTemplate = "payment/success";
        
        /**
         * 支付失败页面模板
         */
        private String failureTemplate = "payment/failure";
    }
} 