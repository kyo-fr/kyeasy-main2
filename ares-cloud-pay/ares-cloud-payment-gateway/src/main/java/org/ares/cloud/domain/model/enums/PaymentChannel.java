package org.ares.cloud.domain.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 支付渠道枚举
 * 
 * @author hugo
 * @version 1.0.0
 * @date 2024/03/15
 */
@Schema(description = "支付渠道")
public enum PaymentChannel {
    /**
     * 支付宝
     */
    ALIPAY,
    
    /**
     * 微信支付
     */
    WECHAT,
    
    /**
     * Braintree支付
     */
    BRAINTREE
} 