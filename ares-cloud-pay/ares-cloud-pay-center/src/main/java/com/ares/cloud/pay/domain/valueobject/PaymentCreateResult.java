package com.ares.cloud.pay.domain.valueobject;

import lombok.Data;

/**
 * 支付订单创建结果值对象
 */
@Data
public class PaymentCreateResult {
    private String orderNo;            // 订单号
    private String merchantOrderNo;    // 商户订单号
    private String paymentUrl;         // 支付地址
    private String qrCodeUrl;          // 二维码支付地址
    private Long amount;               // 支付金额
    private String currency;           // 币种
    private String sign;          // 签名
    private Long expireTime;           // 过期时间
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder {
        private PaymentCreateResult result = new PaymentCreateResult();
        
        public Builder orderNo(String orderNo) {
            result.orderNo = orderNo;
            return this;
        }
        
        public Builder merchantOrderNo(String merchantOrderNo) {
            result.merchantOrderNo = merchantOrderNo;
            return this;
        }
        
        public Builder paymentUrl(String paymentUrl) {
            result.paymentUrl = paymentUrl;
            return this;
        }
        
        public Builder qrCodeUrl(String qrCodeUrl) {
            result.qrCodeUrl = qrCodeUrl;
            return this;
        }
        
        public Builder amount(Long amount) {
            result.amount = amount;
            return this;
        }
        
        public Builder currency(String currency) {
            result.currency = currency;
            return this;
        }
        
        public Builder sign(String sign) {
            result.sign = sign;
            return this;
        }
        
        public Builder expireTime(Long expireTime) {
            result.expireTime = expireTime;
            return this;
        }
        
        public PaymentCreateResult build() {
            return result;
        }
    }
} 