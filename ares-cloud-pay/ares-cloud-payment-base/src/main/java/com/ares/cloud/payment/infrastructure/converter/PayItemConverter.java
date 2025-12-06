package com.ares.cloud.payment.infrastructure.converter;

import com.ares.cloud.base.dto.SysPaymentChannelDto;
import com.ares.cloud.base.service.SysPaymentChannelService;
import org.ares.cloud.common.model.Money;
import com.ares.cloud.payment.application.dto.PayItemDTO;
import com.ares.cloud.payment.domain.model.PayItem;
import com.ares.cloud.payment.infrastructure.persistence.entity.PayItemEntity;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 支付项转换器
 */
@Component
@RequiredArgsConstructor
public class PayItemConverter {
    
    /**
     * 将领域模型转换为实体类
     *
     * @param payItem 支付项领域模型
     * @return 支付项实体类
     */
    public PayItemEntity toEntity(PayItem payItem,String invoiceId) {
        if (payItem == null) {
            return null;
        }
        PayItemEntity entity = new PayItemEntity();
        entity.setId(payItem.getId());
        entity.setInvoiceId(invoiceId);
        entity.setChannelId(payItem.getChannelId());
        entity.setTradeNo(payItem.getTradeNo());
        entity.setAmount(payItem.getAmount() != null ? payItem.getAmount().getAmount() : null);
        entity.setPayTime(payItem.getPayTime());
        entity.setStatus(payItem.getStatus());
        entity.setRemark(payItem.getRemark());
        
        return entity;
    }
    
    /**
     * 将实体类转换为领域模型
     *
     * @param entity 支付项实体类
     * @return 支付项领域模型
     */
    public PayItem toDomain(PayItemEntity entity,String currency, Integer scale) {
        if (entity == null) {
            return null;
        }
        
        return PayItem.builder()
                .id(entity.getId())
                .channelId(entity.getChannelId())
                .tradeNo(entity.getTradeNo())
                .amount(entity.getAmount() != null ? 
                        Money.create(entity.getAmount(), currency, scale) : null)
                .payTime(entity.getPayTime())
                .status(entity.getStatus())
                .invoiceId(entity.getInvoiceId())
                .remark(entity.getRemark())
                .build();
    }
    
    /**
     * 将领域模型列表转换为实体类列表
     *
     * @param domainList 领域模型列表
     * @return 实体类列表
     */
    public List<PayItemEntity> toEntityList(List<PayItem> domainList) {
        if (domainList == null) {
            return null;
        }

        return domainList.stream()
                .map(item -> toEntity(item, null))
                .collect(Collectors.toList());
    }
    
    /**
     * 将实体类列表转换为领域模型列表
     *
     * @param entityList 实体类列表
     * @return 领域模型列表
     */
    public List<PayItem> toDomainList(List<PayItemEntity> entityList, String currency, Integer scale) {
        if (entityList == null) {
            return null;
        }
        
        return entityList.stream()
                .map(item -> toDomain(item, currency, scale))
                .collect(Collectors.toList());
    }

    /**
     * 转换为DTO
     */
    public PayItemDTO toDTO(PayItemEntity entity, String currency, Integer scale, Map<String,SysPaymentChannelDto> channelMap) {
        if (entity == null) {
            return null;
        }
        
        PayItemDTO dto = new PayItemDTO();
        dto.setId(entity.getId());
        dto.setChannelId(entity.getChannelId());
        dto.setTradeNo(entity.getTradeNo());
        dto.setAmount(Money.create(entity.getAmount(), currency, scale).toDecimal());
        dto.setPayTime(entity.getPayTime());
        dto.setStatus(entity.getStatus());
        dto.setPayType(entity.getPayType());
        dto.setRemark(entity.getRemark());
        if (channelMap != null && !channelMap.isEmpty()) {
            SysPaymentChannelDto dto1 = channelMap.get(entity.getChannelId());
            if (dto1 != null) {
                dto.setPayType(dto1.getChannelType());
                dto.setChannelKey(dto1.getChannelKey());
            }
        }
        return dto;
    }
}