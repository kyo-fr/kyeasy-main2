package com.ares.cloud.payment.infrastructure.service;

import com.ares.cloud.payment.domain.model.MerchantInfo;
import com.ares.cloud.payment.domain.repository.MerchantRepository;
import jakarta.annotation.Resource;
import org.ares.cloud.api.merchant.MerchantClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2025/4/13 11:53
 */
@Service
public class MerchantService implements MerchantRepository {
    @Resource
    private MerchantClient merchantClient;
    @Override
    public MerchantInfo getMerchantBasicInfo(String merchantId) {
        // 先获取商户
        org.ares.cloud.api.merchant.dto.MerchantInfo merchantInfo = merchantClient.getMerchantInfoById(merchantId);
        if (merchantInfo == null) {
            merchantInfo = merchantClient.getPlatformInfoById(merchantId);
        }
        if (merchantInfo != null) {
            return MerchantInfo.builder()
                    .merchantId(merchantInfo.getId())
                    .name(merchantInfo.getName())
                    .currency(merchantInfo.getCurrency())
                    .scale(merchantInfo.getCurrencyScale())
                    .taxId(merchantInfo.getTaxNum())
                    .address(merchantInfo.getAddress())
                    .postalCode(merchantInfo.getPostalCode())
                    .countryCode(merchantInfo.getCountryCode())
                    .phone(merchantInfo.getPhone())
                    .email(merchantInfo.getEnterpriseEmail())
                    .build();
        }

        return null;
    }
}
