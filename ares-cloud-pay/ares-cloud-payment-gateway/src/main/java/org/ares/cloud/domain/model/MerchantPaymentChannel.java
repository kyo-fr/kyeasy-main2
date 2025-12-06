package org.ares.cloud.domain.model;

import lombok.Data;
import lombok.Builder;

/**
 * 商户支付渠道配置
 */
@Data
@Builder
public class MerchantPaymentChannel {
    
    /**
     * 商户ID
     */
    private String merchantId;
    
    /**
     * 渠道类型
     */
    private String channelType;
    
    /**
     * 应用ID
     */
    private String appId;
    
    /**
     * 商户号
     */
    private String merchantCode;
    
    /**
     * 私钥
     */
    private String privateKey;
    
    /**
     * 公钥
     */
    private String publicKey;
    
    /**
     * 额外配置
     */
    private String extraConfig;
} 