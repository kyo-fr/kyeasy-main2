package com.ares.cloud.pay.infrastructure.repository;

import com.ares.cloud.pay.domain.model.Account;
import com.ares.cloud.pay.domain.repository.AccountRepository;
import com.ares.cloud.pay.infrastructure.persistence.converter.AccountConverter;
import com.ares.cloud.pay.infrastructure.persistence.entity.AccountEntity;
import com.ares.cloud.pay.infrastructure.persistence.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 账户仓储实现类
 */
@Repository
public class AccountRepositoryImpl implements AccountRepository {
    
    @Autowired
    private AccountMapper accountMapper;
    
    @Autowired
    private AccountConverter accountConverter;
    
    @Override
    public void save(Account account) {
        AccountEntity entity = accountConverter.toEntity(account);
        accountMapper.insert(entity);
    }
    
    @Override
    public Account findById(String id) {
        AccountEntity entity = accountMapper.selectById(id);
        return accountConverter.toDto(entity);
    }
    
    @Override
    public Account findByUserId(String userId) {
        AccountEntity entity = accountMapper.findByUserId(userId);
        return accountConverter.toDto(entity);
    }
    
    @Override
    public Account findByPhone(String phone) {
        AccountEntity entity = accountMapper.findByPhone(phone);
        return accountConverter.toDto(entity);
    }
    
    @Override
    public Account findByAccount(String account) {
        AccountEntity entity = accountMapper.findByAccount(account);
        return accountConverter.toDto(entity);
    }
    
    @Override
    public Account findByCountryCodeAndPhone(String countryCode, String phone) {
        AccountEntity entity = accountMapper.findByCountryCodeAndPhone(countryCode, phone);
        return accountConverter.toDto(entity);
    }
    
    @Override
    public List<Account> findListByUserId(String userId) {
        List<AccountEntity> entities = accountMapper.findListByUserId(userId);
        return accountConverter.listToDto(entities);
    }
    
    @Override
    public void updateStatus(String id, String status) {
        accountMapper.updateStatus(id, status, System.currentTimeMillis());
    }
    
    @Override
    public void updatePassword(String id, String password) {
        accountMapper.updatePassword(id, password, System.currentTimeMillis());
    }
} 