package com.ares.cloud.pay.application.converter;

import com.ares.cloud.pay.domain.model.FeeConfig;
import com.ares.cloud.pay.application.dto.FeeConfigDTO;
import com.ares.cloud.pay.application.dto.CreateFeeConfigCommand;
import com.ares.cloud.pay.application.dto.UpdateFeeConfigCommand;
import org.ares.cloud.common.utils.IdUtils;
import org.springframework.stereotype.Component;

/**
 * 手续费配置应用层转换器
 */
@Component
public class FeeConfigApplicationConverter {
    
    /**
     * 将创建命令转换为领域模型
     */
    public FeeConfig toDomain(CreateFeeConfigCommand command) {
        if (command == null) {
            return null;
        }
        
        FeeConfig feeConfig = new FeeConfig();
        feeConfig.setId(IdUtils.fastSimpleUUID());
        feeConfig.setTransactionType(command.getTransactionType());
        feeConfig.setPaymentRegion(command.getPaymentRegion());
        feeConfig.setFeeRate(command.getFeeRate());
        feeConfig.setMinFeeAmount(command.getMinFeeAmount());
        feeConfig.setMaxFeeAmount(command.getMaxFeeAmount());
        feeConfig.setEnabled(command.getEnabled() != null ? command.getEnabled() : true);
        feeConfig.setCreateTime(System.currentTimeMillis());
        feeConfig.setUpdateTime(System.currentTimeMillis());
        
        return feeConfig;
    }
    
    /**
     * 将更新命令转换为领域模型
     */
    public FeeConfig toDomain(UpdateFeeConfigCommand command) {
        if (command == null) {
            return null;
        }
        
        FeeConfig feeConfig = new FeeConfig();
        feeConfig.setId(command.getId());
        feeConfig.setTransactionType(command.getTransactionType());
        feeConfig.setPaymentRegion(command.getPaymentRegion());
        feeConfig.setFeeRate(command.getFeeRate());
        feeConfig.setMinFeeAmount(command.getMinFeeAmount());
        feeConfig.setMaxFeeAmount(command.getMaxFeeAmount());
        feeConfig.setEnabled(command.getEnabled());
        feeConfig.setUpdateTime(System.currentTimeMillis());
        
        return feeConfig;
    }
    
    /**
     * 将领域模型转换为DTO
     */
    public FeeConfigDTO toDTO(FeeConfig feeConfig) {
        if (feeConfig == null) {
            return null;
        }
        
        FeeConfigDTO dto = new FeeConfigDTO();
        dto.setId(feeConfig.getId());
        dto.setTransactionType(feeConfig.getTransactionType());
        dto.setPaymentRegion(feeConfig.getPaymentRegion());
        dto.setFeeRate(feeConfig.getFeeRate());
        dto.setMinFeeAmount(feeConfig.getMinFeeAmount());
        dto.setMaxFeeAmount(feeConfig.getMaxFeeAmount());
        dto.setEnabled(feeConfig.getEnabled());
        dto.setCreateTime(feeConfig.getCreateTime());
        dto.setUpdateTime(feeConfig.getUpdateTime());
        
        return dto;
    }
} 