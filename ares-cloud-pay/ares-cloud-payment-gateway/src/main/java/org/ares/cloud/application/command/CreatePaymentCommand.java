package org.ares.cloud.application.command;

import lombok.Data;
import org.ares.cloud.domain.model.enums.PaymentChannel;
import org.ares.cloud.domain.model.enums.PlatformType;

import java.math.BigDecimal;

/**
 * 创建支付订单命令
 */
@Data
public class CreatePaymentCommand {
    
    /**
     * 商户ID
     */
    private String merchantId;
    
    /**
     * 商户订单号
     */
    private String merchantOrderNo;
    
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
     * 平台类型
     */
    private PlatformType platform;
    
    /**
     * 同步回调地址
     */
    private String returnUrl;
    
    /**
     * 异步通知地址
     */
    private String notifyUrl;
    
    /**
     * 过期时间(分钟)
     */
    private Integer expireMinutes;
} 