package org.ares.cloud.common.exception;

import org.ares.cloud.common.enums.ResponseCodeEnum;

/**
 * @author hugo
 * @version 1.0
 * @description: 锁异常
 * @date 2024/9/29 14:52
 */
public class NotFoundException extends BaseException{
    /**
     * 默认构造
     */
    public NotFoundException(){super(ResponseCodeEnum.RECODE_NOT_FOUND.getCode(),ResponseCodeEnum.RECODE_NOT_FOUND.getMessageKey());}
    /**
     * 默认的异常
     *
     * @param msg
     */
    public NotFoundException(String msg) {
        super(ResponseCodeEnum.RECODE_NOT_FOUND.getCode(),msg, ResponseCodeEnum.RECODE_NOT_FOUND.getMessageKey());
    }
    /**
     * 默认的异常
     *
     * @param msg
     */
    public NotFoundException(String msg, Exception e) {
        super(ResponseCodeEnum.RECODE_NOT_FOUND.getCode(),msg, ResponseCodeEnum.RECODE_NOT_FOUND.getMessageKey(),e);
    }
    /**
     * 默认的异常
     *
     * @param e 异常
     */
    public NotFoundException(Exception e) {
        super(ResponseCodeEnum.RECODE_NOT_FOUND,e);
    }

    /**
     * 新增
     * @param err
     */
    public NotFoundException(BaseErrorInfoInterface err) {
      super(err);
    }
}
