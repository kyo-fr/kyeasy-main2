package com.ares.cloud.pay.infrastructure.persistence.converter;

import com.ares.cloud.pay.domain.model.AccountFlow;
import com.ares.cloud.pay.infrastructure.persistence.entity.AccountFlowEntity;
import org.ares.cloud.common.convert.BaseConvert;
import org.ares.cloud.common.model.Money;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 账户流水转换器
 */
@Component
public class AccountFlowConverter implements BaseConvert<AccountFlowEntity, AccountFlow> {
    
    @Override
    public AccountFlow toDto(AccountFlowEntity entity) {
        if (entity == null) {
            return null;
        }
        AccountFlow accountFlow = new AccountFlow();
        accountFlow.setId(entity.getId());
        accountFlow.setAccountId(entity.getAccountId());
        accountFlow.setTransactionId(entity.getTransactionId());
        accountFlow.setFlowType(entity.getFlowType());
        accountFlow.setTransactionType(entity.getTransactionType());
        accountFlow.setAmount(entity.getAmount() != null ? 
                Money.create(entity.getAmount(), entity.getCurrency(), entity.getScale()) : null);
        accountFlow.setFeeAmount(entity.getFeeAmount() != null ? 
                Money.create(entity.getFeeAmount(), entity.getCurrency(), entity.getScale()) : null);
        accountFlow.setFeeRate(entity.getFeeRate());
        accountFlow.setActualAmount(entity.getActualAmount() != null ? 
                Money.create(entity.getActualAmount(), entity.getCurrency(), entity.getScale()) : null);
        accountFlow.setBalanceBefore(entity.getBalanceBefore() != null ? 
                Money.create(entity.getBalanceBefore(), entity.getCurrency(), entity.getScale()) : null);
        accountFlow.setBalanceAfter(entity.getBalanceAfter() != null ? 
                Money.create(entity.getBalanceAfter(), entity.getCurrency(), entity.getScale()) : null);
        accountFlow.setCreateTime(entity.getCreateTime());
        accountFlow.setCreator(entity.getCreator());
        accountFlow.setUpdater(entity.getUpdater());
        accountFlow.setUpdateTime(entity.getUpdateTime());
        accountFlow.setVersion(entity.getVersion());
        accountFlow.setDeleted(entity.getDeleted());
        return accountFlow;
    }
    
    @Override
    public List<AccountFlow> listToDto(List<AccountFlowEntity> list) {
        if (list == null) {
            return null;
        }
        return list.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public AccountFlowEntity toEntity(AccountFlow dto) {
        if (dto == null) {
            return null;
        }
        AccountFlowEntity entity = new AccountFlowEntity();
        entity.setId(dto.getId());
        entity.setAccountId(dto.getAccountId());
        entity.setTransactionId(dto.getTransactionId());
        entity.setFlowType(dto.getFlowType());
        entity.setTransactionType(dto.getTransactionType());
        
        if (dto.getAmount() != null) {
            entity.setAmount(dto.getAmount().getAmount());
            entity.setCurrency(dto.getAmount().getCurrency());
            entity.setScale(dto.getAmount().getScale());
        }
        
        if (dto.getFeeAmount() != null) {
            entity.setFeeAmount(dto.getFeeAmount().getAmount());
        }
        
        entity.setFeeRate(dto.getFeeRate());
        
        if (dto.getActualAmount() != null) {
            entity.setActualAmount(dto.getActualAmount().getAmount());
        }
        
        if (dto.getBalanceBefore() != null) {
            entity.setBalanceBefore(dto.getBalanceBefore().getAmount());
        }
        
        if (dto.getBalanceAfter() != null) {
            entity.setBalanceAfter(dto.getBalanceAfter().getAmount());
        }
        
        entity.setCreateTime(dto.getCreateTime());
        entity.setCreator(dto.getCreator());
        entity.setUpdater(dto.getUpdater());
        entity.setUpdateTime(dto.getUpdateTime());
        entity.setVersion(dto.getVersion());
        entity.setDeleted(dto.getDeleted());
        return entity;
    }
    
    @Override
    public List<AccountFlowEntity> listToEntities(List<AccountFlow> list) {
        if (list == null) {
            return null;
        }
        return list.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
} 