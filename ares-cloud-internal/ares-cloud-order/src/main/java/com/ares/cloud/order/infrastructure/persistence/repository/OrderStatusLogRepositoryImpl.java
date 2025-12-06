package com.ares.cloud.order.infrastructure.persistence.repository;

import com.ares.cloud.order.domain.enums.OrderStatus;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.ares.cloud.common.utils.DateUtils;
import com.ares.cloud.order.domain.enums.OrderStatusType;
import com.ares.cloud.order.domain.model.valueobject.OrderStatusLog;
import com.ares.cloud.order.domain.repository.OrderStatusLogRepository;
import com.ares.cloud.order.infrastructure.persistence.converter.OrderConverter;
import com.ares.cloud.order.infrastructure.persistence.entity.OrderStatusLogEntity;
import com.ares.cloud.order.infrastructure.persistence.mapper.OrderStatusLogMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单状态日志仓储实现类
 */
@Repository
@RequiredArgsConstructor
public class OrderStatusLogRepositoryImpl implements OrderStatusLogRepository {

    private final OrderStatusLogMapper orderStatusLogMapper;
    private final OrderConverter orderConverter;

    @Override
    public void save(OrderStatusLog orderStatusLog) {
        OrderStatusLogEntity entity = new OrderStatusLogEntity();
        entity.setOrderId(orderStatusLog.getOrderId());
        entity.setStatusType(orderStatusLog.getStatusType());
        entity.setOldStatus(orderStatusLog.getOldStatus() != null ? orderStatusLog.getOldStatus().getValue() : null);
        entity.setNewStatus(orderStatusLog.getNewStatus() != null ? orderStatusLog.getNewStatus().getValue() : null);
        entity.setOperatorId(orderStatusLog.getOperatorId());
        entity.setRemark(orderStatusLog.getRemark());
        entity.setOperateTime(orderStatusLog.getOperateTime() != null ? orderStatusLog.getOperateTime() : DateUtils.getCurrentTimestampInUTC());
        
        orderStatusLogMapper.insert(entity);
    }

    @Override
    public List<OrderStatusLog> findByOrderId(String orderId, OrderStatusType statusType) {
        LambdaQueryWrapper<OrderStatusLogEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderStatusLogEntity::getOrderId, orderId)
               .eq(statusType != null, OrderStatusLogEntity::getStatusType, statusType)
               .orderByDesc(OrderStatusLogEntity::getOperateTime);
        
        List<OrderStatusLogEntity> entities = orderStatusLogMapper.selectList(wrapper);
        
        return entities.stream()
                .map(this::convertToDomain)
                .collect(Collectors.toList());
    }
    
    /**
     * 将实体转换为领域对象
     */
    private OrderStatusLog convertToDomain(OrderStatusLogEntity entity) {
        return OrderStatusLog.builder()
                .orderId(entity.getOrderId())
                .statusType(entity.getStatusType())
                .oldStatus(entity.getOldStatus() != null ? OrderStatus.fromValue(entity.getOldStatus()) : null)
                .newStatus(entity.getNewStatus() != null ? OrderStatus.fromValue(entity.getNewStatus()) : null)
                .operatorId(entity.getOperatorId())
                .remark(entity.getRemark())
                .operateTime(entity.getOperateTime())
                .build();
    }
}