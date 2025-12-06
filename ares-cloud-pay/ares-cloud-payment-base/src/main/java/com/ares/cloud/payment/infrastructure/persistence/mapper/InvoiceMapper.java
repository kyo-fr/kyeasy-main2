package com.ares.cloud.payment.infrastructure.persistence.mapper;

import com.ares.cloud.payment.infrastructure.persistence.entity.InvoiceEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 发票Mapper接口
 */
@Mapper
public interface InvoiceMapper extends BaseMapper<InvoiceEntity> {

}