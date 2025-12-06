package org.ares.cloud.exception;

import org.ares.cloud.common.exception.BaseException;
import org.springframework.http.HttpStatus;

/**
 * @author hugo
 * @version 1.0
 * @description: 服务不可用异常
 * 用于 Feign Fallback 中，当远程服务不可用时抛出
 * 会被统一异常处理器捕获并返回标准的 Result 格式
 * @date 2024/10/23
 */
public class ServiceUnavailableException extends BaseException {

    private final String serviceName;

    /**
     * 构造函数
     * @param serviceName 服务名称
     */
    public ServiceUnavailableException(String serviceName) {
        super(HttpStatus.SERVICE_UNAVAILABLE.value(), "服务不可用: " + serviceName, "service_unavailable");
        this.serviceName = serviceName;
    }

    /**
     * 构造函数（带详细信息）
     * @param serviceName 服务名称
     * @param detail 详细信息
     */
    public ServiceUnavailableException(String serviceName, String detail) {
        super(HttpStatus.SERVICE_UNAVAILABLE.value(), "服务不可用: " + serviceName + ", " + detail, "service_unavailable");
        this.serviceName = serviceName;
    }

    /**
     * 构造函数（带原始异常）
     * @param serviceName 服务名称
     * @param cause 原始异常
     */
    public ServiceUnavailableException(String serviceName, Exception cause) {
        super(HttpStatus.SERVICE_UNAVAILABLE.value(), "服务不可用: " + serviceName, "service_unavailable", cause);
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }

    @Override
    public Integer getCode() {
        return HttpStatus.SERVICE_UNAVAILABLE.value();
    }

    @Override
    public String getMessageKey() {
        return "service_unavailable";
    }
}

