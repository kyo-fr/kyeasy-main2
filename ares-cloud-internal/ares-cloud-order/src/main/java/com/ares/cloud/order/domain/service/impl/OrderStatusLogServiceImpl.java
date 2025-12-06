package com.ares.cloud.order.domain.service.impl;

import lombok.RequiredArgsConstructor;
import org.ares.cloud.common.utils.DateUtils;
import com.ares.cloud.order.domain.enums.OrderStatus;
import com.ares.cloud.order.domain.enums.OrderStatusType;
import com.ares.cloud.order.domain.model.valueobject.OrderStatusLog;
import com.ares.cloud.order.domain.repository.OrderStatusLogRepository;
import com.ares.cloud.order.domain.service.OrderStatusLogService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单状态日志服务实现类
 */
@Service
@RequiredArgsConstructor
public class OrderStatusLogServiceImpl implements OrderStatusLogService {

    private final OrderStatusLogRepository orderStatusLogRepository;

    @Override
    public void recordStatusChange(String orderId, OrderStatus oldStatus, OrderStatus newStatus,
                                  OrderStatusType statusType, String operatorId, String remark) {
        // 创建状态日志对象
        OrderStatusLog statusLog = OrderStatusLog.builder()
                .orderId(orderId)
                .statusType(statusType)
                .oldStatus(oldStatus)
                .newStatus(newStatus)
                .operatorId(operatorId)
                .remark(remark)
                .operateTime(DateUtils.getCurrentTimestampInUTC())
                .build();

        // 保存状态日志
        orderStatusLogRepository.save(statusLog);
    }

    @Override
    public List<OrderStatusLog> queryStatusLogs(String orderId, OrderStatusType statusType) {
        return orderStatusLogRepository.findByOrderId(orderId, statusType);
    }
}