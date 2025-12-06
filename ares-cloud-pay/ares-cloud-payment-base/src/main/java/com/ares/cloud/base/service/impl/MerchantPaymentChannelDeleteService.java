package com.ares.cloud.base.service.impl;

import com.ares.cloud.base.repository.MerchantPaymentChannelRepository;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
* @author hugo tangxkwork@163.com
* @description 商户支付渠道删除服务 - 独立事务处理
* @version 1.0.0
* @date 2025-05-13
*/
@Service
@RequiredArgsConstructor
public class MerchantPaymentChannelDeleteService {

    @Resource
    private MerchantPaymentChannelRepository merchantPaymentChannelRepository;

    /**
     * 删除商户的所有支付渠道
     * 使用完全独立的事务，避免ORA-12838错误
     * @param merchantId 商户ID
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void deleteChannelsByMerchantId(String merchantId) {
        merchantPaymentChannelRepository.deleteByMerchantId(merchantId);
    }
}