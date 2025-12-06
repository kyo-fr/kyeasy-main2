package com.ares.cloud.pay.domain.enums;

import lombok.Getter;

/**
 * 流水类型枚举
 * 用于标记收入还是支出
 */
@Getter
public enum FlowType {
    
    /**
     * 转入 - 收入
     */
    IN("IN", "转入", true),
    
    /**
     * 转出 - 支出
     */
    OUT("OUT", "转出", false);
    
    private final String code;
    private final String desc;
    private final Boolean isIncome; // true: 收入, false: 支出
    
    FlowType(String code, String desc, Boolean isIncome) {
        this.code = code;
        this.desc = desc;
        this.isIncome = isIncome;
    }

    /**
     * 判断是否为收入
     * @return true: 收入, false: 支出
     */
    public boolean isIncome() {
        return isIncome;
    }
    
    /**
     * 判断是否为支出
     * @return true: 支出, false: 收入
     */
    public boolean isExpense() {
        return !isIncome;
    }
    
    public static FlowType fromCode(String code) {
        for (FlowType type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
}
