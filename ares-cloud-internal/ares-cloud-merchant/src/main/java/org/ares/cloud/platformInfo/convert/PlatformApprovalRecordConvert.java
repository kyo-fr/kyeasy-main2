package org.ares.cloud.platformInfo.convert;

import org.ares.cloud.platformInfo.entity.PlatformApprovalRecordEntity;
import org.ares.cloud.platformInfo.dto.PlatformApprovalRecordDto;
import org.ares.cloud.common.dto.BaseDto;
import org.ares.cloud.database.entity.BaseEntity;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 存储变更明细表 转换器
* @version 1.0.0
* @date 2025-06-16
*/
@Mapper(componentModel = "spring")
public interface PlatformApprovalRecordConvert {
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
    @Mapping(target = "changeMemory", source = "changeMemory")
    @Mapping(target = "approvalId", source = "approvalId")
    @Mapping(target = "recordType", source = "recordType")
    @Mapping(target = "tenantId", source = "tenantId")
    @Mapping(target = "dataSource", source = "dataSource")
    @Mapping(target = "dataType", source = "dataType")
    @Mapping(target = "description", source = "description")
    PlatformApprovalRecordDto toDto(PlatformApprovalRecordEntity entity);

    /**
    * 将多个实体转换为数据传输对象。
    *
    * @param list 实体列表。
    * @return 数据传输对象列表。
    */
    List< PlatformApprovalRecordDto> listToDto(List<PlatformApprovalRecordEntity> list);

    /**
    * 将数据传输对象转换为实体。
    *
    * @param dto 数据传输对象。
    * @return 实体。
    */
    @InheritConfiguration(name = "toBaseEntity")
    @Mapping(target = "changeMemory", source = "changeMemory")
    @Mapping(target = "approvalId", source = "approvalId")
    @Mapping(target = "recordType", source = "recordType")
    @Mapping(target = "tenantId", source = "tenantId")
    @Mapping(target = "dataSource", source = "dataSource")
    @Mapping(target = "dataType", source = "dataType")
    @Mapping(target = "description", source = "description")
    PlatformApprovalRecordEntity toEntity( PlatformApprovalRecordDto dto);

    /**
    * 将多个数据传输对象转换为实体。
    *
    * @param list 数据传输对象列表。
    * @return 实体列表。
    */
    List<PlatformApprovalRecordEntity> listToEntities(List< PlatformApprovalRecordDto> list);
}