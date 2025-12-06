package org.ares.cloud.repository;

import org.ares.cloud.entity.ModelFieldEntity;
import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author hugo  tangxkwork@163.com
 * @description 表属性
 * @date 2024/01/23/22:31
 **/
@Mapper
@InterceptorIgnore(tenantLine = "true")
public interface ModelFieldRepository extends BaseMapper<ModelFieldEntity> {
    @Select("select *\n" +
            "        from gen_model_field\n" +
            "        where model_id = #{modelId}\n" +
            "        order by sort")
    List<ModelFieldEntity> getByTableId(@Param("modelId") String modelId);
    @Delete({
            "<script>",
            "DELETE FROM gen_model_field WHERE model_id IN",
            "<foreach item='modelId' collection='modelIds' open='(' separator=',' close=')'>",
            "#{modelId}",
            "</foreach>",
            "</script>"
    })
    void deleteBatchTableIds(@Param("modelIds") String[] modelIds);
}
