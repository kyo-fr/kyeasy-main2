package org.ares.cloud.merchantInfo.repository;

import org.ares.cloud.merchantInfo.entity.HardWareEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author hugo tangxkwork@163.com
* @description 硬件管理 数据仓库
* @version 1.0.0
* @date 2024-10-12
*/
@Mapper
public interface HardWareRepository extends BaseMapper<HardWareEntity> {
	
}