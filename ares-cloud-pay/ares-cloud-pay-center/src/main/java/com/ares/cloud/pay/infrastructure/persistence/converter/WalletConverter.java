package com.ares.cloud.pay.infrastructure.persistence.converter;

import com.ares.cloud.pay.domain.model.Wallet;
import com.ares.cloud.pay.infrastructure.persistence.entity.WalletEntity;
import org.ares.cloud.common.convert.BaseConvert;
import org.ares.cloud.common.model.Money;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 钱包转换器
 */
@Component
public class WalletConverter implements BaseConvert<WalletEntity, Wallet> {
    
    @Override
    public Wallet toDto(WalletEntity entity) {
        if (entity == null) {
            return null;
        }
        Wallet wallet = new Wallet();
        wallet.setId(entity.getId());
        wallet.setOwnerId(entity.getOwnerId());
        wallet.setOwnerType(entity.getOwnerType());
        wallet.setPaymentRegion(entity.getPaymentRegion());
        // 将Long转换为Money，使用默认精度
        wallet.setBalance(entity.getBalance() != null ? 
                Money.create(entity.getBalance(), entity.getPaymentRegion(), Money.DEFAULT_SCALE) : null);
        wallet.setFrozenAmount(entity.getFrozenAmount() != null ? 
                Money.create(entity.getFrozenAmount(), entity.getPaymentRegion(), Money.DEFAULT_SCALE) : null);
        wallet.setStatus(entity.getStatus());
        wallet.setCreateTime(entity.getCreateTime());
        wallet.setUpdateTime(entity.getUpdateTime());
        return wallet;
    }
    
    @Override
    public List<Wallet> listToDto(List<WalletEntity> list) {
        if (list == null) {
            return null;
        }
        return list.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public WalletEntity toEntity(Wallet dto) {
        if (dto == null) {
            return null;
        }
        WalletEntity entity = new WalletEntity();
        entity.setId(dto.getId());
        entity.setOwnerId(dto.getOwnerId());
        entity.setOwnerType(dto.getOwnerType());
        entity.setPaymentRegion(dto.getPaymentRegion());
        // 将Money转换为Long
        entity.setBalance(dto.getBalance() != null ? dto.getBalance().getAmount() : null);
        entity.setFrozenAmount(dto.getFrozenAmount() != null ? dto.getFrozenAmount().getAmount() : null);
        entity.setStatus(dto.getStatus());
        entity.setCreateTime(dto.getCreateTime());
        entity.setUpdateTime(dto.getUpdateTime());
        return entity;
    }
    
    @Override
    public List<WalletEntity> listToEntities(List<Wallet> list) {
        if (list == null) {
            return null;
        }
        return list.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}