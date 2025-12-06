package com.ares.cloud.pay.infrastructure.repository;

import com.ares.cloud.pay.application.queries.AccountFlowQuery;
import com.ares.cloud.pay.domain.model.AccountFlow;
import com.ares.cloud.pay.domain.repository.AccountFlowRepository;
import com.ares.cloud.pay.infrastructure.persistence.converter.AccountFlowConverter;
import com.ares.cloud.pay.infrastructure.persistence.entity.AccountFlowEntity;
import com.ares.cloud.pay.infrastructure.persistence.mapper.AccountFlowMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 账户流水仓储实现类
 */
@Repository
public class AccountFlowRepositoryImpl implements AccountFlowRepository {

    @Resource
    private AccountFlowMapper accountFlowMapper;
    
    @Resource
    private AccountFlowConverter accountFlowConverter;
    
    @Override
    public void save(AccountFlow accountFlow) {
        AccountFlowEntity entity = accountFlowConverter.toEntity(accountFlow);
        accountFlowMapper.insert(entity);
    }
    
    @Override
    public void saveBatch(List<AccountFlow> accountFlows) {
        if (accountFlows != null && !accountFlows.isEmpty()) {
            List<AccountFlowEntity> entities = accountFlowConverter.listToEntities(accountFlows);
            for (AccountFlowEntity entity : entities) {
                accountFlowMapper.insert(entity);
            }
        }
    }
    
    @Override
    public AccountFlow findById(String id) {
        AccountFlowEntity entity = accountFlowMapper.selectById(id);
        return accountFlowConverter.toDto(entity);
    }
    
    @Override
    public List<AccountFlow> findByAccountId(String accountId) {
        List<AccountFlowEntity> entities = accountFlowMapper.findByAccountId(accountId);
        return accountFlowConverter.listToDto(entities);
    }
    
    @Override
    public List<AccountFlow> findByTransactionId(String transactionId) {
        List<AccountFlowEntity> entities = accountFlowMapper.findByTransactionId(transactionId);
        return accountFlowConverter.listToDto(entities);
    }
    
    @Override
    public List<AccountFlow> findByOrderId(String orderId) {
        List<AccountFlowEntity> entities = accountFlowMapper.findByOrderId(orderId);
        return accountFlowConverter.listToDto(entities);
    }
    
    @Override
    public List<AccountFlow> findByAccountIdAndFlowType(String accountId, String flowType) {
        List<AccountFlowEntity> entities = accountFlowMapper.findByAccountIdAndFlowType(accountId, flowType);
        return accountFlowConverter.listToDto(entities);
    }
    
    @Override
    public List<AccountFlow> findByAccountIdAndTimeRange(String accountId, Long startTime, Long endTime) {
        List<AccountFlowEntity> entities = accountFlowMapper.findByAccountIdAndTimeRange(accountId, startTime.toString(), endTime.toString());
        return accountFlowConverter.listToDto(entities);
    }
    
    @Override
    public List<AccountFlow> findPageByAccountId(String accountId, int page, int limit) {
        Page<AccountFlowEntity> pageParam = new Page<>(page, limit);
        Page<AccountFlowEntity> result = accountFlowMapper.findPageByAccountId(pageParam, accountId);
        return accountFlowConverter.listToDto(result.getRecords());
    }
    
    @Override
    public List<AccountFlow> findByQuery(AccountFlowQuery query, int page, int limit) {
        Page<AccountFlowEntity> pageParam = new Page<>(page, limit);
        Page<AccountFlowEntity> result = accountFlowMapper.findByQuery(pageParam, query);
        return accountFlowConverter.listToDto(result.getRecords());
    }
    
    @Override
    public long countByAccountId(String accountId) {
        return accountFlowMapper.countByAccountId(accountId);
    }
    
    @Override
    public long countByQuery(AccountFlowQuery query) {
        return accountFlowMapper.countByQuery(query);
    }
} 