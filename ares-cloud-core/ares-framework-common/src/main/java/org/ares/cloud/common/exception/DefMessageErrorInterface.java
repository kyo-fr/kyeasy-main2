package org.ares.cloud.common.exception;

/**
 * @author hugo
 * @version 1.0
 * @description: 给默认消息的错误
 * @date 2024/9/30 18:23
 */
public interface DefMessageErrorInterface  extends BaseErrorInfoInterface{

    /**
     * 错误描述
     */
    String getMsg();
}
