package org.ares.cloud.api.order.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true, description = "配送方式: 1-不配送, 2-商户自配送, 3-第三方配送")
public enum ApiDeliveryType {
    @Schema(description = "不配送")
    NONE(1),
    @Schema(description = "商户自配送")
    MERCHANT(2),
    @Schema(description = "第三方配送")
    THIRD_PARTY(3);
    private final int value;

    ApiDeliveryType(int value) {
        this.value = value;
    }
    
    @JsonValue
    public int getValue() {
        return value;
    }
    
    @JsonCreator
    public static ApiDeliveryType fromValue(int value) {
        return java.util.Arrays.stream(ApiDeliveryType.values())
                .filter(type -> type.value == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown DeliveryType value: " + value));
    }
}