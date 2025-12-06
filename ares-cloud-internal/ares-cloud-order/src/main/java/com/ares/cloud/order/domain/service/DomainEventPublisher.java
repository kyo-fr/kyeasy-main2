package com.ares.cloud.order.domain.service;

import com.ares.cloud.order.domain.model.aggregate.Order;
import com.ares.cloud.order.domain.model.command.PayCommand;
import com.ares.cloud.order.domain.model.valueobject.PayItem;
import com.ares.cloud.order.domain.enums.OrderStatus;
import com.ares.cloud.order.domain.enums.DeliveryType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 领域事件发布器接口
 * 定义在领域层，由基础设施层实现
 * 遵循依赖倒置原则，领域层不依赖具体实现
 *
 * @author ares-cloud
 */
public interface DomainEventPublisher {

    /**
     * 发布订单创建事件
     *
     * @param orderId 订单ID
     * @param merchantId 商户ID
     * @param userId 用户ID
     * @param orderType 订单类型
     * @param totalAmount 订单总金额
     * @param createTime 创建时间
     */
    void publishOrderCreated(String orderId, String merchantId, String userId, 
                           String orderType, String totalAmount, LocalDateTime createTime);

    /**
     * 发布订单支付成功事件
     *
     * @param order 订单
     * @param command 商户支付信息
     */
    void publishOrderPaid(Order order, PayCommand command);

    /**
     * 发布订单状态变更事件
     *
     * @param orderId 订单ID
     * @param merchantId 商户ID
     * @param userId 用户ID
     * @param fromStatus 原状态
     * @param toStatus 新状态
     * @param reason 变更原因
     * @param operatorId 操作员ID
     * @param changeTime 变更时间
     */
    void publishOrderStatusChanged(String orderId, String merchantId, String userId,
                                 OrderStatus fromStatus, OrderStatus toStatus, String reason,
                                 String operatorId, LocalDateTime changeTime);

    /**
     * 发布订单退款事件
     *
     * @param orderId 订单ID
     * @param merchantId 商户ID
     * @param userId 用户ID
     * @param refundAmount 退款金额
     * @param refundReason 退款原因
     * @param refundMode 退款方式
     * @param refundTransactionId 退款交易ID
     * @param operatorId 操作员ID
     * @param refundTime 退款时间
     */
    void publishOrderRefunded(String orderId, String merchantId, String userId,
                            BigDecimal refundAmount, String refundReason, String refundMode,
                            String refundTransactionId, String operatorId, LocalDateTime refundTime);

    /**
     * 发布配送开始事件
     *
     * @param orderId 订单ID
     * @param merchantId 商户ID
     * @param userId 用户ID
     * @param riderId 配送员ID
     * @param deliveryType 配送类型
     * @param deliveryAddress 配送地址
     * @param receiverName 收货人姓名
     * @param receiverPhone 收货人电话
     * @param estimatedDeliveryTime 预计配送时间
     * @param startTime 开始时间
     */
    void publishDeliveryStarted(String orderId, String merchantId, String userId, String riderId,
                              DeliveryType deliveryType, String deliveryAddress, String receiverName,
                              String receiverPhone, LocalDateTime estimatedDeliveryTime, LocalDateTime startTime);

    /**
     * 发布配送完成事件
     *
     * @param orderId 订单ID
     * @param merchantId 商户ID
     * @param userId 用户ID
     * @param riderId 配送员ID
     * @param completedTime 完成时间
     * @param actualDeliveryAddress 实际配送地址
     * @param receiverConfirmed 收货人确认
     * @param deliveryNote 配送备注
     * @param deliveryRating 配送评分
     */
    void publishDeliveryCompleted(String orderId, String merchantId, String userId, String riderId,
                                LocalDateTime completedTime, String actualDeliveryAddress,
                                Boolean receiverConfirmed, String deliveryNote, Integer deliveryRating);

    /**
     * 发布库存预留事件
     *
     * @param orderId 订单ID
     * @param merchantId 商户ID
     * @param userId 用户ID
     * @param reservedItems 预留商品信息
     * @param reserveTime 预留时间
     * @param expireTime 过期时间
     */
    void publishInventoryReserved(String orderId, String merchantId, String userId,
                                java.util.List<InventoryReservedItem> reservedItems,
                                LocalDateTime reserveTime, LocalDateTime expireTime);


    /**
     * 库存预留项信息
     */
    class InventoryReservedItem {
        private String productId;
        private String specificationId;
        private Integer quantity;

        public InventoryReservedItem(String productId, String specificationId, Integer quantity) {
            this.productId = productId;
            this.specificationId = specificationId;
            this.quantity = quantity;
        }

        // Getters
        public String getProductId() { return productId; }
        public String getSpecificationId() { return specificationId; }
        public Integer getQuantity() { return quantity; }
    }
}
