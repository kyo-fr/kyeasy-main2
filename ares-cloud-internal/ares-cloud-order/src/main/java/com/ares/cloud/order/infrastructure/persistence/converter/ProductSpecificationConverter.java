package com.ares.cloud.order.infrastructure.persistence.converter;

import com.ares.cloud.order.application.dto.ProductSpecificationDTO;
import com.ares.cloud.order.domain.model.valueobject.ProductSpecification;
import com.ares.cloud.order.infrastructure.persistence.entity.ProductSpecificationEntity;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品规格转换器
 */
@Mapper(componentModel = "spring")
public interface ProductSpecificationConverter extends MoneyConverter {

    /**
     * 批量转换Entity列表
     */
    default List<ProductSpecificationEntity> toEntityList(List<ProductSpecification> domains, String orderItemId) {
        if (domains == null) {
            return null;
        }
        List<ProductSpecificationEntity> result = new ArrayList<>();
        for (ProductSpecification domain : domains) {
            result.add(toEntity(domain, orderItemId));
        }
        return result;
    }

    /**
     * 批量转换DTO列表
     */
    default List<ProductSpecificationDTO> toDTOList(List<ProductSpecificationEntity> entities) {
        if (entities == null) {
            return null;
        }
        List<ProductSpecificationDTO> result = new ArrayList<>();
        for (ProductSpecificationEntity entity : entities) {
            result.add(toDTO(entity));
        }
        return result;
    }
    
    /**
     * 批量转换领域对象列表
     */
    default List<ProductSpecification> toDomainList(List<ProductSpecificationEntity> entities) {
        if (entities == null) {
            return null;
        }
        List<ProductSpecification> result = new ArrayList<>();
        for (ProductSpecificationEntity entity : entities) {
            result.add(toDomain(entity));
        }
        return result;
    }
    /**
     * Entity转领域对象
     */
    default ProductSpecification toDomain(ProductSpecificationEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return ProductSpecification.builder()
            .id(entity.getId())
            .productSpecId(entity.getProductSpecId())
            .name(entity.getName())
            .value(entity.getValue())
            .price(createMoney(entity.getPrice(), entity.getCurrency(), entity.getCurrencyScale()))
            .build();
    }
    /**
     * 领域对象转Entity
     */
    default ProductSpecificationEntity toEntity(ProductSpecification domain, String orderItemId) {
        if (domain == null) {
            return null;
        }

        ProductSpecificationEntity entity = new ProductSpecificationEntity();
        entity.setId(domain.getId());
        entity.setProductSpecId(domain.getProductSpecId());
        entity.setOrderItemId(orderItemId);
        entity.setName(domain.getName());
        entity.setValue(domain.getValue());
        entity.setPrice(getMoneyAmount(domain.getPrice()));
        entity.setCurrency(getMoneyCurrency(domain.getPrice()));
        entity.setCurrencyScale(getMoneyScale(domain.getPrice()));
        return entity;
    }

    /**
     * Entity转DTO
     */
    default ProductSpecificationDTO toDTO(ProductSpecificationEntity entity) {
        if (entity == null) {
            return null;
        }

        return ProductSpecificationDTO.builder()
            .id(entity.getId())
            .productSpecId(entity.getProductSpecId())
            .name(entity.getName())
            .value(entity.getValue())
            .price(toDecimal(entity.getPrice(), entity.getCurrency(), entity.getCurrencyScale()))
            .build();
    }
}