package org.ares.cloud.rider.enums;

import org.ares.cloud.common.exception.BaseErrorInfoInterface;
import org.springframework.http.HttpStatus;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2025/8/26 16:45
 */
public enum RiderError implements BaseErrorInfoInterface {
    RIDER_NOT_EXIST("rider_not_exist"),
    RIDER_EXIST("rider_exist"),
    RIDER_NOT_FOUND("rider_not_found"),
    RIDER_NOT_LOGIN("rider_not_login"),
    RIDER_NOT_AUTHORIZED("rider_not_authorized"),
    RIDER_NOT_ACTIVE("rider_not_active"),
    RIDER_NOT_FOUND_BY_PHONE("rider_not_found_by_phone"),
    NON_MERCHANTS_CANNOT_ADD_KNIGHTS("non_merchants_cannot_add_knights")
    ;
    private final Integer code;
    private final String messageKey;

    RiderError(Integer code, String messageKey) {
        this.code = code;
        this.messageKey = messageKey;
    }

    RiderError(String messageKey) {
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.messageKey = messageKey;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMessageKey() {
        return this.messageKey;
    }
}
