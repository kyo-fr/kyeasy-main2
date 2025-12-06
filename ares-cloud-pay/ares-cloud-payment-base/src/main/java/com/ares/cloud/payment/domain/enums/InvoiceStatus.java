package com.ares.cloud.payment.domain.enums;

/**
 * 发票状态枚举
 */
public enum InvoiceStatus {
    
    /**
     * 已创建
     */
    CREATED("CREATED", "已创建"),
    
    /**
     * 已支付
     */
    PAID("PAID", "已支付"),
    
    /**
     * 已作废
     */
    VOIDED("VOIDED", "已作废"),
    
    /**
     * 已退款
     */
    REFUNDED("REFUNDED", "已退款"),
    
    /**
     * 部分退款
     */
    PARTIALLY_REFUNDED("PARTIALLY_REFUNDED", "部分退款");

    private final String code;
    private final String description;

    InvoiceStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}