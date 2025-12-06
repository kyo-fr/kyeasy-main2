package org.ares.cloud.api.order.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true, description = "订单类型: 1-外卖配送, 2-到店自取, 3-店内就餐, 4-预订订单, 5-虚拟订单, 6-订阅订单, 7-线上订单")
public enum ApiOrderType {
    @Schema(description = "外卖配送")
    DELIVERY(1),
    @Schema(description = "到店自取")
    PICKUP(2),
    @Schema(description = "店内就餐")
    DINE_IN(3),
    @Schema(description = "预订订单")
    RESERVATION(4),
    @Schema(description = "虚拟订单")
    VIRTUAL(5),
    @Schema(description = "订阅订单")
    SUBSCRIPTION(6),
    @Schema(description = "线上订单")
    ONLINE(7);
    private final int value;

    ApiOrderType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    @JsonCreator
    public static ApiOrderType fromValue(int value) {
        return java.util.Arrays.stream(ApiOrderType.values())
                .filter(type -> type.value == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown OrderType value: " + value));
    }

}