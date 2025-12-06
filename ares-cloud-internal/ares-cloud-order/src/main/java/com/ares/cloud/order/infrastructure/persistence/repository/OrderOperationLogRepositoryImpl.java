package com.ares.cloud.order.infrastructure.persistence.repository;

import com.ares.cloud.order.domain.model.valueobject.OrderOperationLog;
import com.ares.cloud.order.domain.repository.OrderOperationLogRepository;
import com.ares.cloud.order.infrastructure.persistence.converter.OrderOperationLogConverter;
import com.ares.cloud.order.infrastructure.persistence.entity.OrderOperationLogEntity;
import com.ares.cloud.order.infrastructure.persistence.mapper.OrderOperationLogMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.ares.cloud.common.utils.DateUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 订单操作日志仓储实现类
 */
@Repository
@RequiredArgsConstructor
public class OrderOperationLogRepositoryImpl implements OrderOperationLogRepository {

    private final OrderOperationLogMapper orderOperationLogMapper;
    private final OrderOperationLogConverter orderOperationLogConverter;

    @Override
    public void save(OrderOperationLog orderOperationLog) {
        OrderOperationLogEntity entity = orderOperationLogConverter.toEntity(orderOperationLog);
        if (entity.getOperateTime() == null) {
            entity.setOperateTime(DateUtils.getCurrentTimestampInUTC());
        }
        
        orderOperationLogMapper.insert(entity);
    }


    @Override
    public List<OrderOperationLog> findByOrderId(String orderId) {
        LambdaQueryWrapper<OrderOperationLogEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderOperationLogEntity::getOrderId, orderId)
               .orderByDesc(OrderOperationLogEntity::getOperateTime);
        
        List<OrderOperationLogEntity> entities = orderOperationLogMapper.selectList(wrapper);
        
        return orderOperationLogConverter.toDomainList(entities);
    }

}