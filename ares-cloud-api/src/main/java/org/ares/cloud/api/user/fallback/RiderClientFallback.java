package org.ares.cloud.api.user.fallback;

import org.ares.cloud.api.user.RiderClient;
import org.ares.cloud.api.user.dto.RiderDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2025/8/26 22:58
 */
@Component
public class RiderClientFallback implements RiderClient {
    @Override
    public RiderDto loadByCountryCodeAndPhone(String countryCode, String phone) {
        return null;
    }
}
