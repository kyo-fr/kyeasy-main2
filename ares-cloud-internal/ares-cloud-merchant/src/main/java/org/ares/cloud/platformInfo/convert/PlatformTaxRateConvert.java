package org.ares.cloud.platformInfo.convert;

import org.ares.cloud.platformInfo.entity.PlatformTaxRateEntity;
import org.ares.cloud.platformInfo.dto.PlatformTaxRateDto;
import org.ares.cloud.common.dto.BaseDto;
import org.ares.cloud.database.entity.BaseEntity;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 税率 转换器
* @version 1.0.0
* @date 2024-10-15
*/
@Mapper(componentModel = "spring")
public interface PlatformTaxRateConvert {
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
    @Mapping(target = "type", source = "type")
    @Mapping(target = "taxRate", source = "taxRate")
    @Mapping(target = "tenantId", source = "tenantId")
    PlatformTaxRateDto toDto(PlatformTaxRateEntity entity);

    /**
    * 将多个实体转换为数据传输对象。
    *
    * @param list 实体列表。
    * @return 数据传输对象列表。
    */
    List< PlatformTaxRateDto> listToDto(List<PlatformTaxRateEntity> list);

    /**
    * 将数据传输对象转换为实体。
    *
    * @param dto 数据传输对象。
    * @return 实体。
    */
    @InheritConfiguration(name = "toBaseEntity")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "taxRate", source = "taxRate")
    @Mapping(target = "tenantId", source = "tenantId")
    PlatformTaxRateEntity toEntity( PlatformTaxRateDto dto);

    /**
    * 将多个数据传输对象转换为实体。
    *
    * @param list 数据传输对象列表。
    * @return 实体列表。
    */
    List<PlatformTaxRateEntity> listToEntities(List< PlatformTaxRateDto> list);
}