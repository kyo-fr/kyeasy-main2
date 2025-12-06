package org.ares.cloud.merchantInfo.convert;

import org.ares.cloud.merchantInfo.entity.MerchantInfoEntity;
import org.ares.cloud.merchantInfo.dto.MerchantInfoDto;
import org.ares.cloud.common.dto.BaseDto;
import org.ares.cloud.database.entity.BaseEntity;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 商户信息 转换器
* @version 1.0.0
* @date 2024-10-08
*/
@Mapper(componentModel = "spring")
public interface MerchantInfoConvert {
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
//    @Mapping(target = "contractId", source = "contractId")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "registerPhone", source = "registerPhone")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "domainName", source = "domainName")
    @Mapping(target = "taxNum", source = "taxNum")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "enterpriseEmail", source = "enterpriseEmail")
    @Mapping(target = "iBan", source = "iBan")
    @Mapping(target = "bic", source = "bic")
    @Mapping(target = "countryCode", source = "countryCode")
    @Mapping(target = "language", source = "language")
    @Mapping(target = "currency", source = "currency")
    @Mapping(target = "pageDisplay", source = "pageDisplay")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "isOpenGift", source = "isOpenGift")
    @Mapping(target = "overdueDate", source = "overdueDate")
    @Mapping(target = "isUpdated", source = "isUpdated")
    @Mapping(target = "siren", source = "siren")
    @Mapping(target = "naf", source = "naf")
    @Mapping(target = "country", source = "country")
    @Mapping(target = "city", source = "city")
    @Mapping(target = "postalCode", source = "postalCode")

    MerchantInfoDto toDto(MerchantInfoEntity entity);

    /**
    * 将多个实体转换为数据传输对象。
    *
    * @param list 实体列表。
    * @return 数据传输对象列表。
    */
    List< MerchantInfoDto> listToDto(List<MerchantInfoEntity> list);

    /**
    * 将数据传输对象转换为实体。
    *
    * @param dto 数据传输对象。
    * @return 实体。
    */
    @InheritConfiguration(name = "toBaseEntity")
//    @Mapping(target = "contractId", source = "contractId")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "registerPhone", source = "registerPhone")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "domainName", source = "domainName")
    @Mapping(target = "taxNum", source = "taxNum")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "enterpriseEmail", source = "enterpriseEmail")
    @Mapping(target = "iBan", source = "iBan")
    @Mapping(target = "bic", source = "bic")
    @Mapping(target = "countryCode", source = "countryCode")
    @Mapping(target = "language", source = "language")
    @Mapping(target = "currency", source = "currency")
    @Mapping(target = "pageDisplay", source = "pageDisplay")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "isOpenGift", source = "isOpenGift")
//    @Mapping(target = "num", source = "num")
    @Mapping(target = "overdueDate", source = "overdueDate")
    @Mapping(target = "isUpdated", source = "isUpdated")
    @Mapping(target = "siren", source = "siren")
    @Mapping(target = "naf", source = "naf")
    @Mapping(target = "country", source = "country")
    @Mapping(target = "city", source = "city")
    @Mapping(target = "postalCode", source = "postalCode")

    MerchantInfoEntity toEntity( MerchantInfoDto dto);

    /**
    * 将多个数据传输对象转换为实体。
    *
    * @param list 数据传输对象列表。
    * @return 实体列表。
    */
    List<MerchantInfoEntity> listToEntities(List< MerchantInfoDto> list);
}