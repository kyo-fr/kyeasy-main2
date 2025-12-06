package org.ares.cloud.merchantInfo.convert;

import org.ares.cloud.common.dto.TenantDto;
import org.ares.cloud.database.entity.TenantEntity;
import org.ares.cloud.merchantInfo.entity.OpeningHoursEntity;
import org.ares.cloud.merchantInfo.dto.OpeningHoursDto;
import org.ares.cloud.common.dto.BaseDto;
import org.ares.cloud.database.entity.BaseEntity;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 商户营业时间 转换器
* @version 1.0.0
* @date 2024-10-08
*/
@Mapper(componentModel = "spring")
public interface OpeningHoursConvert {
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
    @Mapping(target = "weekDay", source = "weekDay")
    @Mapping(target = "openTime1", source = "openTime1")
    @Mapping(target = "closeTime1", source = "closeTime1")
    @Mapping(target = "timeType", source = "timeType")
    @Mapping(target = "isAmRest", source = "isAmRest")
    @Mapping(target = "isPmRest", source = "isPmRest")
    @Mapping(target = "tenantId", source = "tenantId")
    @Mapping(target = "openTime2", source = "openTime2")
    @Mapping(target = "closeTime2", source = "closeTime2")
    OpeningHoursDto toDto(OpeningHoursEntity entity);

    /**
    * 将多个实体转换为数据传输对象。
    *
    * @param list 实体列表。
    * @return 数据传输对象列表。
    */
    List< OpeningHoursDto> listToDto(List<OpeningHoursEntity> list);

    /**
    * 将数据传输对象转换为实体。
    *
    * @param dto 数据传输对象。
    * @return 实体。
    */
    @InheritConfiguration(name = "toBaseEntity")
    @Mapping(target = "weekDay", source = "weekDay")
    @Mapping(target = "openTime1", source = "openTime1")
    @Mapping(target = "closeTime1", source = "closeTime1")
    @Mapping(target = "timeType", source = "timeType")
    @Mapping(target = "isAmRest", source = "isAmRest")
    @Mapping(target = "isPmRest", source = "isPmRest")
    @Mapping(target = "tenantId", source = "tenantId")
    @Mapping(target = "openTime2", source = "openTime2")
    @Mapping(target = "closeTime2", source = "closeTime2")
    OpeningHoursEntity toEntity( OpeningHoursDto dto);

    /**
    * 将多个数据传输对象转换为实体。
    *
    * @param list 数据传输对象列表。
    * @return 实体列表。
    */
    List<OpeningHoursEntity> listToEntities(List< OpeningHoursDto> list);
}