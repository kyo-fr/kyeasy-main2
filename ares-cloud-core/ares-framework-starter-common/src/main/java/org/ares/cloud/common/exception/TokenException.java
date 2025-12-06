package org.ares.cloud.common.exception;


import org.ares.cloud.common.enums.ResponseCodeEnum;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2024/9/23 16:47
 */
public class TokenException extends  BaseException{
    /**
     * 默认的异常
     *
     * @param msg 异常
     */
    public TokenException(String msg) {
        super(ResponseCodeEnum.RECODE_TOKEN_NOT_AVAILABLE.getCode(),msg, ResponseCodeEnum.RECODE_TOKEN_NOT_AVAILABLE.getMessageKey());
    }
    /**
     * 默认的异常
     *
     * @param msg
     */
    public TokenException(String msg, Exception e) {
        super(ResponseCodeEnum.RECODE_TOKEN_NOT_AVAILABLE.getCode(),msg, ResponseCodeEnum.RECODE_TOKEN_NOT_AVAILABLE.getMessageKey(),e);
    }
    /**
     * 默认的异常
     *
     * @param e 异常
     */
    public TokenException(Exception e) {
        super(ResponseCodeEnum.RECODE_TOKEN_NOT_AVAILABLE,e);
    }

    /**
     * 异常
     * @param err 基础错误
     */
    public TokenException(BaseErrorInfoInterface err) {
        super(ResponseCodeEnum.RECODE_TOKEN_NOT_AVAILABLE.getCode(), ResponseCodeEnum.RECODE_TOKEN_NOT_AVAILABLE.getMessageKey());
    }
}
