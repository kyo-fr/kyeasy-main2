package org.ares.cloud.platformInfo.convert;

import org.ares.cloud.platformInfo.entity.PlatformSubscribeEntity;
import org.ares.cloud.platformInfo.dto.PlatformSubscribeDto;
import org.ares.cloud.common.dto.BaseDto;
import org.ares.cloud.database.entity.BaseEntity;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 订阅基础信息 转换器
* @version 1.0.0
* @date 2024-10-31
*/
@Mapper(componentModel = "spring")
public interface PlatformSubscribeConvert {
    /**
    * 父类转换
    * @param entity 实体
    * @return  数据传输对象
    */
    BaseDto toBaseDto(BaseEntity entity);

    /**
    * 父类转换
    * @param dto 数据传输对象
    * @return 实体
    */
    BaseEntity toBaseEntity(BaseDto dto);

    /**
    * 将实体转换为数据传输对象。
    *
    * @param entity 实体。
    * @return 数据传输对象。
    */
    @InheritConfiguration(name = "toBaseDto")
    @Mapping(target = "subscribeType", source = "subscribeType")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "month", source = "month")
    @Mapping(target = "memory", source = "memory")
    PlatformSubscribeDto toDto(PlatformSubscribeEntity entity);

    /**
    * 将多个实体转换为数据传输对象。
    *
    * @param list 实体列表。
    * @return 数据传输对象列表。
    */
    List< PlatformSubscribeDto> listToDto(List<PlatformSubscribeEntity> list);

    /**
    * 将数据传输对象转换为实体。
    *
    * @param dto 数据传输对象。
    * @return 实体。
    */
    @InheritConfiguration(name = "toBaseEntity")
    @Mapping(target = "subscribeType", source = "subscribeType")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "month", source = "month")
    @Mapping(target = "memory", source = "memory")
    PlatformSubscribeEntity toEntity( PlatformSubscribeDto dto);

    /**
    * 将多个数据传输对象转换为实体。
    *
    * @param list 数据传输对象列表。
    * @return 实体列表。
    */
    List<PlatformSubscribeEntity> listToEntities(List< PlatformSubscribeDto> list);
}