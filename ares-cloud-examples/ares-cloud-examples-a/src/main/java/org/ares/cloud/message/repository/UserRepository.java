package org.ares.cloud.message.repository;

import org.ares.cloud.message.entity.UserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author hugo tangxkwork@163.com
* @description 用户 数据仓库
* @version 1.0.0
* @date 2024-10-07
*/
@Mapper
public interface UserRepository extends BaseMapper<UserEntity> {
	
}