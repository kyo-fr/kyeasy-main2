package com.ares.cloud.order.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import com.ares.cloud.order.domain.enums.DeliveryType;

import java.math.BigDecimal;

@Data
@Schema(description = "配送信息DTO")
public class DeliveryInfoDTO {
    @Schema(description = "配送信息ID")
    private String id;
    
    @Schema(description = "配送方式")
    private DeliveryType deliveryType;
    
    @Schema(description = "骑手ID")
    private String riderId;
    
    @Schema(description = "配送单位名称")
    private String deliveryCompany;
    
    @Schema(description = "物流单号")
    private String trackingNo;
    
    @Schema(description = "配送员联系电话")
    private String deliveryPhone;

    @Schema(description = "配送员姓名")
    private String deliveryName;

    @Schema(description = "收货人姓名")
    private String receiverName;

    @Schema(description = "收货人联系电话")
    private String receiverPhone;

    @Schema(description = "配送开始时间")
    private Long deliveryStartTime;
    
    @Schema(description = "配送地址")
    private String deliveryAddress;
    
    @Schema(description = "配送地址纬度")
    private Double deliveryLatitude;
    
    @Schema(description = "配送地址经度")
    private Double deliveryLongitude;
    
    @Schema(description = "配送距离(公里)")
    private Double deliveryDistance;
    
    @Schema(description = "配送国家")
    private String deliveryCountry;
    
    @Schema(description = "配送城市")
    private String deliveryCity;
    
    @Schema(description = "配送邮编")
    private String deliveryPostalCode;
    
    @Schema(description = "配送费用")
    private BigDecimal deliveryFee;
}