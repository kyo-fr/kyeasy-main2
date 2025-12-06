package org.ares.cloud.api.base.fallback;

import org.ares.cloud.api.base.BusinessIdServerClient;
import org.ares.cloud.exception.ServiceUnavailableException;
import org.springframework.stereotype.Component;

/**
 * @author hugo
 * @version 1.0
 * @description: 业务ID服务的降级处理
 * 当服务不可用时抛出 ServiceUnavailableException
 * @date 2024/10/13 13:46
 */
@Component
public class BusinessIdServerClientFallback implements BusinessIdServerClient {
    
    private static final String SERVICE_NAME = "base-service";
    
    @Override
    public String generateRandomBusinessId() {
        throw new ServiceUnavailableException(SERVICE_NAME, "generateRandomBusinessId");
    }

    @Override
    public String generateBusinessId(String modelName) {
        throw new ServiceUnavailableException(SERVICE_NAME, "generateBusinessId");
    }

    @Override
    public String generateSnowflakeId() {
        throw new ServiceUnavailableException(SERVICE_NAME, "generateSnowflakeId");
    }
}

