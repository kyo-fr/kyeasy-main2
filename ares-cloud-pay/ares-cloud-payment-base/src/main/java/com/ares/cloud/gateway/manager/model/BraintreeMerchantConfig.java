package com.ares.cloud.gateway.manager.model;

import com.braintreegateway.Environment;
import lombok.Builder;
import lombok.Data;

/**
 * @author hugo
 * @version 1.0
 * @description:
 * @date 2025/5/13 12:14
 */
@Data
@Builder
public class BraintreeMerchantConfig {
    private String tenantId;
    private String merchantId;
    private String publicKey;
    private String privateKey;
    private Environment environment;
}
