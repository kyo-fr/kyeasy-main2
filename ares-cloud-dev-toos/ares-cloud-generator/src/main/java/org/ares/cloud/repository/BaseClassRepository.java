package org.ares.cloud.repository;

import org.ares.cloud.entity.BaseClassEntity;
import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author hugo  tangxkwork@163.com
 * @description 基类管理
 * @date 2024/01/23/22:28
 **/
@Mapper
@InterceptorIgnore(tenantLine = "true")
public interface BaseClassRepository extends BaseMapper<BaseClassEntity> {
}
