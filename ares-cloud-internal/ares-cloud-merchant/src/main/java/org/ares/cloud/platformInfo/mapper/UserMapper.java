package org.ares.cloud.platformInfo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.ares.cloud.api.user.dto.UserDto;

@Mapper
public interface UserMapper {
    @Update("update USERS set identity = #{identity},MERCHANT_ID = #{tenantId} where id = #{id}")
    int updateUserById(String id,Integer identity,String tenantId);

    @Select("select * from USERS where id = #{userId}")
    UserDto getUserByUserId(String userId);
}
