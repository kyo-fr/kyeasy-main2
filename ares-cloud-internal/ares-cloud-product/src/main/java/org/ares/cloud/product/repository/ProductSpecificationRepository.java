package org.ares.cloud.product.repository;

import org.ares.cloud.product.entity.ProductSpecificationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author hugo tangxkwork@163.com
* @description 商品规格 数据仓库
* @version 1.0.0
* @date 2025-03-18
*/
@Mapper
public interface ProductSpecificationRepository extends BaseMapper<ProductSpecificationEntity> {
	
}