package com.ares.cloud.payment.domain.repository;

import com.ares.cloud.payment.domain.model.MerchantInfo;
import com.ares.cloud.payment.domain.model.Party;

/**
 * @author hugo
 * @version 1.0
 * @description: 商户信息查询
 * @date 2025/4/13 11:48
 */
public interface MerchantRepository {

    /**
     * 获取商户基本信息
     *
     * @param merchantId 商户ID
     * @return 商户基本信息
     */
    MerchantInfo getMerchantBasicInfo(String merchantId);
}
