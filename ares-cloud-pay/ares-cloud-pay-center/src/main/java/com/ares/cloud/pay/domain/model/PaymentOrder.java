package com.ares.cloud.pay.domain.model;

import com.ares.cloud.pay.domain.enums.PaymentMethod;
import com.ares.cloud.pay.domain.enums.PaymentStatus;
import org.ares.cloud.common.model.Money;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 支付订单模型
 * 用于第三方接入时的支付订单管理
 */
@Data
public class PaymentOrder {
    
    /**
     * 订单ID
     */
    private String id;
    
    /**
     * 订单号（系统生成）
     */
    private String orderNo;
    
    /**
     * 商户订单号（商户传入）
     */
    private String merchantOrderNo;
    
    /**
     * 商户ID
     */
    private String merchantId;
    
    /**
     * 用户ID
     */
    private String userId;
    
    /**
     * 支付金额
     */
    private Money amount;
    
    /**
     * 支付区域
     */
    private String paymentRegion;
    
    /**
     * 支付方式
     */
    private PaymentMethod paymentMethod;
    
    /**
     * 支付状态
     */
    private PaymentStatus status;
    
    /**
     * 订单标题
     */
    private String subject;
    
    /**
     * 订单描述
     */
    private String description;
    
    /**
     * 支付完成后的跳转地址
     */
    private String returnUrl;
    
    /**
     * 支付结果通知地址
     */
    private String notifyUrl;
    
    /**
     * 支付渠道
     */
    private String channel;
    
    /**
     * 过期时间
     */
    private Long expireTime;
    
    /**
     * 支付时间
     */
    private Long payTime;
    
    /**
     * 创建时间
     */
    private Long createTime;
    
    /**
     * 更新时间
     */
    private Long updateTime;
    
    /**
     * 支付通知
     */
    private PaymentNotify paymentNotify;
    
    /**
     * 自定义参数
     */
    private Map<String, String> customParams;
    
    /**
     * 生成订单号
     */
    public static String generateOrderNo() {
        return "PAY" + System.currentTimeMillis() + String.format("%04d", (int)(Math.random() * 10000));
    }
    
    /**
     * 创建支付订单
     */
    public static PaymentOrder create(String merchantOrderNo, String merchantId, String userId,
                                    Money amount, String paymentRegion, String subject, 
                                    String description, Long expireTime) {
        PaymentOrder order = new PaymentOrder();
        order.setOrderNo(generateOrderNo());
        order.setMerchantOrderNo(merchantOrderNo);
        order.setMerchantId(merchantId);
        order.setUserId(userId);
        order.setAmount(amount);
        order.setPaymentRegion(paymentRegion);
        order.setSubject(subject);
        order.setDescription(description);
        order.setExpireTime(expireTime);
        order.setStatus(PaymentStatus.WAITING);
        order.setPaymentMethod(PaymentMethod.BALANCE);
        order.setCreateTime(System.currentTimeMillis());
        order.setUpdateTime(System.currentTimeMillis());
        return order;
    }
    
    /**
     * 检查订单是否过期
     */
    public boolean isExpired() {
        return System.currentTimeMillis() > expireTime;
    }
    
    /**
     * 检查订单是否可以支付
     */
    public boolean canPay() {
        return status == PaymentStatus.WAITING && !isExpired();
    }
    
    /**
     * 检查订单是否可以退款
     */
    public boolean canRefund() {
        return status == PaymentStatus.SUCCESS;
    }
} 