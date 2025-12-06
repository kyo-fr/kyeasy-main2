package org.ares.cloud.api.user;

import org.ares.cloud.api.user.dto.RiderDto;
import org.ares.cloud.api.user.fallback.RiderClientFallback;
import org.ares.cloud.feign.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2025/8/26 22:56
 */
@FeignClient(name = "user-service",contextId = "riderClient",configuration = FeignConfig.class,fallback = RiderClientFallback.class)
public interface RiderClient {
    /**
     * 获取账号信息
     * @param countryCode 国家编号
     * @param phone 手机号
     * @return 骑手信息
     */
    @GetMapping("/internal/rider/v1/get_country_phone")
    RiderDto loadByCountryCodeAndPhone(@RequestParam("countryCode") String countryCode, @RequestParam("phone") String phone);
}
