package com.ares.cloud.order.application.service;

import com.ares.cloud.order.application.command.*;
import com.ares.cloud.order.domain.enums.DeliveryType;
import com.ares.cloud.order.domain.enums.OrderError;
import com.ares.cloud.order.domain.model.command.PayCommand;
import com.ares.cloud.order.domain.model.valueobject.DeliveryInfo;
import com.ares.cloud.order.domain.model.valueobject.MerchantInfo;
import com.ares.cloud.order.infrastructure.persistence.entity.OrderEntity;
import com.ares.cloud.order.infrastructure.persistence.mapper.OrderMapper;
import org.ares.cloud.common.model.Money;
import com.ares.cloud.order.domain.model.valueobject.PayItem;
import com.ares.cloud.order.domain.model.valueobject.ReservationInfo;
import com.ares.cloud.order.domain.service.OrderDomainService;
import lombok.RequiredArgsConstructor;
import org.ares.cloud.api.order.commod.CreateOrderCommand;
import org.ares.cloud.api.order.commod.PayOrderCommand;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.common.exception.RequestBadException;
import org.ares.cloud.common.utils.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单应用服务
 */
@Service
@RequiredArgsConstructor
public class OrderApplicationService {

    private final OrderDomainService orderDomainService;
    private final OrderMapper orderMapper;
    /**
     * 创建订单
     *
     * @param command 创建订单命令
     * @return 订单ID
     */
    public String createOrder(CreateOrderCommand command) {
        String tenantId = ApplicationContext.getTenantId();
        if (StringUtils.isBlank(tenantId)) {
            tenantId = command.getMerchantId();
            ApplicationContext.setTenantId(tenantId);
        }else {
            command.setMerchantId(tenantId);
        }
        // 直接将命令对象传递给领域服务
        return orderDomainService.createOrder(command,tenantId);
    }


    /**
     * 确认订单
     *
     * @param orderId 订单ID
     */
    public void confirmOrder(String orderId) {
        orderDomainService.confirmOrder(orderId);
    }

    /**
     * 开始配送
     *
     * @param orderId 订单ID
     */
    public void startDelivery(String orderId) {
        // 开始配送，更新配送状态
        DeliveryInfo deliveryInfo = DeliveryInfo.builder()
                .deliveryType(DeliveryType.MERCHANT)
                .build();
        orderDomainService.startDelivery(orderId, deliveryInfo);
    }

    /**
     * 完成配送
     *
     * @param orderId 订单ID
     */
    public void completeDelivery(String orderId) {
        // 完成配送，更新配送状态
        orderDomainService.completeDelivery(orderId);
    }

    /**
     * 完成订单
     *
     * @param orderId 订单ID
     */
    public void completeOrder(String orderId) {
        orderDomainService.completeOrder(orderId);
    }
    /**
     * 删除订单项
     *
     * @param command 删除订单项命令
     */
    public void deleteOrderItem(DeleteOrderItemCommand command) {
        orderDomainService.deleteOrderItem(
                command.getOrderId(),
                command.getOrderItemId(),
                command.getReason(),
                ApplicationContext.getUserId()
        );
    }

    /**
     * 取消订单
     *
     * @param command 取消命令
     */
    public void cancelOrder(CancelOrderCommand command) {
        orderDomainService.cancelOrder(command.getOrderId(),command.getReason(),ApplicationContext.getUserId());
    }

    /**
     * 手动单支付方式全部结算
     *
     * @param command 支付订单命令
     */
    public void manualSettlementOrder(PayOrderCommand command,boolean isPlatform) {
        if (StringUtils.isNotBlank(command.getMerchantId())) {
            ApplicationContext.setTenantId(command.getMerchantId());
        }
        MerchantInfo merchantInfo = validateMerchant(command.getOrderId(),isPlatform);

        // 构建支付项列表
        List<PayItem> payItems = command.getPayChannels().stream()
                .map(item -> PayItem.builder()
                        .channelId(item.getChannelId())
                        .tradeNo(item.getTradeNo())
                        .amount(Money.of(item.getAmount(), merchantInfo.getCurrency(),merchantInfo.getCurrencyScale()))
                        .build())
                .toList();

        // 构建PayCommand
        PayCommand payCommand = PayCommand.createFullPayCommand(command.getOrderId(),
                payItems,Money.of(command.getOrderDeductAmount(), merchantInfo.getCurrency(), merchantInfo.getCurrencyScale()),
                command.getOrderDeductReason(),
                command.getCountryCode(),
                command.getUserPhone(),
                Money.of(command.getGiftPoints(), merchantInfo.getCurrency(), merchantInfo.getCurrencyScale())
        );
        payCommand.setMerchantId(merchantInfo.getId());
        payCommand.setIsPlatform(isPlatform);
        orderDomainService.payOrder(payCommand);
    }

    /**
     * 退款
     *
     * @param command 退款命令
     */
    public void refundOrder(RefundOrderCommand command) {
        orderDomainService.refundOrder(
                command.getOrderId(),
                command.getReason()
        );
    }

    /**
     * 手动部分结算
     *
     * @param command 部分支付命令
     */
    public void manualPartialSettlementOrder(PartialPayOrderCommand command) {
        MerchantInfo merchantInfo = validateMerchant(command.getOrderId(),false);

        // 构建支付项列表
        List<PayItem> payItems = command.getPayChannels().stream()
                .map(item -> PayItem.builder()
                        .channelId(item.getChannelId())
                        .tradeNo(item.getTradeNo())
                        .amount(Money.of(item.getAmount(), merchantInfo.getCurrency(),merchantInfo.getCurrencyScale()))
                        .build())
                .toList();

        // 构建PayCommand
        PayCommand payCommand = PayCommand.createPartialPayCommand(
                command.getOrderId(),
                payItems,
                command.getOrderItemIds(),
                Money.of(command.getOrderDeductAmount(), merchantInfo.getCurrency(), merchantInfo.getCurrencyScale()),
                command.getOrderDeductReason(),
                command.getCountryCode(),
                command.getUserPhone(),
                Money.of(command.getGiftPoints(), merchantInfo.getCurrency(), merchantInfo.getCurrencyScale())
        );
        payCommand.setMerchantId(merchantInfo.getId());

        orderDomainService.partialPayOrder(payCommand);
    }

    /**
     * 开始就餐
     *
     * @param orderId 订单ID
     */
    public void startDining(String orderId) {
        orderDomainService.startDining(orderId);
    }

    /**
     * 就餐中
     *
     * @param orderId 订单ID
     */
    public void diningInProgress(String orderId) {
        // 更新就餐状态为就餐中
        orderDomainService.diningInProgress(orderId);
    }

    /**
     * 准备取餐
     *
     * @param orderId 订单ID
     */
    public void readyForPickup(String orderId) {
        orderDomainService.readyForPickup(orderId);
    }

    /**
     * 顾客到店
     *
     * @param orderId 订单ID
     */
    public void customerArrived(String orderId) {
        orderDomainService.customerArrived(orderId);
    }

    /**
     * 开始排队
     *
     * @param orderId 订单ID
     */
    public void startQueuing(String orderId) {
        orderDomainService.startQueuing(orderId);
    }

    /**
     * 结束排队
     *
     * @param orderId 订单ID
     */
    public void endQueuing(String orderId) {
        orderDomainService.endQueuing(orderId);
    }
    /**
     * 骑手接单
     */
    public void acceptDelivery(AcceptDeliveryCommand command) {
        if (StringUtils.isBlank(command.getRiderId())) {
            command.setRiderId(ApplicationContext.getUserId());
        }
        DeliveryInfo deliveryInfo = DeliveryInfo.builder()
                .deliveryType(DeliveryType.MERCHANT)
                .riderId(command.getRiderId())
                .build();
        orderDomainService.acceptDelivery(command.getOrderId(), deliveryInfo);
    }

    /**
     * 开始商户自配送
     */
    public void startMerchantDelivery(String orderId) {
        orderDomainService.startDelivery(orderId);
    }

    /**
     * 开始第三方配送
     *
     * @param command 第三方配送命令
     */
    public void startThirdPartyDelivery(StartThirdPartyDeliveryCommand command) {
        MerchantInfo merchantInfo = validateMerchant(command.getOrderId(),false);
        DeliveryInfo deliveryInfo = DeliveryInfo.builder()
                .deliveryType(DeliveryType.THIRD_PARTY)
                .deliveryCompany(command.getDeliveryCompany())
                .trackingNo(command.getTrackingNo())
                .deliveryPhone(command.getDeliveryPhone())
                .deliveryAddress(command.getDeliveryAddress())
                .deliveryLatitude(command.getDeliveryLatitude())
                .deliveryLongitude(command.getDeliveryLongitude())
                .deliveryDistance(command.getDeliveryDistance())
                .deliveryFee(Money.of(command.getDeliveryFee(), merchantInfo.getCurrency(), merchantInfo.getCurrencyScale()))
                .build();

        orderDomainService.startDelivery(command.getOrderId(), deliveryInfo);
    }
    /**
     * 商户校验
     * @return 商户信息
     */
    private MerchantInfo validateMerchant(String orderId,boolean isPlatform) {
        String merchantId = ApplicationContext.getTenantId();
        if (StringUtils.isBlank(merchantId)) {
          throw new RequestBadException(OrderError.CANNOT_SETTLE_ACCOUNTS_ACROSS_MERCHANTS);
        }
        OrderEntity orderEntity = orderMapper.selectById(orderId);
        if (orderEntity == null) {
            throw new RequestBadException(OrderError.ORDER_NOT_FOUND);
        }
        if (!merchantId.equals(orderEntity.getMerchantId())) {
            throw new RequestBadException(OrderError.CANNOT_SETTLE_ACCOUNTS_ACROSS_MERCHANTS);
        }
        MerchantInfo merchantInfo = orderDomainService.getMerchantInfo(merchantId,isPlatform);
        if (merchantInfo == null) {
            throw new RequestBadException(OrderError.MERCHANT_NOT_EXIST);
        }
        return merchantInfo;
    }

    /**
     * 修改预订信息
     *
     * @param command 修改预订信息命令
     */
    public void updateReservationInfo(UpdateReservationCommand command) {
        // 创建预订信息值对象
        ReservationInfo reservationInfo = ReservationInfo.builder()
            .reservationTime(command.getReservationTime())
            .reserverName(command.getReserverName())
            .reserverPhone(command.getReserverPhone())
            .diningNumber(command.getDiningNumber())
            .remarks(command.getRemarks())
            .build();
            
        // 调用领域服务更新预订信息
        orderDomainService.updateReservationInfo(command.getOrderId(), reservationInfo);
    }
}