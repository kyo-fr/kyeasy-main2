package org.ares.cloud.address.domain.model.aggregate;

import org.ares.cloud.address.domain.model.valueobject.Address;
import org.ares.cloud.address.domain.enums.AddressError;
import org.ares.cloud.common.exception.RequestBadException;
import lombok.Builder;
import lombok.Getter;

/**
 * 用户地址聚合根
 */
@Getter
@Builder
public class UserAddress {
    /**
     * 地址ID
     */
    private String id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 地址信息
     */
    private Address address;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 更新时间
     */
    private Long updateTime;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 是否删除
     */
    private boolean deleted;

    public void validate() {
        if (userId == null || userId.trim().isEmpty()) {
            throw new RequestBadException(AddressError.USER_ID_CANNOT_BE_EMPTY);
        }
        if (address == null) {
            throw new RequestBadException(AddressError.ADDRESS_CANNOT_BE_EMPTY);
        }
        address.validate();
    }

    public void setDefault(boolean isDefault) {
        this.address = Address.builder()
                .type(address.getType())
                .name(address.getName())
                .phone(address.getPhone())
                .companyName(address.getCompanyName())
                .businessLicenseNumber(address.getBusinessLicenseNumber())
                .country(address.getCountry())
                .city(address.getCity())
                .postalCode(address.getPostalCode())
                .detail(address.getDetail())
                .latitude(address.getLatitude())
                .longitude(address.getLongitude())
                .isDefault(isDefault)
                .isEnabled(address.isEnabled())
                .isInvoiceAddress(address.isInvoiceAddress())
                .build();
    }

    public void update(Address newAddress) {
        this.address = newAddress;
        this.updateTime = System.currentTimeMillis();
        //this.version++;
    }

    public void delete() {
        this.deleted = true;
        this.updateTime = System.currentTimeMillis();
       // this.version++;
    }
}