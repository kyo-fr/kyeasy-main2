package com.ares.cloud.order.infrastructure.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ares.cloud.order.infrastructure.persistence.entity.OrderOperationLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单操作日志Mapper接口
 */
@Mapper
public interface OrderOperationLogMapper extends BaseMapper<OrderOperationLogEntity> {
}