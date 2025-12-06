package org.ares.cloud.infrastructure.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.ares.cloud.infrastructure.persistence.entity.PaymentOrderEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentOrderMapper extends BaseMapper<PaymentOrderEntity> {
} 