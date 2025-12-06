package com.ares.cloud.pay.infrastructure.persistence.converter;

import com.ares.cloud.pay.domain.model.Transaction;
import com.ares.cloud.pay.infrastructure.persistence.entity.TransactionEntity;
import org.ares.cloud.common.convert.BaseConvert;
import org.ares.cloud.common.model.Money;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 交易转换器
 */
@Component
public class TransactionConverter implements BaseConvert<TransactionEntity, Transaction> {
    
    @Override
    public Transaction toDto(TransactionEntity entity) {
        if (entity == null) {
            return null;
        }
        Transaction transaction = new Transaction();
        transaction.setId(entity.getId());
        transaction.setFromAccountId(entity.getFromAccountId());
        transaction.setToAccountId(entity.getToAccountId());
        transaction.setOrderId(entity.getOrderId());
        transaction.setAmount(entity.getAmount() != null ? 
                Money.create(entity.getAmount(), entity.getPaymentRegion()) : Money.zeroMoney(entity.getPaymentRegion()));
        transaction.setPaymentRegion(entity.getPaymentRegion());
        transaction.setFeeAmount(entity.getFeeAmount() != null ? 
                Money.create(entity.getFeeAmount(), entity.getPaymentRegion()) :  Money.zeroMoney(entity.getPaymentRegion()));
        transaction.setFeeRate(entity.getFeeRate());
        transaction.setActualAmount(entity.getActualAmount() != null ? 
                Money.create(entity.getActualAmount(), entity.getPaymentRegion()) : Money.zeroMoney(entity.getPaymentRegion()));
        transaction.setType(entity.getType());
        transaction.setStatus(entity.getStatus());
        transaction.setDescription(entity.getDescription());
        transaction.setCreateTime(entity.getCreateTime());
        transaction.setUpdateTime(entity.getUpdateTime());
        return transaction;
    }
    
    @Override
    public List<Transaction> listToDto(List<TransactionEntity> list) {
        if (list == null) {
            return null;
        }
        return list.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public TransactionEntity toEntity(Transaction dto) {
        if (dto == null) {
            return null;
        }
        TransactionEntity entity = new TransactionEntity();
        entity.setId(dto.getId());
        entity.setFromAccountId(dto.getFromAccountId());
        entity.setToAccountId(dto.getToAccountId());
        entity.setOrderId(dto.getOrderId());
        entity.setAmount(dto.getAmount() != null ? dto.getAmount().getAmount() : 0);
        entity.setPaymentRegion(dto.getPaymentRegion());
        entity.setFeeAmount(dto.getFeeAmount() != null ? dto.getFeeAmount().getAmount() : 0);
        entity.setFeeRate(dto.getFeeRate());
        entity.setActualAmount(dto.getActualAmount() != null ? dto.getActualAmount().getAmount() : 0);
        entity.setType(dto.getType());
        entity.setStatus(dto.getStatus());
        entity.setDescription(dto.getDescription());
        entity.setCreateTime(dto.getCreateTime());
        entity.setUpdateTime(dto.getUpdateTime());
        return entity;
    }
    
    @Override
    public List<TransactionEntity> listToEntities(List<Transaction> list) {
        if (list == null) {
            return null;
        }
        return list.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
} 