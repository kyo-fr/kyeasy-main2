package com.ares.cloud.order.domain.service;

import com.ares.cloud.order.domain.model.valueobject.MerchantInfo;

import java.util.List;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2025/9/26 00:23
 */
public interface MerchantService {
    /**
     * 根据ID查询商户信息
     * @param Id
     * @return
     */
    MerchantInfo findById(String Id);
    /**
     * 根据D查询平台信息
     * @param Id
     * @return
     */
    MerchantInfo findPlatformById(String Id);

    /**
     * 根据名称查询商户信息
     * @param name
     * @return
     */
    List<MerchantInfo> findByName(String name);
    
    /**
     * 校验商户存储空间是否充足
     * @param merchantId 商户ID
     * @param requiredStorage 需要的存储空间（默认订单存储为10）
     * @return 是否存储空间充足
     */
    boolean validateStorageSpace(String merchantId, Integer requiredStorage);
    
    /**
     * 消耗商户存储空间
     * @param merchantId 商户ID
     * @param orderNo 订单号
     * @param consumedStorage 消耗的存储空间（默认订单存储为10）
     * @return 是否消耗成功
     */
    boolean consumeStorageSpace(String merchantId,String orderNo, Integer consumedStorage);
}
