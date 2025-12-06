package com.ares.cloud.order.domain.service.impl;

import org.ares.cloud.common.model.Money;
import lombok.RequiredArgsConstructor;
import org.ares.cloud.common.dto.PageResult;
import org.ares.cloud.common.utils.DateUtils;
import com.ares.cloud.order.domain.enums.OrderAction;
import com.ares.cloud.order.domain.model.valueobject.OrderOperationLog;
import com.ares.cloud.order.domain.repository.OrderOperationLogRepository;
import com.ares.cloud.order.domain.service.OrderOperationLogService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单操作日志服务实现类
 */
@Service
@RequiredArgsConstructor
public class OrderOperationLogServiceImpl implements OrderOperationLogService {

    private final OrderOperationLogRepository orderOperationLogRepository;

    @Override
    public void recordOperation(String orderId, OrderAction action, String operatorId, String content, String remark
            , Money amount, Integer itemCount, Integer orderCount,String tenantId) {
        // 创建操作日志对象
        OrderOperationLog operationLog = OrderOperationLog.builder()
                .orderId(orderId)
                .action(action)
                .operatorId(operatorId)
                .content(content)
                .remark(remark)
                .operateTime(DateUtils.getCurrentTimestampInUTC())
                .amount(amount)
                .itemCount(itemCount)
                .orderCount(orderCount)
                .tenantId(tenantId)
                .build();

        // 保存操作日志
        orderOperationLogRepository.save(operationLog);
    }


    @Override
    public List<OrderOperationLog> queryOperationLogs(String orderId) {
        return orderOperationLogRepository.findByOrderId(orderId);
    }
}