package org.ares.cloud.product.repository;

import org.ares.cloud.product.entity.ProductAuctionEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author hugo tangxkwork@163.com
* @description 拍卖商品 数据仓库
* @version 1.0.0
* @date 2024-11-08
*/
@Mapper
public interface ProductAuctionRepository extends BaseMapper<ProductAuctionEntity> {
	
}