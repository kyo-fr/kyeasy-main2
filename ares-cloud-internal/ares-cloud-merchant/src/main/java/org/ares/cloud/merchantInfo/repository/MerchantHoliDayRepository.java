package org.ares.cloud.merchantInfo.repository;

import org.ares.cloud.merchantInfo.entity.MerchantHoliDayEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author hugo tangxkwork@163.com
* @description 商户休假 数据仓库
* @version 1.0.0
* @date 2025-01-03
*/
@Mapper
public interface MerchantHoliDayRepository extends BaseMapper<MerchantHoliDayEntity> {
	
}