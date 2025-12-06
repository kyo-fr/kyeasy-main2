package com.ares.cloud.order.domain.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;

/**
 * 支付方式
 */
@Schema(enumAsRef = true, description = "支付方式: 1-在线支付, 2-线下支付")
public enum PaymentMode {
    /**
     * 在线支付
     */
    @Schema(description = "在线支付(1)", enumAsRef = true)
    ONLINE(1),
    
    /**
     * 线下支付
     */
    @Schema(description = "线下支付(2)", enumAsRef = true)
    OFFLINE(2);
    @EnumValue
    private final int value;
    
    PaymentMode(int value) {
        this.value = value;
    }
    
    @JsonValue
    public int getValue() {
        return value;
    }
    
    @JsonCreator
    public static PaymentMode fromValue(Object value) {
        if (value == null || value.equals("") || value.equals(0)) {
            return OFFLINE;
        }
        
        // 处理整数类型
        if (value instanceof Integer) {
            int intValue = (Integer) value;
            return Arrays.stream(PaymentMode.values())
                    .filter(mode -> mode.value == intValue)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Unknown PaymentMode value: " + value));
        }
        // 处理字符串类型
        else if (value instanceof String) {
            String strValue = (String) value;
            // 尝试将字符串解析为整数
            try {
                int intValue = Integer.parseInt(strValue);
                return Arrays.stream(PaymentMode.values())
                        .filter(mode -> mode.value == intValue)
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Unknown PaymentMode value: " + value));
            } catch (NumberFormatException e) {
                // 如果不是数字，尝试匹配枚举名称（区分大小写）
                try {
                    return PaymentMode.valueOf(strValue);
                } catch (IllegalArgumentException ex) {
                    // 尝试忽略大小写匹配枚举名称
                    for (PaymentMode mode : PaymentMode.values()) {
                        if (mode.name().equalsIgnoreCase(strValue)) {
                            return mode;
                        }
                    }
                    
                    // 如果所有尝试都失败，抛出异常
                    throw new IllegalArgumentException("无法将值 '" + value + "' 转换为PaymentMode枚举");
                }
            }
        }
        // 处理其他类型
        else {
            // 尝试转换为字符串后再处理
            try {
                return fromValue(value.toString());
            } catch (Exception e) {
                throw new IllegalArgumentException("无法将类型 '" + value.getClass().getName() + "' 的值 '" + value + "' 转换为PaymentMode枚举");
            }
        }
    }
}