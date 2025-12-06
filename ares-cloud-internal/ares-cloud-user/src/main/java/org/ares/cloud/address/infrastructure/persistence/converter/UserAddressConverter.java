package org.ares.cloud.address.infrastructure.persistence.converter;

import org.ares.cloud.address.domain.model.aggregate.UserAddress;
import org.ares.cloud.address.domain.model.valueobject.Address;
import org.ares.cloud.address.infrastructure.persistence.entity.UserAddressEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * 用户地址实体转换器
 */
public interface UserAddressConverter {
    /**
     * 转换为实体
     */
    UserAddress toDomain(UserAddressEntity entity);
    /****
     * 转换为实体
     */
    UserAddressEntity toEntity(UserAddress domain);

    /**
     * 更新实体
     */
    void updateEntity(UserAddress domain, UserAddressEntity entity);
}