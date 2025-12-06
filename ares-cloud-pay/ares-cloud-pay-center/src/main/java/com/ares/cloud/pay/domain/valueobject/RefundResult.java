package com.ares.cloud.pay.domain.valueobject;

import lombok.Data;

/**
 * 退款结果值对象
 */
@Data
public class RefundResult {
    private String orderNo;            // 订单号
    private String merchantOrderNo;    // 商户订单号
    private Long refundAmount;         // 退款金额
    private String currency;           // 币种
    private String status;             // 退款状态
    private String reason;             // 退款原因
    private String sign;          // 签名
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder {
        private RefundResult result = new RefundResult();
        
        public Builder orderNo(String orderNo) {
            result.orderNo = orderNo;
            return this;
        }
        
        public Builder merchantOrderNo(String merchantOrderNo) {
            result.merchantOrderNo = merchantOrderNo;
            return this;
        }
        
        public Builder refundAmount(Long refundAmount) {
            result.refundAmount = refundAmount;
            return this;
        }
        
        public Builder currency(String currency) {
            result.currency = currency;
            return this;
        }
        
        public Builder status(String status) {
            result.status = status;
            return this;
        }
        
        public Builder reason(String reason) {
            result.reason = reason;
            return this;
        }
        
        public Builder sign(String sign) {
            result.sign = sign;
            return this;
        }
        
        public RefundResult build() {
            return result;
        }
    }
} 