package org.ares.cloud.platformInfo.convert;

import org.ares.cloud.platformInfo.entity.PlatformInfoEntity;
import org.ares.cloud.platformInfo.dto.PlatformInfoDto;
import org.ares.cloud.common.dto.BaseDto;
import org.ares.cloud.database.entity.BaseEntity;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 平台信息 转换器
* @version 1.0.0
* @date 2024-10-15
*/
@Mapper(componentModel = "spring")
public interface PlatformInfoConvert {
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
    @Mapping(target = "platformName", source = "platformName")
    @Mapping(target = "platformPhone", source = "platformPhone")
    @Mapping(target = "taxNum", source = "taxNum")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "language", source = "language")
    @Mapping(target = "country", source = "country")
    @Mapping(target = "mobile", source = "mobile")
    PlatformInfoDto toDto(PlatformInfoEntity entity);

    /**
    * 将多个实体转换为数据传输对象。
    *
    * @param list 实体列表。
    * @return 数据传输对象列表。
    */
    List< PlatformInfoDto> listToDto(List<PlatformInfoEntity> list);

    /**
    * 将数据传输对象转换为实体。
    *
    * @param dto 数据传输对象。
    * @return 实体。
    */
    @InheritConfiguration(name = "toBaseEntity")
    @Mapping(target = "platformName", source = "platformName")
    @Mapping(target = "platformPhone", source = "platformPhone")
    @Mapping(target = "taxNum", source = "taxNum")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "language", source = "language")
    @Mapping(target = "country", source = "country")
    @Mapping(target = "mobile", source = "mobile")
    PlatformInfoEntity toEntity( PlatformInfoDto dto);

    /**
    * 将多个数据传输对象转换为实体。
    *
    * @param list 数据传输对象列表。
    * @return 实体列表。
    */
    List<PlatformInfoEntity> listToEntities(List< PlatformInfoDto> list);
}