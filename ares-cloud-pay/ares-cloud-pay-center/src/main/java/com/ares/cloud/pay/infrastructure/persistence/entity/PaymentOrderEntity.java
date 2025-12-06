package com.ares.cloud.pay.infrastructure.persistence.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ares.cloud.database.entity.BaseEntity;

/**
 * 支付订单实体类
 * 对应数据库表 payment_order
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("payment_order")
public class PaymentOrderEntity extends BaseEntity {
    
    /**
     * 业务订单号（系统生成）
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
     * 订单金额(以分为单位)
     */
    private Long amount;
    
    /**
     * 币种
     */
    private String currency;
    
    /**
     * 币种精度
     */
    private Integer scale;
    
    /**
     * 支付区域
     */
    private String paymentRegion;
    
    /**
     * 支付方式
     */
    private Integer paymentMethod;
    
    /**
     * 订单标题
     */
    private String subject;
    
    /**
     * 订单描述
     */
    private String body;
    
    /**
     * 支付完成后的跳转地址
     */
    private String returnUrl;
    
    /**
     * 支付结果通知地址
     */
    private String notifyUrl;
    
    /**
     * 订单状态(0:待支付 1:成功 2:失败 3:关闭)
     */
    private Integer status;
    
    /**
     * 支付渠道(1:礼物点)
     */
    private Integer channel;
    
    /**
     * 订单过期时间
     */
    private Long expireTime;
    
    /**
     * 支付时间
     */
    private Long payTime;
    
    /**
     * 自定义参数（JSON格式存储）
     */
    private String customParams;
} 