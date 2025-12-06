package org.ares.cloud.product.repository;

import org.ares.cloud.product.entity.ProductAuctionPriceLogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author hugo tangxkwork@163.com
* @description 拍卖商品竞价记录 数据仓库
* @version 1.0.0
* @date 2025-09-23
*/
@Mapper
public interface ProductAuctionPriceLogRepository extends BaseMapper<ProductAuctionPriceLogEntity> {
	
}