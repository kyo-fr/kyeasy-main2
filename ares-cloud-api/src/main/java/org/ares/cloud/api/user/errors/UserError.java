package org.ares.cloud.api.user.errors;

import org.ares.cloud.common.exception.BaseErrorInfoInterface;
import org.springframework.http.HttpStatus;

/**
 * @author hugo
 * @version 1.0
 * @description: 异常
 * @date 2024/10/16 14:01
 */
public enum UserError implements BaseErrorInfoInterface {
    PHONE_CANNOT_BE_EMPTY("phone_cannot_be_empty"),
    UserBanned("user_banned"),
    USER_NOT_FOUND("user_not_found"),
    PASSWORD_IS_EMPTY("password_is_empty"),
    ORIGINAL_PASSWORD_ERROR("original_password_error"),
    THE_TWO_PASSWORDS_DO_NOT_MATCH("the_two_passwords_do_not_match"),
    ACCOUNT_OR_PASSWORD_ERROR("account_or_password_error"),
    PLATFORM_USERS_CANNOT_LOG_IN("platform_users_cannot_log_in"),
    NOT_PLATFORM_USERS_CANNOT_LOG_IN("not_platform_users_cannot_log_in"),
    NOT_KNIGHT_USER_CANNOT_LOG_IN("not_knight_user_cannot_log_in"),
    ;

    private final Integer code;
    private final String messageKey;
    UserError(Integer code, String messageKey) {
        this.code = code;
        this.messageKey = messageKey;
    }
    UserError(String message) {
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
