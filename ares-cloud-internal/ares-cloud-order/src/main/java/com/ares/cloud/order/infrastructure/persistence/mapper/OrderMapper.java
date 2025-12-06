package com.ares.cloud.order.infrastructure.persistence.mapper;

import com.ares.cloud.order.infrastructure.persistence.entity.KnightStatisticsResult;
import com.ares.cloud.order.infrastructure.persistence.entity.OrderEntity;
import com.ares.cloud.order.infrastructure.persistence.entity.PaymentChannelStatisticsResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper extends BaseMapper<OrderEntity> {
    
    /**
     * 根据骑士ID和时间范围统计骑士配送数据
     *
     * @param riderId 骑士ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param paymentRegion 支付区域/货币
     * @return 统计结果
     */
    KnightStatisticsResult getKnightStatistics(@Param("riderId") String riderId,
                                               @Param("startTime") Long startTime,
                                               @Param("endTime") Long endTime,
                                               @Param("paymentRegion") String paymentRegion);
    
    /**
     * 根据骑士ID和时间范围统计按支付渠道分组的数据
     *
     * @param riderId 骑士ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param paymentRegion 支付区域/货币
     * @return 按支付渠道分组的统计结果列表
     */
    List<PaymentChannelStatisticsResult> getPaymentChannelStatistics(@Param("riderId") String riderId,
                                                                      @Param("startTime") Long startTime,
                                                                      @Param("endTime") Long endTime,
                                                                      @Param("paymentRegion") String paymentRegion);
} 