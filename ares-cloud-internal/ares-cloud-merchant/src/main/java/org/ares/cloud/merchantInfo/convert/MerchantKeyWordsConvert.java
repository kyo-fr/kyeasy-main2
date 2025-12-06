package org.ares.cloud.merchantInfo.convert;

import org.ares.cloud.common.dto.TenantDto;
import org.ares.cloud.database.entity.TenantEntity;
import org.ares.cloud.merchantInfo.entity.MerchantKeyWordsEntity;
import org.ares.cloud.merchantInfo.dto.MerchantKeyWordsDto;
import org.ares.cloud.common.dto.BaseDto;
import org.ares.cloud.database.entity.BaseEntity;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 商户关键字 转换器
* @version 1.0.0
* @date 2024-10-11
*/
@Mapper(componentModel = "spring")
public interface MerchantKeyWordsConvert {
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
    @Mapping(target = "keyName", source = "keyName")
//    @Mapping(target = "keyId", source = "keyId")
    @Mapping(target = "status", source = "status")
    MerchantKeyWordsDto toDto(MerchantKeyWordsEntity entity);

    /**
     * 将多个实体转换为数据传输对象。
     *
     * @param list 实体列表。
     * @return 数据传输对象列表。
     */
    List< MerchantKeyWordsDto> listToDto(List<MerchantKeyWordsEntity> list);

    /**
     * 将数据传输对象转换为实体。
     *
     * @param dto 数据传输对象。
     * @return 实体。
     */
    @InheritConfiguration(name = "toBaseEntity")
    @Mapping(target = "keyName", source = "keyName")
//    @Mapping(target = "keyId", source = "keyId")
    @Mapping(target = "status", source = "status")
    MerchantKeyWordsEntity toEntity( MerchantKeyWordsDto dto);

    /**
     * 将多个数据传输对象转换为实体。
     *
     * @param list 数据传输对象列表。
     * @return 实体列表。
     */
    List<MerchantKeyWordsEntity> listToEntities(List< MerchantKeyWordsDto> list);
}