package com.ares.cloud.pay.domain.enums;

/**
 * 支付状态枚举
 */
public enum PaymentStatus {
    
    /**
     * 等待支付
     */
    WAITING("WAITING", "等待支付"),
    
    /**
     * 处理中
     */
    PROCESSING("PROCESSING", "处理中"),
    
    /**
     * 支付成功
     */
    SUCCESS("SUCCESS", "支付成功"),
    
    /**
     * 支付失败
     */
    FAILED("FAILED", "支付失败"),
    
    /**
     * 已关闭
     */
    CLOSED("CLOSED", "已关闭"),
    
    /**
     * 退款中
     */
    REFUNDING("REFUNDING", "退款中"),
    
    /**
     * 已退款
     */
    REFUNDED("REFUNDED", "已退款"),
    
    /**
     * 部分退款
     */
    PARTIAL_REFUNDED("PARTIAL_REFUNDED", "部分退款");
    
    private final String code;
    private final String description;
    
    PaymentStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getDescription() {
        return description;
    }
    
    public static PaymentStatus fromCode(String code) {
        for (PaymentStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("未知的支付状态: " + code);
    }
} 