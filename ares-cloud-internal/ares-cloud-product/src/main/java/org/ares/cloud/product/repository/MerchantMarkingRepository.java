package org.ares.cloud.product.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.ares.cloud.product.entity.MerchantMarkingEntity;

/**
* @author hugo tangxkwork@163.com
* @description 标注主数据 数据仓库
* @version 1.0.0
* @date 2025-03-19
*/
@Mapper
public interface MerchantMarkingRepository extends BaseMapper<MerchantMarkingEntity> {
	
}