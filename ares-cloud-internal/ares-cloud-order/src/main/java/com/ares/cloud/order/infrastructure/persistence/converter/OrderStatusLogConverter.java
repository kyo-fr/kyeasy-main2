package com.ares.cloud.order.infrastructure.persistence.converter;

import com.ares.cloud.order.application.dto.OrderStatusLogDTO;
import com.ares.cloud.order.domain.enums.OrderStatus;
import com.ares.cloud.order.domain.model.valueobject.OrderStatusLog;
import com.ares.cloud.order.infrastructure.persistence.entity.OrderStatusLogEntity;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface OrderStatusLogConverter {
    /**
     * 将实体转换为DTO
     */
    default OrderStatusLogDTO toDTO(OrderStatusLogEntity entity){
        if (entity == null) {
            return null;
        }
        return OrderStatusLogDTO.builder()
                .id(entity.getId())
                .orderId(entity.getOrderId())
                .statusType(entity.getStatusType())
                .oldStatus(entity.getOldStatus())
                .newStatus(entity.getNewStatus())
                .operatorId(entity.getOperatorId())
                .remark(entity.getRemark())
                .operateTime(entity.getOperateTime())
                .build();
    }

    /**
     * 批量将实体转换为DTO列表
     */
    default List<OrderStatusLogDTO> toDTOList(List<OrderStatusLogEntity> entities) {
        return entities.stream()
                .map(this::toDTO)
                .toList();
    }
    
    /**
     * 将实体转换为领域对象
     */
    default OrderStatusLog toDomain(OrderStatusLogEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return OrderStatusLog.builder()
                .id(entity.getId())
                .orderId(entity.getOrderId())
                .statusType(entity.getStatusType())
                .oldStatus(entity.getOldStatus() != null ? OrderStatus.fromValue(entity.getOldStatus()) : null)
                .newStatus(entity.getNewStatus() != null ? OrderStatus.fromValue(entity.getNewStatus()) : null)
                .operatorId(entity.getOperatorId())
                .remark(entity.getRemark())
                .operateTime(entity.getOperateTime())
                .version(entity.getVersion())
                .deleted(entity.getDeleted())
                .build();
    }
    
    /**
     * 批量将实体转换为领域对象列表
     */
    default List<OrderStatusLog> toDomainList(List<OrderStatusLogEntity> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }
    
    /**
     * 将领域对象转换为实体
     */
    default OrderStatusLogEntity toEntity(OrderStatusLog domain) {
        if (domain == null) {
            return null;
        }
        
        OrderStatusLogEntity entity = new OrderStatusLogEntity();
        entity.setId(domain.getId());
        entity.setOrderId(domain.getOrderId());
        entity.setStatusType(domain.getStatusType());
        entity.setOldStatus(domain.getOldStatus() != null ? domain.getOldStatus().getValue() : null);
        entity.setNewStatus(domain.getNewStatus() != null ? domain.getNewStatus().getValue() : null);
        entity.setOperatorId(domain.getOperatorId());
        entity.setRemark(domain.getRemark());
        entity.setOperateTime(domain.getOperateTime());
        entity.setVersion(domain.getVersion());
        entity.setDeleted(domain.getDeleted());
        return entity;
    }
    
    /**
     * 批量将领域对象转换为实体列表
     */
    default List<OrderStatusLogEntity> toEntityList(List<OrderStatusLog> domains) {
        if (domains == null) {
            return null;
        }
        return domains.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}