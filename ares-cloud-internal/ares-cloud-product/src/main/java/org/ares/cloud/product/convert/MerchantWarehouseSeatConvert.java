package org.ares.cloud.product.convert;

import org.ares.cloud.product.entity.MerchantWarehouseSeatEntity;
import org.ares.cloud.product.dto.MerchantWarehouseSeatDto;
import org.ares.cloud.common.dto.TenantDto;
import org.ares.cloud.database.entity.TenantEntity;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 商户仓库位子主数据 转换器
* @version 1.0.0
* @date 2025-03-22
*/
@Mapper(componentModel = "spring")
public interface MerchantWarehouseSeatConvert {
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
    @Mapping(target = "warehouseId", source = "warehouseId")
    @Mapping(target = "seatName", source = "seatName")
    MerchantWarehouseSeatDto toDto(MerchantWarehouseSeatEntity entity);

    /**
    * 将多个实体转换为数据传输对象。
    *
    * @param list 实体列表。
    * @return 数据传输对象列表。
    */
    List<MerchantWarehouseSeatDto> listToDto(List<MerchantWarehouseSeatEntity> list);

    /**
    * 将数据传输对象转换为实体。
    *
    * @param dto 数据传输对象。
    * @return 实体。
    */
    @InheritConfiguration(name = "toBaseEntity")
    @Mapping(target = "warehouseId", source = "warehouseId")
    @Mapping(target = "seatName", source = "seatName")
    MerchantWarehouseSeatEntity toEntity(MerchantWarehouseSeatDto dto);

    /**
    * 将多个数据传输对象转换为实体。
    *
    * @param list 数据传输对象列表。
    * @return 实体列表。
    */
    List<MerchantWarehouseSeatEntity> listToEntities(List<MerchantWarehouseSeatDto> list);
}