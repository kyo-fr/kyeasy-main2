package org.ares.cloud.common.exception;

import org.ares.cloud.common.enums.ResponseCodeEnum;

import java.io.Serial;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2024/9/29 14:53
 */
public class FileUploadException extends BaseException {
    @Serial
    private static final long serialVersionUID = 1L;

    private Object[] args;
    public FileUploadException(Integer code, Object[] args, String msg) {
        super(ResponseCodeEnum.RECODE_FILE_DATA.getCode(),msg, ResponseCodeEnum.RECODE_FILE_DATA.getMessageKey());
        this.args = args;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
    /**
     * 默认的异常
     *
     * @param e 异常
     */
    public FileUploadException(Exception e) {
        super(ResponseCodeEnum.RECODE_FILE_DATA,e);
    }
    /**
     * 默认的异常
     *
     * @param reason 错误原因
     */
    public FileUploadException(String reason) {
        super(ResponseCodeEnum.RECODE_FILE_DATA.getCode(),ResponseCodeEnum.RECODE_FILE_DATA.getMsg(),reason);
    }
}
