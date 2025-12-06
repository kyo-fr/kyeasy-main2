package org.ares.cloud.common.enums;

/**
 * @author hugo  tangxkwork@163.com
 * @description 是或否的枚举
 * @date 2024/01/17/14:49
 **/
public enum YesOrNotEnum {
    /**
     * 是
     */
    Y("Y", "是"),

    /**
     * 否
     */
    N("N", "否");
    private final String code;

    private final String message;

    YesOrNotEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
