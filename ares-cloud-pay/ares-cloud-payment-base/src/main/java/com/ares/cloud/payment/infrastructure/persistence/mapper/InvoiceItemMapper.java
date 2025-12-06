package com.ares.cloud.payment.infrastructure.persistence.mapper;

import com.ares.cloud.payment.infrastructure.persistence.entity.InvoiceItemEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 发票明细项Mapper接口
 */
@Mapper
public interface InvoiceItemMapper extends BaseMapper<InvoiceItemEntity> {

}