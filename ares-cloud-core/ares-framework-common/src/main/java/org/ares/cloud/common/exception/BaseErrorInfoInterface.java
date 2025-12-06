package org.ares.cloud.common.exception;

import org.ares.cloud.common.enums.ResponseCodeEnum;

/**
 * @author hugo
 * @version 1.0
 * @description: 基础异常接口
 * @date 2024/9/23 16:47
 */
public interface BaseErrorInfoInterface {
    /**
     * 错误码
     */
    default Integer getCode(){
        return ResponseCodeEnum.RECODE_ERR.getCode();
    }
    /**
     * 国际化key
     */
    String getMessageKey();
}
