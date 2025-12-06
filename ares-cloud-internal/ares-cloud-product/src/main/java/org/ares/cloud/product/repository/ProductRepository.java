package org.ares.cloud.product.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.ares.cloud.product.entity.ProductEntity;

/**
* @author hugo tangxkwork@163.com
* @description 商品基础信息 数据仓库
* @version 1.0.0
* @date 2024-11-06
*/
@Mapper
public interface ProductRepository extends BaseMapper<ProductEntity> {
	
}