package org.ares.cloud.product.convert;

import org.ares.cloud.common.dto.TenantDto;
import org.ares.cloud.database.entity.TenantEntity;
import org.ares.cloud.product.dto.MerchantSpecificationDto;
import org.ares.cloud.product.entity.MerchantSpecificationEntity;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 主规格主数据 转换器
* @version 1.0.0
* @date 2025-03-18
*/
@Mapper(componentModel = "spring")
public interface MerchantSpecificationConvert {
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
    @Mapping(target = "type", source = "type")
    @Mapping(target = "name", source = "name")
    MerchantSpecificationDto toDto(MerchantSpecificationEntity entity);

    /**
    * 将多个实体转换为数据传输对象。
    *
    * @param list 实体列表。
    * @return 数据传输对象列表。
    */
    List< MerchantSpecificationDto> listToDto(List<MerchantSpecificationEntity> list);

    /**
    * 将数据传输对象转换为实体。
    *
    * @param dto 数据传输对象。
    * @return 实体。
    */
    @InheritConfiguration(name = "toBaseEntity")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "name", source = "name")
    MerchantSpecificationEntity toEntity( MerchantSpecificationDto dto);

    /**
    * 将多个数据传输对象转换为实体。
    *
    * @param list 数据传输对象列表。
    * @return 实体列表。
    */
    List<MerchantSpecificationEntity> listToEntities(List< MerchantSpecificationDto> list);
}