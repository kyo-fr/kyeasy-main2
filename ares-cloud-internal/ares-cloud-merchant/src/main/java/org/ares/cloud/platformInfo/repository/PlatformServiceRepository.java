package org.ares.cloud.platformInfo.repository;

import org.ares.cloud.platformInfo.entity.PlatformServiceEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author hugo tangxkwork@163.com
* @description 服务和帮助 数据仓库
* @version 1.0.0
* @date 2024-10-30
*/
@Mapper
public interface PlatformServiceRepository extends BaseMapper<PlatformServiceEntity> {
	
}