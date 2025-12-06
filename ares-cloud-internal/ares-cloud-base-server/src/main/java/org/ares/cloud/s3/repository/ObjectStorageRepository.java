package org.ares.cloud.s3.repository;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.ares.cloud.s3.entity.ObjectStorageEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author hugo tangxkwork@163.com
* @description s3存储 数据仓库
* @version 1.0.0
* @date 2024-10-12
*/
@Mapper
public interface ObjectStorageRepository extends BaseMapper<ObjectStorageEntity> {
    /**
     * 根据文件名获取文件信息
     * @param name name
     * @return 文件实体
     */
    @Select("select * from  sys_s3_storage where name = #{name}")
    ObjectStorageEntity findByName(@Param("name") String name);
    /**
     * 根据文件ID删除文件信息
     * @param id 文件ID
     * @return 影响的行数（大于0表示删除成功）
     */
    @Delete("DELETE FROM sys_s3_storage WHERE id = #{id}")
    int permanentlyDeleteById(@Param("id") String id);

}