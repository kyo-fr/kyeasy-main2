package com.ares.cloud.pay.domain.repository;

import com.ares.cloud.pay.application.queries.AccountFlowQuery;
import com.ares.cloud.pay.domain.model.AccountFlow;

import java.util.List;

/**
 * 账户流水仓储接口
 */
public interface AccountFlowRepository {
    
    /**
     * 保存账户流水
     */
    void save(AccountFlow accountFlow);
    
    /**
     * 批量保存账户流水
     */
    void saveBatch(List<AccountFlow> accountFlows);
    
    /**
     * 根据ID查询账户流水
     */
    AccountFlow findById(String id);
    
    /**
     * 根据账户ID查询流水记录
     */
    List<AccountFlow> findByAccountId(String accountId);
    
    /**
     * 根据交易ID查询流水记录
     */
    List<AccountFlow> findByTransactionId(String transactionId);
    
    /**
     * 根据订单ID查询流水记录
     */
    List<AccountFlow> findByOrderId(String orderId);
    
    /**
     * 根据账户ID和流水类型查询流水记录
     */
    List<AccountFlow> findByAccountIdAndFlowType(String accountId, String flowType);
    
    /**
     * 根据账户ID和时间范围查询流水记录
     */
    List<AccountFlow> findByAccountIdAndTimeRange(String accountId, Long startTime, Long endTime);
    
    /**
     * 分页查询账户流水
     */
    List<AccountFlow> findPageByAccountId(String accountId, int page, int limit);
    
    /**
     * 根据查询条件分页查询账户流水
     */
    List<AccountFlow> findByQuery(AccountFlowQuery query, int page, int limit);
    
    /**
     * 根据账户ID统计流水总数
     */
    long countByAccountId(String accountId);
    
    /**
     * 根据查询条件统计账户流水总数
     */
    long countByQuery(AccountFlowQuery query);
} 