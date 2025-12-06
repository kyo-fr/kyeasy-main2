package com.ares.cloud.pay.infrastructure.persistence.mapper;

import com.ares.cloud.pay.application.queries.AccountFlowQuery;
import com.ares.cloud.pay.infrastructure.persistence.entity.AccountFlowEntity;
import com.ares.cloud.pay.infrastructure.persistence.entity.AccountStatisticsResult;
import com.ares.cloud.pay.infrastructure.persistence.entity.TransactionTypeStatisticsResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 账户流水Mapper
 */
@Mapper
public interface AccountFlowMapper extends BaseMapper<AccountFlowEntity> {
    
    /**
     * 根据账户ID查询流水记录
     *
     * @param accountId 账户ID
     * @return 流水记录列表
     */
    @Select("SELECT * FROM payment_account_flow WHERE account_id = #{accountId} AND deleted = 0 ORDER BY create_time DESC")
    List<AccountFlowEntity> findByAccountId(@Param("accountId") String accountId);
    
    /**
     * 根据交易ID查询流水记录
     *
     * @param transactionId 交易ID
     * @return 流水记录列表
     */
    @Select("SELECT * FROM payment_account_flow WHERE transaction_id = #{transactionId} AND deleted = 0")
    List<AccountFlowEntity> findByTransactionId(@Param("transactionId") String transactionId);
    
    /**
     * 根据订单ID查询流水记录
     *
     * @param orderId 订单ID
     * @return 流水记录列表
     */
    @Select("SELECT * FROM payment_account_flow WHERE order_id = #{orderId} AND deleted = 0")
    List<AccountFlowEntity> findByOrderId(@Param("orderId") String orderId);
    
    /**
     * 根据账户ID和流水类型查询流水记录
     *
     * @param accountId 账户ID
     * @param flowType 流水类型
     * @return 流水记录列表
     */
    @Select("SELECT * FROM payment_account_flow WHERE account_id = #{accountId} AND flow_type = #{flowType} AND deleted = 0 ORDER BY create_time DESC")
    List<AccountFlowEntity> findByAccountIdAndFlowType(@Param("accountId") String accountId, @Param("flowType") String flowType);
    
    /**
     * 根据账户ID和时间范围查询流水记录
     *
     * @param accountId 账户ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 流水记录列表
     */
    @Select("SELECT * FROM payment_account_flow WHERE account_id = #{accountId} AND create_time >= #{startTime} AND create_time <= #{endTime} AND deleted = 0 ORDER BY create_time DESC")
    List<AccountFlowEntity> findByAccountIdAndTimeRange(@Param("accountId") String accountId, @Param("startTime") String startTime, @Param("endTime") String endTime);
    
    /**
     * 分页查询账户流水
     *
     * @param page 分页对象
     * @param accountId 账户ID
     * @return 分页结果
     */
    @Select("SELECT * FROM payment_account_flow WHERE account_id = #{accountId} AND deleted = 0 ORDER BY create_time DESC")
    Page<AccountFlowEntity> findPageByAccountId(Page<AccountFlowEntity> page, @Param("accountId") String accountId);
    
    /**
     * 根据查询条件分页查询账户流水
     *
     * @param page 分页对象
     * @param query 查询条件
     * @return 分页结果
     */
    Page<AccountFlowEntity> findByQuery(Page<AccountFlowEntity> page, @Param("query") AccountFlowQuery query);
    
    /**
     * 根据查询条件统计账户流水总数
     *
     * @param query 查询条件
     * @return 总数
     */
    long countByQuery(@Param("query") AccountFlowQuery query);
    
    /**
     * 统计账户流水总数
     *
     * @param accountId 账户ID
     * @return 总数
     */
    @Select("SELECT COUNT(*) FROM payment_account_flow WHERE account_id = #{accountId} AND deleted = 0")
    long countByAccountId(@Param("accountId") String accountId);
    
    /**
     * 根据账户ID和时间范围统计交易数据
     *
     * @param accountId 账户ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param paymentRegion 支付区域
     * @return 统计结果
     */
    AccountStatisticsResult getAccountStatistics(@Param("accountId") String accountId, 
                                                @Param("startTime") Long startTime, 
                                                @Param("endTime") Long endTime,
                                                @Param("paymentRegion") String paymentRegion);
    
    /**
     * 根据账户ID和时间范围统计按交易类型分组的数据
     *
     * @param accountId 账户ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param paymentRegion 支付区域
     * @return 按交易类型分组的统计结果列表
     */
    List<TransactionTypeStatisticsResult> getTransactionTypeStatistics(@Param("accountId") String accountId, 
                                                                       @Param("startTime") Long startTime, 
                                                                       @Param("endTime") Long endTime,
                                                                       @Param("paymentRegion") String paymentRegion);
} 