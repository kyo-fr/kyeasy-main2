package com.ares.cloud.order.domain.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.ares.cloud.order.domain.enums.*;
import com.ares.cloud.order.domain.model.command.PayCommand;
import com.ares.cloud.order.domain.model.valueobject.*;
import com.ares.cloud.order.domain.service.OrderCodeGenerator;
import com.ares.cloud.order.domain.service.*;
import com.ares.cloud.order.infrastructure.service.KnightServiceImpl;
import com.ares.cloud.order.domain.service.DomainEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ares.cloud.api.order.commod.CreateOrderCommand;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.common.exception.RequestBadException;
import org.ares.cloud.common.model.Money;
import org.ares.cloud.common.utils.DateUtils;
import java.time.LocalDateTime;
import com.ares.cloud.order.domain.model.aggregate.Order;
import com.ares.cloud.order.domain.model.entity.OrderItem;
import com.ares.cloud.order.domain.repository.OrderRepository;
import com.ares.cloud.order.domain.repository.ProductInventoryRepository;
import org.ares.cloud.common.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 订单领域服务实现
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OrderDomainServiceImpl implements OrderDomainService {

    private final OrderRepository orderRepository;
    private final MerchantService merchantService;
    private final OrderRuleService orderRuleService;
    private final OrderStatusTransitionService statusTransitionService;
    private final OrderStatusLogService orderStatusLogService;
    private final OrderCodeGenerator orderCodeGenerator;
    private final KnightServiceImpl knightService;
    private final OrderOperationLogService orderOperationLogService;
    private final ProductInventoryRepository productInventoryRepository;
    private final ProductService productService;
    private final DomainEventPublisher domainEventPublisher;
    
    // 用于记录状态变更前的状态

    /**
     * 从命令中获取用户手机号
     * 根据订单类型从不同的信息中获取手机号
     *
     * @param command   创建订单命令
     * @param orderType 订单类型
     * @return 用户手机号
     */
    private String getPhoneNumberFromCommand(CreateOrderCommand command, OrderType orderType) {
        String phoneNumber = command.getUserPhone();
        if (StringUtils.isNotEmpty(phoneNumber)) {
            return phoneNumber;
        }
        switch (orderType) {
            case RESERVATION -> {
                if (command.getReservationInfo() != null) {
                    phoneNumber = command.getReservationInfo().getReserverPhone();
                }
            }
            case DELIVERY -> {
                if (command.getDeliveryInfo() != null) {
                    phoneNumber = command.getDeliveryInfo().getReceiverPhone();
                }
            }
            case DINE_IN, PICKUP, ONLINE, SUBSCRIPTION, VIRTUAL -> {
                // 对于店内就餐、自取、在线、订阅和虚拟订单，可能需要从其他地方获取手机号
                // 这里可以根据实际业务需求进行扩展
            }
        }

        return phoneNumber;
    }

    @Override
    @Transactional
    public String createOrder(CreateOrderCommand command, String tenantId) {
        // 获取商户信息
        MerchantInfo merchantInfo = getMerchantInfo(command.getMerchantId(),command.isPlatform());
        if (merchantInfo == null) {
            throw new RequestBadException(OrderError.MERCHANT_NOT_EXIST);
        }
        
        // 校验商户存储空间是否充足
        if (!merchantService.validateStorageSpace(merchantInfo.getId(), null)) {
            throw new RequestBadException(OrderError.INSUFFICIENT_STORAGE_SPACE);
        }
        
        // 获取用户ID
        String userId = ApplicationContext.getUserId();

        // 转换订单项
        List<OrderItem> items = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(command.getItems())) {
            items = command.getItems().stream()
                    .map(item -> {
                        OrderItem orderItem = OrderItem.builder()
                                .productId(item.getProductId())
                                .productName(item.getProductName())
                                .quantity(item.getQuantity())
                                .unitPrice(Money.of(item.getUnitPrice(), merchantInfo.getCurrency(),merchantInfo.getCurrencyScale()))
                                .paymentStatus(PaymentStatus.UNPAID)
                                .build();

                        // 处理商品规格
                        if (CollectionUtil.isNotEmpty(item.getSpecifications())) {
                            List<ProductSpecification> specifications = item.getSpecifications().stream()
                                    .map(spec -> ProductSpecification.builder()
                                            .productSpecId(spec.getProductSpecId())
                                            .name(spec.getName())
                                            .value(spec.getValue())
                                            .price(spec.getPrice() != null ? Money.of(spec.getPrice(), merchantInfo.getCurrency(),merchantInfo.getCurrencyScale()) : Money.zeroMoney(merchantInfo.getCurrency(), merchantInfo.getCurrencyScale()))
                                            .build())
                                    .collect(Collectors.toList());
                            orderItem.setSpecifications(specifications);
                        }

                        return orderItem;
                    })
                    .collect(Collectors.toList());
        }

        // 创建服务费Money对象
        Money serviceFee = command.getServiceFee() != null ?
                Money.of(command.getServiceFee(), merchantInfo.getCurrency(),merchantInfo.getCurrencyScale()) :
                Money.zeroMoney(merchantInfo.getCurrency(), merchantInfo.getCurrencyScale());

        // 如果服务费不为零，将其作为特殊订单项
        if (serviceFee.isGreaterThan(Money.zeroMoney(merchantInfo.getCurrency(), merchantInfo.getCurrencyScale()))) {
            OrderItem serviceFeeItem = OrderItem.builder()
                    .productId("service_fee")
                    .productName("服务费")
                    .quantity(1)
                    .unitPrice(serviceFee)
                    .currency(merchantInfo.getCurrency())
                    .currencyScale(merchantInfo.getCurrencyScale())
                    .paymentStatus(PaymentStatus.UNPAID)
                    .build();
            serviceFeeItem.calculateTotalPrice();
            items.add(serviceFeeItem);
        }

        // 转换订单类型和支付方式
        OrderType orderType = OrderType.valueOf(command.getOrderType().name());
        PaymentMode paymentMode = command.getPaymentMode() != null ?
                PaymentMode.valueOf(command.getPaymentMode().name()) : null;

        // 获取用户手机号
        String phoneNumber = getPhoneNumberFromCommand(command, orderType);

        // 创建订单
        Order order = Order.builder()
                .userId(userId)
                .merchantId(merchantInfo.getId())
                .orderType(orderType)
                .serviceFee(serviceFee)
                .currency(merchantInfo.getCurrency())
                .currencyScale(merchantInfo.getCurrencyScale())
                .timezone(merchantInfo.getTimezone())
                .paymentMode(paymentMode)
                .paymentChannelId(command.getPaymentChannelId())
                .createTime(DateUtils.getCurrentTimestampInUTC())
                .orderNumber(orderCodeGenerator.generateOrderNumber(command.getMerchantId()))
                .orderCode(orderCodeGenerator.generateOrderCode(orderType, command.getMerchantId()))
                .tenantId(StringUtils.isNotEmpty(tenantId) ? tenantId : merchantInfo.getId())
                .countryCode(command.getCountryCode())
                .phoneNumber(phoneNumber)
                .sourceType(command.getSourceType())
                .build();
        // 初始化订单状态
        order.initOrderStatus(statusTransitionService);
        // 根据订单类型设置相关信息
        switch (orderType) {
            case RESERVATION -> {
                if (command.getReservationInfo() == null) {
                    throw new RequestBadException(OrderError.RESERVATION_INFO_REQUIRED);
                }
                ReservationInfo reservationInfo = ReservationInfo.builder()
                        .reservationTime(command.getReservationInfo().getReservationTime())
                        .reserverName(command.getReservationInfo().getReserverName())
                        .reserverPhone(command.getReservationInfo().getReserverPhone())
                        .diningNumber(command.getReservationInfo().getDiningNumber()).remarks(command.getReservationInfo().getRemarks())
                        .build();
                order.setReservationInfo(reservationInfo);
            }
            case DINE_IN -> {
                if (command.getDiningInfo() == null) {
                    throw new RequestBadException(OrderError.TABLE_NO_REQUIRED);
                }
                order.setDiningInfo(command.getDiningInfo().getTableNo(), command.getDiningInfo().getDiningNumber());
            }
            case PICKUP -> {
                if (command.getPickupInfo() == null) {
                    throw new RequestBadException(OrderError.PICKUP_TIME_REQUIRED);
                }
                order.setPickupTime(command.getPickupInfo().getPickupTime());
            }
            case DELIVERY -> {
                if (command.getDeliveryInfo() == null) {
                    throw new RequestBadException(OrderError.DELIVERY_INFO_REQUIRED);
                }
                // 设置配送方式
                DeliveryType deliveryType = DeliveryType.MERCHANT;
                if (command.getDeliveryInfo().getDeliveryType() != null && command.getDeliveryInfo().getDeliveryType().getValue() > 0) {
                    deliveryType = DeliveryType.fromValue(command.getDeliveryInfo().getDeliveryType());
                }
                order.setDeliveryType(deliveryType);

                // 创建配送信息
                DeliveryInfo deliveryInfo = DeliveryInfo.builder()
                        .deliveryType(deliveryType)
                        .receiverName(command.getDeliveryInfo().getReceiverName())
                        .receiverPhone(command.getDeliveryInfo().getReceiverPhone())
                        .deliveryAddress(command.getDeliveryInfo().getDeliveryAddress())
                        .deliveryLatitude(command.getDeliveryInfo().getDeliveryLatitude())
                        .deliveryLongitude(command.getDeliveryInfo().getDeliveryLongitude())
                        .deliveryDistance(command.getDeliveryInfo().getDeliveryDistance() != null ? command.getDeliveryInfo().getDeliveryDistance() : 0)
                        .deliveryCountry(command.getDeliveryInfo().getDeliveryCountry())
                        .deliveryCity(command.getDeliveryInfo().getDeliveryCity())
                        .deliveryPostalCode(command.getDeliveryInfo().getDeliveryPostalCode())
                        .deliveryFee(command.getDeliveryInfo().getDeliveryFee() != null ?
                                Money.of(command.getDeliveryInfo().getDeliveryFee(), merchantInfo.getCurrency(),merchantInfo.getCurrencyScale()) :
                                Money.zeroMoney(merchantInfo.getCurrency(), merchantInfo.getCurrencyScale()))
                        .build();

                // 设置配送信息
                order.setDeliveryInfo(deliveryInfo);
                
                // 如果配送费不为零，将其作为特殊订单项
                if (deliveryInfo.getDeliveryFee() != null && 
                    deliveryInfo.getDeliveryFee().isGreaterThan(Money.zeroMoney(merchantInfo.getCurrency(), merchantInfo.getCurrencyScale()))) {
                    OrderItem deliveryFeeItem = OrderItem.builder()
                            .productId("delivery_fee")
                            .productName("配送费")
                            .quantity(1)
                            .unitPrice(deliveryInfo.getDeliveryFee())
                            .currency(merchantInfo.getCurrency())
                            .currencyScale(merchantInfo.getCurrencyScale())
                            .paymentStatus(PaymentStatus.UNPAID)
                            .build();
                    deliveryFeeItem.calculateTotalPrice();
                    items.add(deliveryFeeItem);
                }
            }
            case ONLINE, SUBSCRIPTION, VIRTUAL -> {
                // 在线订单、订阅订单和虚拟订单的处理
                // 根据实际业务需求进行扩展
            }
        }

        // 添加订单项
        items.forEach(order::addItem);

        // 过滤出需要校验的商品项（排除服务费和配送费）
        List<OrderItem> productItems = items.stream()
                .filter(item -> !"service_fee".equals(item.getProductId()) && !"delivery_fee".equals(item.getProductId()))
                .collect(Collectors.toList());
        
        // 使用新的商品领域服务进行验证（包括价格和规格验证）
        if (!productItems.isEmpty()&& orderType != OrderType.SUBSCRIPTION) {
            // 验证商品信息（包括价格和规格）
            productService.validateOrderItems(productItems, merchantInfo.getId(), merchantInfo.getCurrency(), merchantInfo.getCurrencyScale());
            
            // 检查商品库存
            if (!checkStockAvailability(productItems, merchantInfo.getId())) {
                throw new RequestBadException(OrderError.INSUFFICIENT_STOCK);
            }
        }

        // 校验订单
        order.validate();

        // 执行创建前规则
        if (!orderRuleService.createBefore(order)) {
            throw new RequestBadException(OrderError.ORDER_CHECK_FAILED);
        }

        // 保存订单
        orderRepository.save(order);

        // 预留库存（只对商品项进行库存操作）
        if (!productItems.isEmpty()) {
            try {
                reserveStock(productItems, merchantInfo.getId(), orderType);
            } catch (Exception e) {
                log.error("预留库存失败，订单ID: {}", order.getId(), e);
                // 如果预留库存失败，可以考虑删除已创建的订单或进行其他补偿操作
                throw new RequestBadException(OrderError.INVENTORY_RESERVE_FAILED);
            }
        }

        // 记录订单初始状态日志
        orderStatusLogService.recordStatusChange(
                order.getId(),
                null,  // 初始状态为null
                order.getStatus(),
                OrderStatusType.ORDER,
                order.getUserId(),
                "订单创建"
        );

        try {
            // 发布订单创建事件（通过领域事件发布器）
            domainEventPublisher.publishOrderCreated(
                    order.getId(),
                    order.getMerchantId(),
                    order.getUserId(),
                    order.getOrderType().name(),
                    order.getTotalAmount().toString(),
                    LocalDateTime.now()
            );

        }catch (Exception e){
            log.error("订单创建事件发布失败", e);
        }

        return order.getId();
    }

    /**
     * 确认订单
     *
     * @param orderId 订单ID
     */
    @Override
    @Transactional
    public void confirmOrder(String orderId) {
        Order order = getOrder(orderId);
        OrderAction action = OrderAction.CONFIRM;
        // 1. 校验状态转换是否合法
        if (order.getOrderType() != OrderType.RESERVATION) {
            throw new RequestBadException(OrderError.CONFIRMATION_INVALID_ORDER_TYPE);
        }
        validateStatus(order, action);
        order.confirm();
        // 执行状态转换
        statusTransitionService.transit(order, action);
        OrderStatus oldStatus = order.setStatusAndGetOld(OrderStatus.UNSETTLED);
        orderRepository.save(order);
        publishOrderStatusChanged(order, oldStatus, OrderStatus.UNSETTLED, "订单确认");
    }

    @Override
    @Transactional
    public void acceptDelivery(String orderId, DeliveryInfo deliveryInfo) {
        Order order = getOrder(orderId);
        OrderAction action = OrderAction.ACCEPT_DELIVERY;

        // 验证状态转换是否合法
        validateStatus(order, action);

        // 验证订单未被接单
        order.validateNotAccepted();

        // 验证配送信息
        if (deliveryInfo == null || deliveryInfo.getDeliveryType() != DeliveryType.MERCHANT) {
            throw new RequestBadException(OrderError.INVALID_DELIVERY_INFO);
        }

        // 获取骑手
        KnightInfo knightInfo = knightService.findById(deliveryInfo.getRiderId());
        if (knightInfo == null) {
            throw new RequestBadException(OrderError.INVALID_DELIVERY_INFO);
        }

        // 设置骑手信息
        order.assignRider(knightInfo);

        // 更新配送状态为已接单
        order.setDeliveryStatus(DeliveryStatus.ORDER_ACCEPTED);

        // 执行状态转换
        statusTransitionService.transit(order, action);
        orderRepository.save(order);
        
        // 发布状态变更事件（配送员分配）
        publishOrderStatusChanged(order, order.getStatus(), order.getStatus(), "配送员分配: " + knightInfo.getName());
    }

    @Override
    @Transactional
    public void startDelivery(String orderId) {
        Order order = getOrder(orderId);
        OrderAction action = OrderAction.START_DELIVERY;

        // 验证状态转换是否合法
        validateStatus(order, action);

        // 验证是否已接单
        if (order.getDeliveryStatus() != DeliveryStatus.ORDER_ACCEPTED) {
            throw new RequestBadException(OrderError.INVALID_STATUS_TRANSITION);
        }
        DeliveryInfo deliveryInfo = order.getDeliveryInfo();
        deliveryInfo.setDeliveryType(DeliveryType.MERCHANT);
        // 开始配送
        order.startDelivery(deliveryInfo);

        // 执行状态转换
        statusTransitionService.transit(order, action);
        saveAndPublish(order);
        
        // 发布配送开始事件
        try {
            domainEventPublisher.publishDeliveryStarted(
                    order.getId(),
                    order.getMerchantId(),
                    order.getUserId(),
                    order.getRiderId(),
                    deliveryInfo.getDeliveryType(),
                    deliveryInfo.getDeliveryAddress(),
                    deliveryInfo.getReceiverName(),
                    deliveryInfo.getReceiverPhone(),
                    LocalDateTime.now().plusHours(1), // 默认1小时后配送
                    LocalDateTime.now()
            );
        } catch (Exception e) {
            log.error("发布配送开始事件失败，订单ID: {}, 错误信息: {}", order.getId(), e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public void startDelivery(String orderId, DeliveryInfo deliveryInfo) {
        Order order = getOrder(orderId);
        OrderAction action = OrderAction.START_DELIVERY;

        // 验证状态转换是否合法
        validateStatus(order, action);

        // 验证配送信息
        if (deliveryInfo == null) {
            throw new RequestBadException(OrderError.DELIVERY_INFO_REQUIRED);
        }

        // 获取现有配送信息
        DeliveryInfo existingDeliveryInfo = order.getDeliveryInfo();
        if (existingDeliveryInfo == null) {
            throw new RequestBadException(OrderError.DELIVERY_INFO_NOT_FOUND);
        }
        // 更新配送信息
        existingDeliveryInfo.setRiderId(deliveryInfo.getRiderId());
        existingDeliveryInfo.setDeliveryCompany(deliveryInfo.getDeliveryCompany());
        existingDeliveryInfo.setTrackingNo(deliveryInfo.getTrackingNo());
        existingDeliveryInfo.setDeliveryPhone(deliveryInfo.getDeliveryPhone());
        existingDeliveryInfo.setDeliveryName(deliveryInfo.getDeliveryName());
        existingDeliveryInfo.setDeliveryType(deliveryInfo.getDeliveryType());
        if (StringUtils.isNotBlank(deliveryInfo.getDeliveryAddress())){
            existingDeliveryInfo.setDeliveryAddress(deliveryInfo.getDeliveryAddress());
        }
        if (deliveryInfo.getDeliveryLatitude() != null && deliveryInfo.getDeliveryLongitude() != null) {
            existingDeliveryInfo.setDeliveryLatitude(deliveryInfo.getDeliveryLatitude());
            existingDeliveryInfo.setDeliveryLongitude(deliveryInfo.getDeliveryLongitude());
            existingDeliveryInfo.setDeliveryDistance(deliveryInfo.getDeliveryDistance());
        }
        if (deliveryInfo.getDeliveryFee() != null) {
            existingDeliveryInfo.setDeliveryFee(deliveryInfo.getDeliveryFee());
        }
        if (StringUtils.isNotBlank(deliveryInfo.getDeliveryCountry())) {
            existingDeliveryInfo.setDeliveryCountry(deliveryInfo.getDeliveryCountry());
        }
        if (StringUtils.isNotBlank(deliveryInfo.getDeliveryCity())) {
            existingDeliveryInfo.setDeliveryCity(deliveryInfo.getDeliveryCity());
        }
        if (StringUtils.isNotBlank(deliveryInfo.getDeliveryPostalCode())) {
            existingDeliveryInfo.setDeliveryPostalCode(deliveryInfo.getDeliveryPostalCode());
        }
        // 验证配送信息
        existingDeliveryInfo.validate();
        // 开始配送
        order.startDelivery(existingDeliveryInfo);

        // 执行状态转换
        statusTransitionService.transit(order, action);
        saveAndPublish(order);
    }

    @Override
    @Transactional
    public void completeOrder(String orderId) {
        Order order = getOrder(orderId);
        OrderAction action = OrderAction.COMPLETE;
        validateStatus(order, action);
        OrderStatus oldStatus = order.setStatusAndGetOld(OrderStatus.SETTLED);
        order.complete();
        statusTransitionService.transit(order, action);
        orderRepository.save(order);
        
        // 发布状态变更事件
        publishOrderStatusChanged(order, oldStatus, OrderStatus.SETTLED, "订单完成");
    }

    @Override
    @Transactional
    public void cancelOrder(String orderId, String reason, String operatorId) {
        Order order = getOrder(orderId);
        OrderAction action = OrderAction.CANCEL;
        // 使用状态转换服务校验
        validateStatus(order, action);
        // 使用带取消原因的cancel方法
        OrderStatus oldStatus = order.setStatusAndGetOld(OrderStatus.CANCELLED);
        order.cancel(reason);
        
        // 释放预留库存
        try {
            releaseStock(order.getItems(), order.getMerchantId(), order.getOrderType());
        } catch (Exception e) {
            log.error("释放库存失败，订单ID: {}", orderId, e);
            // 即使释放库存失败，订单取消操作也应该继续
            log.warn("订单取消时释放库存失败，但订单取消操作继续执行");
        }
        
        // 执行状态转换
        statusTransitionService.transit(order, action);
        
        // 记录操作日志
        orderOperationLogService.recordOperation(
                orderId,
                OrderAction.CANCEL,
                operatorId,
                String.format("取消订单：%s 总价：%s", order.getOrderNumber(), order.getTotalAmount()),
                reason,
                order.getTotalAmount(),
                0,
                1,
                order.getTenantId()
                );
        orderRepository.save(order);
        
        // 发布状态变更事件
        publishOrderStatusChanged(order, oldStatus, OrderStatus.CANCELLED, "订单取消: " + reason);
    }

    @Override
    @Transactional
    public void payOrder(PayCommand payOrderCommand) {
        Order order = getOrder(payOrderCommand.getOrderId());
        OrderAction action = OrderAction.PAY;
        order.validateNotPaid();
        order.validateNotTimeout();
        // 支付订单
        order.fullPay(payOrderCommand.getPayItems(), payOrderCommand.getDeductAmount());

        //检查订单状态
        validateStatus(order, action);
        // 执行状态转换
        statusTransitionService.transit(order, action);
        
        // 扣减库存（支付完成后，同步操作避免超卖）
        try {
            deductStock(order.getItems(), order.getMerchantId(), order.getOrderType());
        } catch (Exception e) {
            log.error("扣减库存失败，订单ID: {}", order.getId(), e);
            throw new RequestBadException(OrderError.INVENTORY_DEDUCT_FAILED);
        }
        
        // 保存订单并发布事件
        saveAndPublish(order);
        
        // 发布订单支付成功事件
        domainEventPublisher.publishOrderPaid(order,payOrderCommand);

        // 发送订单支付成功消息（异步处理发票生成和积分奖励）
//        try {
//            orderSettlementMessageService.sendOrderPaidMessage(order);
//        } catch (Exception e) {
//            log.error("发送订单支付成功消息失败，订单ID: {}, 错误信息: {}", order.getId(), e.getMessage(), e);
//            // 消息发送失败不影响主流程，只记录日志
//        }
    }

    @Override
    @Transactional
    public void refundOrder(String orderId, String reason) {
        Order order = getOrder(orderId);
        OrderAction action = OrderAction.REQUEST_REFUND;
        // 使用状态转换服务校验
        validateStatus(order, action);
        // 发起退款
        order.requestRefund(reason);
        // 执行状态转换
        statusTransitionService.transit(order, action);
        saveAndPublish(order);
        
        // 发布订单退款事件
        try {
            domainEventPublisher.publishOrderRefunded(
                    order.getId(),
                    order.getMerchantId(),
                    order.getUserId(),
                    order.getTotalAmount().toDecimal(), // 使用订单总金额作为退款金额
                    reason,
                    "SYSTEM", // 退款方式
                    "REFUND_" + order.getId(), // 生成退款交易ID
                    ApplicationContext.getUserId(),
                    LocalDateTime.now()
            );
        } catch (Exception e) {
            log.error("发布订单退款事件失败，订单ID: {}, 错误信息: {}", order.getId(), e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public void completeRefund(String orderId) {
        Order order = getOrder(orderId);
        OrderAction action = OrderAction.COMPLETE_REFUND;
        // 使用状态转换服务校验
        validateStatus(order, action);
        order.refund("退款完成");
        // 执行状态转换
        statusTransitionService.transit(order, action);
        saveAndPublish(order);
    }

    @Override
    public MerchantInfo getMerchantInfo(String merchantId,boolean isPlatform) {
        MerchantInfo merchantInfo = null;
        if (isPlatform){
            merchantInfo = merchantService.findPlatformById(merchantId);
        }else {
            merchantInfo = merchantService.findById(merchantId);
        }
        if (merchantInfo == null) {
            throw new RequestBadException("商户信息不存在");
        }
        return merchantInfo;
    }

    @Override
    @Transactional
    public void partialPayOrder(PayCommand payOrderCommand) {
        Order order = getOrder(payOrderCommand.getOrderId());
        OrderAction action = OrderAction.PARTIAL_PAY;

        order.validateNotTimeout();
        // 执行部分支付
        order.partialPay(payOrderCommand.getOrderItemIds(), payOrderCommand.getPayItems(), payOrderCommand.getDeductAmount());

        // 使用状态转换服务校验
        validateStatus(order, action);
        // 如果全部支付完成，状态变更为待确认
        if (order.isFullyPaid()) {
            // 执行状态转换
            statusTransitionService.transit(order, OrderAction.PAY);
        } else {
            // 执行状态转换
            statusTransitionService.transit(order,action);
        }
        try {
            deductStock(order.getItems(), order.getMerchantId(), order.getOrderType());
        } catch (Exception e) {
            log.error("扣减库存失败，订单ID: {}", order.getId(), e);
            throw new RequestBadException(OrderError.INVENTORY_DEDUCT_FAILED);
        }
        // 保存订单并发布事件
        orderRepository.save(order);
        publishEvents(order);
        
        // 发布订单支付成功事件（部分支付和全部支付都发布）
        domainEventPublisher.publishOrderPaid(order,  payOrderCommand);
        
        // 如果全部支付完成，发送订单支付成功消息（异步处理发票生成和积分奖励）
//        if (order.isFullyPaid()) {
//            try {
//                orderSettlementMessageService.sendOrderPaidMessage(order);
//            } catch (Exception e) {
//                log.error("发送订单支付成功消息失败，订单ID: {}, 错误信息: {}", order.getId(), e.getMessage(), e);
//                // 消息发送失败不影响主流程，只记录日志
//            }
//        }
    }

    @Override
    @Transactional
    public void startDining(String orderId) {
        Order order = getOrder(orderId);
        OrderAction action = OrderAction.START_DINING;
        validateStatus(order, action);
        order.startDining();
        // 执行状态转换
        statusTransitionService.transit(order, action);
        saveAndPublish(order);
    }

    @Override
    @Transactional
    public void diningInProgress(String orderId) {
        Order order = getOrder(orderId);
        OrderAction action = OrderAction.DINING_IN_PROGRESS;
        validateStatus(order, action);
        // 执行状态转换
        statusTransitionService.transit(order, action);
        saveAndPublish(order);
    }

    @Override
    @Transactional
    public void readyForPickup(String orderId) {
        Order order = getOrder(orderId);
        OrderAction action = OrderAction.DINING_IN_PROGRESS;
        validateStatus(order, action);
        // 执行状态转换
        statusTransitionService.transit(order, action);
        saveAndPublish(order);
    }

    @Override
    @Transactional
    public void customerArrived(String orderId) {
        Order order = getOrder(orderId);
        OrderAction action = OrderAction.DINING_IN_PROGRESS;
        OrderStatus fromStatus = order.getStatus();
        validateStatus(order, action);
        // 执行状态转换
        statusTransitionService.transit(order, action);
        saveAndPublishWithStatusChange(order, fromStatus, order.getStatus(), "客户到店");
    }


    @Override
    @Transactional
    public void startQueuing(String orderId) {
        Order order = getOrder(orderId);
        OrderAction action = OrderAction.START_QUEUE;
        OrderStatus fromStatus = order.getStatus();
        validateStatus(order, action);
        // 执行状态转换
        statusTransitionService.transit(order, action);
        saveAndPublishWithStatusChange(order, fromStatus, order.getStatus(), "开始排队");
    }

    @Override
    @Transactional
    public void endQueuing(String orderId) {
        Order order = getOrder(orderId);
        OrderAction action = OrderAction.END_QUEUE;
        OrderStatus fromStatus = order.getStatus();
        validateStatus(order, action);
        // 执行状态转换
        statusTransitionService.transit(order, action);
        saveAndPublishWithStatusChange(order, fromStatus, order.getStatus(), "结束排队");
    }

    @Override
    @Transactional
    public void deleteOrderItem(String orderId, List<String> orderItemIds, String reason, String operatorId) {
        // 1. 获取订单
        Order order = getOrder(orderId);

        // 2. 批量删除订单商品
        if (CollectionUtil.isEmpty(orderItemIds)) {
            throw new RequestBadException(OrderError.ORDER_ITEM_NOT_FOUND);
        }

        StringBuilder logContentBuilder = new StringBuilder();

        Money optAmount = Money.zeroMoney(order.getTotalAmount().getCurrency(), order.getTotalAmount().getScale());
        for (String orderItemId : orderItemIds) {
            // 删除订单商品
            OrderItem removedItem = order.removeItem(orderItemId);
            optAmount = optAmount.add(removedItem.getTotalPrice());
            // 构建操作日志内容
            if (!logContentBuilder.isEmpty()) {
                logContentBuilder.append("\n");
            }
            logContentBuilder.append(String.format("删除商品：%s，数量：%d，单价：%s",
                    removedItem.getProductName(),
                    removedItem.getQuantity(),
                    removedItem.getUnitPrice().toString()));
        }

        // 3. 保存订单
        orderRepository.save(order);

        // 4. 记录操作日志
        orderOperationLogService.recordOperation(
                orderId,
                OrderAction.REMOVE_ITEM,
                operatorId,
                logContentBuilder.toString(),
                reason,
                optAmount,
                orderItemIds.size(),
                1,
                order.getTenantId()
                );

        // 5. 发布事件
        publishEvents(order);
    }

    @Override
    @Transactional
    public void completeDelivery(String orderId) {
        Order order = getOrder(orderId);
        OrderAction action = OrderAction.COMPLETE_DELIVERY;
        // 使用状态转换服务校验
        validateStatus(order, action);
        // 完成配送
        order.completeDelivery();
        // 执行状态转换
        statusTransitionService.transit(order, action);
        saveAndPublish(order);
        
        // 发布配送完成事件
        try {
            domainEventPublisher.publishDeliveryCompleted(
                    order.getId(),
                    order.getMerchantId(),
                    order.getUserId(),
                    order.getRiderId(),
                    LocalDateTime.now(),
                    order.getDeliveryInfo().getDeliveryAddress(),
                    true, // 假设配送完成时收货人已确认
                    "配送完成",
                    null // 配送评分，由用户后续评价
            );
        } catch (Exception e) {
            log.error("发布配送完成事件失败，订单ID: {}, 错误信息: {}", order.getId(), e.getMessage(), e);
        }
    }

    /**
     * 获取订单
     */
    private Order getOrder(String orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RequestBadException(OrderError.ORDER_NOT_FOUND));
    }

    /**
     * 发布事件
     */
    private void publishEvents(Order order) {
        // 事件发布已改为通过DomainEventPublisher发布MQ消息
        // 具体的事件发布在各个业务方法中单独处理
    }

    /**
     * 保存并发布事件
     */
    private void saveAndPublish(Order order) {
        orderRepository.save(order);
        publishEvents(order);
    }
    
    /**
     * 保存并发布状态变更事件
     */
    private void saveAndPublishWithStatusChange(Order order, OrderStatus fromStatus, OrderStatus toStatus, String reason) {
        orderRepository.save(order);
        
        // 发布状态变更事件
        try {
            domainEventPublisher.publishOrderStatusChanged(
                    order.getId(),
                    order.getMerchantId(),
                    order.getUserId(),
                    fromStatus,
                    toStatus,
                    reason,
                    ApplicationContext.getUserId(),
                    LocalDateTime.now()
            );
        } catch (Exception e) {
            log.error("发布订单状态变更事件失败，订单ID: {}, 错误信息: {}", order.getId(), e.getMessage(), e);
        }
    }

    /**
     * 发布订单状态变更事件（从Order聚合根调用）
     */
    public void publishOrderStatusChanged(Order order, OrderStatus fromStatus, OrderStatus toStatus, String reason) {
        try {
            domainEventPublisher.publishOrderStatusChanged(
                    order.getId(),
                    order.getMerchantId(),
                    order.getUserId(),
                    fromStatus,
                    toStatus,
                    reason,
                    ApplicationContext.getUserId(),
                    LocalDateTime.now()
            );
        } catch (Exception e) {
            log.error("发布订单状态变更事件失败，订单ID: {}, 错误信息: {}", order.getId(), e.getMessage(), e);
        }
    }

    /**
     * 执行状态转换前校验
     */
    private void validateStatus(Order order, OrderAction action) {
        if (!statusTransitionService.validateTransition(order, action)) {
            throw new RequestBadException(OrderError.INVALID_STATUS_TRANSITION);
        }
    }

    /**
     * 更新预订信息
     */
    @Override
    @Transactional
    public void updateReservationInfo(String orderId, ReservationInfo newReservationInfo) {
        // 1. 获取订单
        Order order = getOrder(orderId);
        // 先获取旧的
        ReservationInfo ri = order.getReservationInfo();
        if (StringUtils.isNotEmpty(newReservationInfo.getReserverName())) {
            ri.setReserverName(newReservationInfo.getReserverName());
        }
        if (StringUtils.isNotEmpty(newReservationInfo.getReserverPhone())) {
            ri.setReserverPhone(newReservationInfo.getReserverPhone());
        }
        if (newReservationInfo.getReservationTime() != null) {
            ri.setReservationTime(newReservationInfo.getReservationTime());
        }
        if (newReservationInfo.getDiningNumber() != null) {
            ri.setDiningNumber(newReservationInfo.getDiningNumber());
        }
        if (StringUtils.isNotEmpty(newReservationInfo.getRemarks())) {
            ri.setRemarks(newReservationInfo.getRemarks());
        }
        // 2. 更新预订信息
        order.updateReservationInfo(ri);

        // 3. 保存订单并发布事件
        saveAndPublish(order);
    }

    /**
     * 检查库存可用性
     * 
     * @param orderItems 订单项列表
     * @param merchantId 商户ID
     * @return 库存是否充足
     */
    private boolean checkStockAvailability(List<OrderItem> orderItems, String merchantId) {
        for (OrderItem item : orderItems) {
            // 获取商品规格ID（如果有）
            String productSpecId = null;
            if (CollectionUtil.isNotEmpty(item.getSpecifications())) {
                // 取第一个规格的ID作为商品规格ID
                productSpecId = item.getSpecifications().get(0).getProductSpecId();
            }
            
            // 查询库存信息
            Optional<ProductInventory> inventoryOpt;
            if (StringUtils.isNotEmpty(productSpecId)) {
                inventoryOpt = productInventoryRepository.findByProductIdAndSpecId(
                        item.getProductId(), productSpecId, merchantId);
            } else {
                inventoryOpt = productInventoryRepository.findByProductId(
                        item.getProductId(), merchantId);
            }
            
            if (inventoryOpt.isEmpty()) {
                log.warn("商品库存信息不存在，商品ID: {}, 规格ID: {}", item.getProductId(), productSpecId);
                return false;
            }
            
            ProductInventory inventory = inventoryOpt.get();
            // 检查可用库存是否充足
            if (inventory.getAvailableStock() < item.getQuantity()) {
                log.warn("商品库存不足，商品ID: {}, 规格ID: {}, 需要数量: {}, 可用库存: {}", 
                        item.getProductId(), productSpecId, item.getQuantity(), inventory.getAvailableStock());
                return false;
            }
        }
        return true;
    }

    /**
     * 预留库存
     * 
     * @param orderItems 订单项列表
     * @param merchantId 商户ID
     * @param orderType 订单类型
     */
    private void reserveStock(List<OrderItem> orderItems, String merchantId, OrderType orderType) {
        // 订阅订单类型，非正常商品，无需走商品的校验和库存的管理
        if (orderType == OrderType.SUBSCRIPTION) {
            log.info("订阅订单无需预留库存，商户ID: {}", merchantId);
            return;
        }
        
        for (OrderItem item : orderItems) {
            // 获取商品规格ID（如果有）
            String productSpecId = null;
            if (CollectionUtil.isNotEmpty(item.getSpecifications())) {
                // 取第一个规格的ID作为商品规格ID
                productSpecId = item.getSpecifications().get(0).getProductSpecId();
            }
            
            // 预留库存
            boolean success = productInventoryRepository.reserveStock(
                    item.getProductId(), productSpecId, item.getQuantity(), merchantId);
            
            if (!success) {
                log.error("预留库存失败，商品ID: {}, 规格ID: {}, 数量: {}", 
                        item.getProductId(), productSpecId, item.getQuantity());
                throw new RequestBadException(OrderError.INVENTORY_RESERVE_FAILED);
            }
        }
    }

    /**
     * 释放库存
     * 
     * @param orderItems 订单项列表
     * @param merchantId 商户ID
     * @param orderType 订单类型
     */
    private void releaseStock(List<OrderItem> orderItems, String merchantId, OrderType orderType) {
        // 订阅订单类型，非正常商品，无需走商品的校验和库存的管理
        if (orderType == OrderType.SUBSCRIPTION) {
            log.info("订阅订单无需释放库存，商户ID: {}", merchantId);
            return;
        }
        
        // 过滤出商品项（排除服务费和配送费）
        List<OrderItem> productItems = orderItems.stream()
                .filter(item -> !"service_fee".equals(item.getProductId()) && !"delivery_fee".equals(item.getProductId()))
                .collect(Collectors.toList());
        
        for (OrderItem item : productItems) {
            // 获取商品规格ID（如果有）
            String productSpecId = null;
            if (CollectionUtil.isNotEmpty(item.getSpecifications())) {
                // 取第一个规格的ID作为商品规格ID
                productSpecId = item.getSpecifications().get(0).getProductSpecId();
            }
            
            // 释放预留库存
            boolean success = productInventoryRepository.releaseReservedStock(
                    item.getProductId(), productSpecId, item.getQuantity(), merchantId);
            
            if (!success) {
                log.error("释放预留库存失败，商品ID: {}, 规格ID: {}, 数量: {}", 
                        item.getProductId(), productSpecId, item.getQuantity());
                // 释放库存失败不抛异常，只记录日志
            }
        }
    }

    /**
     * 扣减库存
     * 
     * @param orderItems 订单项列表
     * @param merchantId 商户ID
     * @param orderType 订单类型
     */
    private void deductStock(List<OrderItem> orderItems, String merchantId, OrderType orderType) {
        // 订阅订单类型，非正常商品，无需走商品的校验和库存的管理
        if (orderType == OrderType.SUBSCRIPTION) {
            log.info("订阅订单无需扣减库存，商户ID: {}", merchantId);
            return;
        }
        
        // 过滤出商品项（排除服务费和配送费）
        List<OrderItem> productItems = orderItems.stream()
                .filter(item -> !"service_fee".equals(item.getProductId()) && !"delivery_fee".equals(item.getProductId()))
                .toList();
        
        for (OrderItem item : productItems) {
            if  (item.getPaymentStatus() !=  PaymentStatus.PAID) {
                continue;
            }
            // 获取商品规格ID（如果有）
            String productSpecId = null;
            if (CollectionUtil.isNotEmpty(item.getSpecifications())) {
                // 取第一个规格的ID作为商品规格ID
                productSpecId = item.getSpecifications().get(0).getProductSpecId();
            }
            
            // 扣减实际库存
            boolean success = productInventoryRepository.deductStock(
                    item.getProductId(), productSpecId, item.getQuantity(), merchantId);
            
            if (!success) {
                log.error("扣减库存失败，商品ID: {}, 规格ID: {}, 数量: {}", 
                        item.getProductId(), productSpecId, item.getQuantity());
                throw new RequestBadException(OrderError.INVENTORY_DEDUCT_FAILED);
            }
        }
    }

}