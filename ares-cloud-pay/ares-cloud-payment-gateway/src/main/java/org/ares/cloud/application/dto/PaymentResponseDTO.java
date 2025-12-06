package org.ares.cloud.application.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 支付响应DTO
 */
@Data
@Builder
public class PaymentResponseDTO {
    
    /**
     * 订单号
     */
    private String orderNo;
    
    /**
     * 支付链接
     */
    private String paymentUrl;
    
    /**
     * 支付状态
     */
    private String status;
} 