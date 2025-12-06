package com.ares.cloud.pay.infrastructure.persistence.converter;

import com.ares.cloud.pay.domain.model.FeeConfig;
import com.ares.cloud.pay.infrastructure.persistence.entity.FeeConfigEntity;
import org.ares.cloud.common.convert.BaseConvert;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 手续费配置转换器
 */
@Component
public class FeeConfigConverter implements BaseConvert<FeeConfigEntity, FeeConfig> {
    
    @Override
    public FeeConfig toDto(FeeConfigEntity entity) {
        if (entity == null) {
            return null;
        }
        FeeConfig feeConfig = new FeeConfig();
        feeConfig.setId(entity.getId());
        feeConfig.setTransactionType(entity.getTransactionType());
        feeConfig.setPaymentRegion(entity.getPaymentRegion());
        feeConfig.setFeeRate(entity.getFeeRate());
        feeConfig.setMinFeeAmount(entity.getMinFeeAmount());
        feeConfig.setMaxFeeAmount(entity.getMaxFeeAmount());
        feeConfig.setEnabled(entity.getEnabled());
        feeConfig.setCreateTime(entity.getCreateTime());
        feeConfig.setUpdateTime(entity.getUpdateTime());
        return feeConfig;
    }
    
    @Override
    public List<FeeConfig> listToDto(List<FeeConfigEntity> list) {
        if (list == null) {
            return null;
        }
        return list.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public FeeConfigEntity toEntity(FeeConfig dto) {
        if (dto == null) {
            return null;
        }
        FeeConfigEntity entity = new FeeConfigEntity();
        entity.setId(dto.getId());
        entity.setTransactionType(dto.getTransactionType());
        entity.setPaymentRegion(dto.getPaymentRegion());
        entity.setFeeRate(dto.getFeeRate());
        entity.setMinFeeAmount(dto.getMinFeeAmount());
        entity.setMaxFeeAmount(dto.getMaxFeeAmount());
        entity.setEnabled(dto.getEnabled());
        entity.setCreateTime(dto.getCreateTime());
        entity.setUpdateTime(dto.getUpdateTime());
        return entity;
    }
    
    @Override
    public List<FeeConfigEntity> listToEntities(List<FeeConfig> list) {
        if (list == null) {
            return null;
        }
        return list.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
} 