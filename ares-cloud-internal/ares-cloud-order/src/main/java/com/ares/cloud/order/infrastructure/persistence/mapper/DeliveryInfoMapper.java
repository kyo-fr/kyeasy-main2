package com.ares.cloud.order.infrastructure.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.ares.cloud.order.infrastructure.persistence.entity.DeliveryInfoDO;

@Mapper
public interface DeliveryInfoMapper extends BaseMapper<DeliveryInfoDO> {
    // BaseMapper已经提供了基础的CRUD方法，无需额外定义
    // 根据订单id获取配送信息
    @Select("SELECT * FROM ORDER_DELIVERY_INFO WHERE order_id = #{orderId}")
    DeliveryInfoDO selectByOrderId(@Param("orderId") String orderId);
} 