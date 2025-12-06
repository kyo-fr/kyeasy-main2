package com.ares.cloud.pay.domain.model;

import lombok.Data;

/**
 * 支付二维码模型
 */
@Data
public class PaymentQRCode {
    
    /**
     * 所有者ID(用户ID或商户ID)
     */
    private String ownerId;
    
    /**
     * 所有者类型(USER/MERCHANT)
     */
    private String ownerType;
    
    /**
     * 支付区域
     */
    private String paymentRegion;
    
    /**
     * 二维码内容
     */
    private String qrContent;
    
    /**
     * 二维码状态(ACTIVE/INACTIVE)
     */
    private String status;
    
    /**
     * 创建时间
     */
    private Long createTime;
} 