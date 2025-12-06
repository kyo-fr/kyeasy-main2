package com.ares.cloud.order.domain.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.Arrays;

/**
 * 支付状态枚举
 */
@Schema(enumAsRef = true, description = "支付状态枚举: 1-未支付, 2-部分支付, 3-已支付, 4-已退款, 5-部分退款")
@Getter
public enum PaymentStatus {
    /**
     * 未支付
     */
    @Schema(description = "未支付状态(1)")
    UNPAID(1,"未支付"),
    
    /**
     * 部分支付
     */
    @Schema(description = "部分支付状态(2)")
    PARTIALLY_PAID(2,"部分支付"),
    
    /**
     * 已支付
     */
    @Schema(description = "已支付状态(3)")
    PAID(3,"已支付"),
    
    /**
     * 已退款
     */
    @Schema(description = "已退款状态(4)")
    REFUNDED(4,"已退款"),
    
    /**
     * 部分退款
     */
    @Schema(description = "部分退款状态(5)")
    PARTIALLY_REFUNDED(5,"部分退款");

    @EnumValue
    private final int value;
    private final String description;
    
    PaymentStatus(int value, String description) {
        this.value = value;
        this.description = description;
    }
    
    @JsonValue
    public int getValue() {
        return value;
    }

    
    @JsonCreator
    public static PaymentStatus fromValue(Object value) {
        if (value == null || value.equals("") || value.equals(0)) {
            return UNPAID;
        }

        // 处理整数类型
        if (value instanceof Integer) {
            int intValue = (Integer) value;
            return Arrays.stream(PaymentStatus.values())
                    .filter(status -> status.value == intValue)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Unknown PaymentStatus value: " + value));
        }
        // 处理字符串类型
        else if (value instanceof String) {
            String strValue = (String) value;
            // 尝试将字符串解析为整数
            try {
                int intValue = Integer.parseInt(strValue);
                return Arrays.stream(PaymentStatus.values())
                        .filter(status -> status.value == intValue)
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Unknown PaymentStatus value: " + value));
            } catch (NumberFormatException e) {
                // 如果不是数字，尝试匹配枚举名称（区分大小写）
                try {
                    return PaymentStatus.valueOf(strValue);
                } catch (IllegalArgumentException ex) {
                    // 尝试忽略大小写匹配枚举名称
                    for (PaymentStatus status : PaymentStatus.values()) {
                        if (status.name().equalsIgnoreCase(strValue)) {
                            return status;
                        }
                    }

                    // 如果所有尝试都失败，抛出异常
                    throw new IllegalArgumentException("无法将值 '" + value + "' 转换为PaymentStatus枚举");
                }
            }
        }
        // 处理其他类型
        else {
            // 尝试转换为字符串后再处理
            try {
                return fromValue(value.toString());
            } catch (Exception e) {
                throw new IllegalArgumentException("无法将类型 '" + value.getClass().getName() + "' 的值 '" + value + "' 转换为PaymentStatus枚举");
            }
        }
    }
}