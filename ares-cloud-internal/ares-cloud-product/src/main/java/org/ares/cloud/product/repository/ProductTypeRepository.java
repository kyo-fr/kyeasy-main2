package org.ares.cloud.product.repository;

import org.ares.cloud.product.entity.ProductTypeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author hugo tangxkwork@163.com
* @description 商品分类管理 数据仓库
* @version 1.0.0
* @date 2024-10-28
*/
@Mapper
public interface ProductTypeRepository extends BaseMapper<ProductTypeEntity> {
	
}