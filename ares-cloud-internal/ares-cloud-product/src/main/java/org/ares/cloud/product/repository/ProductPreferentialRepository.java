package org.ares.cloud.product.repository;

import org.ares.cloud.product.entity.ProductPreferentialEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author hugo tangxkwork@163.com
* @description 优惠商品 数据仓库
* @version 1.0.0
* @date 2024-11-07
*/
@Mapper
public interface ProductPreferentialRepository extends BaseMapper<ProductPreferentialEntity> {
	
}