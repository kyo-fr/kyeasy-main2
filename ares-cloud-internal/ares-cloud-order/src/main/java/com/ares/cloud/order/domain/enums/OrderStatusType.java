package com.ares.cloud.order.domain.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(enumAsRef = true, description = "状态类型: 1-订单状态, 2-支付状态, 3-配送状态")
public enum OrderStatusType {
    ORDER("订单状态", 1),
    PAYMENT("支付状态", 2),
    DELIVERY("配送状态", 3);

    private final String desc;
    @JsonValue
    @EnumValue
    private final int code;

    OrderStatusType(String desc, int code) {
        this.desc = desc;
        this.code = code;
    }

    @JsonCreator
    public static OrderStatusType fromCode(int code) {
        for (OrderStatusType type : OrderStatusType.values()) {
            if (type.code == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid OrderStatusType code: " + code);
    }

}