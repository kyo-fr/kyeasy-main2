package org.ares.cloud.product.convert;

import org.ares.cloud.product.entity.ProductBaseInfoEntity;
import org.ares.cloud.product.dto.ProductBaseInfoDto;
import org.ares.cloud.common.dto.TenantDto;
import org.ares.cloud.database.entity.TenantEntity;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 商品基础信息 转换器
* @version 1.0.0
* @date 2024-11-06
*/
@Mapper(componentModel = "spring")
public interface ProductBaseInfoConvert {
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
    @Mapping(target = "price", source = "price")
    @Mapping(target = "inventory", source = "inventory")
    @Mapping(target = "levelOneId", source = "levelOneId")
    @Mapping(target = "levelTwoId", source = "levelTwoId")
    @Mapping(target = "levelThreeId", source = "levelThreeId")
    @Mapping(target = "taxId", source = "taxId")
    @Mapping(target = "taxRate", source = "taxRate")
    @Mapping(target = "briefly", source = "briefly")
    @Mapping(target = "pictureUrl", source = "pictureUrl")
    @Mapping(target = "videoUrl", source = "videoUrl")
    @Mapping(target = "weight", source = "weight")
    @Mapping(target = "length", source = "length")
    @Mapping(target = "isDistribution", source = "isDistribution")
    @Mapping(target = "isServe", source = "isServe")
    @Mapping(target = "deliveryFee", source = "deliveryFee")
    @Mapping(target = "preServeFee", source = "preServeFee")
    @Mapping(target = "serveFee", source = "serveFee")
    @Mapping(target = "width", source = "width")
    @Mapping(target = "height", source = "height")
    @Mapping(target = "perDeliveryFee", source = "perDeliveryFee")
    @Mapping(target = "isEnable", source = "isEnable")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "warehouseId", source = "warehouseId")
    @Mapping(target = "warehouseSeatId", source = "warehouseSeatId")
    @Mapping(target = "produceTime", source = "produceTime")
    @Mapping(target = "overdueTime", source = "overdueTime")
    ProductBaseInfoDto toDto(ProductBaseInfoEntity entity);

    /**
    * 将多个实体转换为数据传输对象。
    *
    * @param list 实体列表。
    * @return 数据传输对象列表。
    */
    List< ProductBaseInfoDto> listToDto(List<ProductBaseInfoEntity> list);

    /**
    * 将数据传输对象转换为实体。
    *
    * @param dto 数据传输对象。
    * @return 实体。
    */
    @InheritConfiguration(name = "toBaseEntity")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "inventory", source = "inventory")
    @Mapping(target = "levelOneId", source = "levelOneId")
    @Mapping(target = "levelTwoId", source = "levelTwoId")
    @Mapping(target = "levelThreeId", source = "levelThreeId")
    @Mapping(target = "taxId", source = "taxId")
    @Mapping(target = "taxRate", source = "taxRate")
    @Mapping(target = "briefly", source = "briefly")
    @Mapping(target = "pictureUrl", source = "pictureUrl")
    @Mapping(target = "videoUrl", source = "videoUrl")
    @Mapping(target = "weight", source = "weight")
    @Mapping(target = "length", source = "length")
    @Mapping(target = "isDistribution", source = "isDistribution")
    @Mapping(target = "isServe", source = "isServe")
    @Mapping(target = "deliveryFee", source = "deliveryFee")
    @Mapping(target = "preServeFee", source = "preServeFee")
    @Mapping(target = "serveFee", source = "serveFee")
    @Mapping(target = "width", source = "width")
    @Mapping(target = "height", source = "height")
    @Mapping(target = "perDeliveryFee", source = "perDeliveryFee")
    @Mapping(target = "isEnable", source = "isEnable")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "warehouseId", source = "warehouseId")
    @Mapping(target = "warehouseSeatId", source = "warehouseSeatId")
    @Mapping(target = "produceTime", source = "produceTime")
    @Mapping(target = "overdueTime", source = "overdueTime")
    ProductBaseInfoEntity toEntity( ProductBaseInfoDto dto);

    /**
    * 将多个数据传输对象转换为实体。
    *
    * @param list 数据传输对象列表。
    * @return 实体列表。
    */
    List<ProductBaseInfoEntity> listToEntities(List< ProductBaseInfoDto> list);
}