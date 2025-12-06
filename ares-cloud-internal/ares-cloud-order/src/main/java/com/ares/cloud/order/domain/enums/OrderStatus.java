package com.ares.cloud.order.domain.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;

/**
 * 订单状态枚举
 */
@Schema(enumAsRef = true, description = "订单状态枚举: 1-待确认, 2-未结算, 3-部分结算, 4-已结算, 5-待就餐, 6-就餐中, 7-已取消")
public enum OrderStatus {
     /**
     * 待确认 - 需要确认才能进入正常流程
     */
    @Schema(description = "待确认 - 需要确认才能进入正常流程(1)", enumAsRef = true)
    TO_BE_CONFIRMED(1,"待确认"),
    /**
     * 未结算 - 订单未进行结算
     */
    @Schema(description = "未结算 - 订单未进行结算(2)", enumAsRef = true)
    UNSETTLED(2, "未结算"),

    /**
     * 部分结算 - 订单已部分结算但未完全结算
     */
    @Schema(description = "部分结算 - 订单已部分结算但未完全结算(3)", enumAsRef = true)
    PARTIALLY_SETTLED(3, "部分结算"),

    /**
     * 已结算 - 订单已完全结算
     */
    @Schema(description = "已结算 - 订单已完全结算(4)", enumAsRef = true)
    SETTLED(4, "已结算"),

    /**
     * 待就餐 - 订单已确认，等待就餐
     */
    @Schema(description = "待就餐 - 订单已确认，等待就餐(5)", enumAsRef = true)
    WAITING_DINING(5, "待就餐"),

    /**
     * 就餐中 - 客户正在就餐
     */
    @Schema(description = "就餐中 - 客户正在就餐(6)", enumAsRef = true)
    DINING_IN_PROGRESS(6, "就餐中"),

    /**
     * 已取消 - 订单已取消
     */
    @Schema(description = "已取消 - 订单已取消(7)", enumAsRef = true)
    CANCELLED(7, "已取消");

    @EnumValue
    private final int value;
    private final String description;

    OrderStatus(int value, String description) {
        this.value = value;
        this.description = description;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
    
    @JsonCreator
    public static OrderStatus fromValue(Object value) {
        if (value == null || value.equals("") || value.equals(0)) {
            return TO_BE_CONFIRMED;
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
                    return OrderStatus.valueOf(strValue);
                } catch (IllegalArgumentException ex) {
                    // 尝试忽略大小写匹配枚举名称
                    for (OrderStatus status : OrderStatus.values()) {
                        if (status.name().equalsIgnoreCase(strValue)) {
                            return status;
                        }
                    }
                    
                    // 如果所有尝试都失败，返回null而不是抛出异常
                    return null;
                }
            }
        }
        // 处理其他类型
        else {
            // 尝试转换为字符串后再处理
            try {
                return fromValue(value.toString());
            } catch (Exception e) {
                return null;
            }
        }
    }
    
    /**
     * 根据数值获取枚举实例
     */
    private static OrderStatus getByValue(int value) {
        return Arrays.stream(OrderStatus.values())
                .filter(status -> status.value == value)
                .findFirst()
                .orElse(null);
    }

}
