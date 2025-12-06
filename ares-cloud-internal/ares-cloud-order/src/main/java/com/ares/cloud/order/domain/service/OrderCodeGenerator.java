package com.ares.cloud.order.domain.service;

import com.ares.cloud.order.domain.enums.OrderType;

/**
 * 订单编号生成器接口
 * 根据不同订单类型生成不同格式的订单编号
 */
public interface OrderCodeGenerator {
    
    /**
     * 根据订单类型生成订单编号
     *
     * @param orderType 订单类型
     * @param merchantId 商户ID
     * @return 订单编号
     */
    String generateOrderCode(OrderType orderType, String merchantId);
    /**
     * 生成订单号
     * 格式：日期(8位) + 商户ID后4位 + 6位随机数
     * 例如：202307150001123456
     *
     * @param merchantId 商户ID
     * @return 订单号
     */
     String generateOrderNumber(String merchantId);
}