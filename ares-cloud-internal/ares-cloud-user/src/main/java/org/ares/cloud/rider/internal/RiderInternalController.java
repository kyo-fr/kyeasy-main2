package org.ares.cloud.rider.internal;

import jakarta.annotation.Resource;
import org.ares.cloud.api.user.dto.RiderDto;
import org.ares.cloud.exception.RpcCallException;
import org.ares.cloud.rider.service.RiderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2025/8/26 22:43
 */
@RestController
@RequestMapping("/internal/rider/v1")
public class RiderInternalController {
    @Resource
    private RiderService riderService;

    @RequestMapping("get_country_phone")
    public ResponseEntity<RiderDto> loadByCountryCodeAndPhone(@RequestParam("countryCode") String countryCode, @RequestParam("phone") String phone){
        try {
            return ResponseEntity.ok(riderService.loadByAccount(countryCode, phone));
        } catch (Exception e) {
            throw new RpcCallException(e);
        }
    }
}
