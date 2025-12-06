package org.ares.cloud.product.convert;

import org.ares.cloud.product.entity.ProductTypeEntity;
import org.ares.cloud.product.dto.ProductTypeDto;
import org.ares.cloud.common.dto.TenantDto;
import org.ares.cloud.database.entity.TenantEntity;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 商品分类管理 转换器
* @version 1.0.0
* @date 2024-10-28
*/

@Mapper(componentModel = "spring")
public interface ProductTypeConvert {
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
    @Mapping(target = "picture", source = "picture")
    @Mapping(target = "parentId", source = "parentId")
    @Mapping(target = "levels", source = "levels")
    ProductTypeDto toDto(ProductTypeEntity entity);

    /**
     * 将多个实体转换为数据传输对象。
     *
     * @param list 实体列表。
     * @return 数据传输对象列表。
     */
    List< ProductTypeDto> listToDto(List<ProductTypeEntity> list);

    /**
     * 将数据传输对象转换为实体。
     *
     * @param dto 数据传输对象。
     * @return 实体。
     */
    @InheritConfiguration(name = "toBaseEntity")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "picture", source = "picture")
    @Mapping(target = "parentId", source = "parentId")
    @Mapping(target = "levels", source = "levels")
    ProductTypeEntity toEntity( ProductTypeDto dto);

    /**
     * 将多个数据传输对象转换为实体。
     *
     * @param list 数据传输对象列表。
     * @return 实体列表。
     */
    List<ProductTypeEntity> listToEntities(List< ProductTypeDto> list);
}