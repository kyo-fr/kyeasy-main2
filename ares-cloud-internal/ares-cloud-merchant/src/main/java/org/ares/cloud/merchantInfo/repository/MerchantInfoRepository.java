package org.ares.cloud.merchantInfo.repository;

import org.ares.cloud.merchantInfo.entity.MerchantInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author hugo tangxkwork@163.com
* @description 商户信息 数据仓库
* @version 1.0.0
* @date 2024-10-08
*/
@Mapper
public interface MerchantInfoRepository extends BaseMapper<MerchantInfoEntity> {
	
}