package com.ares.cloud.pay.application.service;

import com.ares.cloud.pay.domain.model.FeeConfig;
import com.ares.cloud.pay.domain.service.FeeConfigDomainService;
import com.ares.cloud.pay.application.dto.FeeConfigDTO;
import com.ares.cloud.pay.application.dto.CreateFeeConfigCommand;
import com.ares.cloud.pay.application.dto.UpdateFeeConfigCommand;
import com.ares.cloud.pay.application.converter.FeeConfigApplicationConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 手续费配置应用服务
 */
@Service
public class FeeConfigApplicationService {
    
    @Autowired
    private FeeConfigDomainService feeConfigDomainService;
    
    @Autowired
    private FeeConfigApplicationConverter feeConfigApplicationConverter;
    
    /**
     * 创建手续费配置
     */
    @Transactional
    public FeeConfigDTO createFeeConfig(CreateFeeConfigCommand command) {
        FeeConfig feeConfig = feeConfigApplicationConverter.toDomain(command);
        FeeConfig createdFeeConfig = feeConfigDomainService.createFeeConfig(feeConfig);
        return feeConfigApplicationConverter.toDTO(createdFeeConfig);
    }
    
    /**
     * 更新手续费配置
     */
    @Transactional
    public void updateFeeConfig(UpdateFeeConfigCommand command) {
        FeeConfig feeConfig = feeConfigApplicationConverter.toDomain(command);
        feeConfigDomainService.updateFeeConfig(feeConfig);
    }
    
    /**
     * 删除手续费配置
     */
    @Transactional
    public void deleteFeeConfig(String id) {
        feeConfigDomainService.deleteFeeConfig(id);
    }
    
    /**
     * 根据ID查询手续费配置
     */
    public FeeConfigDTO getFeeConfigById(String id) {
        FeeConfig feeConfig = feeConfigDomainService.getFeeConfigById(id);
        return feeConfigApplicationConverter.toDTO(feeConfig);
    }
    
    /**
     * 根据交易类型和支付区域查询手续费配置
     */
    public FeeConfigDTO getFeeConfig(String transactionType, String paymentRegion) {
        FeeConfig feeConfig = feeConfigDomainService.getFeeConfig(transactionType, paymentRegion);
        return feeConfigApplicationConverter.toDTO(feeConfig);
    }
    
    /**
     * 查询所有启用的手续费配置
     */
    public List<FeeConfigDTO> getAllEnabledFeeConfigs() {
        List<FeeConfig> feeConfigs = feeConfigDomainService.getAllEnabledFeeConfigs();
        return feeConfigs.stream()
                .map(feeConfigApplicationConverter::toDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * 根据交易类型查询手续费配置
     */
    public List<FeeConfigDTO> getFeeConfigsByTransactionType(String transactionType) {
        List<FeeConfig> feeConfigs = feeConfigDomainService.getFeeConfigsByTransactionType(transactionType);
        return feeConfigs.stream()
                .map(feeConfigApplicationConverter::toDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * 根据支付区域查询手续费配置
     */
    public List<FeeConfigDTO> getFeeConfigsByPaymentRegion(String paymentRegion) {
        List<FeeConfig> feeConfigs = feeConfigDomainService.getFeeConfigsByPaymentRegion(paymentRegion);
        return feeConfigs.stream()
                .map(feeConfigApplicationConverter::toDTO)
                .collect(Collectors.toList());
    }
} 