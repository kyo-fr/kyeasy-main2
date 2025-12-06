package org.ares.cloud.merchantInfo.repository;

import org.ares.cloud.merchantInfo.entity.MerchantKeyWordsEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author hugo tangxkwork@163.com
* @description 商户关键字 数据仓库
* @version 1.0.0
* @date 2024-10-11
*/
@Mapper
public interface MerchantKeyWordsRepository extends BaseMapper<MerchantKeyWordsEntity> {
	
}