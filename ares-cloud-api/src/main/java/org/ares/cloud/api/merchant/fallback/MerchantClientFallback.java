package org.ares.cloud.api.merchant.fallback;

import org.ares.cloud.api.merchant.MerchantClient;
import org.ares.cloud.api.merchant.dto.MerchantInfo;
import org.ares.cloud.api.merchant.dto.PlatformApprovalRecordDto;
import org.ares.cloud.api.merchant.dto.PlatformTaxRateDto;
import org.ares.cloud.exception.ServiceUnavailableException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author hugo
 * @description: MerchantClient 的降级处理
 * 当商户服务不可用时抛出 ServiceUnavailableException
 */
@Component
public class MerchantClientFallback implements MerchantClient {

    private static final String SERVICE_NAME = "merchant-service";

    @Override
    public PlatformTaxRateDto getTaxRateById(String id) {
        throw new ServiceUnavailableException(SERVICE_NAME, "getTaxRateById");
    }

    @Override
    public MerchantInfo getMerchantInfoById(String id) {
        throw new ServiceUnavailableException(SERVICE_NAME, "getMerchantInfoById");
    }

    @Override
    public MerchantInfo getPlatformInfoById(String id) {
        throw new ServiceUnavailableException(SERVICE_NAME, "getPlatformInfoById");
    }

    @Override
    public MerchantInfo getMerchantInfoByUserId(String userId) {
        throw new ServiceUnavailableException(SERVICE_NAME, "getMerchantInfoByUserId");
    }

    @Override
    public MerchantInfo getMerchantInfoByDomain(String domain) {
        throw new ServiceUnavailableException(SERVICE_NAME, "getMerchantInfoByDomain");
    }

    @Override
    public String updatePlatformApprovalRecord(PlatformApprovalRecordDto dto) {
        throw new ServiceUnavailableException(SERVICE_NAME, "updatePlatformApprovalRecord");
    }

    @Override
    public List<MerchantInfo> getMerchantInfoByName(String name) {
        throw new ServiceUnavailableException(SERVICE_NAME, "getMerchantInfoByName");
    }

    @Override
    public Long getAvailableStorage(String tenantId) {
        throw new ServiceUnavailableException(SERVICE_NAME, "getAvailableStorage");
    }
}

