package com.ares.cloud.pay.domain.enums;

/**
 * 支付方式枚举
 * 系统内部支付方式，流通礼物点
 */
public enum PaymentMethod {
    
    /**
     * 余额支付（礼物点）
     */
    BALANCE("BALANCE", "余额支付"),
    
    /**
     * 系统充值
     */
    RECHARGE("RECHARGE", "系统充值"),
    
    /**
     * 系统扣款
     */
    DEDUCTION("DEDUCTION", "系统扣款"),
    
    /**
     * 礼物点转账
     */
    TRANSFER("TRANSFER", "礼物点转账"),
    
    /**
     * 商户收款
     */
    MERCHANT_PAYMENT("MERCHANT_PAYMENT", "商户收款"),
    
    /**
     * 退款
     */
    REFUND("REFUND", "退款");
    
    private final String code;
    private final String description;
    
    PaymentMethod(String code, String description) {
        this.code = code;
        this.description = description;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getDescription() {
        return description;
    }
    
    public static PaymentMethod fromCode(String code) {
        for (PaymentMethod method : values()) {
            if (method.getCode().equals(code)) {
                return method;
            }
        }
        throw new IllegalArgumentException("未知的支付方式: " + code);
    }
} 