package org.ares.cloud.product.convert;

import org.ares.cloud.common.dto.BaseDto;
import org.ares.cloud.database.entity.BaseEntity;
import org.ares.cloud.product.entity.ProductListEntity;
import org.ares.cloud.product.dto.ProductListDto;
import org.ares.cloud.common.dto.TenantDto;
import org.ares.cloud.database.entity.TenantEntity;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 商品清单 转换器
* @version 1.0.0
* @date 2025-04-03
*/
@Mapper(componentModel = "spring")
public interface ProductListConvert {
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
    @Mapping(target = "subProductId", source = "subProductId")
    @Mapping(target = "productId", source = "productId")
    ProductListDto toDto(ProductListEntity entity);

    /**
    * 将多个实体转换为数据传输对象。
    *
    * @param list 实体列表。
    * @return 数据传输对象列表。
    */
    List< ProductListDto> listToDto(List<ProductListEntity> list);

    /**
    * 将数据传输对象转换为实体。
    *
    * @param dto 数据传输对象。
    * @return 实体。
    */
    @InheritConfiguration(name = "toBaseEntity")
    @Mapping(target = "subProductId", source = "subProductId")
    @Mapping(target = "productId", source = "productId")
    ProductListEntity toEntity( ProductListDto dto);

    /**
    * 将多个数据传输对象转换为实体。
    *
    * @param list 数据传输对象列表。
    * @return 实体列表。
    */
    List<ProductListEntity> listToEntities(List< ProductListDto> list);
}