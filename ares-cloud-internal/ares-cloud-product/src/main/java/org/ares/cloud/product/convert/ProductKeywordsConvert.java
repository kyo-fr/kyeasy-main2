package org.ares.cloud.product.convert;

import org.ares.cloud.product.entity.ProductKeywordsEntity;
import org.ares.cloud.product.dto.ProductKeywordsDto;
import org.ares.cloud.common.dto.BaseDto;
import org.ares.cloud.database.entity.BaseEntity;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 商品关键字 转换器
* @version 1.0.0
* @date 2025-03-18
*/
@Mapper(componentModel = "spring")
public interface ProductKeywordsConvert {
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
    @Mapping(target = "keyWordsId", source = "keyWordsId")
    ProductKeywordsDto toDto(ProductKeywordsEntity entity);

    /**
    * 将多个实体转换为数据传输对象。
    *
    * @param list 实体列表。
    * @return 数据传输对象列表。
    */
    List< ProductKeywordsDto> listToDto(List<ProductKeywordsEntity> list);

    /**
    * 将数据传输对象转换为实体。
    *
    * @param dto 数据传输对象。
    * @return 实体。
    */
    @InheritConfiguration(name = "toBaseEntity")
    @Mapping(target = "productId", source = "productId")
    @Mapping(target = "keyWordsId", source = "keyWordsId")
    ProductKeywordsEntity toEntity( ProductKeywordsDto dto);

    /**
    * 将多个数据传输对象转换为实体。
    *
    * @param list 数据传输对象列表。
    * @return 实体列表。
    */
    List<ProductKeywordsEntity> listToEntities(List< ProductKeywordsDto> list);
}