package com.ares.cloud.base.enums;

/**
 * @author hugo
 * @version 1.0
 * @description: 支付类型枚举
 * @date 2025/5/13 02:20
 */
public enum PaymentType {
    Braintree("braintree",  "braintree支付");

    private final String key ;
    private final String name ;

    PaymentType(String key, String name){
        this.key = key;
        this.name = name;
    }
    public String getKey() {
        return key;
    }
    public String getName() {
        return name;
    }

}
