package org.ares.cloud.enums;

import org.ares.cloud.common.exception.BaseErrorInfoInterface;
import org.springframework.http.HttpStatus;

/**
 * @author hugo
 * @version 1.0
 * @description: 认证的错误
 * @date 2024/9/30 18:46
 */
public enum AuthError implements BaseErrorInfoInterface {
    TOKEN_SAVE_ERROR("token_save_error"),
    PASSWORD_CANNOT_BE_EMPTY("password_cannot_be_empty"),
    THE_TWO_PASSWORD_DOES_NOT_MATCH("the_two_password_does_not_match"),
    REGISTER_ACCOUNT_ERROR("register_account_error"),
    ACCOUNT_ALREADY_EXIST("account_already_exist"),
    REFRESH_TOKEN_INVALID("refresh_token_invalid"),
    TOkEN_INVALID("token_invalid"),
    SAVE_SIGN_ERROR("save_sign_error"),
    MERCHANT_INFO_IS_NOT_EXIST_ERROR("merchant_info_is_not_exist_error"),
    INVALID_SIGN("invalid_sign"),;
    private final Integer code;
    private final String messageKey;
    AuthError(Integer code, String messageKey) {
        this.code = code;
        this.messageKey = messageKey;
    }
    AuthError(String message) {
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.messageKey = message;
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
