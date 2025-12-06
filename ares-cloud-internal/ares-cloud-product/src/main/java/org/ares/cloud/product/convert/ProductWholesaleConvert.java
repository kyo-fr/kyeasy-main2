package org.ares.cloud.product.convert;

import org.ares.cloud.product.entity.ProductWholesaleEntity;
import org.ares.cloud.product.dto.ProductWholesaleDto;
import org.ares.cloud.common.dto.TenantDto;
import org.ares.cloud.database.entity.TenantEntity;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 批发商品 转换器
* @version 1.0.0
* @date 2024-11-08
*/
@Mapper(componentModel = "spring")
public interface ProductWholesaleConvert {
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
    @Mapping(target = "numOne", source = "numOne")
    @Mapping(target = "numTwo", source = "numTwo")
    @Mapping(target = "priceThree", source = "priceThree")
    @Mapping(target = "numThree", source = "numThree")
    @Mapping(target = "productId", source = "productId")
    @Mapping(target = "priceOne", source = "priceOne")
    @Mapping(target = "priceTwo", source = "priceTwo")
    ProductWholesaleDto toDto(ProductWholesaleEntity entity);

    /**
    * 将多个实体转换为数据传输对象。
    *
    * @param list 实体列表。
    * @return 数据传输对象列表。
    */
    List< ProductWholesaleDto> listToDto(List<ProductWholesaleEntity> list);

    /**
    * 将数据传输对象转换为实体。
    *
    * @param dto 数据传输对象。
    * @return 实体。
    */
    @InheritConfiguration(name = "toBaseEntity")
    @Mapping(target = "numOne", source = "numOne")
    @Mapping(target = "numTwo", source = "numTwo")
    @Mapping(target = "priceThree", source = "priceThree")
    @Mapping(target = "numThree", source = "numThree")
    @Mapping(target = "productId", source = "productId")
    @Mapping(target = "priceOne", source = "priceOne")
    @Mapping(target = "priceTwo", source = "priceTwo")
    ProductWholesaleEntity toEntity( ProductWholesaleDto dto);

    /**
    * 将多个数据传输对象转换为实体。
    *
    * @param list 数据传输对象列表。
    * @return 实体列表。
    */
    List<ProductWholesaleEntity> listToEntities(List< ProductWholesaleDto> list);
}