package org.ares.cloud.platformInfo.repository;

import org.ares.cloud.platformInfo.entity.PlatformApprovalEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author hugo tangxkwork@163.com
* @description 平台审批 数据仓库
* @version 1.0.0
* @date 2024-10-31
*/
@Mapper
public interface PlatformApprovalRepository extends BaseMapper<PlatformApprovalEntity> {
	
}