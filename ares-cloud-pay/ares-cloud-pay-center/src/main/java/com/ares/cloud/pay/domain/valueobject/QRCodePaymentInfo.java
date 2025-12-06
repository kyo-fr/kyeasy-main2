package com.ares.cloud.pay.domain.valueobject;

import lombok.Data;

/**
 * 二维码支付信息值对象
 * 封装二维码支付的相关信息
 */
@Data
public class QRCodePaymentInfo {
    
    /**
     * 订单号
     */
    private String orderNo;
    
    /**
     * 支付金额
     */
    private Long amount;
    
    /**
     * 币种
     */
    private String currency;
    
    /**
     * 过期时间
     */
    private Long expireTime;
    
    /**
     * 票据
     */
    private String ticket;
}