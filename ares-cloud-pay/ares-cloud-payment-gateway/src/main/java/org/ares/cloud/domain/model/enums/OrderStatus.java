package org.ares.cloud.domain.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 订单状态枚举
 * 
 * @author hugo
 * @version 1.0.0
 * @date 2024/03/15
 */
@Schema(description = "订单状态")
public enum OrderStatus {
    /**
     * 待支付
     */
    PENDING,
    
    /**
     * 已支付
     */
    PAID,
    
    /**
     * 支付失败
     */
    FAILED,
    
    /**
     * 已关闭
     */
    CLOSED
} 