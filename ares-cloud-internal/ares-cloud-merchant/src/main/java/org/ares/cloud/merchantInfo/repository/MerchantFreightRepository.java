package org.ares.cloud.merchantInfo.repository;

import org.ares.cloud.merchantInfo.entity.MerchantFreightEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author hugo tangxkwork@163.com
* @description 货运配送费用 数据仓库
* @version 1.0.0
* @date 2024-11-05
*/
@Mapper
public interface MerchantFreightRepository extends BaseMapper<MerchantFreightEntity> {
	
}