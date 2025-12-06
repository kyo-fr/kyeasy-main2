package com.ares.cloud.pay.infrastructure.persistence.mapper;

import com.ares.cloud.pay.infrastructure.persistence.entity.FeeConfigEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 手续费配置Mapper
 */
@Mapper
public interface FeeConfigMapper extends BaseMapper<FeeConfigEntity> {
    
    /**
     * 根据交易类型和支付区域查询手续费配置
     *
     * @param transactionType 交易类型
     * @param paymentRegion 支付区域
     * @return 手续费配置
     */
    @Select("SELECT * FROM fee_config WHERE transaction_type = #{transactionType} AND payment_region = #{paymentRegion} AND enabled = 1 AND deleted = 0")
    FeeConfigEntity findByTransactionTypeAndRegion(@Param("transactionType") String transactionType, @Param("paymentRegion") String paymentRegion);
    
    /**
     * 查询所有启用的手续费配置
     *
     * @return 手续费配置列表
     */
    @Select("SELECT * FROM fee_config WHERE enabled = 1 AND deleted = 0 ORDER BY create_time DESC")
    List<FeeConfigEntity> findAllEnabled();
    
    /**
     * 根据交易类型查询手续费配置
     *
     * @param transactionType 交易类型
     * @return 手续费配置列表
     */
    @Select("SELECT * FROM fee_config WHERE transaction_type = #{transactionType} AND enabled = 1 AND deleted = 0")
    List<FeeConfigEntity> findByTransactionType(@Param("transactionType") String transactionType);
    
    /**
     * 根据支付区域查询手续费配置
     *
     * @param paymentRegion 支付区域
     * @return 手续费配置列表
     */
    @Select("SELECT * FROM fee_config WHERE payment_region = #{paymentRegion} AND enabled = 1 AND deleted = 0")
    List<FeeConfigEntity> findByPaymentRegion(@Param("paymentRegion") String paymentRegion);
} 