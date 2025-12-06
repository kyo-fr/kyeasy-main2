package org.ares.cloud.common.exception;

import org.ares.cloud.common.enums.ResponseCodeEnum;

/**
 * @author hugo
 * @version 1.0
 * @description: 锁异常
 * @date 2024/9/29 14:52
 */
public class LockException extends BaseException{
    /**
     * 默认构造
     */
    public LockException(){super();}
    /**
     * 默认的异常
     *
     * @param msg
     */
    public LockException(String msg) {
        super(ResponseCodeEnum.RECODE_BUS_ERR.getCode(),msg, ResponseCodeEnum.RECODE_BUS_ERR.getMessageKey());
    }
    /**
     * 默认的异常
     *
     * @param msg
     */
    public LockException(String msg,Exception e) {
        super(ResponseCodeEnum.RECODE_BUS_ERR.getCode(),msg, ResponseCodeEnum.RECODE_BUS_ERR.getMessageKey(),e);
    }
    /**
     * 默认的异常
     *
     * @param e 异常
     */
    public LockException(Exception e) {
        super(ResponseCodeEnum.RECODE_BUS_ERR,e);
    }
}
