package org.ares.cloud.businessId.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.ares.cloud.businessId.entity.BusinessIdEntity;

/**
* @author hugo tangxkwork@163.com
* @description 系统业务id 数据仓库
* @version 1.0.0
* @date 2024-10-13
*/
@Mapper
public interface BusinessIdRepository extends BaseMapper<BusinessIdEntity> {
    @Select("select * from  sys_business_id where module_name = #{moduleName}")
    BusinessIdEntity findByModuleName(@Param("moduleName") String moduleName);
}