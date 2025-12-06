package org.ares.cloud.merchantInfo.convert;

import org.ares.cloud.merchantInfo.entity.MerchantDistributionEntity;
import org.ares.cloud.merchantInfo.dto.MerchantDistributionDto;
import org.ares.cloud.common.dto.TenantDto;
import org.ares.cloud.database.entity.TenantEntity;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 商户配送设置 转换器
* @version 1.0.0
* @date 2024-11-05
*/
@Mapper(componentModel = "spring")
public interface MerchantDistributionConvert {
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
    @Mapping(target = "perKilometer", source = "perKilometer")
    @Mapping(target = "excessFees", source = "excessFees")
    @Mapping(target = "isThirdParties", source = "isThirdParties")
    @Mapping(target = "addressType", source = "addressType")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "minimumDeliveryAmount", source = "minimumDeliveryAmount")
    @Mapping(target = "beyondKilometerNotDelivered", source = "beyondKilometerNotDelivered")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "stage", source = "stage")
    @Mapping(target = "globalThirdParties", source = "globalThirdParties")
    @Mapping(target = "kilometersExceeded", source = "kilometersExceeded")
    MerchantDistributionDto toDto(MerchantDistributionEntity entity);

    /**
    * 将多个实体转换为数据传输对象。
    *
    * @param list 实体列表。
    * @return 数据传输对象列表。
    */
    List< MerchantDistributionDto> listToDto(List<MerchantDistributionEntity> list);

    /**
    * 将数据传输对象转换为实体。
    *
    * @param dto 数据传输对象。
    * @return 实体。
    */
    @InheritConfiguration(name = "toBaseEntity")
    @Mapping(target = "perKilometer", source = "perKilometer")
    @Mapping(target = "excessFees", source = "excessFees")
    @Mapping(target = "isThirdParties", source = "isThirdParties")
    @Mapping(target = "addressType", source = "addressType")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "minimumDeliveryAmount", source = "minimumDeliveryAmount")
    @Mapping(target = "beyondKilometerNotDelivered", source = "beyondKilometerNotDelivered")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "stage", source = "stage")
    @Mapping(target = "globalThirdParties", source = "globalThirdParties")
    @Mapping(target = "kilometersExceeded", source = "kilometersExceeded")
    MerchantDistributionEntity toEntity( MerchantDistributionDto dto);

    /**
    * 将多个数据传输对象转换为实体。
    *
    * @param list 数据传输对象列表。
    * @return 实体列表。
    */
    List<MerchantDistributionEntity> listToEntities(List< MerchantDistributionDto> list);
}