package org.ares.cloud.rider.convert;

import org.ares.cloud.rider.entity.RiderEntity;
import org.ares.cloud.api.user.dto.RiderDto;
import org.ares.cloud.common.dto.TenantDto;
import org.ares.cloud.database.entity.TenantEntity;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 骑手 转换器
* @version 1.0.0
* @date 2025-08-26
*/
@Mapper(componentModel = "spring")
public interface RiderConvert {
    /**
    * 父类转换
    * @param entity 实体
    * @return  数据传输对象
    */
    TenantDto toBaseDto(TenantEntity entity);

    /**
    * 父类转换
    * @param dto 数据传输对象
    * @return 实体
    */
    TenantEntity toBaseEntity(TenantDto dto);

    /**
    * 将实体转换为数据传输对象。
    *
    * @param entity 实体。
    * @return 数据传输对象。
    */
    @InheritConfiguration(name = "toBaseDto")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "countryCode", source = "countryCode")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "status", source = "status")
    RiderDto toDto(RiderEntity entity);

    /**
    * 将多个实体转换为数据传输对象。
    *
    * @param list 实体列表。
    * @return 数据传输对象列表。
    */
    List< RiderDto> listToDto(List<RiderEntity> list);

    /**
    * 将数据传输对象转换为实体。
    *
    * @param dto 数据传输对象。
    * @return 实体。
    */
    @InheritConfiguration(name = "toBaseEntity")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "countryCode", source = "countryCode")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "status", source = "status")
    RiderEntity toEntity( RiderDto dto);

    /**
    * 将多个数据传输对象转换为实体。
    *
    * @param list 数据传输对象列表。
    * @return 实体列表。
    */
    List<RiderEntity> listToEntities(List< RiderDto> list);
}