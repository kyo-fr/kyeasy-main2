package org.ares.cloud.enums;

import org.ares.cloud.common.exception.BaseErrorInfoInterface;
import org.springframework.http.HttpStatus;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2024/9/30 18:46
 */
public enum ExamplesError implements BaseErrorInfoInterface {
    SAY_HELLO("say.hello"),
    NAME_ERROR("name.error"),
    SAY_NAME("say.name");
    private Integer code;
    private String messageKey;
    ExamplesError(Integer code, String messageKey) {
        this.code = code;
        this.messageKey = messageKey;
    }
    ExamplesError(String message) {
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
