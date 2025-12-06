package org.ares.cloud.merchantInfo.convert;

import org.ares.cloud.merchantInfo.entity.MerchantSubSpecificationEntity;
import org.ares.cloud.merchantInfo.dto.MerchantSubSpecificationDto;
import org.ares.cloud.common.dto.TenantDto;
import org.ares.cloud.database.entity.TenantEntity;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 子规格主数据 转换器
* @version 1.0.0
* @date 2025-03-18
*/
@Mapper(componentModel = "spring")
public interface MerchantSubSpecificationConvert {
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
    @Mapping(target = "subName", source = "subName")
    @Mapping(target = "subNum", source = "subNum")
    @Mapping(target = "subPrice", source = "subPrice")
    @Mapping(target = "subPicture", source = "subPicture")
    @Mapping(target = "specificationId", source = "specificationId")
    MerchantSubSpecificationDto toDto(MerchantSubSpecificationEntity entity);

    /**
    * 将多个实体转换为数据传输对象。
    *
    * @param list 实体列表。
    * @return 数据传输对象列表。
    */
    List< MerchantSubSpecificationDto> listToDto(List<MerchantSubSpecificationEntity> list);

    /**
    * 将数据传输对象转换为实体。
    *
    * @param dto 数据传输对象。
    * @return 实体。
    */
    @InheritConfiguration(name = "toBaseEntity")
    @Mapping(target = "subName", source = "subName")
    @Mapping(target = "subNum", source = "subNum")
    @Mapping(target = "subPrice", source = "subPrice")
    @Mapping(target = "subPicture", source = "subPicture")
    @Mapping(target = "specificationId", source = "specificationId")
    MerchantSubSpecificationEntity toEntity( MerchantSubSpecificationDto dto);

    /**
    * 将多个数据传输对象转换为实体。
    *
    * @param list 数据传输对象列表。
    * @return 实体列表。
    */
    List<MerchantSubSpecificationEntity> listToEntities(List< MerchantSubSpecificationDto> list);
}