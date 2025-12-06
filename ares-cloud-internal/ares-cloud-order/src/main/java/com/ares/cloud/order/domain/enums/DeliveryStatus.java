package com.ares.cloud.order.domain.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import java.util.Arrays;

/**
 * 发货状态枚举
 */
@Getter
@Schema(enumAsRef = true, description = "发货状态枚举: 1-客户下单, 2-创建订单成功, 3-订单已接单, 4-配送中, 5-已取货, 6-完成配送, 7-已取消")
public enum DeliveryStatus {
    /**
     * 客户下单 - 初始状态
     */
    @Schema(description = "客户下单 - 初始状态")
    CUSTOMER_ORDER(1,"客户下单"),

    /**
     * 创建订单成功
     */
    @Schema(description = "创建订单成功")
    ORDER_CREATED(2,"创建订单成功"),
    /**
     * 订单已接单
     */
    @Schema(description = "订单已接单")
    ORDER_ACCEPTED(3,"订单已接单"),
    /**
     * 配送中 - 骑手配送中
     */
    @Schema(description = "配送中 - 骑手配送中")
    DELIVERING(4,"配送中"),

    /**
     * 已取货
     */
    @Schema(description = "已取货")
    PICKED_UP(5,"已取货"),

    /**
     * 完成配送
     */
    @Schema(description = "完成配送")
    DELIVERY_COMPLETED(6,"完成配送"),

    /**
     * 已取消
     */
    @Schema(description = "已取消")
    CANCELLED(7,"已取消");

    @EnumValue
    private final int value;
    private final String description;

    DeliveryStatus(int value, String description) {
        this.value = value;
        this.description = description;
    }

    @JsonValue
    public int getValue() {
        return value;
    }


    @JsonCreator
    public static DeliveryStatus of(Object value) {
        if (value == null) {
            return null;
        }
        // 支持前台传字符串或数字
        String str = value.toString();
        for (DeliveryStatus s : values()) {
            if (String.valueOf(s.value).equals(str)) {
                return s;
            }
        }
        throw new IllegalArgumentException("未知的配送状态: " + value);
    }
}