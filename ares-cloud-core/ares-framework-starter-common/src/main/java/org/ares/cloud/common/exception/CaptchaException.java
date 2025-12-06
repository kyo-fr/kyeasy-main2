package org.ares.cloud.common.exception;

import org.ares.cloud.common.enums.ResponseCodeEnum;

/**
 * @author hugo
 * @version 1.0
 * @description: 验证码异常
 * @date 2024/9/29 14:52
 */
public class CaptchaException extends BaseException{
    /**
     * 默认构造
     */
    public CaptchaException(){super();}
    /**
     * 默认的异常
     *
     * @param msg
     */
    public CaptchaException(String msg) {
        super(ResponseCodeEnum.REQUEST_PARAMETER_ERROR.getCode(),msg, ResponseCodeEnum.REQUEST_PARAMETER_ERROR.getMessageKey());
    }
    /**
     * 默认的异常
     *
     * @param msg
     */
    public CaptchaException(String msg,Exception e) {
        super(ResponseCodeEnum.REQUEST_PARAMETER_ERROR.getCode(),msg, ResponseCodeEnum.REQUEST_PARAMETER_ERROR.getMessageKey(),e);
    }
    /**
     * 默认的异常
     *
     * @param e 异常
     */
    public CaptchaException(Exception e) {
        super(ResponseCodeEnum.REQUEST_PARAMETER_ERROR,e);
    }
}
