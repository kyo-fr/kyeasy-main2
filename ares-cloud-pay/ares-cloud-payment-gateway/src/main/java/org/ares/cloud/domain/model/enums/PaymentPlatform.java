package org.ares.cloud.domain.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 支付平台枚举
 * 
 * @author hugo
 * @version 1.0.0
 * @date 2024/03/15
 */
@Schema(description = "支付平台")
public enum PaymentPlatform {
    /**
     * APP支付
     */
    APP,
    
    /**
     * H5支付
     */
    H5,
    
    /**
     * PC网页支付
     */
    PC
} 