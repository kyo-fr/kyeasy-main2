package org.ares.cloud.merchantInfo.repository;

import org.ares.cloud.merchantInfo.entity.MerchantSpecificationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author hugo tangxkwork@163.com
* @description 主规格主数据 数据仓库
* @version 1.0.0
* @date 2025-03-18
*/
@Mapper
public interface MerchantSpecificationRepository extends BaseMapper<MerchantSpecificationEntity> {
	
}