package org.ares.cloud.application.dto;

import lombok.Data;
import org.ares.cloud.domain.model.enums.PaymentChannel;
import org.ares.cloud.domain.model.enums.PaymentStatus;
import org.ares.cloud.domain.model.enums.PlatformType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 支付订单DTO
 */
@Data
public class PaymentOrderDTO {
    
    /**
     * 订单号
     */
    private String orderNo;
    
    /**
     * 商户ID
     */
    private String merchantId;
    
    /**
     * 支付渠道
     */
    private PaymentChannel channel;
    
    /**
     * 支付金额
     */
    private BigDecimal amount;
    
    /**
     * 订单标题
     */
    private String subject;
    
    /**
     * 支付状态
     */
    private PaymentStatus status;
    
    /**
     * 平台类型
     */
    private PlatformType platform;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 支付时间
     */
    private LocalDateTime payTime;
} 