package org.ares.cloud.address.infrastructure.persistence.converter;

import org.ares.cloud.address.domain.model.aggregate.UserAddress;
import org.ares.cloud.address.domain.model.valueobject.Address;
import org.ares.cloud.address.infrastructure.persistence.entity.UserAddressEntity;
import org.springframework.stereotype.Component;

/**
 * 用户地址实体转换器实现类
 */
@Component
public class UserAddressConverterImpl implements UserAddressConverter {

    /**
     * 实体转聚合根
     */
    @Override
    public UserAddress toDomain(UserAddressEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return UserAddress.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .address(toAddress(entity))
                .createTime(entity.getCreateTime())
                .updateTime(entity.getUpdateTime())
                .deleted(entity.getDeleted() != null && entity.getDeleted() == 1)
                .build();
    }

    /**
     * 聚合根转实体
     */
    @Override
    public UserAddressEntity toEntity(UserAddress domain) {
        if (domain == null) {
            return null;
        }
        
        UserAddressEntity entity = new UserAddressEntity();
        entity.setId(domain.getId());
        entity.setUserId(domain.getUserId());
        
        Address address = domain.getAddress();
        if (address != null) {
            entity.setType(address.getType());
            entity.setName(address.getName());
            entity.setPhone(address.getPhone());
            entity.setCompanyName(address.getCompanyName());
            entity.setBusinessLicenseNumber(address.getBusinessLicenseNumber());
            entity.setCountry(address.getCountry());
            entity.setCity(address.getCity());
            entity.setPostalCode(address.getPostalCode());
            entity.setDetail(address.getDetail());
            entity.setLatitude(address.getLatitude());
            entity.setLongitude(address.getLongitude());
            entity.setIsDefault(address.isDefault());
            entity.setIsEnabled(address.isEnabled());
            entity.setIsInvoiceAddress(address.isInvoiceAddress());
        }
        
        entity.setCreateTime(domain.getCreateTime());
        entity.setUpdateTime(domain.getUpdateTime());
        entity.setVersion(1);
        entity.setDeleted(domain.isDeleted() ? 1 : 0);
        
        return entity;
    }

    /**
     * 实体转地址值对象
     */
    public Address toAddress(UserAddressEntity entity) {
        if (entity == null) {
            return null;
        }
        return Address.builder()
                .type(entity.getType())
                .name(entity.getName())
                .phone(entity.getPhone())
                .companyName(entity.getCompanyName())
                .businessLicenseNumber(entity.getBusinessLicenseNumber())
                .country(entity.getCountry())
                .city(entity.getCity())
                .postalCode(entity.getPostalCode())
                .detail(entity.getDetail())
                .latitude(entity.getLatitude())
                .longitude(entity.getLongitude())
                .isDefault(entity.getIsDefault())
                .isEnabled(entity.getIsEnabled())
                .isInvoiceAddress(entity.getIsInvoiceAddress())
                .build();
    }

    /**
     * 更新实体
     */
    @Override
    public void updateEntity(UserAddress domain, UserAddressEntity entity) {
        if (domain == null || entity == null) {
            return;
        }
        
        entity.setUserId(domain.getUserId());
        
        Address address = domain.getAddress();
        if (address != null) {
            entity.setType(address.getType());
            entity.setName(address.getName());
            entity.setPhone(address.getPhone());
            entity.setCompanyName(address.getCompanyName());
            entity.setBusinessLicenseNumber(address.getBusinessLicenseNumber());
            entity.setCountry(address.getCountry());
            entity.setCity(address.getCity());
            entity.setPostalCode(address.getPostalCode());
            entity.setDetail(address.getDetail());
            entity.setLatitude(address.getLatitude());
            entity.setLongitude(address.getLongitude());
            entity.setIsDefault(address.isDefault());
            entity.setIsEnabled(address.isEnabled());
            entity.setIsInvoiceAddress(address.isInvoiceAddress());
        }
        
        entity.setUpdateTime(domain.getUpdateTime());
        entity.setDeleted(domain.isDeleted() ? 1 : 0);
    }
}