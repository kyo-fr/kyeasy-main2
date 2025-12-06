package com.ares.cloud.pay.infrastructure.repository;

import com.ares.cloud.pay.domain.model.Wallet;
import com.ares.cloud.pay.domain.repository.WalletRepository;
import com.ares.cloud.pay.infrastructure.persistence.converter.WalletConverter;
import com.ares.cloud.pay.infrastructure.persistence.entity.WalletEntity;
import com.ares.cloud.pay.infrastructure.persistence.mapper.WalletMapper;
import org.ares.cloud.common.model.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 钱包仓储实现类
 */
@Repository
public class WalletRepositoryImpl implements WalletRepository {
    
    @Autowired
    private WalletMapper walletMapper;
    
    @Autowired
    private WalletConverter walletConverter;
    
    @Override
    public void save(Wallet wallet) {
        WalletEntity entity = walletConverter.toEntity(wallet);
        walletMapper.insert(entity);
    }
    
    @Override
    public Wallet findById(String id) {
        WalletEntity entity = walletMapper.selectById(id);
        return walletConverter.toDto(entity);
    }
    
    @Override
    public Wallet findByOwnerIdAndRegion(String ownerId, String paymentRegion) {
        WalletEntity entity = walletMapper.findByOwnerIdAndRegion(ownerId, paymentRegion);
        return walletConverter.toDto(entity);
    }
    
    @Override
    public List<Wallet> findByOwnerId(String ownerId) {
        List<WalletEntity> entities = walletMapper.findByOwnerId(ownerId);
        return walletConverter.listToDto(entities);
    }
    
    @Override
    public List<Wallet> findByOwnerType(String ownerType) {
        List<WalletEntity> entities = walletMapper.findByOwnerType(ownerType);
        return walletConverter.listToDto(entities);
    }
    
    @Override
    public List<Wallet> findByPaymentRegion(String paymentRegion) {
        List<WalletEntity> entities = walletMapper.findByPaymentRegion(paymentRegion);
        return walletConverter.listToDto(entities);
    }
    
    @Override
    public void updateStatus(String id, String status) {
        WalletEntity entity = new WalletEntity();
        entity.setId(id);
        entity.setStatus(status);
        walletMapper.updateById(entity);
    }
    
    @Override
    public void updateBalance(String id, Money balance) {
        WalletEntity entity = new WalletEntity();
        entity.setId(id);
        entity.setBalance(balance != null ? balance.getAmount() : null);
        walletMapper.updateById(entity);
    }
    
    @Override
    public void updateFrozenAmount(String id, Money frozenAmount) {
        WalletEntity entity = new WalletEntity();
        entity.setId(id);
        entity.setFrozenAmount(frozenAmount != null ? frozenAmount.getAmount() : null);
        walletMapper.updateById(entity);
    }
} 