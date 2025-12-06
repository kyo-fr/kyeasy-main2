package org.ares.cloud.repository;

import org.ares.cloud.entity.FieldTypeEntity;
import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

/**
 * @author hugo  tangxkwork@163.com
 * @description 数据类型
 * @date 2024/01/23/22:28
 **/
@Mapper
@InterceptorIgnore(tenantLine = "true")
public interface FieldTypeRepository extends BaseMapper<FieldTypeEntity> {
    /**
     * 根据modelId，获取包列表
     */
    @Select("select t1.package_name\n" +
            "        from gen_field_type t1,\n" +
            "             gen_model_field t2\n" +
            "        where t1.attr_type = t2.attr_type\n" +
            "          and t2.model_id = #{modelId}")
    Set<String> getPackageByTableId(@Param("modelId") String modelId);

    /**
     * 获取全部字段类型
     */
    @Select("select attr_type from gen_field_type")
    Set<String> list();
}
