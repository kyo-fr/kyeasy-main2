package org.ares.cloud.platformInfo.convert;

import org.ares.cloud.common.dto.TenantDto;
import org.ares.cloud.database.entity.TenantEntity;
import org.ares.cloud.platformInfo.dto.PlatformComplaintsDto;
import org.ares.cloud.platformInfo.entity.PlatformComplaintsEntity;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 平台投诉建议 转换器
* @version 1.0.0
* @date 2024-10-17
*/
@Mapper(componentModel = "spring")
public interface PlatformComplaintsConvert {
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
    @Mapping(target = "userName", source = "userName")
    @Mapping(target = "userEmail", source = "userEmail")
    @Mapping(target = "content", source = "content")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "userId", source = "userId")
    PlatformComplaintsDto toDto(PlatformComplaintsEntity entity);

    /**
    * 将多个实体转换为数据传输对象。
    *
    * @param list 实体列表。
    * @return 数据传输对象列表。
    */
    List<PlatformComplaintsDto> listToDto(List<PlatformComplaintsEntity> list);

    /**
    * 将数据传输对象转换为实体。
    *
    * @param dto 数据传输对象。
    * @return 实体。
    */
    @InheritConfiguration(name = "toBaseEntity")
    @Mapping(target = "userName", source = "userName")
    @Mapping(target = "userEmail", source = "userEmail")
    @Mapping(target = "content", source = "content")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "userId", source = "userId")
    PlatformComplaintsEntity toEntity( PlatformComplaintsDto dto);

    /**
    * 将多个数据传输对象转换为实体。
    *
    * @param list 数据传输对象列表。
    * @return 实体列表。
    */
    List<PlatformComplaintsEntity> listToEntities(List< PlatformComplaintsDto> list);
}