package com.ares.cloud.pay.domain.repository;

import com.ares.cloud.pay.domain.model.FeeConfig;

import java.util.List;

/**
 * 手续费配置仓储接口
 */
public interface FeeConfigRepository {
    
    /**
     * 保存手续费配置
     */
    void save(FeeConfig feeConfig);
    
    /**
     * 根据ID查询手续费配置
     */
    FeeConfig findById(String id);
    
    /**
     * 根据交易类型和支付区域查询手续费配置
     */
    FeeConfig findByTransactionTypeAndRegion(String transactionType, String paymentRegion);
    
    /**
     * 查询所有启用的手续费配置
     */
    List<FeeConfig> findAllEnabled();
    
    /**
     * 根据交易类型查询手续费配置
     */
    List<FeeConfig> findByTransactionType(String transactionType);
    
    /**
     * 根据支付区域查询手续费配置
     */
    List<FeeConfig> findByPaymentRegion(String paymentRegion);
    
    /**
     * 更新手续费配置
     */
    void update(FeeConfig feeConfig);
    
    /**
     * 删除手续费配置
     */
    void delete(String id);
} 