package org.ares.cloud.repository;

import org.ares.cloud.entity.DataSourceEntity;
import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author hugo  tangxkwork@163.com
 * @description 数据源
 * @date 2024/01/23/22:28
 **/
@Mapper
@InterceptorIgnore(tenantLine = "true")
public interface DataSourceRepository extends BaseMapper<DataSourceEntity> {
}
