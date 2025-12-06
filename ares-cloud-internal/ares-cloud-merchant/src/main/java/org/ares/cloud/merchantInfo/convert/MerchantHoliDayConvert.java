package org.ares.cloud.merchantInfo.convert;

import org.ares.cloud.merchantInfo.entity.MerchantHoliDayEntity;
import org.ares.cloud.merchantInfo.dto.MerchantHoliDayDto;
import org.ares.cloud.common.dto.TenantDto;
import org.ares.cloud.database.entity.TenantEntity;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 商户休假 转换器
* @version 1.0.0
* @date 2025-01-03
*/
@Mapper(componentModel = "spring")
public interface MerchantHoliDayConvert {
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
    @Mapping(target = "contents", source = "contents")
    MerchantHoliDayDto toDto(MerchantHoliDayEntity entity);

    /**
    * 将多个实体转换为数据传输对象。
    *
    * @param list 实体列表。
    * @return 数据传输对象列表。
    */
    List< MerchantHoliDayDto> listToDto(List<MerchantHoliDayEntity> list);

    /**
    * 将数据传输对象转换为实体。
    *
    * @param dto 数据传输对象。
    * @return 实体。
    */
    @InheritConfiguration(name = "toBaseEntity")
    @Mapping(target = "contents", source = "contents")
    MerchantHoliDayEntity toEntity( MerchantHoliDayDto dto);

    /**
    * 将多个数据传输对象转换为实体。
    *
    * @param list 数据传输对象列表。
    * @return 实体列表。
    */
    List<MerchantHoliDayEntity> listToEntities(List< MerchantHoliDayDto> list);
}