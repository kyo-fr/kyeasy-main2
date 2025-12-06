package org.ares.cloud.product.enums;

import org.ares.cloud.common.exception.BaseErrorInfoInterface;
import org.springframework.http.HttpStatus;

/**
 * @author hugo
 * @version 1.0
 * @description: 认证的错误
 * @date 2024/9/30 18:46
 */
public enum ProductError implements BaseErrorInfoInterface {
    MERCHANT_KEYWORDS_EXIST_ERROR("merchant.keywords.exist.error"),

    MERCHANT_SPECIFICATION_NAME_EXIST_ERROR("merchant.specification.name.exist.error"),

    MERCHANT_SUB_SPECIFICATION_NAME_EXIST_ERROR("merchant.sub.specification.name.exist.error"),

    MERCHANT_MARKING_NAME_EXIST_ERROR("merchant.marking.name.exist.error"),

    MERCHANT_SUB_SPECIFICATION_IS_NOT_DELETE_ERROR("merchant.sub.specification.isNotDelete.error"),

    PRODUCT_IS_ENABLE_ERROR("product.is.enable.error"), //商品未下架
    PRODUCT_IS_NOT_ENABLE_ERROR("product.is.not.enable.error"), //商品已经下架

    PRODUCT_TYPE_ID_NOT_BLANK_ERROR("product.typeId.isBlank.error"),

    PRODUCT_NAME_EXIST_ERROR("product.name.exist.error"),//该商户商品名称已存在
    PRODUCT_PREFERENTIAL_IS_NULL_ERROR("product.preferential.is.null.error"),//商户优惠信息不能为空
    PRODUCT_AUCTION_IS_NULL_ERROR("product.auction.is.null.error"),//商户拍卖信息不能为空
    PRODUCT_WHOLESALE_IS_NULL_ERROR("product.wholesale.is.null.error"),//商品批发信息不能为空
    PRODUCT_HAS_ORDER_EXIST_ERROR("product.has.order.exist.error"),//该商品下存在未处理的订单
    PRODUCT_TYPE_NAME_EXIST_ERROR("product.type.name.exist.error"),//商品分类名称已存在
    PRODUCT_WAREHOUSE_NAME_EXIST_ERROR("product.warehouse.name.exist.error"),//仓库名称已存在
    PRODUCT_WAREHOUSE_SEAT_NAME_EXIST_ERROR("product.warehouse.seat.name.exist.error"),//位子名称已存在
    PRODUCT_NOT_EXIST_ERROR("product.not.exist.error"), //商品不存在
    PRODUCT_ADD_PRICE_IS_HIGH_FIXED_PRICE_ERROR("product.add.price.is.high.fixedPrice.error"),//商品加价不能高于一口价
    PRODUCT_ADD_PRICE_IS_LOW_ERROR("product.add.price.is.low.error"),//商品加价不能低于上次加价后的价格
    PRODUCT_NOT_START_AUCTION_ERROR("product.not.start.auction.error"),//商品未开始拍卖
    PRODUCT_END_AUCTION_ERROR("product.end.auction.error"),//商品已结束拍卖
    MERCHANT_STORAGE_NOT_ENOUGH_ERROR("merchant.storage.not.enough.error"),//商户存储空间不足
    MERCHANT_SERVICE_ERROR("merchant.service.error"),//商户服务异常
    MERCHANT_STORAGE_QUOTA_RESPONSE_ERROR("merchant.storage.quota.response.error"),//存储配额响应格式错误，请稍后重试


    ;

    private final Integer code;
    private final String messageKey;


    ProductError(Integer code, String messageKey) {
        this.code = code;
        this.messageKey = messageKey;
    }
    ProductError(String message) {
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
