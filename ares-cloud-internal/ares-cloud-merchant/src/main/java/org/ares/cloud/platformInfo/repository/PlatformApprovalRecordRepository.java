package org.ares.cloud.platformInfo.repository;

import org.ares.cloud.platformInfo.entity.PlatformApprovalRecordEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author hugo tangxkwork@163.com
* @description 存储变更明细表 数据仓库
* @version 1.0.0
* @date 2025-06-16
*/
@Mapper
public interface PlatformApprovalRecordRepository extends BaseMapper<PlatformApprovalRecordEntity> {
	
}