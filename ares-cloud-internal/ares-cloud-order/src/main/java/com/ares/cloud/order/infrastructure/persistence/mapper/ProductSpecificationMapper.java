package com.ares.cloud.order.infrastructure.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ares.cloud.order.infrastructure.persistence.entity.ProductSpecificationEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品规格Mapper
 */
@Mapper
public interface ProductSpecificationMapper extends BaseMapper<ProductSpecificationEntity> {
}