package org.ares.cloud.domain.model;

import lombok.Builder;
import lombok.Data;
import org.ares.cloud.domain.model.enums.PaymentChannel;
import org.ares.cloud.domain.model.enums.PaymentStatus;
import org.ares.cloud.domain.model.enums.PlatformType;
import org.ares.cloud.domain.service.PaymentChannelService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 支付订单聚合根
 */
@Data
@Builder
public class PaymentOrder {
    
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
     * 同步回调地址
     */
    private String returnUrl;
    
    /**
     * 异步通知地址
     */
    private String notifyUrl;
    
    /**
     * 支付过期时间
     */
    private LocalDateTime expireTime;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 支付时间
     */
    private LocalDateTime payTime;
    
    /**
     * 渠道交易号
     */
    private String channelTradeNo;
    
    /**
     * 创建支付链接
     */
    public String generatePaymentUrl(PaymentChannelService channelService) {
        return channelService.generatePaymentUrl(this);
    }
    
    /**
     * 处理支付通知
     */
    public void handlePaymentNotify(PaymentNotifyRecord notify) {
        if (PaymentStatus.SUCCESS.equals(notify.getStatus())) {
            this.status = PaymentStatus.SUCCESS;
            this.payTime = LocalDateTime.now();
        } else if (PaymentStatus.FAILED.equals(notify.getStatus())) {
            this.status = PaymentStatus.FAILED;
        }
    }
} 