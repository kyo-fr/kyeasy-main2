package com.ares.cloud.order.application.service;

import com.ares.cloud.order.application.dto.OrderDTO;
import com.ares.cloud.order.application.dto.OrderOperationLogDTO;
import com.ares.cloud.order.application.query.OrderOperationLogQuery;
import com.ares.cloud.order.domain.model.valueobject.OrderOperationLog;
import com.ares.cloud.order.infrastructure.persistence.converter.OrderOperationLogConverter;
import com.ares.cloud.order.infrastructure.persistence.entity.OrderItemEntity;
import com.ares.cloud.order.infrastructure.persistence.entity.OrderOperationLogEntity;
import com.ares.cloud.order.infrastructure.persistence.mapper.OrderOperationLogMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.ares.cloud.common.dto.PageResult;
import org.ares.cloud.common.utils.StringUtils;
import org.ares.cloud.database.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单操作日志查询服务
 */
@Service
@RequiredArgsConstructor
public class OrderOperationLogQueryService  extends BaseServiceImpl<OrderOperationLogMapper, OrderOperationLogEntity> {

    private final OrderOperationLogConverter orderOperationLogConverter;

    /**
     * 分页查询订单操作日志
     *
     * @param query 查询条件
     * @return 订单操作日志分页结果
     */
    public PageResult<OrderOperationLogDTO> queryOperationLogs(OrderOperationLogQuery query) {
        LambdaQueryWrapper<OrderOperationLogEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(query.getOrderId())) {
            wrapper.eq(OrderOperationLogEntity::getOrderId, query.getOrderId());
        }
        if (query.getStartTime() != null){
            wrapper.ge(OrderOperationLogEntity::getOperateTime, query.getStartTime());
        }
        if (query.getEndTime() != null){
            wrapper.le(OrderOperationLogEntity::getOperateTime, query.getEndTime());
        }
        // 查询订单
        IPage<OrderOperationLogEntity> page = baseMapper.selectPage(getPage(query), wrapper);
        // 查询订单项并组装
        List<OrderOperationLogDTO> orderDTOs = page.getRecords().stream()
                .map(orderOperationLogConverter::toDTO)
                .toList();

        return new PageResult<>(orderDTOs, page.getTotal());
    }
}