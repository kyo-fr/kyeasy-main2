package org.ares.cloud.infrastructure.payment.model;

import lombok.Data;

/**
 * 商户支付渠道配置DTO
 * 用于传输商户支付渠道的配置信息
 */
@Data
public class MerchantPaymentConfigDTO {
    
    /**
     * 商户ID
     * 商户的唯一标识
     */
    private String merchantId;
    
    /**
     * 支付渠道类型
     * 如：ALIPAY、WECHAT、BRAINTREE等
     */
    private String channelType;
    
    /**
     * 支付网关地址
     * 支付渠道的API接口地址
     */
    private String gatewayUrl;
    
    /**
     * 应用ID
     * 支付渠道分配的应用标识
     */
    private String appId;
    
    /**
     * 商户号
     * 支付渠道分配的商户标识
     */
    private String merchantCode;
    
    /**
     * 私钥
     * 用于签名的商户私钥
     */
    private String privateKey;
    
    /**
     * 公钥
     * 用于验签的支付渠道公钥
     */
    private String publicKey;
    
    /**
     * 额外配置
     * JSON格式，存储特定渠道的额外配置信息
     */
    private String extraConfig;
} 