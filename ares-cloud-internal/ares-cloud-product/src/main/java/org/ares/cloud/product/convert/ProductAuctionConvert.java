package org.ares.cloud.product.convert;

import org.ares.cloud.product.entity.ProductAuctionEntity;
import org.ares.cloud.product.dto.ProductAuctionDto;
import org.ares.cloud.common.dto.TenantDto;
import org.ares.cloud.database.entity.TenantEntity;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 拍卖商品 转换器
* @version 1.0.0
* @date 2024-11-08
*/
@Mapper(componentModel = "spring")
public interface ProductAuctionConvert {
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
    @Mapping(target = "fares", source = "fares")
    @Mapping(target = "fixedPrice", source = "fixedPrice")
    @Mapping(target = "startTime", source = "startTime")
    @Mapping(target = "endTime", source = "endTime")
    @Mapping(target = "productId", source = "productId")
    ProductAuctionDto toDto(ProductAuctionEntity entity);

    /**
    * 将多个实体转换为数据传输对象。
    *
    * @param list 实体列表。
    * @return 数据传输对象列表。
    */
    List< ProductAuctionDto> listToDto(List<ProductAuctionEntity> list);

    /**
    * 将数据传输对象转换为实体。
    *
    * @param dto 数据传输对象。
    * @return 实体。
    */
    @InheritConfiguration(name = "toBaseEntity")
    @Mapping(target = "fares", source = "fares")
    @Mapping(target = "fixedPrice", source = "fixedPrice")
    @Mapping(target = "startTime", source = "startTime")
    @Mapping(target = "endTime", source = "endTime")
    @Mapping(target = "productId", source = "productId")
    ProductAuctionEntity toEntity( ProductAuctionDto dto);

    /**
    * 将多个数据传输对象转换为实体。
    *
    * @param list 数据传输对象列表。
    * @return 实体列表。
    */
    List<ProductAuctionEntity> listToEntities(List< ProductAuctionDto> list);
}