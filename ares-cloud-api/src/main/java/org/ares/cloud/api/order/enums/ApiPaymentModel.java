package org.ares.cloud.api.order.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true, description = "支付方式: 1-在线支付, 2-线下支付")
public enum ApiPaymentModel {
    @Schema(description = "在线支付")
    ONLINE(1),
    @Schema(description = "线下支付")
    OFFLINE(2);
    private final int value;

    ApiPaymentModel(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    @JsonCreator
    public static ApiPaymentModel fromValue(int value) {
        return java.util.Arrays.stream(ApiPaymentModel.values())
                .filter(mode -> mode.value == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown PaymentMode value: " + value));
    }
}