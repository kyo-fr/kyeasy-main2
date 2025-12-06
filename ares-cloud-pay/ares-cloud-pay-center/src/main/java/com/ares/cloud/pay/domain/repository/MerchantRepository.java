package com.ares.cloud.pay.domain.repository;

import com.ares.cloud.pay.domain.model.Merchant;

import java.util.List;

/**
 * 商户仓储接口
 */
public interface MerchantRepository {
    
    /**
     * 保存商户
     */
    void save(Merchant merchant);
    
    /**
     * 根据ID查询商户
     */
    Merchant findById(String id);
    
    /**
     * 根据商户号查询商户
     */
    Merchant findByMerchantNo(String merchantNo);
    
    /**
     * 根据租户ID查询商户列表
     */
    List<Merchant> findByTenantId(String tenantId);
    
    /**
     * 根据状态查询商户列表
     */
    List<Merchant> findByStatus(String status);
    
    /**
     * 更新商户状态
     */
    void updateStatus(String id, String status);
    
    /**
     * 更新商户支持的支付区域
     */
    void updateSupportedRegions(String id, List<String> supportedRegions);
    
    /**
     * 更新商户支付密码
     */
    void updatePayPassword(String id, String payPassword);
    
    /**
     * 更新商户支付密钥
     */
    void updatePaySecret(String id, String paySecret);
    
    /**
     * 更新商户基本信息
     */
    void updateBasicInfo(String id, String merchantName, String contactPerson, 
                        String contactPhone, String contactEmail, String address, 
                        String businessLicense, String legalRepresentative);
} 