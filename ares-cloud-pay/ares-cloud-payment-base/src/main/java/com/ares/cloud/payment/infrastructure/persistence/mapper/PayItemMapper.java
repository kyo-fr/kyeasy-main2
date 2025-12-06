package com.ares.cloud.payment.infrastructure.persistence.mapper;

import com.ares.cloud.payment.infrastructure.persistence.entity.PayItemEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 支付项Mapper接口
 */
@Mapper
public interface PayItemMapper extends BaseMapper<PayItemEntity> {

}