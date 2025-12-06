package com.ares.cloud.pay.infrastructure.repository;

import com.ares.cloud.pay.domain.model.FeeConfig;
import com.ares.cloud.pay.domain.repository.FeeConfigRepository;
import com.ares.cloud.pay.infrastructure.persistence.converter.FeeConfigConverter;
import com.ares.cloud.pay.infrastructure.persistence.entity.FeeConfigEntity;
import com.ares.cloud.pay.infrastructure.persistence.mapper.FeeConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 手续费配置仓储实现类
 */
@Repository
public class FeeConfigRepositoryImpl implements FeeConfigRepository {
    
    @Autowired
    private FeeConfigMapper feeConfigMapper;
    
    @Autowired
    private FeeConfigConverter feeConfigConverter;
    
    @Override
    public void save(FeeConfig feeConfig) {
        FeeConfigEntity entity = feeConfigConverter.toEntity(feeConfig);
        feeConfigMapper.insert(entity);
    }
    
    @Override
    public FeeConfig findById(String id) {
        FeeConfigEntity entity = feeConfigMapper.selectById(id);
        return feeConfigConverter.toDto(entity);
    }
    
    @Override
    public FeeConfig findByTransactionTypeAndRegion(String transactionType, String paymentRegion) {
        FeeConfigEntity entity = feeConfigMapper.findByTransactionTypeAndRegion(transactionType, paymentRegion);
        return feeConfigConverter.toDto(entity);
    }
    
    @Override
    public List<FeeConfig> findAllEnabled() {
        List<FeeConfigEntity> entities = feeConfigMapper.findAllEnabled();
        return feeConfigConverter.listToDto(entities);
    }
    
    @Override
    public List<FeeConfig> findByTransactionType(String transactionType) {
        List<FeeConfigEntity> entities = feeConfigMapper.findByTransactionType(transactionType);
        return feeConfigConverter.listToDto(entities);
    }
    
    @Override
    public List<FeeConfig> findByPaymentRegion(String paymentRegion) {
        List<FeeConfigEntity> entities = feeConfigMapper.findByPaymentRegion(paymentRegion);
        return feeConfigConverter.listToDto(entities);
    }
    
    @Override
    public void update(FeeConfig feeConfig) {
        FeeConfigEntity entity = feeConfigConverter.toEntity(feeConfig);
        feeConfigMapper.updateById(entity);
    }
    
    @Override
    public void delete(String id) {
        feeConfigMapper.deleteById(id);
    }
} 