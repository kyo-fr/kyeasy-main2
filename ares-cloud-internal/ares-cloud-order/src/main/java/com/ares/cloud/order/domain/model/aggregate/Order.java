package com.ares.cloud.order.domain.model.aggregate;

import cn.hutool.core.collection.CollectionUtil;
import com.ares.cloud.order.domain.enums.*;
// 事件相关导入已移除，现在通过DomainEventPublisher发布MQ消息
import com.ares.cloud.order.domain.model.entity.OrderItem;
import com.ares.cloud.order.domain.model.valueobject.DeliveryInfo;
import com.ares.cloud.order.domain.model.valueobject.KnightInfo;
import com.ares.cloud.order.domain.model.valueobject.PayItem;
import com.ares.cloud.order.domain.model.valueobject.*;
import com.ares.cloud.order.domain.service.OrderStatusTransitionService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.ares.cloud.common.exception.RequestBadException;
import org.ares.cloud.common.model.Money;
import org.ares.cloud.common.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单聚合根
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    /**
     * 支付超时时间(分钟)
     */
    private static final int PAYMENT_TIMEOUT_MINUTES = 30;

    /**
     * 订单来源类型常量
     */
    public static final Integer SOURCE_TYPE_ONLINE = 1;  // 网上下单
    public static final Integer SOURCE_TYPE_MERCHANT = 2;  // 商户下单
    
    /**
     * 订单基本信息
     */
    private String id;
    private String merchantId;
    private String userId;  // 在线订单必填
    private String orderNumber;  // 订单号
    private String orderCode;  // 订单编号
    private String countryCode; // 国家代码
    private String phoneNumber;  // 用户手机号
    private OrderType orderType;
    private OrderStatus status;
    private String currency;
    private Integer currencyScale;
    private String timezone;
    private Integer sourceType;  // 订单来源类型

    /**
     * 订单金额信息
     */
    private Money totalAmount;
    private Money serviceFee;
    private Money paidAmount;

    /**
     * 支付相关信息
     */
    private PaymentMode paymentMode;
    private String paymentChannelId;
    private String paymentTradeNo;
    private Long paymentTime;
    private Long paymentDeadline;
    @Builder.Default
    private PaymentStatus paymentStatus = PaymentStatus.UNPAID;

    /**
     * 订单项信息
     */
    @Builder.Default
    private List<OrderItem> items = new ArrayList<>();
    @Builder.Default
    private List<String> settledItemIds = new ArrayList<>();

    /**
     * 时间信息
     */
    private Long createTime;
    private Long deliveryTime;
    private Long finishTime;

    /**
     * 配送信息 - 配送订单专用
     */
    private String riderId;
    private DeliveryStatus deliveryStatus;

    /**
     * 预订信息 - 预订订单专用
     */
    private Long reservationTime;

    /**
     * 就餐信息 - 店内就餐专用
     */
    private String tableNo;
    private Integer diningNumber;

    /**
     * 自取信息 - 自取订单专用
     */
    private Long pickupTime;

    /**
     * 配送信息
     */
    private DeliveryInfo deliveryInfo;

    /**
     * 配送方式
     */
    private DeliveryType deliveryType;

    /**
     * 预订信息
     */
    private ReservationInfo reservationInfo;

    // 领域事件列表已移除，现在通过DomainEventPublisher发布MQ消息
    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 取消时间
     */
    private Long cancelTime;

    /**
     * 取消原因
     */
    private String cancelReason;

    /**
     * 获取商户时区的当前时间戳
     */
    private Long getCurrentTimeInMerchantZone() {
        if (StringUtils.isBlank(timezone)) {
            return System.currentTimeMillis();
        }
        return DateUtils.getCurrentTimestampInUTC();
    }

    // 事件相关方法已移除，现在通过DomainEventPublisher发布MQ消息

    /**
     * 添加订单项
     */
    public void addItem(OrderItem item) {
        if (item == null) {
            throw new RequestBadException(OrderError.ORDER_ITEM_NOT_FOUND);
        }
        this.currencyScale = currencyScale == null ? 2 : currencyScale;
        // 设置订单项的币种和精度
        item.setCurrency(this.currency);
        item.setCurrencyScale(this.currencyScale);
        // 计算订单项总价
        item.calculateTotalPrice();
        // 添加到订单项列表
        this.items.add(item);
        // 重新计算订单总金额
        recalculateAmount();
    }

    /**
     * 删除订单项
     *
     * @param orderItemId 订单项ID
     * @return 被删除的订单项
     */
    public OrderItem removeItem(String orderItemId) {
        if (StringUtils.isBlank(orderItemId)) {
            throw new RequestBadException(OrderError.ORDER_ITEM_NOT_FOUND);
        }

        // 查找订单项
        OrderItem itemToRemove = items.stream()
                .filter(item -> orderItemId.equals(item.getId()))
                .findFirst()
                .orElseThrow(() -> new RequestBadException(OrderError.ORDER_ITEM_NOT_FOUND));

        // 检查订单项是否已支付
        if (itemToRemove.getPaymentStatus() == PaymentStatus.PAID) {
            throw new RequestBadException(OrderError.CANNOT_DELETE_PAID_ITEM);
        }

        // 标记删除
        itemToRemove.setDeleted(true);

        // 重新计算订单金额
        recalculateAmount();

        return itemToRemove;
    }

    /**
     * 重新计算金额
     */
    private void recalculateAmount() {
        // 计算订单项总价（包含服务费），过滤掉已删除的订单项
        this.totalAmount = items.stream()
                .filter(item -> item.getDeleted() == null || !item.getDeleted())
                .map(OrderItem::getTotalPrice)
                .reduce(Money::add)
                .orElse(Money.zeroMoney(currency, currencyScale));

        // 如果服务费为空，初始化为0
        if (this.serviceFee == null) {
            this.serviceFee = Money.zeroMoney(currency, currencyScale);
        }

        // 添加配送费到总金额（如果存在）配送费现在是订单项了不用再单独计算了
//        if (this.deliveryInfo != null && this.deliveryInfo.getDeliveryFee() != null) {
//            // 验证配送费的币种和精度是否与订单一致
//            Money deliveryFee = this.deliveryInfo.getDeliveryFee();
//            if (!deliveryFee.getCurrency().equals(this.currency)) {
//                throw new RequestBadException(OrderError.INVALID_DELIVERY_FEE_CURRENCY);
//            }
//            if (!deliveryFee.getScale().equals(this.currencyScale)) {
//                throw new RequestBadException(OrderError.INVALID_DELIVERY_FEE_SCALE);
//            }
//            // 将配送费添加到总金额
//            this.totalAmount = this.totalAmount.add(deliveryFee);
//        }
    }

    /**
     * 订单全部支付
     */
    public void fullPay(List<PayItem> payItems, Money deductAmount) {
        // 1. 验证支付项列表不为空
        if (payItems == null || payItems.isEmpty()) {
            throw new RequestBadException(OrderError.INVALID_PAYMENT_ITEMS);
        }

        // 2. 计算总支付金额
        Money totalPayAmount = payItems.stream()
                .map(PayItem::getAmount)
                .reduce(Money.zero(currency, currencyScale), Money::add);

        // 3. 验证支付金额是否等于订单剩余金额（考虑扣减金额）
        Money remainingAmount = getRemainingAmount();
        if (deductAmount != null) {
            remainingAmount = remainingAmount.subtract(deductAmount);
        }
        if (!totalPayAmount.isEqual(remainingAmount)) {
            throw new RequestBadException(OrderError.INVALID_PAYMENT_AMOUNT);
        }

        // 4. 更新支付信息
        for (PayItem payItem : payItems) {
            updatePaymentInfo(payItem);
        }

        // 5. 更新订单项支付状态
        items.forEach(OrderItem::completePay);

        // 6. 更新订单支付状态
        this.paymentStatus = PaymentStatus.PAID;

        // 7. 订单支付成功事件将通过DomainEventPublisher发布
    }

    /**
     * 部分支付
     *
     * @param orderItemIds 支付订单项的ID
     * @param payItems     支付项
     * @param deductAmount 扣减金额
     */
    public void partialPay(List<String> orderItemIds, List<PayItem> payItems, Money deductAmount) {
        // 1. 验证支付项列表不为空
        if (payItems == null || payItems.isEmpty()) {
            throw new RequestBadException(OrderError.INVALID_PAYMENT_ITEMS);
        }

        // 2. 计算总支付金额
        Money totalPayAmount = payItems.stream()
                .map(PayItem::getAmount)
                .reduce(Money.zero(currency, currencyScale), Money::add);

        // 3. 如果指定了订单项
        if (orderItemIds != null && !orderItemIds.isEmpty()) {
            // 3.1 验证订单项是否存在
            List<OrderItem> payingItems = items.stream()
                    .filter(item -> orderItemIds.contains(item.getId()))
                    .toList();

            if (payingItems.size() != orderItemIds.size()) {
                throw new RequestBadException(OrderError.ORDER_ITEM_NOT_FOUND);
            }

            // 3.2 验证订单项是否可以支付
            for (OrderItem item : payingItems) {
                if (!item.canPay()) {
                    throw new RequestBadException(OrderError.ITEM_ALREADY_PAID);
                }
            }

            // 3.3 验证支付金额是否等于订单项总金额（考虑扣减金额）
            Money itemsTotal = payingItems.stream()
                    .map(OrderItem::getTotalAmount)
                    .reduce(Money.zero(currency, currencyScale), Money::add);

            // 如果有扣减金额，从订单项总金额中减去
            if (deductAmount != null) {
                itemsTotal = itemsTotal.subtract(deductAmount);
            }

            if (!totalPayAmount.equals(itemsTotal)) {
                throw new RequestBadException(OrderError.INVALID_PAYMENT_AMOUNT);
            }

            // 3.4 更新订单项支付状态
            payingItems.forEach(OrderItem::completePay);
        }

        // 4. 验证支付金额不超过剩余应付金额（考虑扣减金额）
        Money remainingAmount = getRemainingAmount();
        if (deductAmount != null) {
            remainingAmount = remainingAmount.subtract(deductAmount);
        }
        if (totalPayAmount.isGreaterThan(remainingAmount)) {
            throw new RequestBadException(OrderError.PAYMENT_AMOUNT_EXCEEDS_REMAINING);
        }

        // 5. 更新支付信息
        for (PayItem payItem : payItems) {
            updatePaymentInfo(payItem);
        }

        // 6. 如果全部支付完成，更新订单支付状态
        if (isFullyPaid()) {
            this.paymentStatus = PaymentStatus.PAID;
        } else {
            this.paymentStatus = PaymentStatus.PARTIALLY_PAID;
        }

        // 7. 部分支付事件将通过DomainEventPublisher发布
    }

    /**
     * 确认订单
     */
    public void confirm() {
        OrderStatus oldStatus = this.status;
        if (this.status != OrderStatus.TO_BE_CONFIRMED) {
            throw new RequestBadException(OrderError.INVALID_STATUS_TRANSITION);
        }
        this.status = OrderStatus.UNSETTLED;
        // 状态变更事件将通过DomainEventPublisher发布
    }

    /**
     * 开始配送
     */
    public void startDelivery(DeliveryInfo deliveryInfo) {
        // 1. 验证订单类型
        if (this.orderType != OrderType.DELIVERY) {
            throw new RequestBadException(OrderError.ORDER_NOT_DELIVERY);
        }

        // 2. 验证配送信息
        if (deliveryInfo == null) {
            throw new RequestBadException(OrderError.DELIVERY_INFO_REQUIRED);
        }
        deliveryInfo.validate();

        // 3. 验证配送费的币种和精度
        if (deliveryInfo.getDeliveryFee() != null) {
            Money deliveryFee = deliveryInfo.getDeliveryFee();
            if (!deliveryFee.getCurrency().equals(this.currency)) {
                throw new RequestBadException(OrderError.INVALID_DELIVERY_FEE_CURRENCY);
            }
            if (!deliveryFee.getScale().equals(this.currencyScale)) {
                throw new RequestBadException(OrderError.INVALID_DELIVERY_FEE_SCALE);
            }
        }

        // 4. 验证配送距离
//        if (deliveryInfo.getDeliveryDistance() == null || deliveryInfo.getDeliveryDistance() <= 0) {
//            throw new RequestBadException(OrderError.INVALID_DELIVERY_DISTANCE);
//        }

        // 5. 设置配送信息
        this.deliveryInfo = deliveryInfo;
        this.deliveryType = deliveryInfo.getDeliveryType();

        // 6. 根据配送类型进行不同处理
//        if (deliveryType == DeliveryType.MERCHANT) {
//            // 设置骑手ID
//            if (StringUtils.isNotBlank(deliveryInfo.getRiderId())) {
//                this.riderId = deliveryInfo.getRiderId();
//            }
//        }

        // 7. 更新状态
        this.deliveryStatus = DeliveryStatus.DELIVERING;
        this.deliveryTime = DateUtils.getCurrentTimestampInUTC();

        // 8. 重新计算订单金额（包含配送费）
        recalculateAmount();

        // 9. 配送开始事件将通过DomainEventPublisher发布
    }

    /**
     * 完成配送
     */
    public void completeDelivery() {
        // 1. 验证订单类型
        if (this.orderType != OrderType.DELIVERY) {
            throw new RequestBadException(OrderError.ORDER_NOT_DELIVERY);
        }

        // 2. 验证订单状态
        if (this.deliveryStatus != DeliveryStatus.DELIVERING) {
            throw new RequestBadException(OrderError.INVALID_STATUS_TRANSITION);
        }

        // 3. 更新配送状态
        this.deliveryStatus = DeliveryStatus.DELIVERY_COMPLETED;

        // 4. 根据支付方式设置状态
        if (paymentMode == PaymentMode.OFFLINE) {
            // 货到付款订单设置为未结算状态
            setStatus(OrderStatus.UNSETTLED);
            // 重新设置支付截止时间
            initPaymentDeadline();
        } else if (isFullyPaid()) {
            // 在线支付且已全额支付，设置为已结算状态
            setStatus(OrderStatus.SETTLED);
            this.finishTime = DateUtils.getCurrentTimestampInUTC();
        } else {
            // 在线支付但未全额支付，设置为未结算状态
            setStatus(OrderStatus.UNSETTLED);
            initPaymentDeadline();
        }

        // 5. 配送完成事件将通过DomainEventPublisher发布
    }

    /**
     * 完成订单
     */
    public void complete() {
        OrderStatus oldStatus = this.status;
        this.status = OrderStatus.SETTLED;
        this.finishTime = getCurrentTimeInMerchantZone();
        // 状态变更事件将通过DomainEventPublisher发布
    }

    /**
     * 取消订单
     * 
     * @param cancelReason 取消原因
     */
    public void cancel(String cancelReason) {
        // 1. 验证订单状态是否可以取消
        if (this.status == OrderStatus.SETTLED || this.status == OrderStatus.CANCELLED) {
            throw new RequestBadException(OrderError.INVALID_STATUS_TRANSITION);
        }

        // 2. 如果是配送订单且正在配送中，不允许取消
        if (this.orderType == OrderType.DELIVERY &&
                (this.deliveryStatus == DeliveryStatus.DELIVERING)) {
            throw new RequestBadException(OrderError.CANNOT_CANCEL_DELIVERING_ORDER);
        }

        // 3. 更新状态
        OrderStatus oldStatus = this.status;
        this.status = OrderStatus.CANCELLED;

        // 4. 如果是配送订单，更新配送状态
        if (this.orderType == OrderType.DELIVERY) {
            this.deliveryStatus = DeliveryStatus.CANCELLED;
        }

        // 5. 记录取消时间和原因
        this.cancelTime = getCurrentTimeInMerchantZone();
        this.cancelReason = cancelReason;

        // 6. 状态变更事件将通过DomainEventPublisher发布

        // 7. 更新完成时间
        this.finishTime = getCurrentTimeInMerchantZone();
    }

    /**
     * 检查是否支付超时
     */
    public boolean isPaymentTimeout() {
        if (this.paymentDeadline == null) {
            return false;
        }
        return getCurrentTimeInMerchantZone() > paymentDeadline;
    }

    /**
     * 初始化支付截止时间
     */
    public void initPaymentDeadline() {
        if (this.createTime == null) {
            throw new RequestBadException(OrderError.INVALID_PAYMENT_TIME);
        }
        this.paymentDeadline = this.createTime + PAYMENT_TIMEOUT_MINUTES * 60 * 1000;
    }

    /**
     * 设置预订信息
     */
    public void setReservationInfo(ReservationInfo reservationInfo) {
        // 1. 验证订单类型
        if (orderType != OrderType.RESERVATION) {
            throw new RequestBadException(OrderError.INVALID_ORDER_TYPE);
        }

        // 2. 验证预订信息
        if (reservationInfo == null) {
            throw new RequestBadException(OrderError.RESERVATION_INFO_REQUIRED);
        }
        reservationInfo.validate();

        // 3. 设置预订信息
        this.reservationInfo = reservationInfo;
        this.reservationTime = reservationInfo.getReservationTime();
        this.diningNumber = reservationInfo.getDiningNumber();

        // 4. 预订信息更新事件将通过DomainEventPublisher发布
    }

    /**
     * 更新预订信息
     */
    public void updateReservationInfo(ReservationInfo newReservationInfo) {
        // 1. 验证订单类型
        if (orderType != OrderType.RESERVATION) {
            throw new RequestBadException(OrderError.INVALID_ORDER_TYPE);
        }

        // 2. 验证订单状态
        if (status == OrderStatus.SETTLED || status == OrderStatus.CANCELLED) {
            throw new RequestBadException(OrderError.CANNOT_UPDATE_COMPLETED_ORDER);
        }

        // 3. 验证新的预订信息
        if (newReservationInfo == null) {
            throw new RequestBadException(OrderError.RESERVATION_INFO_REQUIRED);
        }
        newReservationInfo.validate();

        // 4. 保存旧的预订信息用于事件发布
        ReservationInfo oldReservationInfo = this.reservationInfo;

        // 5. 更新预订信息
        this.reservationInfo = newReservationInfo;
        this.reservationTime = newReservationInfo.getReservationTime();
        this.diningNumber = newReservationInfo.getDiningNumber();

        // 6. 预订信息变更事件将通过DomainEventPublisher发布
    }

    /**
     * 验证预订信息
     */
    private void validateReservationInfo() {
        if (orderType == OrderType.RESERVATION) {
            if (reservationInfo == null) {
                throw new RequestBadException(OrderError.RESERVATION_INFO_REQUIRED);
            }
            reservationInfo.validate();

            // 验证预订时间是否合理
            long currentTime = getCurrentTimeInMerchantZone();
            if (reservationInfo.getReservationTime() <= currentTime) {
                throw new RequestBadException(OrderError.INVALID_RESERVATION_TIME);
            }
        }
    }

    /**
     * 获取预订信息
     */
    public ReservationInfo getReservationInfo() {
        if (orderType != OrderType.RESERVATION) {
            return null;
        }
        return this.reservationInfo;
    }

    /**
     * 设置就餐信息
     */
    public void setDiningInfo(String tableNo, Integer diningNumber) {
        if (tableNo == null) {
            throw new RequestBadException(OrderError.INVALID_ORDER_TYPE);
        }
        this.tableNo = tableNo;
        this.diningNumber = diningNumber;
    }

    /**
     * 设置取餐时间
     */
    public void setPickupTime(Long pickupTime) {
        if (this.orderType != OrderType.PICKUP) {
            throw new RequestBadException(OrderError.INVALID_ORDER_TYPE);
        }
        this.pickupTime = pickupTime;
    }

    /**
     * 发起退款
     */
    public void requestRefund(String reason) {
        // 退款请求事件将通过DomainEventPublisher发布
    }

    /**
     * 退款
     */
    public void refund(String reason) {
        this.paymentStatus = PaymentStatus.REFUNDED;

        // 退款事件将通过DomainEventPublisher发布
    }

    /**
     * 设置配送时间
     */
    public void setDeliveryTime(Long deliveryTime) {
        if (this.orderType != OrderType.DELIVERY) {
            throw new RequestBadException(OrderError.INVALID_ORDER_TYPE);
        }
        if (deliveryTime == null || deliveryTime <= 0) {
            throw new RequestBadException(OrderError.INVALID_DELIVERY_TIME);
        }
        this.deliveryTime = deliveryTime;
    }

    /**
     * 设置支付时间
     */
    public void setPaymentTime(Long paymentTime) {
        if (paymentTime == null || paymentTime <= 0) {
            throw new RequestBadException(OrderError.INVALID_PAYMENT_TIME);
        }
        this.paymentTime = paymentTime;
    }

    /**
     * 校验订单
     */
    public void validate() {
        // 1. 验证基本信息
        if (StringUtils.isBlank(merchantId)) {
            throw new RequestBadException(OrderError.MERCHANT_ID_REQUIRED);
        }

        // 2. 验证用户ID
        if (orderType == OrderType.ONLINE && StringUtils.isBlank(userId)) {
            throw new RequestBadException(OrderError.USER_ID_REQUIRED);
        }

        // 4. 验证订单项
        if (CollectionUtil.isEmpty(items) && orderType != OrderType.SUBSCRIPTION) {
            throw new RequestBadException(OrderError.ORDER_ITEMS_REQUIRED);
        }

        // 5. 根据订单类型验证特定信息
        switch (orderType) {
            case RESERVATION:
                validateReservationInfo();
                break;
            case DINE_IN:
                if (StringUtils.isBlank(tableNo)) {
                    throw new RequestBadException(OrderError.TABLE_NO_REQUIRED);
                }
                break;
            case PICKUP:
                if (pickupTime == null) {
                    throw new RequestBadException(OrderError.PICKUP_TIME_REQUIRED);
                }
                break;
            case DELIVERY:
                // 验证配送信息
                if (deliveryInfo == null) {
                    throw new RequestBadException(OrderError.DELIVERY_INFO_REQUIRED);
                }
                // 验证收货人信息
                if (StringUtils.isBlank(deliveryInfo.getReceiverName())) {
                    throw new RequestBadException(OrderError.RECEIVER_NAME_REQUIRED);
                }
                if (StringUtils.isBlank(deliveryInfo.getReceiverPhone())) {
                    throw new RequestBadException(OrderError.RECEIVER_PHONE_REQUIRED);
                }
                // 验证配送地址
                if (StringUtils.isBlank(deliveryInfo.getDeliveryAddress())) {
                    throw new RequestBadException(OrderError.DELIVERY_ADDRESS_REQUIRED);
                }
                if (deliveryInfo.getDeliveryLatitude() == null || deliveryInfo.getDeliveryLongitude() == null) {
                    throw new RequestBadException(OrderError.DELIVERY_LOCATION_REQUIRED);
                }
                break;
        }

        // 配送订单必须指定配送方式
        if (needsDelivery() && deliveryType == null) {
            throw new RequestBadException(OrderError.DELIVERY_TYPE_REQUIRED);
        }

        // 验证支付方式
        if (this.orderType == OrderType.ONLINE && paymentMode == null) {
            throw new RequestBadException(OrderError.PAYMENT_MODE_REQUIRED);
        }

        // 验证支付相关信息
        if (orderType == OrderType.ONLINE || orderType == OrderType.DELIVERY) {
            // 在线支付必须选择支付渠道
            if (paymentMode == PaymentMode.ONLINE && paymentChannelId == null) {
                throw new RequestBadException(OrderError.PAYMENT_CHANNEL_REQUIRED);
            }
        }
    }

    /**
     * 校验是否已支付
     */
    public void validateNotPaid() {
        if (isFullyPaid()) {
            throw new RequestBadException(OrderError.ORDER_ALREADY_PAID);
        }
    }

    /**
     * 校验是否超时
     */
    public void validateNotTimeout() {
        if (this.paymentDeadline != null && System.currentTimeMillis() > paymentDeadline) {
            throw new RequestBadException(OrderError.PAYMENT_TIMEOUT);
        }
    }

    /**
     * 校验是否可退款
     */
    public void validateRefundable() {
        if (!isFullyPaid()) {
            throw new RequestBadException(OrderError.ORDER_NOT_REFUNDABLE);
        }
        // 已结算或已取消的订单不可退款
        if (status == OrderStatus.SETTLED || status == OrderStatus.CANCELLED) {
            throw new RequestBadException(OrderError.ORDER_NOT_REFUNDABLE);
        }
    }

    /**
     * 计算指定订单项的总金额
     */
    public Money calculateItemsAmount(List<String> orderItemIds) {
        return items.stream()
                .filter(item -> orderItemIds.contains(item.getId()))
                .map(OrderItem::getTotalPrice)
                .reduce(Money.zeroMoney(currency, currencyScale), Money::add);
    }

    /**
     * 校验订单项是否存在
     */
    public void validateOrderItems(List<String> orderItemIds) {
        if (orderItemIds == null || orderItemIds.isEmpty()) {
            throw new RequestBadException(OrderError.ORDER_ITEM_NOT_FOUND);
        }
        boolean allExist = items.stream()
                .map(OrderItem::getId)
                .collect(Collectors.toSet())
                .containsAll(orderItemIds);
        if (!allExist) {
            throw new RequestBadException(OrderError.ORDER_ITEM_NOT_FOUND);
        }
    }

    /**
     * 是否已全额支付
     */
    public boolean isFullyPaid() {
        if (paidAmount == null) {
            return false;
        }
        return paidAmount.isGreaterThanOrEqual(totalAmount);
    }

    /**
     * 设置订单状态
     * 注意：此方法只负责状态变更，事件发布由调用方负责
     */
    public void setStatus(OrderStatus newStatus) {
        if (newStatus != null && this.status != newStatus) {
            this.status = newStatus;
        }
    }

    /**
     * 设置订单状态并返回原状态（用于事件发布）
     */
    public OrderStatus setStatusAndGetOld(OrderStatus newStatus) {
        if (newStatus != null && this.status != newStatus) {
            OrderStatus oldStatus = this.status;
            this.status = newStatus;
            return oldStatus;
        }
        return this.status;
    }


    /**
     * 验证结算项
     */
    private void validateSettlementItems(List<String> itemIds) {
        // 1. 验证订单项是否存在
        boolean allExist = items.stream()
                .map(OrderItem::getId)
                .collect(Collectors.toSet())
                .containsAll(itemIds);
        if (!allExist) {
            throw new RequestBadException(OrderError.ORDER_ITEM_NOT_FOUND);
        }

        // 2. 验证订单项是否已结算
        if (settledItemIds.stream().anyMatch(itemIds::contains)) {
            throw new RequestBadException(OrderError.ITEM_ALREADY_SETTLED);
        }

        // 3. 验证结算项金额是否合理
        Money itemsTotal = calculateItemsAmount(itemIds);
        if (itemsTotal.isGreaterThan(getRemainingSettlementAmount())) {
            throw new RequestBadException(OrderError.SETTLEMENT_AMOUNT_EXCEEDS_REMAINING);
        }
    }

    /**
     * 是否可以完全结算
     */
    public boolean canCompleteSettle() {
        // 1. 验证是否全部支付
        if (!isFullyPaid()) {
            return false;
        }

        // 2. 验证是否有未结算的订单项
        if (!settledItemIds.isEmpty() && settledItemIds.size() < items.size()) {
            return false;
        }

        // 3. 验证是否有未结算的金额
        return getRemainingSettlementAmount().isZero();
    }

//    /**
//     * 完全结算
//     */
//    public void completeSettle() {
//        // 1. 验证是否可以完全结算
//        if (!canCompleteSettle()) {
//            throw new RequestBadException(OrderError.CANNOT_COMPLETE_SETTLE);
//        }
//
//        // 2. 设置状态为已结算
//        setStatus(OrderStatus.SETTLED);
//
//        // 3. 发布完全结算事件
//        events.add(new OrderSettledEvent(
//                this.id,
//                this.merchantId,
//                this.totalAmount.toString(),
//                this.userId,    // 添加用户ID
//                this.riderId    // 添加骑手ID
//        ));
//    }

    /**
     * 设置骑手
     */
    public void assignRider(KnightInfo knightInfo) {
        if (this.orderType != OrderType.DELIVERY) {
            throw new RequestBadException(OrderError.INVALID_ORDER_TYPE);
        }
        this.riderId = knightInfo.getId();
        if (this.deliveryInfo == null) {
            this.deliveryInfo = DeliveryInfo.builder().riderId(riderId)
                    .deliveryType(this.deliveryType)
                    .deliveryPhone(knightInfo.getPhone())
                    .deliveryName(knightInfo.getName())
                    .build();
        } else {
            this.deliveryInfo.setRiderId(riderId);
            this.deliveryInfo.setDeliveryPhone(knightInfo.getPhone());
            this.deliveryInfo.setDeliveryName(knightInfo.getName());
        }

        // 骑手分配事件将通过DomainEventPublisher发布
    }

    /**
     * 是否需要配送
     */
    public boolean needsDelivery() {
        return this.orderType == OrderType.DELIVERY;
    }

    /**
     * 初始化订单状态
     */
    public void initOrderStatus(OrderStatusTransitionService orderStatusTransitionService) {
        // 根据订单类型和支付方式设置初始状态
        this.status = orderStatusTransitionService.getInitialStatus(this.orderType);
        // 根据配送类型初始化配送状态
        if (this.orderType == OrderType.DELIVERY) {
            this.deliveryStatus = DeliveryStatus.ORDER_CREATED;
        }
    }

    /**
     * 验证商品支付金额
     */
    public void validateItemPayment(String itemId, List<PayItem> payItems) {
        // 1. 验证商品是否存在
        OrderItem item = items.stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new RequestBadException(OrderError.ORDER_ITEM_NOT_FOUND));

        // 2. 计算该商品的总支付金额
        Money totalPayAmount = payItems.stream()
                .map(PayItem::getAmount)
                .reduce(Money.zero(item.getCurrency(), item.getCurrencyScale()), Money::add);

        // 3. 验证支付金额不超过商品金额
        if (totalPayAmount.isGreaterThan(item.getTotalAmount())) {
            throw new RequestBadException(OrderError.PAYMENT_AMOUNT_EXCEEDS_ITEM_AMOUNT);
        }
    }

    @Data
    @Builder
    public static class ItemPayment {
        private String itemId;
        private List<PayItem> payItems;
    }

    private void updatePaymentInfo(PayItem payItem) {
        if (payItem.getChannelId() != null) {
            this.paymentChannelId = payItem.getChannelId();
        }
        this.paymentTradeNo = payItem.getTradeNo();
        this.paymentTime = DateUtils.getCurrentTimestampInUTC();

        // 累加已支付金额
        if (this.paidAmount == null) {
            this.paidAmount = payItem.getAmount();
        } else {
            this.paidAmount = this.paidAmount.add(payItem.getAmount());
        }

        // 更新支付状态
        if (isFullyPaid()) {
            this.paymentStatus = PaymentStatus.PAID;
        }
    }

    /**
     * 获取剩余应付金额
     */
    public Money getRemainingAmount() {
        if (paidAmount == null) {
            return totalAmount;
        }
        return totalAmount.subtract(paidAmount);
    }

    /**
     * 获取剩余可结算金额
     * 剩余可结算金额等于已支付金额
     */
    public Money getRemainingSettlementAmount() {
        if (paidAmount == null) {
            return Money.zero(totalAmount.getCurrency(), totalAmount.getScale());
        }
        return paidAmount;
    }

    /**
     * 判断是否可以结算
     */
    public boolean canSettle() {
        return isFullyPaid() && !getRemainingSettlementAmount().isZero();
    }

    /**
     * 开始就餐
     */
    public void startDining() {

    }

    /**
     * 验证订单未被接单
     * 如果订单已被接单，则抛出异常
     */
    public void validateNotAccepted() {
        if (StringUtils.isNotBlank(this.riderId) || this.deliveryStatus == DeliveryStatus.ORDER_ACCEPTED) {
            throw new RequestBadException(OrderError.ORDER_ALREADY_ACCEPTED);
        }
    }
}