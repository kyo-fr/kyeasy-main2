package com.ares.cloud.order.infrastructure.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.ares.cloud.order.infrastructure.persistence.entity.OrderPaymentEntity;

@Mapper
public interface OrderPaymentMapper extends BaseMapper<OrderPaymentEntity> {
} 