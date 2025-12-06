package org.ares.cloud.api.base;

import org.ares.cloud.api.base.fallback.BusinessIdServerClientFallback;
import org.ares.cloud.feign.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author hugo
 * @version 1.0
 * @description: 业务ID服务
 * @date 2024/10/13 13:43
 */
@FeignClient(name = "base-service",configuration = FeignConfig.class ,fallback = BusinessIdServerClientFallback.class)
public interface BusinessIdServerClient {
    /**
     * 生成随机的业务id
     * @return 业务ud
     */
    @GetMapping("/internal/base/v1/business_id/generate_random_business_id")
    String generateRandomBusinessId();

    /**
     * 根据模块名生成业务id
     * @param modelName 业务模块名
     * @return 业务id
     */
    @GetMapping("/internal/base/v1/business_id/generate_business_id")
    String generateBusinessId(@RequestParam("modelName") String modelName);

    /**
     * 生成16位短雪花ID
     * 格式：秒级时间戳(10位) + 机器ID(2位) + 序列号(4位)
     * @return 16位雪花ID
     */
    @GetMapping("/internal/base/v1/business_id/generate_snowflake_id")
    String generateSnowflakeId();
}
