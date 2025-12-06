package com.ares.cloud.pay.infrastructure.persistence.mapper;

import com.ares.cloud.pay.infrastructure.persistence.entity.PaymentOrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 支付订单Mapper接口
 */
@Mapper
public interface PaymentOrderMapper extends BaseMapper<PaymentOrderEntity> {
    
    /**
     * 根据商户订单号查询支付订单
     *
     * @param merchantOrderNo 商户订单号
     * @return 支付订单
     */
    @Select("SELECT * FROM payment_order WHERE merchant_order_no = #{merchantOrderNo} AND deleted = 0")
    PaymentOrderEntity findByMerchantOrderNo(@Param("merchantOrderNo") String merchantOrderNo);
    
    /**
     * 根据商户ID查询支付订单列表
     *
     * @param merchantId 商户ID
     * @return 支付订单列表
     */
    @Select("SELECT * FROM payment_order WHERE merchant_id = #{merchantId} AND deleted = 0")
    List<PaymentOrderEntity> findByMerchantId(@Param("merchantId") String merchantId);
    
    /**
     * 根据用户ID查询支付订单列表
     *
     * @param userId 用户ID
     * @return 支付订单列表
     */
    @Select("SELECT * FROM payment_order WHERE from_user_id = #{userId} AND deleted = 0")
    List<PaymentOrderEntity> findByUserId(@Param("userId") String userId);
    
    /**
     * 根据状态查询支付订单列表
     *
     * @param status 状态
     * @return 支付订单列表
     */
    @Select("SELECT * FROM payment_order WHERE status = #{status} AND deleted = 0")
    List<PaymentOrderEntity> findByStatus(@Param("status") Integer status);
} 