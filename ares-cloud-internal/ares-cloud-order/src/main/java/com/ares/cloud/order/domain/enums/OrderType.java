package com.ares.cloud.order.domain.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

/**
 * 订单类型枚举
 */
@Schema(enumAsRef = true, description = "订单类型枚举: 1-外卖配送, 2-到店自取, 3-店内就餐, 4-预订订单, 5-虚拟订单, 6-订阅类型订单, 7-线上订单")
public enum OrderType {
    /**
     * 外卖配送
     */
    @Schema(description = "外卖配送(1)", enumAsRef = true)
    DELIVERY(1),

    /**
     * 到店自取
     */
    @Schema(description = "到店自取(2)", enumAsRef = true)
    PICKUP(2),

    /**
     * 店内就餐
     */
    @Schema(description = "店内就餐(3)", enumAsRef = true)
    DINE_IN(3),

    /**
     * 预订订单
     */
    @Schema(description = "预订订单(4)", enumAsRef = true)
    RESERVATION(4),

    /**
     * 虚拟订单 - 无需配送，支付后直接完成
     */
    @Schema(description = "虚拟订单 - 无需配送，支付后直接完成(5)", enumAsRef = true)
    VIRTUAL(5),

    /**
     * 订阅类型订单，价格什么的都直接传入
     */
    @Schema(description = "订阅类型订单，价格什么的都直接传入(6)", enumAsRef = true)
    SUBSCRIPTION(6),

    /**
     * 线上订单 - 无需配送，支付后直接完成
     */
    @Schema(description = "线上订单 - 无需配送，支付后直接完成(7)", enumAsRef = true)
    ONLINE(7);
    @EnumValue
    private final int value;

    OrderType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    @JsonCreator
    public static OrderType fromValue(Object value) {
        if (value == null || value.equals("") || value.equals(0)) {
            return DELIVERY;
        }

        // 处理整数类型
        if (value instanceof Integer) {
            int intValue = (Integer) value;
            return getByValue(intValue);
        }
        // 处理字符串类型
        else if (value instanceof String) {
            String strValue = (String) value;
            // 尝试将字符串解析为整数
            try {
                int intValue = Integer.parseInt(strValue);
                return getByValue(intValue);
            } catch (NumberFormatException e) {
                // 如果不是数字，尝试匹配枚举名称（区分大小写）
                try {
                    return OrderType.valueOf(strValue);
                } catch (IllegalArgumentException ex) {
                    // 尝试忽略大小写匹配枚举名称
                    for (OrderType type : OrderType.values()) {
                        if (type.name().equalsIgnoreCase(strValue)) {
                            return type;
                        }
                    }

                    // 如果所有尝试都失败，抛出异常
                    throw new IllegalArgumentException("无法将值 '" + value + "' 转换为OrderType枚举");
                }
            }
        }
        // 处理其他类型
        else {
            // 尝试转换为字符串后再处理
            try {
                return fromValue(value.toString());
            } catch (Exception e) {
                throw new IllegalArgumentException("无法将类型 '" + value.getClass().getName() + "' 的值 '" + value + "' 转换为OrderType枚举");
            }
        }
    }

    /**
     * 根据数值获取枚举实例
     */
    private static OrderType getByValue(int value) {
        return Arrays.stream(OrderType.values())
                .filter(type -> type.value == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("无法找到值为 '" + value + "' 的OrderType枚举"));
    }

}