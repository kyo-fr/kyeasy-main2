package org.ares.cloud.product.convert;

import org.ares.cloud.common.dto.BaseDto;
import org.ares.cloud.database.entity.BaseEntity;
import org.ares.cloud.product.entity.ProductSubSpecificationEntity;
import org.ares.cloud.product.dto.ProductSubSpecificationDto;
import org.ares.cloud.common.dto.TenantDto;
import org.ares.cloud.database.entity.TenantEntity;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 商品子规格 转换器
* @version 1.0.0
* @date 2025-03-24
*/
@Mapper(componentModel = "spring")
public interface ProductSubSpecificationConvert {
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
    @Mapping(target = "productId", source = "productId")
    @Mapping(target = "subSpecificationId", source = "subSpecificationId")
    @Mapping(target = "specificationId", source = "specificationId")
    ProductSubSpecificationDto toDto(ProductSubSpecificationEntity entity);

    /**
    * 将多个实体转换为数据传输对象。
    *
    * @param list 实体列表。
    * @return 数据传输对象列表。
    */
    List< ProductSubSpecificationDto> listToDto(List<ProductSubSpecificationEntity> list);

    /**
    * 将数据传输对象转换为实体。
    *
    * @param dto 数据传输对象。
    * @return 实体。
    */
    @InheritConfiguration(name = "toBaseEntity")
    @Mapping(target = "productId", source = "productId")
    @Mapping(target = "subSpecificationId", source = "subSpecificationId")
    @Mapping(target = "specificationId", source = "specificationId")
    ProductSubSpecificationEntity toEntity( ProductSubSpecificationDto dto);

    /**
    * 将多个数据传输对象转换为实体。
    *
    * @param list 数据传输对象列表。
    * @return 实体列表。
    */
    List<ProductSubSpecificationEntity> listToEntities(List< ProductSubSpecificationDto> list);
}