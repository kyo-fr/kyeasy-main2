package org.ares.cloud.s3.convert;

import org.ares.cloud.s3.entity.ObjectStorageEntity;
import org.ares.cloud.s3.dto.ObjectStorageDto;
import org.ares.cloud.common.dto.TenantDto;
import org.ares.cloud.database.entity.TenantEntity;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description s3存储 转换器
* @version 1.0.0
* @date 2024-10-13
*/
@Mapper(componentModel = "spring")
public interface ObjectStorageConvert {
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
    @Mapping(target = "originalFileName", source = "originalFileName")
    @Mapping(target = "fileType", source = "fileType")
    @Mapping(target = "container", source = "container")
    @Mapping(target = "model", source = "model")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "fileSize", source = "fileSize")
    ObjectStorageDto toDto(ObjectStorageEntity entity);

    /**
    * 将多个实体转换为数据传输对象。
    *
    * @param list 实体列表。
    * @return 数据传输对象列表。
    */
    List< ObjectStorageDto> listToDto(List<ObjectStorageEntity> list);

    /**
    * 将数据传输对象转换为实体。
    *
    * @param dto 数据传输对象。
    * @return 实体。
    */
    @InheritConfiguration(name = "toBaseEntity")
    @Mapping(target = "originalFileName", source = "originalFileName")
    @Mapping(target = "fileType", source = "fileType")
    @Mapping(target = "container", source = "container")
    @Mapping(target = "model", source = "model")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "fileSize", source = "fileSize")
    ObjectStorageEntity toEntity( ObjectStorageDto dto);

    /**
    * 将多个数据传输对象转换为实体。
    *
    * @param list 数据传输对象列表。
    * @return 实体列表。
    */
    List<ObjectStorageEntity> listToEntities(List< ObjectStorageDto> list);
}