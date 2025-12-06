package org.ares.cloud.merchantInfo.convert;

import org.ares.cloud.common.dto.TenantDto;
import org.ares.cloud.database.entity.TenantEntity;
import org.ares.cloud.merchantInfo.entity.MerchantSocializeEntity;
import org.ares.cloud.merchantInfo.dto.MerchantSocializeDto;
import org.ares.cloud.common.dto.BaseDto;
import org.ares.cloud.database.entity.BaseEntity;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 商户社交 转换器
* @version 1.0.0
* @date 2024-10-09
*/
@Mapper(componentModel = "spring")
public interface MerchantSocializeConvert {
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
    @Mapping(target = "wechat", source = "wechat")
    @Mapping(target = "whatsApp", source = "whatsApp")
    @Mapping(target = "tiktok", source = "tiktok")
    @Mapping(target = "youtube", source = "youtube")
    @Mapping(target = "faceBook", source = "faceBook")
    @Mapping(target = "douYin", source = "douYin")
    @Mapping(target = "redBook", source = "redBook")
    @Mapping(target = "twitter", source = "twitter")
    MerchantSocializeDto toDto(MerchantSocializeEntity entity);

    /**
    * 将多个实体转换为数据传输对象。
    *
    * @param list 实体列表。
    * @return 数据传输对象列表。
    */
    List< MerchantSocializeDto> listToDto(List<MerchantSocializeEntity> list);

    /**
    * 将数据传输对象转换为实体。
    *
    * @param dto 数据传输对象。
    * @return 实体。
    */
    @InheritConfiguration(name = "toBaseEntity")
    @Mapping(target = "wechat", source = "wechat")
    @Mapping(target = "whatsApp", source = "whatsApp")
    @Mapping(target = "tiktok", source = "tiktok")
    @Mapping(target = "youtube", source = "youtube")
    @Mapping(target = "faceBook", source = "faceBook")
    @Mapping(target = "douYin", source = "douYin")
    @Mapping(target = "redBook", source = "redBook")
    @Mapping(target = "twitter", source = "twitter")
    MerchantSocializeEntity toEntity( MerchantSocializeDto dto);

    /**
    * 将多个数据传输对象转换为实体。
    *
    * @param list 数据传输对象列表。
    * @return 实体列表。
    */
    List<MerchantSocializeEntity> listToEntities(List< MerchantSocializeDto> list);
}