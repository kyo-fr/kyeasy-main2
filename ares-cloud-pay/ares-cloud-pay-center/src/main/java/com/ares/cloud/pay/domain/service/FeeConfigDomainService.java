package com.ares.cloud.pay.domain.service;

import com.ares.cloud.pay.domain.model.FeeConfig;
import com.ares.cloud.pay.domain.repository.FeeConfigRepository;
import com.ares.cloud.pay.domain.enums.PaymentError;
import org.ares.cloud.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 手续费配置领域服务
 */
@Service
public class FeeConfigDomainService {
    
    @Autowired
    private FeeConfigRepository feeConfigRepository;
    
    /**
     * 创建手续费配置
     */
    @Transactional
    public FeeConfig createFeeConfig(FeeConfig feeConfig) {
        // 参数校验
        if (feeConfig == null) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        if (feeConfig.getTransactionType() == null || feeConfig.getTransactionType().trim().isEmpty()) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        if (feeConfig.getPaymentRegion() == null || feeConfig.getPaymentRegion().trim().isEmpty()) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        if (feeConfig.getFeeRate() == null || feeConfig.getFeeRate() < 0) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        
        // 检查是否已存在相同配置
        FeeConfig existingConfig = feeConfigRepository.findByTransactionTypeAndRegion(
            feeConfig.getTransactionType(), feeConfig.getPaymentRegion());
        if (existingConfig != null) {
            throw new BusinessException(PaymentError.FEE_CONFIG_ALREADY_EXISTS);
        }
        
        try {
            feeConfigRepository.save(feeConfig);
            return feeConfig;
        } catch (Exception e) {
            throw new BusinessException(PaymentError.SYSTEM_ERROR);
        }
    }
    
    /**
     * 更新手续费配置
     */
    @Transactional
    public void updateFeeConfig(FeeConfig feeConfig) {
        // 参数校验
        if (feeConfig == null || feeConfig.getId() == null) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        
        try {
            FeeConfig existingConfig = feeConfigRepository.findById(feeConfig.getId());
            if (existingConfig == null) {
                throw new BusinessException(PaymentError.FEE_CONFIG_NOT_FOUND);
            }
            
            feeConfigRepository.update(feeConfig);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(PaymentError.SYSTEM_ERROR);
        }
    }
    
    /**
     * 删除手续费配置
     */
    @Transactional
    public void deleteFeeConfig(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        
        try {
            FeeConfig existingConfig = feeConfigRepository.findById(id);
            if (existingConfig == null) {
                throw new BusinessException(PaymentError.FEE_CONFIG_NOT_FOUND);
            }
            
            feeConfigRepository.delete(id);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(PaymentError.SYSTEM_ERROR);
        }
    }
    
    /**
     * 根据ID查询手续费配置
     */
    public FeeConfig getFeeConfigById(String id) {
        if (id == null || id.trim().isEmpty()) {
            return null;
        }
        
        try {
            return feeConfigRepository.findById(id);
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * 根据交易类型和支付区域查询手续费配置
     */
    public FeeConfig getFeeConfig(String transactionType, String paymentRegion) {
        if (transactionType == null || paymentRegion == null) {
            return null;
        }
        
        try {
            return feeConfigRepository.findByTransactionTypeAndRegion(transactionType, paymentRegion);
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * 查询所有启用的手续费配置
     */
    public List<FeeConfig> getAllEnabledFeeConfigs() {
        try {
            return feeConfigRepository.findAllEnabled();
        } catch (Exception e) {
            return List.of();
        }
    }
    
    /**
     * 根据交易类型查询手续费配置
     */
    public List<FeeConfig> getFeeConfigsByTransactionType(String transactionType) {
        if (transactionType == null) {
            return List.of();
        }
        
        try {
            return feeConfigRepository.findByTransactionType(transactionType);
        } catch (Exception e) {
            return List.of();
        }
    }
    
    /**
     * 根据支付区域查询手续费配置
     */
    public List<FeeConfig> getFeeConfigsByPaymentRegion(String paymentRegion) {
        if (paymentRegion == null) {
            return List.of();
        }
        
        try {
            return feeConfigRepository.findByPaymentRegion(paymentRegion);
        } catch (Exception e) {
            return List.of();
        }
    }
} 