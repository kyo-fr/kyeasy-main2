package org.ares.cloud.common.exception;

import org.ares.cloud.common.enums.ResponseCodeEnum;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2024/9/23 19:59
 */
public class BaseException extends RuntimeException implements DefMessageErrorInterface{
    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    protected Integer code;
    /**
     * 错误信息
     */
    protected String msg;
    /**
     * 国际化key
     */
    protected String msgKey;
    /**
     * 获取异常
     */
    protected Exception exception;

    public BaseException() {
        super();
    }

    /**
     * 默认的异常
     *
     * @param msg 异常信息
     */
    protected BaseException(String msg) {
        super(msg);
        this.msg = msg;
        this.code = ResponseCodeEnum.RECODE_ERR.getCode();
        this.msg = ResponseCodeEnum.RECODE_ERR.getMsg();
        this.msgKey = ResponseCodeEnum.RECODE_ERR.getMessageKey();
    }
    /**
     * 默认的异常
     *
     * @param msg 异常信息
     */
    public BaseException(String msg,Exception exception) {
        super(msg);
        this.msg = msg;
        this.code = ResponseCodeEnum.RECODE_ERR.getCode();
        this.exception =  exception;
    }
    /**
     * 默认的异常
     *
     * @param msg 异常消息
     */
    public BaseException(String msg, Throwable cause) {
        super(msg);
        this.msg = msg;
        this.code = ResponseCodeEnum.RECODE_ERR.getCode();
    }

    public BaseException(Integer code, String msg) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public BaseException(Integer code, String msg, String msgKey, Exception exception) {
        this.code = code;
        this.msg = msg;
        this.msgKey = msgKey;
        this.exception = exception;
    }
    public BaseException(Integer code, String msg, String msgKey) {
        this.code = code;
        this.msg = msg;
        this.msgKey = msgKey;
    }
    public BaseException(DefMessageErrorInterface err) {
        this.code = err.getCode();
        this.msg = err.getMsg();
        this.msgKey = err.getMessageKey();
    }

    public BaseException(DefMessageErrorInterface err,Exception exception) {
        this.code = err.getCode();
        this.msg = err.getMsg();
        this.msgKey = err.getMessageKey();
        this.exception = exception;
    }
    public BaseException(BaseErrorInfoInterface err) {
        this.code = err.getCode();
        this.msgKey = err.getMessageKey();
    }

    public BaseException(BaseErrorInfoInterface err,Exception exception) {
        this.code = err.getCode();
        this.msgKey = err.getMessageKey();
        this.exception = exception;
    }


    /**
     * 错误码
     */
    @Override
    public Integer getCode() {
        return code;
    }

    /**
     * 错误描述
     */
    @Override
    public String getMsg() {
        return msg;
    }



    @Override
    public String getMessageKey() {
        return this.msgKey;
    }

    public void setMessageKey(String msgKey) {
        this.msgKey = msgKey;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsgKey() {
        return msgKey;
    }

    public void setMsgKey(String msgKey) {
        this.msgKey = msgKey;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
