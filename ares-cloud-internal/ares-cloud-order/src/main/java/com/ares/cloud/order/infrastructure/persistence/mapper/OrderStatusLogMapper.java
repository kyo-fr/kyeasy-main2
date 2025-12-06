package com.ares.cloud.order.infrastructure.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.ares.cloud.order.infrastructure.persistence.entity.OrderStatusLogEntity;

@Mapper
public interface OrderStatusLogMapper extends BaseMapper<OrderStatusLogEntity> {
}