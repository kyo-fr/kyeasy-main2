package org.ares.cloud.businessId.enums;

import org.ares.cloud.common.exception.BaseErrorInfoInterface;
import org.springframework.http.HttpStatus;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2024/10/16 17:02
 */
public enum BusinessIdError implements BaseErrorInfoInterface {
    GENERATE_BUSINESS_ID_ERROR("generate_business_id_error");
    private final Integer code;
    private final String messageKey;
    BusinessIdError(String messageKey) {
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.messageKey = messageKey;
    }
    public Integer getCode() {
        return code;
    }
    public String getMessageKey() {
        return messageKey;
    }
}
