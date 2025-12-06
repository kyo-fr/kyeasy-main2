package org.ares.cloud.common.exception;


import org.ares.cloud.common.enums.ResponseCodeEnum;

import java.io.Serial;

/**
 * @Author hugo  tangxkwork@163.com
 * @description 业务异常
 * @date 2024/01/17/14:46
 **/
public class BusinessException extends BaseException{

    @Serial
    private static final long serialVersionUID = 1L;

    public BusinessException() {
        super();
    }

    /**
     * 基础异常的处理
     */
    public BusinessException(BaseErrorInfoInterface err) {
        super(err);
    }

    public BusinessException(BaseErrorInfoInterface err, Throwable cause) {
        super(err);
    }
    public BusinessException(BaseErrorInfoInterface err, Exception exception) {
        super(err);
        this.exception = exception;
    }
    public BusinessException(DefMessageErrorInterface err) {
        super(err);
    }

    public BusinessException(DefMessageErrorInterface err, Throwable cause) {
        super(err);
    }
    public BusinessException(DefMessageErrorInterface err, Exception exception) {
        super(err);
        this.exception = exception;
    }
    /**
     * 默认的异常
     */
    public BusinessException(String msg) {
        super(ResponseCodeEnum.RECODE_BUS_ERR.getCode(),msg, ResponseCodeEnum.RECODE_BUS_ERR.getMessageKey());
    }

    /**
     * 默认的异常
     */
    public BusinessException(Exception e) {
        super(ResponseCodeEnum.RECODE_BUS_ERR,e);
    }
    public BusinessException(Integer code, String msg) {
        super(code,msg);
    }

    public BusinessException(Integer code, String msg, String msgKey) {
        super(code,msg,msgKey);
    }
}
