package com.ares.cloud.pay.domain.repository;

import com.ares.cloud.pay.domain.enums.PaymentStatus;
import com.ares.cloud.pay.domain.model.PaymentOrder;
import java.util.List;

/**
 * 支付订单仓储接口
 */
public interface PaymentOrderRepository {
    
    /**
     * 保存支付订单
     *
     * @param paymentOrder 支付订单
     */
    void save(PaymentOrder paymentOrder);
    
    /**
     * 根据订单号查询支付订单
     *
     * @param orderNo 订单号
     * @return 支付订单
     */
    PaymentOrder findByOrderNo(String orderNo);
    
    /**
     * 根据商户订单号查询支付订单
     *
     * @param merchantOrderNo 商户订单号
     * @return 支付订单
     */
    PaymentOrder findByMerchantOrderNo(String merchantOrderNo);
    
    /**
     * 根据商户ID查询支付订单列表
     *
     * @param merchantId 商户ID
     * @return 支付订单列表
     */
    List<PaymentOrder> findByMerchantId(String merchantId);
    
    /**
     * 根据用户ID查询支付订单列表
     *
     * @param userId 用户ID
     * @return 支付订单列表
     */
    List<PaymentOrder> findByUserId(String userId);
    
    /**
     * 根据支付状态查询支付订单列表
     *
     * @param status 支付状态
     * @return 支付订单列表
     */
    List<PaymentOrder> findByStatus(PaymentStatus status);
    
    /**
     * 更新支付订单状态
     *
     * @param orderNo 订单号
     * @param status 新状态
     */
    void updateStatus(String orderNo, PaymentStatus status);
} 