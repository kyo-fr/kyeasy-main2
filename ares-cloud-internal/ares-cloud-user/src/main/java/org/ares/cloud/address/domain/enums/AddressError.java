package org.ares.cloud.address.domain.enums;

import org.ares.cloud.common.exception.BaseErrorInfoInterface;
import org.springframework.http.HttpStatus;

/**
 * 地址相关错误枚举
 */
public enum AddressError implements BaseErrorInfoInterface {
    // 地址类型错误
    ADDRESS_TYPE_CANNOT_BE_EMPTY("address_type_cannot_be_empty"),
    
    // 公司地址错误
    COMPANY_NAME_CANNOT_BE_EMPTY("company_name_cannot_be_empty"),
    
    // 地址信息错误
    CITY_CANNOT_BE_EMPTY("city_cannot_be_empty"),
    DETAIL_CANNOT_BE_EMPTY("detail_cannot_be_empty"),
    LATITUDE_CANNOT_BE_EMPTY("latitude_cannot_be_empty"),
    LONGITUDE_CANNOT_BE_EMPTY("longitude_cannot_be_empty"),
    
    // 用户地址错误
    USER_ID_CANNOT_BE_EMPTY("user_id_cannot_be_empty"),
    ADDRESS_CANNOT_BE_EMPTY("address_cannot_be_empty");
    
    private final Integer code;
    private final String messageKey;
    
    AddressError(Integer code, String messageKey) {
        this.code = code;
        this.messageKey = messageKey;
    }
    
    AddressError(String messageKey) {
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.messageKey = messageKey;
    }
    
    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMessageKey() {
        return messageKey;
    }
}