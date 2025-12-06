package org.ares.cloud.user.convert;

import org.ares.cloud.api.user.dto.UserDto;
import org.ares.cloud.user.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 用户 转换器
* @version 1.0.0
* @date 2024-11-11
*/
@Mapper(componentModel = "spring")
public interface UserConvert {
    /**
    * 将实体转换为数据传输对象。
    *
    * @param entity 实体。
    * @return 数据传输对象。
    */
    // @InheritConfiguration(name = "toBaseDto")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "version", source = "version")
    @Mapping(target = "deleted", source = "deleted")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "enterpriseNumber", source = "enterpriseNumber")
    @Mapping(target = "contractId", source = "contractId")
    @Mapping(target = "merchantId", source = "merchantId")
    @Mapping(target = "tenantId", source = "tenantId")
    @Mapping(target = "identity", source = "identity")
    @Mapping(target = "countryCode", source = "countryCode")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "account", source = "account")
    @Mapping(target = "updateTime", source = "updateTime")
    @Mapping(target = "updater", source = "updater")
    @Mapping(target = "createTime", source = "createTime")
    @Mapping(target = "creator", source = "creator")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nickname", source = "nickname")
    UserDto toDto(UserEntity entity);

    /**
    * 将多个实体转换为数据传输对象。
    *
    * @param list 实体列表。
    * @return 数据传输对象列表。
    */
    List<UserDto> listToDto(List<UserEntity> list);

    /**
    * 将数据传输对象转换为实体。
    *
    * @param dto 数据传输对象。
    * @return 实体。
    */
    // @InheritConfiguration(name = "toBaseEntity")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "version", source = "version")
    @Mapping(target = "deleted", source = "deleted")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "enterpriseNumber", source = "enterpriseNumber")
    @Mapping(target = "contractId", source = "contractId")
    @Mapping(target = "merchantId", source = "merchantId")
    @Mapping(target = "tenantId", source = "tenantId")
    @Mapping(target = "identity", source = "identity")
    @Mapping(target = "countryCode", source = "countryCode")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "account", source = "account")
    @Mapping(target = "updateTime", source = "updateTime")
    @Mapping(target = "updater", source = "updater")
    @Mapping(target = "createTime", source = "createTime")
    @Mapping(target = "creator", source = "creator")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nickname", source = "nickname")
    UserEntity toEntity(UserDto dto);

    /**
    * 将多个数据传输对象转换为实体。
    *
    * @param list 数据传输对象列表。
    * @return 实体列表。
    */
    List<UserEntity> listToEntities(List<UserDto> list);
}