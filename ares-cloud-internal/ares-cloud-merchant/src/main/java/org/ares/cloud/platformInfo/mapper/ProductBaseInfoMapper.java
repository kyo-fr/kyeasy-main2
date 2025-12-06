package org.ares.cloud.platformInfo.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ProductBaseInfoMapper {
    @Select("select count(1) from PRODUCT_BASE_INFO where tenant_id = #{tenantId} and is_enable = 'enable'")
    int getProductCountByTenantId(String tenantId);
}
