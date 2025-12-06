package com.ares.cloud.order.domain.model.valueobject;

import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.ares.cloud.common.exception.RequestBadException;
import com.ares.cloud.order.domain.enums.DeliveryType;
import com.ares.cloud.order.domain.enums.OrderError;
import org.ares.cloud.common.model.Money;

/**
 * 配送信息值对象
 */
@Data
@Builder
public class DeliveryInfo {
    /**
     * 配送信息ID
     */
    private String id;
    /**
     * 配送方式
     */
    private DeliveryType deliveryType;
    
    /**
     * 骑手ID (商户自配送时必填)
     */
    private String riderId;
    
    /**
     * 配送单位名称 (第三方配送时必填)
     */
    private String deliveryCompany;
    
    /**
     * 物流单号 (第三方配送时必填)
     */
    private String trackingNo;
    
    /**
     * 配送员联系电话 (第三方配送时必填)
     */
    private String deliveryPhone;

    /**
     * 配送员姓名 (第三方配送时必填)
     */
    private String deliveryName;
    
    /**
     * 收货人姓名
     */
    private String receiverName;
    
    /**
     * 收货人电话
     */
    private String receiverPhone;
    
    /**
     * 配送地址
     */
    private String deliveryAddress;
    
    /**
     * 配送地址纬度
     */
    private Double deliveryLatitude;
    
    /**
     * 配送地址经度
     */
    private Double deliveryLongitude;
    
    /**
     * 配送距离(公里)
     */
    private Double deliveryDistance;
    
    /**
     * 配送国家
     */
    private String deliveryCountry;
    
    /**
     * 配送城市
     */
    private String deliveryCity;
    
    /**
     * 配送邮编
     */
    private String deliveryPostalCode;
    
    /**
     * 配送费用
     */
    private Money deliveryFee;
    
    /**
     * 验证配送信息
     */
    public void validate() {
        if (deliveryType == null) {
            throw new RequestBadException(OrderError.DELIVERY_TYPE_REQUIRED);
        }

        if (StringUtils.isBlank(receiverName)) {
            throw new RequestBadException(OrderError.RECEIVER_NAME_REQUIRED);
        }

        if (StringUtils.isBlank(receiverPhone)) {
            throw new RequestBadException(OrderError.RECEIVER_PHONE_REQUIRED);
        }

        if (StringUtils.isBlank(deliveryAddress)) {
            throw new RequestBadException(OrderError.DELIVERY_ADDRESS_REQUIRED);
        }

        if (deliveryLatitude == null || deliveryLongitude == null) {
            throw new RequestBadException(OrderError.DELIVERY_LOCATION_REQUIRED);
        }
        switch (deliveryType) {
            case MERCHANT -> {
//                if (deliveryDistance == null || deliveryDistance < 0) {
//                    throw new RequestBadException(OrderError.INVALID_DELIVERY_DISTANCE);
//                }
                if (deliveryFee == null) {
                    throw new RequestBadException(OrderError.DELIVERY_FEE_REQUIRED);
                }

                if (StringUtils.isBlank(riderId)) {
                    throw new RequestBadException(OrderError.RIDER_ID_REQUIRED);
                }
            }
            case THIRD_PARTY -> {
                if (StringUtils.isBlank(deliveryCompany)) {
                    throw new RequestBadException(OrderError.DELIVERY_COMPANY_REQUIRED);
                }
                if (StringUtils.isBlank(trackingNo)) {
                    throw new RequestBadException(OrderError.TRACKING_NO_REQUIRED);
                }
            }
        }
    }
}