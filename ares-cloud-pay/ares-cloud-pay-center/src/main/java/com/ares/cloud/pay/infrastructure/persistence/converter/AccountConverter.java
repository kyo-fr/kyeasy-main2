package com.ares.cloud.pay.infrastructure.persistence.converter;

import com.ares.cloud.pay.domain.model.Account;
import com.ares.cloud.pay.infrastructure.persistence.entity.AccountEntity;
import org.ares.cloud.common.convert.BaseConvert;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 账户转换器
 */
@Component
public class AccountConverter implements BaseConvert<AccountEntity, Account> {
    
    @Override
    public Account toDto(AccountEntity entity) {
        if (entity == null) {
            return null;
        }
        Account account = new Account();
        account.setId(entity.getId());
        account.setUserId(entity.getUserId());
        account.setPassword(entity.getPassword());
        account.setCountryCode(entity.getCountryCode());
        account.setPhone(entity.getPhone());
        account.setAccount(entity.getAccount());
        account.setStatus(entity.getStatus());
        account.setCreator(entity.getCreator());
        account.setCreateTime(entity.getCreateTime());
        account.setUpdater(entity.getUpdater());
        account.setUpdateTime(entity.getUpdateTime());
        account.setVersion(entity.getVersion());
        account.setDeleted(entity.getDeleted());
        return account;
    }
    
    @Override
    public List<Account> listToDto(List<AccountEntity> list) {
        if (list == null) {
            return null;
        }
        return list.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public AccountEntity toEntity(Account dto) {
        if (dto == null) {
            return null;
        }
        AccountEntity entity = new AccountEntity();
        entity.setId(dto.getId());
        entity.setUserId(dto.getUserId());
        entity.setPassword(dto.getPassword());
        entity.setCountryCode(dto.getCountryCode());
        entity.setPhone(dto.getPhone());
        entity.setAccount(dto.getAccount());
        entity.setStatus(dto.getStatus());
        entity.setCreator(dto.getCreator());
        entity.setCreateTime(dto.getCreateTime());
        entity.setUpdater(dto.getUpdater());
        entity.setUpdateTime(dto.getUpdateTime());
        entity.setVersion(dto.getVersion());
        entity.setDeleted(dto.getDeleted());
        return entity;
    }
    
    @Override
    public List<AccountEntity> listToEntities(List<Account> list) {
        if (list == null) {
            return null;
        }
        return list.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
} 