package org.ares.cloud.domain.repository;

import org.ares.cloud.domain.model.PaymentOrder;
import java.util.Optional;

/**
 * 支付订单仓储接口
 */
public interface PaymentOrderRepository {
    
    /**
     * 保存支付订单
     *
     * @param order 支付订单
     */
    void save(PaymentOrder order);
    
    /**
     * 根据订单号查询支付订单
     *
     * @param orderNo 订单号
     * @return 支付订单
     */
    Optional<PaymentOrder> findByOrderNo(String orderNo);
} 