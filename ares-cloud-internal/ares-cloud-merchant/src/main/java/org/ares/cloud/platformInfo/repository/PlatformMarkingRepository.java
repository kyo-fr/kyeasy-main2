package org.ares.cloud.platformInfo.repository;

import org.ares.cloud.platformInfo.entity.PlatformMarkingEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author hugo platformInfo
* @description 商品标注 数据仓库
* @version 1.0.0
* @date 2024-11-04
*/
@Mapper
public interface PlatformMarkingRepository extends BaseMapper<PlatformMarkingEntity> {
	
}