package org.ares.cloud.product.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.ares.cloud.product.entity.MerchantKeyWordsEntity;

/**
* @author hugo tangxkwork@163.com
* @description 商户关键字 数据仓库
* @version 1.0.0
* @date 2024-10-11
*/
@Mapper
public interface MerchantKeyWordsRepository extends BaseMapper<MerchantKeyWordsEntity> {
	
}