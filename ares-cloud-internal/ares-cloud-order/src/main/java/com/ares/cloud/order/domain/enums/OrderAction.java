package com.ares.cloud.order.domain.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.Arrays;

/**
 * 订单动作枚举
 */
@Schema(enumAsRef = true, description = "订单动作枚举: 1-创建订单, 2-支付订单, 3-部分支付, 4-确认订单, 5-待就餐, 6-开始配送, 7-完成配送, 8-开始排队, 9-结束排队, 10-开始就餐, 11-就餐中, 12-完成就餐, 13-取消订单, 14-申请退款, 15-退款, 16-完成, 17-退款完成, 18-接单, 19-删除商品")
@Getter
public enum OrderAction {
    /**
     * 创建订单
     */
    @Schema(description = "创建订单(1)", enumAsRef = true)
    CREATE(1, "创建订单"),
    
    /**
     * 支付订单
     */
    @Schema(description = "支付订单(2)", enumAsRef = true)
    PAY(2, "支付订单"),
    
    /**
     * 部分支付
     */
    @Schema(description = "部分支付(3)", enumAsRef = true)
    PARTIAL_PAY(3, "部分支付"),
    
    /**
     * 确认订单
     */
    @Schema(description = "确认订单(4)", enumAsRef = true)
    CONFIRM(4, "确认订单"),
    
    /**
     * 待就餐
     */
    @Schema(description = "待就餐(5)", enumAsRef = true)
    WAITING_DINING(5, "待就餐"),
    
    /**
     * 开始配送
     */
    @Schema(description = "开始配送(6)", enumAsRef = true)
    START_DELIVERY(6, "开始配送"),
    
    /**
     * 完成配送
     */
    @Schema(description = "完成配送(7)", enumAsRef = true)
    COMPLETE_DELIVERY(7, "完成配送"),

    /**
     * 开始排队
     */
    @Schema(description = "开始排队(8)", enumAsRef = true)
    START_QUEUE(8, "开始排队"),
    
    /**
     * 结束排队
     */
    @Schema(description = "结束排队(9)", enumAsRef = true)
    END_QUEUE(9, "结束排队"),

    /**
     * 开始就餐
     */
    @Schema(description = "开始就餐(10)", enumAsRef = true)
    START_DINING(10, "开始就餐"),
    
    /**
     * 就餐中
     */
    @Schema(description = "就餐中(11)", enumAsRef = true)
    DINING_IN_PROGRESS(11, "就餐中"),
    
    /**
     * 完成就餐
     */
    @Schema(description = "完成就餐(12)", enumAsRef = true)
    COMPLETE_DINING(12, "完成就餐"),
    
    /**
     * 取消订单
     */
    @Schema(description = "取消订单(13)", enumAsRef = true)
    CANCEL(13, "取消订单"),
    
    /**
     * 申请退款
     */
    @Schema(description = "申请退款(14)", enumAsRef = true)
    REQUEST_REFUND(14, "申请退款"),

    /**
     * 退款
     */
    @Schema(description = "退款(15)", enumAsRef = true)
    REFUND(15, "退款"),
    
    /**
     * 完成
     */
    @Schema(description = "完成(16)", enumAsRef = true)
    COMPLETE(16, "完成"),
    
    /**
     * 退款完成
     */
    @Schema(description = "退款完成(17)", enumAsRef = true)
    COMPLETE_REFUND(17, "退款完成"),

    /**
     * 接单
     */
    @Schema(description = "接单(18)", enumAsRef = true)
    ACCEPT_DELIVERY(18, "接单"),
    
    /**
     * 删除商品
     */
    @Schema(description = "删除商品(19)", enumAsRef = true)
    REMOVE_ITEM(19, "删除商品");
    
    @EnumValue
    private final int value;
    
    private final String description;
    
    OrderAction(int value, String description) {
        this.value = value;
        this.description = description;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    @JsonCreator
    public static OrderAction fromValue(Object value) {
        if (value == null || value.equals("") || value.equals(0)) {
            return CREATE;
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
                    return OrderAction.valueOf(strValue);
                } catch (IllegalArgumentException ex) {
                    // 尝试忽略大小写匹配枚举名称
                    for (OrderAction action : OrderAction.values()) {
                        if (action.name().equalsIgnoreCase(strValue)) {
                            return action;
                        }
                    }

                    // 如果所有尝试都失败，抛出异常
                    throw new IllegalArgumentException("无法将值 '" + value + "' 转换为OrderAction枚举");
                }
            }
        }
        // 处理其他类型
        else {
            // 尝试转换为字符串后再处理
            try {
                return fromValue(value.toString());
            } catch (Exception e) {
                throw new IllegalArgumentException("无法将类型 '" + value.getClass().getName() + "' 的值 '" + value + "' 转换为OrderAction枚举");
            }
        }
    }

    /**
     * 根据数值获取枚举实例
     */
    private static OrderAction getByValue(int value) {
        return Arrays.stream(OrderAction.values())
                .filter(action -> action.value == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("无法找到值为 '" + value + "' 的OrderAction枚举"));
    }
}