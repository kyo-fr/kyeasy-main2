package org.ares.cloud.exception;

import org.ares.cloud.common.exception.BaseErrorInfoInterface;
import org.ares.cloud.common.exception.BaseException;
import org.ares.cloud.common.exception.DefMessageErrorInterface;
import org.ares.cloud.common.model.Result;
import org.ares.cloud.common.utils.ExceptionUtils;
import org.springframework.http.HttpStatus;

/**
 * @author hugo
 * @version 1.0
 * @description: RPC 调用异常
 * 支持多种构建方式：
 * 1. 包装普通异常
 * 2. 包装 BaseException 并透传其信息
 * 3. 从远程服务的 Result 响应构建，保留所有上下文信息
 * @date 2024/10/16 14:51
 */
public class RpcCallException extends BaseException {

    /**
     * 远程服务返回的异常上下文信息
     */
    private String remoteExceptionContext;

    /**
     * 构造函数：包装普通异常
     * @param e 异常对象
     */
    public RpcCallException(Exception e) {
        this.exception = e;
    }

    /**
     * 构造函数：从远程服务的 Result 响应构建
     * 保留远程服务的所有错误上下文信息
     * 
     * @param code 错误码
     * @param msg 错误消息
     * @param reason 错误原因（messageKey）
     * @param remoteException 远程异常信息
     */
    public RpcCallException(Integer code, String msg, String reason, String remoteException) {
        super(code, msg, reason);
        this.remoteExceptionContext = remoteException;
    }

    /**
     * 构造函数：从 Result 对象构建
     * @param result 远程服务返回的 Result 对象
     */
    public RpcCallException(Result<?> result) {
        super(result.getCode(), result.getMsg(), result.getReason());
        this.remoteExceptionContext = result.getException();
    }

    /**
     * 构造函数：从 BaseException 构建并附加远程异常信息
     * @param baseException 基础异常
     * @param remoteException 远程异常信息
     */
    public RpcCallException(BaseException baseException, String remoteException) {
        super(baseException.getCode(), baseException.getMsg(), baseException.getMessageKey());
        this.exception = baseException.getException();
        this.remoteExceptionContext = remoteException;
    }

    @Override
    public Integer getCode() {
        // 如果有直接设置的 code，使用它
        if (code != null) {
            return code;
        }
        // 如果包装的异常实现了 BaseErrorInfoInterface，使用其错误码
        if (exception instanceof BaseErrorInfoInterface baseErrorInfo) {
            return baseErrorInfo.getCode();
        }
        // 否则使用默认的 503 错误码
        return HttpStatus.SERVICE_UNAVAILABLE.value();
    }

    @Override
    public String getMsg() {
        // 如果有直接设置的 msg，使用它
        if (msg != null) {
            return msg;
        }
        // 如果包装的异常实现了 DefMessageErrorInterface
        if (exception instanceof DefMessageErrorInterface defMessageError) {
            String message = defMessageError.getMsg();
            // 如果该异常内部还包装了其他异常，将其信息也追加上
            if (exception instanceof BaseException baseException) {
                Exception innerException = baseException.getException();
                if (innerException != null) {
                    String innerMsg = ExceptionUtils.getExceptionMessage(innerException);
                    if (innerMsg != null && !innerMsg.isEmpty()) {
                        return message != null ? message + "; Cause: " + innerMsg : innerMsg;
                    }
                }
            }
            return message;
        }
        // 否则使用默认消息
        return "RPC service call failed";
    }

    @Override
    public String getMessageKey() {
        // 如果有直接设置的 msgKey，使用它
        if (msgKey != null) {
            return msgKey;
        }
        // 如果包装的异常实现了 BaseErrorInfoInterface，使用其消息键
        if (exception instanceof BaseErrorInfoInterface baseErrorInfo) {
            return baseErrorInfo.getMessageKey();
        }
        // 否则使用默认的 RPC 异常消息键
        return "rpc_service_exception";
    }

    @Override
    public Exception getException() {
        // 如果包装的异常是 BaseException，并且它内部还有异常，返回最内层的异常
        if (exception instanceof BaseException baseException) {
            Exception innerException = baseException.getException();
            if (innerException != null) {
                return innerException;
            }
        }
        return exception;
    }

    public void setException(Exception e) {
        this.exception = e;
    }

    /**
     * 获取包装的原始异常
     * @return 包装的异常对象
     */
    public Exception getWrappedException() {
        return exception;
    }

    /**
     * 获取远程异常上下文信息
     * @return 远程异常信息
     */
    public String getRemoteExceptionContext() {
        return remoteExceptionContext;
    }

    /**
     * 设置远程异常上下文信息
     * @param remoteException 远程异常信息
     */
    public void setRemoteExceptionContext(String remoteException) {
        this.remoteExceptionContext = remoteException;
    }

    /**
     * 获取完整的异常消息，包括所有层级的异常信息
     * @return 完整的异常消息
     */
    public String getFullExceptionMessage() {
        StringBuilder fullMessage = new StringBuilder();
        
        // 1. 添加远程异常上下文（如果有）
        if (remoteExceptionContext != null && !remoteExceptionContext.isEmpty()) {
            fullMessage.append(remoteExceptionContext);
        }
        
        // 2. 添加包装异常的信息
        if (exception != null) {
            String exceptionMsg = ExceptionUtils.getExceptionMessage(exception);
            if (exceptionMsg != null && !exceptionMsg.isEmpty()) {
                if (fullMessage.length() > 0) {
                    fullMessage.append(" -> ");
                }
                fullMessage.append(exceptionMsg);
            }
            
            // 3. 如果是 BaseException，获取其内部异常信息
            if (exception instanceof BaseException baseException) {
                Exception innerException = baseException.getException();
                if (innerException != null) {
                    String innerMsg = ExceptionUtils.getExceptionMessage(innerException);
                    if (innerMsg != null && !innerMsg.isEmpty()) {
                        fullMessage.append(" -> Caused by: ").append(innerMsg);
                    }
                }
            }
        }
        
        // 如果没有任何异常信息，返回消息键
        if (fullMessage.length() == 0) {
            return this.getMessageKey();
        }
        
        return fullMessage.toString();
    }

    public String getExMessage() {
        return getFullExceptionMessage();
    }

    /**
     * 是否来自远程服务的错误响应
     * @return true 如果是从 Result 构建的
     */
    public boolean isFromRemoteResult() {
        return code != null || msgKey != null || remoteExceptionContext != null;
    }
}

