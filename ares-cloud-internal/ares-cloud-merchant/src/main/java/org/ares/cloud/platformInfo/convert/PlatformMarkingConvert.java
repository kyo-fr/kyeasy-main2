package org.ares.cloud.platformInfo.convert;

import org.ares.cloud.common.dto.TenantDto;
import org.ares.cloud.platformInfo.entity.PlatformMarkingEntity;
import org.ares.cloud.platformInfo.dto.PlatformMarkingDto;
import org.ares.cloud.common.dto.BaseDto;
import org.ares.cloud.database.entity.BaseEntity;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
* @author hugo platformInfo
* @description 商品标注 转换器
* @version 1.0.0
* @date 2024-11-04
*/
@Mapper(componentModel = "spring")
public interface PlatformMarkingConvert {
    /**
    * 父类转换
    * @param entity 实体
    * @return  数据传输对象
    */
    TenantDto toBaseDto(TenantDto entity);

    /**
    * 父类转换
    * @param dto 数据传输对象
    * @return 实体
    */
    TenantDto toBaseEntity(TenantDto dto);

    /**
    * 将实体转换为数据传输对象。
    *
    * @param entity 实体。
    * @return 数据传输对象。
    */
    @InheritConfiguration(name = "toBaseDto")
    @Mapping(target = "markingName", source = "markingName")
    PlatformMarkingDto toDto(PlatformMarkingEntity entity);

    /**
    * 将多个实体转换为数据传输对象。
    *
    * @param list 实体列表。
    * @return 数据传输对象列表。
    */
    List< PlatformMarkingDto> listToDto(List<PlatformMarkingEntity> list);

    /**
    * 将数据传输对象转换为实体。
    *
    * @param dto 数据传输对象。
    * @return 实体。
    */
    @InheritConfiguration(name = "toBaseEntity")
    @Mapping(target = "markingName", source = "markingName")
    PlatformMarkingEntity toEntity( PlatformMarkingDto dto);

    /**
    * 将多个数据传输对象转换为实体。
    *
    * @param list 数据传输对象列表。
    * @return 实体列表。
    */
    List<PlatformMarkingEntity> listToEntities(List< PlatformMarkingDto> list);
}