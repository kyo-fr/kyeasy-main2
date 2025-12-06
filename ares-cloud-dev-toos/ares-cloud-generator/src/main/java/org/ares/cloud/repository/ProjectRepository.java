package org.ares.cloud.repository;

import org.ares.cloud.entity.ProjectEntity;
import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author hugo  tangxkwork@163.com
 * @description 项目
 * @date 2024/01/23/22:31
 **/
@Mapper
@InterceptorIgnore(tenantLine = "true")
public interface ProjectRepository extends BaseMapper<ProjectEntity> {
}
