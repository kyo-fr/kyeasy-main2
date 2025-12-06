package org.ares.cloud.common.exception;

import org.ares.cloud.common.enums.ResponseCodeEnum;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2024/9/29 14:51
 */
public class DaoException extends BaseException{

    private static final long serialVersionUID = -1004067818388851972L;

    public DaoException() {
        super();
    }


    /**
     * 默认的异常
     *
     * @param msg
     */
    public DaoException(String msg) {
        super(ResponseCodeEnum.RECODE_BUS_ERR.getCode(),msg, ResponseCodeEnum.RECODE_BUS_ERR.getMessageKey());
    }
    /**
     * 默认的异常
     *
     * @param msg
     */
    public DaoException(String msg,Exception e) {
        super(ResponseCodeEnum.RECODE_BUS_ERR.getCode(),msg, ResponseCodeEnum.RECODE_BUS_ERR.getMessageKey(),e);
    }
    /**
     * 默认的异常
     *
     * @param e 异常
     */
    public DaoException(Exception e) {
        super(ResponseCodeEnum.RECODE_BUS_ERR,e);
    }
}

