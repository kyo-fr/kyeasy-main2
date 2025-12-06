package com.ares.cloud.pay.infrastructure.repository;

import com.ares.cloud.pay.domain.model.Transaction;
import com.ares.cloud.pay.domain.repository.TransactionRepository;
import com.ares.cloud.pay.infrastructure.persistence.converter.TransactionConverter;
import com.ares.cloud.pay.infrastructure.persistence.entity.TransactionEntity;
import com.ares.cloud.pay.infrastructure.persistence.mapper.TransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 交易仓储实现类
 */
@Repository
public class TransactionRepositoryImpl implements TransactionRepository {
    
    @Autowired
    private TransactionMapper transactionMapper;
    
    @Autowired
    private TransactionConverter transactionConverter;
    
    @Override
    public void save(Transaction transaction) {
        TransactionEntity entity = transactionConverter.toEntity(transaction);
        transactionMapper.insert(entity);
    }
    
    @Override
    public Transaction findById(String id) {
        TransactionEntity entity = transactionMapper.selectById(id);
        return transactionConverter.toDto(entity);
    }
    
    @Override
    public List<Transaction> findByOrderId(String orderId) {
        List<TransactionEntity> entities = transactionMapper.findByOrderId(orderId);
        return transactionConverter.listToDto(entities);
    }
    
    @Override
    public List<Transaction> findByFromAccountId(String fromAccountId) {
        List<TransactionEntity> entities = transactionMapper.findByFromAccountId(fromAccountId);
        return transactionConverter.listToDto(entities);
    }
    
    @Override
    public List<Transaction> findByToAccountId(String toAccountId) {
        List<TransactionEntity> entities = transactionMapper.findByToAccountId(toAccountId);
        return transactionConverter.listToDto(entities);
    }
    
    @Override
    public List<Transaction> findByAccountId(String accountId) {
        List<TransactionEntity> entities = transactionMapper.findByAccountId(accountId);
        return transactionConverter.listToDto(entities);
    }
    
    @Override
    public List<Transaction> findByPaymentRegion(String paymentRegion) {
        List<TransactionEntity> entities = transactionMapper.findByPaymentRegion(paymentRegion);
        return transactionConverter.listToDto(entities);
    }
    
    @Override
    public List<Transaction> findByType(String type) {
        List<TransactionEntity> entities = transactionMapper.findByType(type);
        return transactionConverter.listToDto(entities);
    }
    
    @Override
    public List<Transaction> findByStatus(String status) {
        List<TransactionEntity> entities = transactionMapper.findByStatus(status);
        return transactionConverter.listToDto(entities);
    }
    
    @Override
    public void updateStatus(String id, String status) {
        TransactionEntity entity = new TransactionEntity();
        entity.setId(id);
        entity.setStatus(status);
        transactionMapper.updateById(entity);
    }
} 