package org.ares.cloud.merchantInfo.enums;

import org.ares.cloud.common.exception.BaseErrorInfoInterface;
import org.springframework.http.HttpStatus;

/**
 * @author hugo
 * @version 1.0
 * @description: 认证的错误
 * @date 2024/9/30 18:46
 */
public enum MerchantError implements BaseErrorInfoInterface {
    SYS_BUSINESS_ID_SERVICE_ERROR("sys.business.id.service.error"),
    MERCHANT_KEYWORDS_EXIST_ERROR("merchant.keywords.exist.error"),
    MERCHANT_ID_NOT_BLANK_ERROR("validation.tenantId.notBlank"),
    MERCHANT_PHONE_NOT_EXIST_USER_ERROR("validation.merchantPhone.not.exist.user.error"),

    MERCHANT_PHONE_NOT_EQ_REGISTER_PHONE_ERROR("validation.merchantPhone.not.eq.registerPhone.error"),

    MERCHANT_INFO_IS_EXIST_ERROR("validation.merchantInfo.exist.error"),//该商户信息已存在

    MERCHANT_INFO_IS_NOT_EXIST_ERROR("validation.merchantInfo.not.exist.error"),//商户信息不存在

    MERCHANT_UPDATE_OPENING_HOURS_COUNT_MORE_ERROR("validation.merchant.update.opening.hour.count.more.error"),//商户修改营业时间超过2次修改
    MERCHANT_UPDATE_ADVERTISED_COUNT_MORE_ERROR("validation.merchant.update.advertised.count.more.error"),//商户修改广告语超过2次修改
    MERCHANT_UPDATE_HOLIDAY_COUNT_MORE_ERROR("validation.merchant.update.holiday.count.more.error"),//商户修改休假超过2次修改异常

    MERCHANT_SPECIFICATION_NAME_EXIST_ERROR("merchant.specification.name.exist.error"),

    MERCHANT_SUB_SPECIFICATION_NAME_EXIST_ERROR("merchant.sub.specification.name.exist.error"),

    MERCHANT_MARKING_NAME_EXIST_ERROR("merchant.marking.name.exist.error"),

    MERCHANT_SUB_SPECIFICATION_IS_NOT_DELETE_ERROR("merchant.sub.specification.isNotDelete.error"),

    PRODUCT_IS_ENABLE_ERROR("product.is.enable.error"),

    MERCHANT_FREIGHT_TYPE_EXIST_ERROR("merchant.freight.type.exist.error"),//商户货运配送类型已存在

    MERCHANT_INFO_COUNTRY_CODE_NOT_NULL_ERROR("merchantInfo.countryCode.notBlank.error"), //商户国家代码不能为空

    MERCHANT_INFO_REGISTER_PHONE_NOT_NULL_ERROR("merchantInfo.registerPhone.notBlank.error"), //商户注册手机号不能为空
    ;

    private final Integer code;
    private final String messageKey;


    MerchantError(Integer code, String messageKey) {
        this.code = code;
        this.messageKey = messageKey;
    }
    MerchantError(String message) {
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.messageKey = message;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMessageKey() {
        return messageKey;
    }
}
