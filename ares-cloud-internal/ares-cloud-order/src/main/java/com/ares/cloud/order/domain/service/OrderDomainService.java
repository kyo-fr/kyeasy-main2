package com.ares.cloud.order.domain.service;

import com.ares.cloud.order.domain.model.command.PayCommand;
import com.ares.cloud.order.domain.model.valueobject.*;
import org.ares.cloud.api.order.commod.CreateOrderCommand;

import java.util.List;

/**
 * 订单领域服务
 */
public interface OrderDomainService {

    /**
     * 创建订单
     *
     * @param command 创建订单命令
     * param tenantId 租户ID
     *  param isPlatform 是否平台订单
     * @return 订单ID
     */
    String createOrder(CreateOrderCommand command, String tenantId);

    /**
     * 确认订单
     *
     * @param orderId 订单ID
     */
    void confirmOrder(String orderId);
    /**
     * 骑手接单
     *
     * @param orderId 订单ID
     * @param deliveryInfo 配送信息
     */
    void acceptDelivery(String orderId, DeliveryInfo deliveryInfo);

    /**
     * 开始配送
     *
     * @param orderId 订单ID
     */
    void startDelivery(String orderId);
    /**
     * 开始配送
     *
     * @param orderId 订单ID
     * @param deliveryInfo 配送信息
     */
    void startDelivery(String orderId, DeliveryInfo deliveryInfo);

    /**
     * 完成订单
     *
     * @param orderId 订单ID
     */
    void completeOrder(String orderId);

    /**
     * 取消订单
     *
     * @param orderId 订单ID
     * @param reason 取消原因
     * @param operatorId 操作人ID
     */
    void cancelOrder(String orderId,String reason, String operatorId);

    /**
     * 支付订单
     *
     * @param payOrderCommand 支付订单命令
     */
    void payOrder(PayCommand payOrderCommand);

    /**
     * 退款
     *
     * @param orderId 订单ID
     * @param reason 退款原因
     */
    void refundOrder(String orderId, String reason);
    
    /**
     * 退款完成
     *
     * @param orderId 订单ID
     */
    void completeRefund(String orderId);
    
    /**
     * 部分支付订单
     *
     * @param payOrderCommand 支付订单命令
     */
    void partialPayOrder(PayCommand payOrderCommand);
    
    /**
     * 根据商户ID获取商户信息
     *
     * @param merchantId 商户ID
     * @return 商户信息
     */
    MerchantInfo getMerchantInfo(String merchantId,boolean isPlatform);

    /**
     * 完成配送
     *
     * @param orderId 订单ID
     */
    void completeDelivery(String orderId);

    /**
     * 开始就餐 - 店内就餐订单专用
     * @param orderId 订单ID
     */
    void startDining(String orderId);

    /**
     * 就餐中 - 店内就餐订单专用
     * @param orderId 订单ID
     */
    void diningInProgress(String orderId);

    /**
     * 准备取餐 - 自取订单专用
     * @param orderId 订单ID
     */
    void readyForPickup(String orderId);

    /**
     * 顾客到店 - 预订订单专用
     * @param orderId 订单ID
     */
    void customerArrived(String orderId);

    /**
     * 进入排队 - 店内就餐和预订订单专用
     * @param orderId 订单ID
     */
    void startQueuing(String orderId);

    /**
     * 结束排队开始就餐 - 店内就餐和预订订单专用
     * @param orderId 订单ID
     */
    void endQueuing(String orderId);
    /**
     * 删除订单商品
     *
     * @param orderId 订单ID
     * @param orderItemIds 订单商品ID列表
     * @param reason 删除原因
     * @param operatorId 操作人ID
     */
    void deleteOrderItem(String orderId, List<String> orderItemIds, String reason, String operatorId);
    
    /**
     * 更新预订信息
     *
     * @param orderId 订单ID
     * @param newReservationInfo 新的预订信息
     */
    void updateReservationInfo(String orderId, ReservationInfo newReservationInfo);
}