package org.ares.cloud.platformInfo.convert;

import org.ares.cloud.platformInfo.entity.PlatformSocializeEntity;
import org.ares.cloud.platformInfo.dto.PlatformSocializeDto;
import org.ares.cloud.common.dto.BaseDto;
import org.ares.cloud.database.entity.BaseEntity;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 平台海外社交 转换器
* @version 1.0.0
* @date 2024-10-15
*/
@Mapper(componentModel = "spring")
public interface PlatformSocializeConvert {
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
    @Mapping(target = "whatsApp", source = "whatsApp")
    @Mapping(target = "wechat", source = "wechat")
    @Mapping(target = "tiktok", source = "tiktok")
    @Mapping(target = "youtube", source = "youtube")
    @Mapping(target = "facebook", source = "facebook")
    @Mapping(target = "bilibili", source = "bilibili")
    @Mapping(target = "douYin", source = "douYin")
    @Mapping(target = "twitter", source = "twitter")
    @Mapping(target = "chinaVideo", source = "chinaVideo")
    @Mapping(target = "foreignVideo", source = "foreignVideo")
    PlatformSocializeDto toDto(PlatformSocializeEntity entity);

    /**
    * 将多个实体转换为数据传输对象。
    *
    * @param list 实体列表。
    * @return 数据传输对象列表。
    */
    List< PlatformSocializeDto> listToDto(List<PlatformSocializeEntity> list);

    /**
    * 将数据传输对象转换为实体。
    *
    * @param dto 数据传输对象。
    * @return 实体。
    */
    @InheritConfiguration(name = "toBaseEntity")
    @Mapping(target = "whatsApp", source = "whatsApp")
    @Mapping(target = "wechat", source = "wechat")
    @Mapping(target = "tiktok", source = "tiktok")
    @Mapping(target = "youtube", source = "youtube")
    @Mapping(target = "facebook", source = "facebook")
    @Mapping(target = "bilibili", source = "bilibili")
    @Mapping(target = "douYin", source = "douYin")
    @Mapping(target = "twitter", source = "twitter")
    @Mapping(target = "chinaVideo", source = "chinaVideo")
    @Mapping(target = "foreignVideo", source = "foreignVideo")
    PlatformSocializeEntity toEntity( PlatformSocializeDto dto);

    /**
    * 将多个数据传输对象转换为实体。
    *
    * @param list 数据传输对象列表。
    * @return 实体列表。
    */
    List<PlatformSocializeEntity> listToEntities(List< PlatformSocializeDto> list);
}