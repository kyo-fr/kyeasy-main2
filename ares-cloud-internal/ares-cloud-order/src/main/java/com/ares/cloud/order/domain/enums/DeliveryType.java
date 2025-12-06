package com.ares.cloud.order.domain.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;

/**
 * 配送方式
 */
@Schema(enumAsRef = true, description = "配送方式: 1-不配送, 2-商户自配送, 3-第三方配送")
public enum DeliveryType {
    /**
     * 不配送
     */
    @Schema(description = "不配送")
    NONE(1),
    /**
     * 商户自配送
     */
    @Schema(description = "商户自配送")
    MERCHANT(2),
    
    /**
     * 第三方配送
     */
    @Schema(description = "第三方配送")
    THIRD_PARTY(3);
    @EnumValue
    private final int value;
    
    DeliveryType(int value) {
        this.value = value;
    }
    
    @JsonValue
    public int getValue() {
        return value;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static DeliveryType fromValue(Object value) {
        if (value == null || value.equals("") || value.equals(0)) {
            return NONE;
        }

        // 处理整数类型
        if (value instanceof Integer) {
            int intValue = (Integer) value;
            return Arrays.stream(DeliveryType.values())
                    .filter(type -> type.value == intValue)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Unknown DeliveryType value: " + value));
        }
        // 处理字符串类型
        else if (value instanceof String) {
            String strValue = (String) value;
            // 尝试将字符串解析为整数
            try {
                int intValue = Integer.parseInt(strValue);
                return Arrays.stream(DeliveryType.values())
                        .filter(type -> type.value == intValue)
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Unknown DeliveryType value: " + value));
            } catch (NumberFormatException e) {
                // 如果不是数字，尝试匹配枚举名称（区分大小写）
                try {
                    return DeliveryType.valueOf(strValue);
                } catch (IllegalArgumentException ex) {
                    // 尝试忽略大小写匹配枚举名称
                    for (DeliveryType type : DeliveryType.values()) {
                        if (type.name().equalsIgnoreCase(strValue)) {
                            return type;
                        }
                    }

                    // 如果所有尝试都失败，抛出异常
                    throw new IllegalArgumentException("无法将值 '" + value + "' 转换为DeliveryType枚举");
                }
            }
        }
        // 处理其他类型
        else {
            // 尝试转换为字符串后再处理
            try {
                return fromValue(value.toString());
            } catch (Exception e) {
                throw new IllegalArgumentException("无法将类型 '" + value.getClass().getName() + "' 的值 '" + value + "' 转换为DeliveryType枚举");
            }
        }
    }
}