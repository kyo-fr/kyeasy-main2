package com.ares.cloud.payment.domain.enums;

import lombok.Getter;

/**
 * 交易方类型枚举
 */
@Getter
public enum TransactionPartyType {
    
    /**
     * 商户对商户
     */
    B2B("B2B", "商户对商户"),
    
    /**
     * 商户对客户
     */
    B2C("B2C", "商户对客户"),
    
    /**
     * 客户对客户
     */
    C2C("C2C", "客户对客户"),
    
    /**
     * 客户对商户
     */
    C2B("C2B", "客户对商户");

    private final String code;
    private final String description;

    TransactionPartyType(String code, String description) {
        this.code = code;
        this.description = description;
    }

}