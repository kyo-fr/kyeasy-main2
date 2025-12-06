package com.ares.cloud.order.infrastructure.persistence.converter;

import com.ares.cloud.order.application.dto.OrderOperationLogDTO;
import com.ares.cloud.order.domain.model.valueobject.OrderOperationLog;
import com.ares.cloud.order.infrastructure.persistence.entity.OrderOperationLogEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * 订单操作日志转换器
 */
@Mapper(componentModel = "spring", uses = MoneyConverter.class, unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface OrderOperationLogConverter extends MoneyConverter {
    
    /**
     * 将领域模型转换为持久化实体
     */
    default OrderOperationLogEntity toEntity(OrderOperationLog log) {
        if (log == null) {
            return null;
        }
        
        OrderOperationLogEntity entity = new OrderOperationLogEntity();
        entity.setId(log.getId());
        entity.setOrderId(log.getOrderId());
        entity.setAction(log.getAction());
        entity.setOperatorId(log.getOperatorId());
        entity.setContent(log.getContent());
        entity.setRemark(log.getRemark());
        entity.setOperateTime(log.getOperateTime());
        entity.setVersion(log.getVersion());
        entity.setAmount(getMoneyAmount(log.getAmount()));
        entity.setCurrency(getMoneyCurrency(log.getAmount()));
        entity.setCurrencyScale(getMoneyScale(log.getAmount()));
        entity.setItemCount(log.getItemCount());
        entity.setOrderCount(log.getOrderCount());
        entity.setVersion(1);
        entity.setTenantId(log.getTenantId());
        return entity;
    }
    
    /**
     * 将持久化实体转换为领域模型
     */
    default OrderOperationLog toDomain(OrderOperationLogEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return OrderOperationLog.builder()
                .id(entity.getId())
                .orderId(entity.getOrderId())
                .action(entity.getAction())
                .operatorId(entity.getOperatorId())
                .content(entity.getContent())
                .remark(entity.getRemark())
                .operateTime(entity.getOperateTime())
                .version(entity.getVersion())
                .amount(createMoney(entity.getAmount(), entity.getCurrency(), entity.getCurrencyScale()))
                .itemCount(entity.getItemCount())
                .orderCount(entity.getOrderCount())
                .build();
    }
    
    /**
     * 批量将持久化实体转换为领域模型列表
     */
    default List<OrderOperationLog> toDomainList(List<OrderOperationLogEntity> entities) {
        return entities.stream()
                .map(this::toDomain)
                .toList();
    }
    
    /**
     * 将持久化实体转换为DTO
     */
    default OrderOperationLogDTO toDTO(OrderOperationLogEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return OrderOperationLogDTO.builder()
                .id(entity.getId())
                .orderId(entity.getOrderId())
                .action(entity.getAction())
                .actionDesc(entity.getAction() != null ? entity.getAction().getDescription() : null)
                .operatorId(entity.getOperatorId())
                .content(entity.getContent())
                .remark(entity.getRemark())
                .operateTime(entity.getOperateTime())
                .amount(toDecimal(entity.getAmount(), entity.getCurrency(), entity.getCurrencyScale()))
                .itemCount(entity.getItemCount())
                .orderCount(entity.getOrderCount())
                .build();
    }
    
    /**
     * 批量将持久化实体转换为DTO列表
     */
    default List<OrderOperationLogDTO> toDTOList(List<OrderOperationLogEntity> entities) {
        return entities.stream()
                .map(this::toDTO)
                .toList();
    }
}