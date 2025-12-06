package org.ares.cloud.platformInfo.convert;

import org.ares.cloud.platformInfo.entity.PlatformWorkOrderContentEntity;
import org.ares.cloud.platformInfo.dto.PlatformWorkOrderContentDto;
import org.ares.cloud.common.dto.BaseDto;
import org.ares.cloud.database.entity.BaseEntity;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 工单内容 转换器
* @version 1.0.0
* @date 2024-10-17
*/
@Mapper(componentModel = "spring")
public interface PlatformWorkOrderContentConvert {
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
    @Mapping(target = "sendId", source = "sendId")
    @Mapping(target = "receiverId", source = "receiverId")
    @Mapping(target = "workOrderId", source = "workOrderId")
    @Mapping(target = "content", source = "content")
    PlatformWorkOrderContentDto toDto(PlatformWorkOrderContentEntity entity);

    /**
    * 将多个实体转换为数据传输对象。
    *
    * @param list 实体列表。
    * @return 数据传输对象列表。
    */
    List< PlatformWorkOrderContentDto> listToDto(List<PlatformWorkOrderContentEntity> list);

    /**
    * 将数据传输对象转换为实体。
    *
    * @param dto 数据传输对象。
    * @return 实体。
    */
    @InheritConfiguration(name = "toBaseEntity")
    @Mapping(target = "sendId", source = "sendId")
    @Mapping(target = "receiverId", source = "receiverId")
    @Mapping(target = "workOrderId", source = "workOrderId")
    @Mapping(target = "content", source = "content")
    PlatformWorkOrderContentEntity toEntity( PlatformWorkOrderContentDto dto);

    /**
    * 将多个数据传输对象转换为实体。
    *
    * @param list 数据传输对象列表。
    * @return 实体列表。
    */
    List<PlatformWorkOrderContentEntity> listToEntities(List< PlatformWorkOrderContentDto> list);
}