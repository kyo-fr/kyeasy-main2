package com.ares.cloud.payment.infrastructure.converter;

import org.ares.cloud.common.model.Money;
import com.ares.cloud.payment.application.dto.InvoiceItemDTO;
import com.ares.cloud.payment.domain.model.InvoiceItem;
import com.ares.cloud.payment.infrastructure.persistence.entity.InvoiceItemEntity;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 发票明细项转换器
 * 负责在领域模型、DTO和持久化实体之间进行转换
 */
@Component
@RequiredArgsConstructor
public class InvoiceItemConverter {

    /**
     * 将领域模型转换为DTO
     */
    public InvoiceItemDTO toDTO(InvoiceItem invoiceItem) {
        if (invoiceItem == null) {
            return null;
        }
        
        InvoiceItemDTO dto = new InvoiceItemDTO();
        dto.setId(invoiceItem.getId());
        dto.setProductId(invoiceItem.getProductId());
        dto.setOrderItemId(invoiceItem.getOrderItemId());
        dto.setProductName(invoiceItem.getProductName());
        dto.setQuantity(invoiceItem.getQuantity());
        dto.setOriginalPrice(invoiceItem.getOriginalPrice() != null ? invoiceItem.getOriginalPrice().toDecimal() : null);
        dto.setUnitPrice(invoiceItem.getUnitPrice() != null ? invoiceItem.getUnitPrice().toDecimal() : null);
        dto.setTaxRate(invoiceItem.getTaxRate());
        dto.setTaxAmount(invoiceItem.getTaxAmount() != null ? invoiceItem.getTaxAmount().toDecimal() : null);
        dto.setTotalAmount(invoiceItem.getTotalAmount() != null ? invoiceItem.getTotalAmount().toDecimal() : null);
        dto.setRemark(invoiceItem.getRemark());
        
        return dto;
    }

    /**
     * 将领域模型转换为持久化实体
     */
    public InvoiceItemEntity toEntity(InvoiceItem item, String invoiceId) {
        if (item == null) {
            return null;
        }
        
        InvoiceItemEntity entity = new InvoiceItemEntity();
        entity.setId(item.getId());
        entity.setInvoiceId(invoiceId);
        entity.setProductId(item.getProductId());
        entity.setOrderItemId(item.getOrderItemId());
        entity.setProductName(item.getProductName());
        entity.setQuantity(item.getQuantity());
        // 转换金额时使用币种和精度信息
        entity.setOriginalPrice(item.getOriginalPrice() != null ? 
                item.getOriginalPrice().getAmount() : null);
        entity.setUnitPrice(item.getUnitPrice() != null ? 
                item.getUnitPrice().getAmount() : null);
        entity.setTaxRate(item.getTaxRate() != null ? 
                item.getTaxRate() : null);
        entity.setTaxAmount(item.getTaxAmount() != null ? 
                item.getTaxAmount().getAmount() : null);
        entity.setTotalAmount(item.getTotalAmount() != null ? 
                item.getTotalAmount().getAmount() : null);
        entity.setRemark(item.getRemark());
        return entity;
    }

    /**
     * 将持久化实体转换为领域模型
     */
    public InvoiceItem toDomain(InvoiceItemEntity entity, String currency, Integer scale) {
        if (entity == null) {
            return null;
        }
        
        return InvoiceItem.builder()
                .id(entity.getId())
                .invoiceId(entity.getInvoiceId())
                .productId(entity.getProductId())
                .orderItemId(entity.getOrderItemId())
                .productName(entity.getProductName())
                .quantity(entity.getQuantity())
                // 转换金额时使用币种和精度信息
                .originalPrice(entity.getOriginalPrice() != null ?  Money.create(entity.getOriginalPrice(),currency, scale) : null)
                .unitPrice(entity.getUnitPrice() != null ?  Money.create(entity.getUnitPrice(),currency, scale) : null)
                .taxRate(entity.getTaxRate() != null ? entity.getTaxRate() : null)
                .taxAmount(entity.getTaxAmount() != null ?  Money.create(entity.getTaxAmount(),currency, scale) : null)
                .totalAmount(entity.getTotalAmount() != null ? Money.create(entity.getTotalAmount(),currency, scale) : null)
                .remark(entity.getRemark())
                .build();
    }
    /**
     * 将领域模型列表转换为DTO列表
     */
    public List<InvoiceItemDTO> toDTOList(List<InvoiceItem> domainList) {
        if (domainList == null) {
            return null;
        }
        
        return domainList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    /**
     * 将领域模型列表转换为持久化实体列表
     */
    public List<InvoiceItemEntity> toEntityList(List<InvoiceItem> domainList, String invoiceId) {
        if (domainList == null) {
            return null;
        }
        
        return domainList.stream()
                .map(item->toEntity(item, invoiceId))
                .collect(Collectors.toList());
    }

    /**
     * 转换为DTO
     */
    public InvoiceItemDTO toDTO(InvoiceItemEntity entity,String currency, Integer scale) {
        if (entity == null) {
            return null;
        }
        
        InvoiceItemDTO dto = new InvoiceItemDTO();
        dto.setId(entity.getId());
        dto.setProductId(entity.getProductId());
        dto.setOrderItemId(entity.getOrderItemId());
        dto.setProductName(entity.getProductName());
        dto.setQuantity(entity.getQuantity());
        // 转换金额相关字段
        dto.setOriginalPrice(Money.create(entity.getOriginalPrice(), currency, scale).toDecimal());
        dto.setUnitPrice(Money.create(entity.getUnitPrice(), currency, scale).toDecimal());
        dto.setTaxRate(entity.getTaxRate());
        dto.setTaxAmount(Money.create(entity.getTaxAmount(), currency, scale).toDecimal());
        dto.setTotalAmount(Money.create(entity.getTotalAmount(), currency, scale).toDecimal());
        dto.setRemark(entity.getRemark());
        
        return dto;
    }
}