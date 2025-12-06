package org.ares.cloud.platformInfo.repository;

import org.ares.cloud.platformInfo.entity.PlatformInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author hugo tangxkwork@163.com
* @description 平台信息 数据仓库
* @version 1.0.0
* @date 2024-10-15
*/
@Mapper
public interface PlatformInfoRepository extends BaseMapper<PlatformInfoEntity> {
	
}