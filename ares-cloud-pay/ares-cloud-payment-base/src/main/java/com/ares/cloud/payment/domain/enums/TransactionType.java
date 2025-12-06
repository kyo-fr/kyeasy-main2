package com.ares.cloud.payment.domain.enums;

import lombok.Getter;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2025/4/13 13:21
 */
public enum TransactionType {
    // 包含收入和支出
    INCOME("INCOME", "收入"),
    EXPENSE("EXPENSE", "支出");

    private final String code;
    private final String description;

    TransactionType(String code, String description) {
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
