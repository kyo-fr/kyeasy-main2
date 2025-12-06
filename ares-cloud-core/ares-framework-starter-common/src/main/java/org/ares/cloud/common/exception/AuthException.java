package org.ares.cloud.common.exception;


import org.ares.cloud.common.enums.ResponseCodeEnum;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2024/9/23 16:47
 */
public class AuthException extends BaseException{
    /**
     * 默认构造
     */
    public AuthException(){super();}
    /**
     * 默认的异常
     *
     * @param msg
     */
    public AuthException(String msg) {
        super(ResponseCodeEnum.RECODE_TOKEN_BE_OVERDUE.getCode(),msg, ResponseCodeEnum.RECODE_TOKEN_BE_OVERDUE.getMessageKey());
    }
    /**
     * 默认的异常
     *
     * @param msg
     */
    public AuthException(String msg, Exception e) {
        super(ResponseCodeEnum.RECODE_TOKEN_BE_OVERDUE.getCode(),msg, ResponseCodeEnum.RECODE_TOKEN_BE_OVERDUE.getMessageKey(),e);
    }
}
