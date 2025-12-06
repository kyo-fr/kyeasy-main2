package com.ares.cloud.pay.infrastructure.persistence.mapper;

import com.ares.cloud.pay.infrastructure.persistence.entity.MerchantEntity;
import com.ares.cloud.pay.infrastructure.persistence.entity.MerchantStatisticsResult;
import com.ares.cloud.pay.infrastructure.persistence.entity.TransactionTypeStatisticsResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 商户Mapper接口
 */
@Mapper
public interface MerchantMapper extends BaseMapper<MerchantEntity> {
    
    /**
     * 根据商户号查询商户
     *
     * @param merchantNo 商户号
     * @return 商户
     */
    @Select("SELECT * FROM merchants WHERE merchant_no = #{merchantNo} AND deleted = 0")
    MerchantEntity findByMerchantNo(@Param("merchantNo") String merchantNo);
    
    /**
     * 根据租户ID查询商户列表
     *
     * @param tenantId 租户ID
     * @return 商户列表
     */
    @Select("SELECT * FROM merchants WHERE tenant_id = #{tenantId} AND deleted = 0")
    List<MerchantEntity> findByTenantId(@Param("tenantId") String tenantId);
    
    /**
     * 根据状态查询商户列表
     *
     * @param status 商户状态
     * @return 商户列表
     */
    @Select("SELECT * FROM merchants WHERE status = #{status} AND deleted = 0")
    List<MerchantEntity> findByStatus(@Param("status") String status);
    
    /**
     * 根据商户ID和时间范围统计商户交易数据
     *
     * @param merchantId 商户ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param paymentRegion 支付区域
     * @return 统计结果
     */
    MerchantStatisticsResult getMerchantStatistics(@Param("merchantId") String merchantId,
                                                   @Param("startTime") Long startTime,
                                                   @Param("endTime") Long endTime,
                                                   @Param("paymentRegion") String paymentRegion);
    
    /**
     * 根据商户ID和时间范围统计按交易类型分组的数据
     *
     * @param merchantId 商户ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param paymentRegion 支付区域
     * @return 按交易类型分组的统计结果列表
     */
    List<TransactionTypeStatisticsResult> getMerchantTransactionTypeStatistics(@Param("merchantId") String merchantId, 
                                                                               @Param("startTime") Long startTime, 
                                                                               @Param("endTime") Long endTime,
                                                                               @Param("paymentRegion") String paymentRegion);
} 