package com.ares.cloud.payment.domain.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.Arrays;

/**
 * 支付渠道枚举
 */
@Schema(enumAsRef = true, description = "支付渠道枚举: 1-现金, 2-银行转账, 3-支付宝, 4-微信支付, 5-银联, 6-其他")
@Getter
public enum PayChannel {
    /**
     * 现金
     */
    @Schema(description = "现金(1)", enumAsRef = true)
    CASH(1, "现金"),
    
    /**
     * 银行转账
     */
    @Schema(description = "银行转账(2)", enumAsRef = true)
    BANK_TRANSFER(2, "银行转账"),
    
    /**
     * 支付宝
     */
    @Schema(description = "支付宝(3)", enumAsRef = true)
    ALIPAY(3, "支付宝"),
    
    /**
     * 微信支付
     */
    @Schema(description = "微信支付(4)", enumAsRef = true)
    WECHAT_PAY(4, "微信支付"),
    
    /**
     * 银联
     */
    @Schema(description = "银联(5)", enumAsRef = true)
    UNION_PAY(5, "银联"),
    
    /**
     * 其他
     */
    @Schema(description = "其他(6)", enumAsRef = true)
    OTHER(6, "其他");
    
    @EnumValue
    private final int value;
    
    private final String description;
    
    PayChannel(int value, String description) {
        this.value = value;
        this.description = description;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    @JsonCreator
    public static PayChannel fromValue(Object value) {
        if (value == null || value.equals("") || value.equals(0)) {
            return CASH;
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
                    return PayChannel.valueOf(strValue);
                } catch (IllegalArgumentException ex) {
                    // 尝试忽略大小写匹配枚举名称
                    for (PayChannel channel : PayChannel.values()) {
                        if (channel.name().equalsIgnoreCase(strValue)) {
                            return channel;
                        }
                    }

                    // 如果所有尝试都失败，抛出异常
                    throw new IllegalArgumentException("无法将值 '" + value + "' 转换为PayChannel枚举");
                }
            }
        }
        // 处理其他类型
        else {
            // 尝试转换为字符串后再处理
            try {
                return fromValue(value.toString());
            } catch (Exception e) {
                throw new IllegalArgumentException("无法将类型 '" + value.getClass().getName() + "' 的值 '" + value + "' 转换为PayChannel枚举");
            }
        }
    }

    /**
     * 根据数值获取枚举实例
     */
    private static PayChannel getByValue(int value) {
        return Arrays.stream(PayChannel.values())
                .filter(channel -> channel.value == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("无法找到值为 '" + value + "' 的PayChannel枚举"));
    }
} 