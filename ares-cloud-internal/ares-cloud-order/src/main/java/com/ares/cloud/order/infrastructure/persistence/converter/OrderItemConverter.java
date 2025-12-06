package com.ares.cloud.order.infrastructure.persistence.converter;

import com.ares.cloud.order.application.dto.OrderDTO;
import com.ares.cloud.order.domain.enums.PaymentStatus;
import com.ares.cloud.order.domain.model.entity.OrderItem;
import com.ares.cloud.order.infrastructure.persistence.entity.OrderItemEntity;
import com.ares.cloud.order.infrastructure.persistence.entity.ProductSpecificationEntity;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单项转换器
 */
@Mapper(componentModel = "spring")
public interface OrderItemConverter extends MoneyConverter {

    ProductSpecificationConverter productSpecificationConverter = org.mapstruct.factory.Mappers.getMapper(ProductSpecificationConverter.class);

    /**
     * Entity转领域对象
     */
    default OrderItem toDomain(OrderItemEntity entity, List<ProductSpecificationEntity> specEntities) {
        if (entity == null) {
            return null;
        }
        
        OrderItem orderItem = OrderItem.builder()
            .id(entity.getId())
            .orderId(entity.getOrderId())
            .productId(entity.getProductId())
            .productName(entity.getProductName())
            .quantity(entity.getQuantity())
            .unitPrice(createMoney(entity.getUnitPrice(), entity.getCurrency(), entity.getCurrencyScale()))
            .discountedPrice(createMoney(entity.getDiscountedPrice(), entity.getCurrency(), entity.getCurrencyScale()))
            .totalPrice(createMoney(entity.getTotalPrice(), entity.getCurrency(), entity.getCurrencyScale()))
            .currency(entity.getCurrency())
            .currencyScale(entity.getCurrencyScale())
            .paymentStatus(convertToPaymentStatus(entity.getPaymentStatus()))
            .deleted(entity.getDeleted() != null && entity.getDeleted() == 1)
            .build();
            
        // 设置商品规格
        if (specEntities != null && !specEntities.isEmpty()) {
            orderItem.setSpecifications(productSpecificationConverter.toDomainList(specEntities));
        }
        
        return orderItem;
    }
    
    /**
     * 兼容旧代码的转换方法
     */
    default OrderItem toDomain(OrderItemEntity entity) {
        return toDomain(entity, null);
    }

    /**
     * 领域对象转Entity
     */
    default OrderItemEntity toEntity(OrderItem domain) {
        if (domain == null) {
            return null;
        }

        OrderItemEntity entity = new OrderItemEntity();
        entity.setId(domain.getId());
        entity.setOrderId(domain.getOrderId());
        entity.setProductId(domain.getProductId());
        entity.setProductName(domain.getProductName());
        entity.setQuantity(domain.getQuantity());
        entity.setUnitPrice(getMoneyAmount(domain.getUnitPrice()));
        entity.setDiscountedPrice(getMoneyAmount(domain.getDiscountedPrice()));
        entity.setTotalPrice(getMoneyAmount(domain.getTotalPrice()));
        entity.setCurrency(getMoneyCurrency(domain.getUnitPrice()));
        entity.setCurrencyScale(getMoneyScale(domain.getUnitPrice()));
        entity.setPaymentStatus(convertFromPaymentStatus(domain.getPaymentStatus()));
        entity.setDeleted(domain.getDeleted() != null && domain.getDeleted() ? 1 : 0);
        return entity;
    }

    /**
     * Entity转DTO
     */
    default OrderDTO.OrderItemDTO toDTO(OrderItemEntity entity, List<ProductSpecificationEntity> specEntities) {
        if (entity == null) {
            return null;
        }

        OrderDTO.OrderItemDTO dto = OrderDTO.OrderItemDTO.builder()
            .id(entity.getId())
            .orderId(entity.getOrderId())
            .productId(entity.getProductId())
            .productName(entity.getProductName())
            .quantity(entity.getQuantity())
            .unitPrice(toDecimal(entity.getUnitPrice(), entity.getCurrency(), entity.getCurrencyScale()))
            .discountedPrice(toDecimal(entity.getDiscountedPrice(), entity.getCurrency(), entity.getCurrencyScale()))
            .totalPrice(toDecimal(entity.getTotalPrice(), entity.getCurrency(), entity.getCurrencyScale()))
            .currency(entity.getCurrency())
            .currencyScale(entity.getCurrencyScale())
            .paymentStatus(entity.getPaymentStatus() != null ? convertToPaymentStatus(entity.getPaymentStatus()) : null)
            .build();
            
        // 设置商品规格
        if (specEntities != null && !specEntities.isEmpty()) {
            dto.setSpecifications(productSpecificationConverter.toDTOList(specEntities));
        }
        
        return dto;
    }
    
    /**
     * 兼容旧代码的转换方法
     */
    default OrderDTO.OrderItemDTO toDTO(OrderItemEntity entity) {
        return toDTO(entity, null);
    }

    /**
     * 批量转换Domain列表
     */
    default List<OrderItem> toDomainList(List<OrderItemEntity> entities) {
        if (entities == null) {
            return null;
        }
        List<OrderItem> result = new ArrayList<>();
        for (OrderItemEntity entity : entities) {
            result.add(toDomain(entity));
        }
        return result;
    }

    /**
     * 批量转换Entity列表
     */
    default List<OrderItemEntity> toEntityList(List<OrderItem> domains) {
        if (domains == null) {
            return null;
        }
        List<OrderItemEntity> result = new ArrayList<>();
        for (OrderItem domain : domains) {
            result.add(toEntity(domain));
        }
        return result;
    }

    /**
     * 批量转换DTO列表
     */
    default List<OrderDTO.OrderItemDTO> toDTOList(List<OrderItemEntity> entities) {
        if (entities == null) {
            return null;
        }
        List<OrderDTO.OrderItemDTO> result = new ArrayList<>();
        for (OrderItemEntity entity : entities) {
            result.add(toDTO(entity));
        }
        return result;
    }
    
    /**
     * 支付状态枚举转换 - 数字转枚举
     */
    default PaymentStatus convertToPaymentStatus(Integer status) {
        if (status == null) {
            return null;
        }

        if (status == 0) {
            return PaymentStatus.UNPAID;
        }
        for (PaymentStatus paymentStatus : PaymentStatus.values()) {
            if (paymentStatus.getValue() == status) {
                return paymentStatus;
            }
        }
        
        throw new IllegalArgumentException("Unknown payment status code: " + status);
    }
    
    /**
     * 支付状态枚举转换 - 枚举转数字
     */
    default Integer convertFromPaymentStatus(PaymentStatus status) {
        return status != null ? status.getValue() : null;
    }
}
