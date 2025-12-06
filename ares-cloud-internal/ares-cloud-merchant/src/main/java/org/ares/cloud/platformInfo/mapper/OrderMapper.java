package org.ares.cloud.platformInfo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OrderMapper {
    @Select("select count(1) from orders where tenant_id = #{tenantId} and status in ('2','3')")
    int getOrderCountByTenantId(String tenantId);
}
