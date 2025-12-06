package com.ares.cloud.pay.infrastructure.persistence.mapper;

import com.ares.cloud.pay.infrastructure.persistence.entity.TransactionEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 交易Mapper接口
 */
@Mapper
public interface TransactionMapper extends BaseMapper<TransactionEntity> {
    
    /**
     * 根据订单ID查询交易列表
     *
     * @param orderId 订单ID
     * @return 交易列表
     */
    @Select("SELECT * FROM blockchain_transactions WHERE order_id = #{orderId} AND deleted = 0")
    List<TransactionEntity> findByOrderId(@Param("orderId") String orderId);
    
    /**
     * 根据来源账户ID查询交易列表
     *
     * @param fromAccountId 来源账户ID
     * @return 交易列表
     */
    @Select("SELECT * FROM blockchain_transactions WHERE from_account_id = #{fromAccountId} AND deleted = 0")
    List<TransactionEntity> findByFromAccountId(@Param("fromAccountId") String fromAccountId);
    
    /**
     * 根据目标账户ID查询交易列表
     *
     * @param toAccountId 目标账户ID
     * @return 交易列表
     */
    @Select("SELECT * FROM blockchain_transactions WHERE to_account_id = #{toAccountId} AND deleted = 0")
    List<TransactionEntity> findByToAccountId(@Param("toAccountId") String toAccountId);
    
    /**
     * 根据账户ID查询交易列表（作为来源或目标）
     *
     * @param accountId 账户ID
     * @return 交易列表
     */
    @Select("SELECT * FROM blockchain_transactions WHERE (from_account_id = #{accountId} OR to_account_id = #{accountId}) AND deleted = 0")
    List<TransactionEntity> findByAccountId(@Param("accountId") String accountId);
    
    /**
     * 根据支付区域查询交易列表
     *
     * @param paymentRegion 支付区域
     * @return 交易列表
     */
    @Select("SELECT * FROM blockchain_transactions WHERE payment_region = #{paymentRegion} AND deleted = 0")
    List<TransactionEntity> findByPaymentRegion(@Param("paymentRegion") String paymentRegion);
    
    /**
     * 根据交易类型查询交易列表
     *
     * @param type 交易类型
     * @return 交易列表
     */
    @Select("SELECT * FROM blockchain_transactions WHERE type = #{type} AND deleted = 0")
    List<TransactionEntity> findByType(@Param("type") String type);
    
    /**
     * 根据交易状态查询交易列表
     *
     * @param status 交易状态
     * @return 交易列表
     */
    @Select("SELECT * FROM blockchain_transactions WHERE status = #{status} AND deleted = 0")
    List<TransactionEntity> findByStatus(@Param("status") String status);
} 