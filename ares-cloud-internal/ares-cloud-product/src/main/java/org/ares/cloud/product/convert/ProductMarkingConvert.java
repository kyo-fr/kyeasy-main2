package org.ares.cloud.product.convert;

import org.ares.cloud.common.dto.BaseDto;
import org.ares.cloud.database.entity.BaseEntity;
import org.ares.cloud.product.entity.ProductMarkingEntity;
import org.ares.cloud.product.dto.ProductMarkingDto;
import org.ares.cloud.common.dto.TenantDto;
import org.ares.cloud.database.entity.TenantEntity;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 商品标注 转换器
* @version 1.0.0
* @date 2024-11-08
*/
@Mapper(componentModel = "spring")
public interface ProductMarkingConvert {
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
    @Mapping(target = "productId", source = "productId")
    @Mapping(target = "markingId", source = "markingId")
    ProductMarkingDto toDto(ProductMarkingEntity entity);

    /**
    * 将多个实体转换为数据传输对象。
    *
    * @param list 实体列表。
    * @return 数据传输对象列表。
    */
    List< ProductMarkingDto> listToDto(List<ProductMarkingEntity> list);

    /**
    * 将数据传输对象转换为实体。
    *
    * @param dto 数据传输对象。
    * @return 实体。
    */
    @InheritConfiguration(name = "toBaseEntity")
    @Mapping(target = "productId", source = "productId")
    @Mapping(target = "markingId", source = "markingId")
    ProductMarkingEntity toEntity( ProductMarkingDto dto);

    /**
    * 将多个数据传输对象转换为实体。
    *
    * @param list 数据传输对象列表。
    * @return 实体列表。
    */
    List<ProductMarkingEntity> listToEntities(List< ProductMarkingDto> list);
}