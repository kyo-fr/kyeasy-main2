package com.ares.cloud.pay.domain.valueobject;

import lombok.Data;

/**
 * 支付订单查询结果值对象
 */
@Data
public class PaymentQueryResult {
    private String orderNo;            // 订单号
    private String merchantOrderNo;    // 商户订单号
    private String status;             // 支付状态
    private Long amount;               // 支付金额
    private String currency;           // 币种
    private Long payTime;              // 支付时间
    private String sign;          // 签名
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder {
        private PaymentQueryResult result = new PaymentQueryResult();
        
        public Builder orderNo(String orderNo) {
            result.orderNo = orderNo;
            return this;
        }
        
        public Builder merchantOrderNo(String merchantOrderNo) {
            result.merchantOrderNo = merchantOrderNo;
            return this;
        }
        
        public Builder status(String status) {
            result.status = status;
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
        
        public Builder payTime(Long payTime) {
            result.payTime = payTime;
            return this;
        }
        
        public Builder signature(String sign) {
            result.sign = sign;
            return this;
        }
        
        public PaymentQueryResult build() {
            return result;
        }
    }
} 