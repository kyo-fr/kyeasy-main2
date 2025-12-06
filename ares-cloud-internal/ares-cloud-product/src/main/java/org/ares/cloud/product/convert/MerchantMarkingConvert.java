package org.ares.cloud.product.convert;

import org.ares.cloud.common.dto.TenantDto;
import org.ares.cloud.database.entity.TenantEntity;
import org.ares.cloud.product.dto.MerchantMarkingDto;
import org.ares.cloud.product.entity.MerchantMarkingEntity;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 标注主数据 转换器
* @version 1.0.0
* @date 2025-03-19
*/
@Mapper(componentModel = "spring")
public interface MerchantMarkingConvert {
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
    @Mapping(target = "name", source = "name")
    MerchantMarkingDto toDto(MerchantMarkingEntity entity);

    /**
    * 将多个实体转换为数据传输对象。
    *
    * @param list 实体列表。
    * @return 数据传输对象列表。
    */
    List< MerchantMarkingDto> listToDto(List<MerchantMarkingEntity> list);

    /**
    * 将数据传输对象转换为实体。
    *
    * @param dto 数据传输对象。
    * @return 实体。
    */
    @InheritConfiguration(name = "toBaseEntity")
    @Mapping(target = "name", source = "name")
    MerchantMarkingEntity toEntity( MerchantMarkingDto dto);

    /**
    * 将多个数据传输对象转换为实体。
    *
    * @param list 数据传输对象列表。
    * @return 实体列表。
    */
    List<MerchantMarkingEntity> listToEntities(List< MerchantMarkingDto> list);
}