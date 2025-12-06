package org.ares.cloud.merchantInfo.convert;

import org.ares.cloud.merchantInfo.entity.HardWareEntity;
import org.ares.cloud.merchantInfo.dto.HardWareDto;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 硬件管理 转换器
* @version 1.0.0
* @date 2024-10-12
*/
@Mapper(componentModel = "spring")
public interface HardWareConvert {
    /**
    * 将实体转换为数据传输对象。
    *
    * @param entity 实体。
    * @return 数据传输对象。
    */
    @InheritConfiguration(name = "toBaseDto")
    @Mapping(target = "hardwareId", source = "hardwareId")
    @Mapping(target = "typeId", source = "typeId")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "status", source = "status")
    HardWareDto toDto(HardWareEntity entity);

    /**
    * 将多个实体转换为数据传输对象。
    *
    * @param list 实体列表。
    * @return 数据传输对象列表。
    */
    List<HardWareDto> listToDto(List<HardWareEntity> list);

    /**
    * 将数据传输对象转换为实体。
    *
    * @param dto 数据传输对象。
    * @return 实体。
    */
    @InheritConfiguration(name = "toBaseEntity")
    @Mapping(target = "hardwareId", source = "hardwareId")
    @Mapping(target = "typeId", source = "typeId")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "status", source = "status")
    HardWareEntity toEntity(HardWareDto dto);

    /**
    * 将多个数据传输对象转换为实体。
    *
    * @param list 数据传输对象列表。
    * @return 实体列表。
    */
    List<HardWareEntity> listToEntities(List<HardWareDto> list);
}