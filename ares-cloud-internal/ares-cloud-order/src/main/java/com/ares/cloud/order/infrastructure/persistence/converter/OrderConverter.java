package com.ares.cloud.order.infrastructure.persistence.converter;

import com.ares.cloud.order.application.dto.OrderDTO;
import com.ares.cloud.order.application.dto.ProductSpecificationDTO;
import com.ares.cloud.order.domain.enums.OrderStatus;
import com.ares.cloud.order.domain.enums.OrderType;
import com.ares.cloud.order.domain.enums.PaymentStatus;
import com.ares.cloud.order.domain.model.aggregate.Order;
import com.ares.cloud.order.domain.model.valueobject.MerchantInfo;
import com.ares.cloud.order.infrastructure.persistence.entity.DeliveryInfoDO;
import com.ares.cloud.order.infrastructure.persistence.entity.OrderEntity;
import com.ares.cloud.order.infrastructure.persistence.entity.OrderItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单转换器
 */
@Component
public class OrderConverter implements MoneyConverter {

    @Autowired
    private DeliveryInfoConverter deliveryInfoConverter;

    /**
     * Entity转领域对象
     */
    public Order toDomain(OrderEntity entity) {
        if (entity == null) {
            return null;
        }

        return Order.builder()
                .id(entity.getId())
                .merchantId(entity.getMerchantId())
                .orderNumber(entity.getOrderNumber())
                .orderCode(entity.getOrderCode())
                .countryCode(entity.getCountryCode())
                .phoneNumber(entity.getPhoneNumber())
                .totalAmount(createMoney(entity.getTotalAmount(), entity.getCurrency(), entity.getCurrencyScale()))
                .serviceFee(createMoney(entity.getServiceFee(), entity.getCurrency(), entity.getCurrencyScale()))
                .paidAmount(createMoney(entity.getPaidAmount(), entity.getCurrency(), entity.getCurrencyScale()))
                .currency(entity.getCurrency())
                .currencyScale(entity.getCurrencyScale())
                //.serviceFeeRate(entity.getServiceFeeRate()) // 移除不存在的方法调用
                .status(entity.getStatus())
                .paymentStatus(entity.getPaymentStatus())
                .paymentMode(entity.getPaymentMode())
                .orderType(entity.getOrderType())
                .createTime(entity.getCreateTime())
                .paymentTime(entity.getPaymentTime())
                .paymentDeadline(entity.getPaymentDeadline())
                .deliveryTime(entity.getDeliveryTime())
                .finishTime(entity.getFinishTime())
                .paymentChannelId(entity.getPaymentChannelId())
                .paymentTradeNo(entity.getPaymentTradeNo())
                .timezone(entity.getTimezone())
                .reservationTime(entity.getReservationTime())
                .tableNo(entity.getTableNo())
                .diningNumber(entity.getDiningNumber())
                .pickupTime(entity.getPickupTime())
                .deliveryType(entity.getDeliveryType())
                .deliveryStatus(entity.getDeliveryStatus())
                .riderId(entity.getRiderId())
                .tenantId(entity.getTenantId())
                .sourceType(entity.getSourceType())
                .cancelTime(entity.getCancelTime())
                .cancelReason(entity.getCancelReason())
                .build();
    }

    /**
     * 领域对象转Entity
     */
    public OrderEntity toEntity(Order domain) {
        if (domain == null) {
            return null;
        }

        OrderEntity entity = new OrderEntity();
        entity.setId(domain.getId());
        entity.setMerchantId(domain.getMerchantId());
        entity.setCountryCode(domain.getCountryCode());
        entity.setOrderNumber(domain.getOrderNumber());
        entity.setOrderCode(domain.getOrderCode());
        entity.setPhoneNumber(domain.getPhoneNumber());
        entity.setTotalAmount(domain.getTotalAmount().getAmount());
        entity.setServiceFee(domain.getServiceFee().getAmount());
        entity.setPaidAmount(domain.getPaidAmount() != null ? domain.getPaidAmount().getAmount() : null);
        entity.setCurrency(domain.getCurrency());
        entity.setCurrencyScale(domain.getCurrencyScale());
        //entity.setServiceFeeRate(domain.getServiceFeeRate()); // 移除不存在的方法调用
        entity.setStatus(domain.getStatus());
        entity.setPaymentStatus(domain.getPaymentStatus());
        entity.setPaymentMode(domain.getPaymentMode());
        entity.setOrderType(domain.getOrderType());
        entity.setCreateTime(domain.getCreateTime());
        entity.setPaymentTime(domain.getPaymentTime());
        entity.setPaymentDeadline(domain.getPaymentDeadline());
        entity.setDeliveryTime(domain.getDeliveryTime());
        entity.setFinishTime(domain.getFinishTime());
        entity.setPaymentChannelId(domain.getPaymentChannelId());
        entity.setPaymentTradeNo(domain.getPaymentTradeNo());
        entity.setTimezone(domain.getTimezone());
        entity.setReservationTime(domain.getReservationTime());
        entity.setTableNo(domain.getTableNo());
        entity.setDiningNumber(domain.getDiningNumber());
        entity.setPickupTime(domain.getPickupTime());
        entity.setDeliveryType(domain.getDeliveryType());
        entity.setDeliveryStatus(domain.getDeliveryStatus());
        entity.setRiderId(domain.getRiderId());
        entity.setTenantId(domain.getTenantId());
        entity.setSourceType(domain.getSourceType());
        entity.setCancelTime(domain.getCancelTime());
        entity.setCancelReason(domain.getCancelReason());
        return entity;
    }

    /**
     * Entity转DTO
     */
    public OrderDTO toDTO(OrderEntity order, List<OrderItemEntity> items, DeliveryInfoDO deliveryInfo, MerchantInfo merchantInfo) {
        if (order == null) {
            return null;
        }
        String merchantName;
        if (merchantInfo == null){
            merchantName = null;
        }else {
            merchantName = merchantInfo.getName();
        }
        OrderDTO dto = OrderDTO.builder()
                .merchantName(merchantName)
                .merchantId(order.getMerchantId())
                .orderNumber(order.getOrderNumber())
                .orderCode(order.getOrderCode())
                .countryCode(order.getCountryCode())
                .phoneNumber(order.getPhoneNumber())
                .totalAmount(createMoney(order.getTotalAmount(), order.getCurrency(), order.getCurrencyScale()).toDecimal())
                .serviceFee(createMoney(order.getServiceFee(), order.getCurrency(), order.getCurrencyScale()).toDecimal())
                .paidAmount(createMoney(order.getPaidAmount(), order.getCurrency(), order.getCurrencyScale()).toDecimal())
                .status(order.getStatus())
                .paymentStatus(order.getPaymentStatus())
                .paymentMode(order.getPaymentMode())
                .orderType(order.getOrderType())
                .paymentTime(order.getPaymentTime())
                .paymentDeadline(order.getPaymentDeadline())
                .deliveryTime(order.getDeliveryTime())
                .finishTime(order.getFinishTime())
                .paymentChannelId(order.getPaymentChannelId())
                .paymentTradeNo(order.getPaymentTradeNo())
                .reservationTime(order.getReservationTime())
                .tableNo(order.getTableNo())
                .diningNumber(order.getDiningNumber())
                .pickupTime(order.getPickupTime())
                .deliveryType(order.getDeliveryType())
                .currency(order.getCurrency())
                .currencyScale(order.getCurrencyScale())
                .deliveryStatus(order.getDeliveryStatus())
                .sourceType(order.getSourceType())
                .cancelTime(order.getCancelTime())
                .cancelReason(order.getCancelReason())
                .items(items != null ? items.stream()
                        .map(item -> OrderDTO.OrderItemDTO.builder()
                                .id(item.getId())
                                .orderId(item.getOrderId())
                                .productId(item.getProductId())
                                .productName(item.getProductName())
                                .unitPrice(createMoney(item.getUnitPrice(), item.getCurrency(), item.getCurrencyScale()).toDecimal())
                                .quantity(item.getQuantity())
                                .totalPrice(createMoney(item.getTotalPrice(), item.getCurrency(), item.getCurrencyScale()).toDecimal())
                                .currency(item.getCurrency())
                                .currencyScale(item.getCurrencyScale())
                                .paymentStatus(PaymentStatus.fromValue(item.getPaymentStatus()))
                                .specifications(item.getSpecifications() != null ? item.getSpecifications().stream().map(spec -> ProductSpecificationDTO.builder()
                                                .id(spec.getId())
                                                .productSpecId(spec.getProductSpecId())
                                                .name(spec.getName())
                                                .value(spec.getValue())
                                                .price(createMoney(spec.getPrice(), spec.getCurrency(), spec.getCurrencyScale()).toDecimal())
                                                .build())
                                        .collect(Collectors.toList()) : null)
                                .build())
                        .collect(Collectors.toList()) : null)
                .deliveryInfo(deliveryInfo != null ? deliveryInfoConverter.toDeliveryInfoDTO(deliveryInfo) : null)
                .build();

        // 设置基础字段
        dto.setId(order.getId());
        dto.setCreateTime(order.getCreateTime());
        dto.setUpdateTime(order.getUpdateTime());

        return dto;
    }

    /**
     * 批量转换Domain列表
     */
    public List<Order> toDomainList(List<OrderEntity> entities) {
        if (entities == null) {
            return null;
        }
        List<Order> result = new ArrayList<>();
        for (OrderEntity entity : entities) {
            result.add(toDomain(entity));
        }
        return result;
    }

    /**
     * 批量转换Entity列表
     */
    public List<OrderEntity> toEntityList(List<Order> domains) {
        if (domains == null) {
            return null;
        }
        List<OrderEntity> result = new ArrayList<>();
        for (Order domain : domains) {
            result.add(toEntity(domain));
        }
        return result;
    }

    public Integer convertFromOrderStatus(OrderStatus status) {
        if (status == null) {
            return null;
        }
        return status.getValue();
    }

    /**
     * 支付状态转换
     */
    public PaymentStatus convertToPaymentStatus(Integer status) {
        if (status == null) {
            return null;
        }

        for (PaymentStatus paymentStatus : PaymentStatus.values()) {
            if (paymentStatus.getValue() == status) {
                return paymentStatus;
            }
        }

        throw new IllegalArgumentException("Unknown payment status code: " + status);
    }

    public Integer convertFromOrderType(OrderType type) {
        if (type == null) {
            return null;
        }
        return type.getValue();
    }
}
