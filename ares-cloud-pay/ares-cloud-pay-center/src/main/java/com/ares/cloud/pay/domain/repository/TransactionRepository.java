package com.ares.cloud.pay.domain.repository;

import com.ares.cloud.pay.domain.model.Transaction;

import java.util.List;

/**
 * 交易仓储接口
 */
public interface TransactionRepository {
    
    /**
     * 保存交易
     */
    void save(Transaction transaction);
    
    /**
     * 根据ID查询交易
     */
    Transaction findById(String id);
    
    /**
     * 根据订单ID查询交易列表
     */
    List<Transaction> findByOrderId(String orderId);
    
    /**
     * 根据来源账户ID查询交易列表
     */
    List<Transaction> findByFromAccountId(String fromAccountId);
    
    /**
     * 根据目标账户ID查询交易列表
     */
    List<Transaction> findByToAccountId(String toAccountId);
    
    /**
     * 根据账户ID查询交易列表（作为来源或目标）
     */
    List<Transaction> findByAccountId(String accountId);
    
    /**
     * 根据支付区域查询交易列表
     */
    List<Transaction> findByPaymentRegion(String paymentRegion);
    
    /**
     * 根据交易类型查询交易列表
     */
    List<Transaction> findByType(String type);
    
    /**
     * 根据交易状态查询交易列表
     */
    List<Transaction> findByStatus(String status);
    
    /**
     * 更新交易状态
     */
    void updateStatus(String id, String status);
} 