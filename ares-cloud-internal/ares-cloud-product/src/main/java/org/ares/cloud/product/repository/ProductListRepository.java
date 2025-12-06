package org.ares.cloud.product.repository;

import org.ares.cloud.product.entity.ProductListEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author hugo tangxkwork@163.com
* @description 商品清单 数据仓库
* @version 1.0.0
* @date 2025-04-03
*/
@Mapper
public interface ProductListRepository extends BaseMapper<ProductListEntity> {
	
}