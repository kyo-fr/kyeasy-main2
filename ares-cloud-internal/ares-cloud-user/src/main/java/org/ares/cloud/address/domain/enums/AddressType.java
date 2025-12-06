package org.ares.cloud.address.domain.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 地址类型枚举
 */
public enum AddressType {
    /**
     * 个人地址
     */
    PERSONAL(1, "个人地址"),

    /**
     * 公司地址
     */
    COMPANY(2, "公司地址");

    @EnumValue
    private final int value;
    @Getter
    private final String description;

    AddressType(int value, String description) {
        this.value = value;
        this.description = description;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    /**
     * 根据数值获取枚举实例
     *
     * @param value 枚举数值
     * @return 对应的枚举实例
     */
    public static AddressType getByValue(int value) {
        for (AddressType type : AddressType.values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("无法找到值为 '" + value + "' 的AddressType枚举");
    }

    /**
     * JSON反序列化时使用的转换方法
     *
     * @param value 需要转换的值
     * @return 对应的枚举实例
     */
    @JsonCreator
    public static AddressType fromValue(Object value) {
        if (value == null) {
            return null;
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
                    return AddressType.valueOf(strValue);
                } catch (IllegalArgumentException ex) {
                    // 尝试忽略大小写匹配枚举名称
                    for (AddressType type : AddressType.values()) {
                        if (type.name().equalsIgnoreCase(strValue)) {
                            return type;
                        }
                    }
                    
                    // 如果所有尝试都失败，抛出异常
                    throw new IllegalArgumentException("无法将值 '" + value + "' 转换为AddressType枚举");
                }
            }
        }
        // 处理其他类型
        else {
            // 尝试转换为字符串后再处理
            try {
                return fromValue(value.toString());
            } catch (Exception e) {
                throw new IllegalArgumentException("无法将类型 '" + value.getClass().getName() + "' 的值 '" + value + "' 转换为AddressType枚举");
            }
        }
    }
}