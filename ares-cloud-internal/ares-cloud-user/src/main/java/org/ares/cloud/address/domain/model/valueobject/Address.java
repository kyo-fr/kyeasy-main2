package org.ares.cloud.address.domain.model.valueobject;

import org.ares.cloud.address.domain.enums.AddressType;
import org.ares.cloud.address.domain.enums.AddressError;
import org.ares.cloud.common.exception.RequestBadException;
import lombok.Builder;
import lombok.Value;

/**
 * 地址值对象
 */
@Value
@Builder
public class Address {
    /**
     * 地址类型
     */
    private AddressType type;

    /**
     * 姓名（可选）
     */
    private String name;

    /**
     * 手机号（可选）
     */
    private String phone;

    /**
     * 公司名称（公司地址必填）
     */
    private String companyName;
    
    /**
     * 企业营业号
     */
    private String businessLicenseNumber;

    /**
     * 国家
     */
    private String country;

    /**
     * 城市
     */
    private String city;
    
    /**
     * 邮编
     */
    private String postalCode;

    /**
     * 详细地址
     */
    private String detail;

    /**
     * 纬度
     */
    private Double latitude;

    /**
     * 经度
     */
    private Double longitude;

    /**
     * 是否默认地址
     */
    private boolean isDefault;

    /**
     * 是否启用
     */
    private boolean isEnabled;

    /**
     * 是否发票地址（仅公司地址可用）
     */
    private boolean isInvoiceAddress;

    public void validate() {
        if (type == null) {
            throw new RequestBadException(AddressError.ADDRESS_TYPE_CANNOT_BE_EMPTY);
        }

        if (type == AddressType.COMPANY && (companyName == null || companyName.trim().isEmpty())) {
            throw new RequestBadException(AddressError.COMPANY_NAME_CANNOT_BE_EMPTY);
        }

        if (city == null || city.trim().isEmpty()) {
            throw new RequestBadException(AddressError.CITY_CANNOT_BE_EMPTY);
        }

        if (detail == null || detail.trim().isEmpty()) {
            throw new RequestBadException(AddressError.DETAIL_CANNOT_BE_EMPTY);
        }

        if (latitude == null) {
            throw new RequestBadException(AddressError.LATITUDE_CANNOT_BE_EMPTY);
        }

        if (longitude == null) {
            throw new RequestBadException(AddressError.LONGITUDE_CANNOT_BE_EMPTY);
        }
    }
}