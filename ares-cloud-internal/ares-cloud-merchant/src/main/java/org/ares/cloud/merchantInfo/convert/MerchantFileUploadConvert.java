package org.ares.cloud.merchantInfo.convert;

import org.ares.cloud.merchantInfo.entity.MerchantFileUploadEntity;
import org.ares.cloud.merchantInfo.dto.MerchantFileUploadDto;
import org.ares.cloud.common.dto.TenantDto;
import org.ares.cloud.database.entity.TenantEntity;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 商户文件上传 转换器
* @version 1.0.0
* @date 2024-10-09
*/
@Mapper(componentModel = "spring")
public interface MerchantFileUploadConvert {
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
    @Mapping(target = "businessLicense", source = "businessLicense")
    @Mapping(target = "bankRib", source = "bankRib")
    @Mapping(target = "logo", source = "logo")
    MerchantFileUploadDto toDto(MerchantFileUploadEntity entity);

    /**
    * 将多个实体转换为数据传输对象。
    *
    * @param list 实体列表。
    * @return 数据传输对象列表。
    */
    List< MerchantFileUploadDto> listToDto(List<MerchantFileUploadEntity> list);

    /**
    * 将数据传输对象转换为实体。
    *
    * @param dto 数据传输对象。
    * @return 实体。
    */
    @InheritConfiguration(name = "toBaseEntity")
    @Mapping(target = "businessLicense", source = "businessLicense")
    @Mapping(target = "bankRib", source = "bankRib")
    @Mapping(target = "logo", source = "logo")
    MerchantFileUploadEntity toEntity( MerchantFileUploadDto dto);

    /**
    * 将多个数据传输对象转换为实体。
    *
    * @param list 数据传输对象列表。
    * @return 实体列表。
    */
    List<MerchantFileUploadEntity> listToEntities(List< MerchantFileUploadDto> list);
}