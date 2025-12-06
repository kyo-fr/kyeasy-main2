package org.ares.cloud.api.order.commod;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.ares.cloud.api.order.enums.ApiDeliveryType;
import org.ares.cloud.api.order.enums.ApiOrderType;
import org.ares.cloud.api.order.enums.ApiPaymentModel;
import org.ares.cloud.common.deserializer.CustomDateDeserializer;

import java.math.BigDecimal;
import java.util.List;

/**
 * 创建订单命令
 */
@Data
@Schema(description = "创建订单命令")
public class CreateOrderCommand {

    /**
     * 基础信息
     */
    @Schema(description = "商户ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("merchantId")
    @NotBlank(message = "{validation.merchantId.notBlank}")
    private String merchantId;

    @Schema(description = "订单类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("orderType")
    @NotNull(message = "{validation.orderType.notNull}")
    private ApiOrderType orderType;

    @Schema(description = "国家代码")
    @JsonProperty(value = "countryCode")
    private String countryCode;
    @Schema(description = "手机号")
    @JsonProperty("userPhone")
    private String userPhone;

    @Schema(description = "服务费")
    @JsonProperty("serviceFee")
    private BigDecimal serviceFee;

    /**
     * 订单来源类型
     */
    private Integer sourceType;
    /**
     * 是否平台订单
     */
    private boolean isPlatform;

    /**
     * 订单项信息
     */
    @Schema(description = "订单项列表")
    @JsonProperty("items")
    @NotNull(message = "{validation.items.notNull}")
    @Valid
    private List<OrderItemCommand> items;

    /**
     * 支付信息
     */
    @Schema(description = "支付方式")
    @JsonProperty("paymentMode")
    private ApiPaymentModel paymentMode;

    @Schema(description = "支付渠道ID")
    @JsonProperty("paymentChannelId")
    private String paymentChannelId;

    /**
     * 店内就餐信息
     */
    @Schema(description = "就餐信息")
    @JsonProperty("diningInfo")
    @Valid
    private DiningInfo diningInfo;

    /**
     * 配送信息
     */
    @Schema(description = "配送信息")
    @JsonProperty("deliveryInfo")
    @Valid
    private DeliveryInfo deliveryInfo;

    /**
     * 预订信息
     */
    @Schema(description = "预订信息")
    @JsonProperty("reservationInfo")
    @Valid
    private ReservationInfo reservationInfo;

    /**
     * 自取信息
     */
    @Schema(description = "自取信息")
    @JsonProperty("pickupInfo")
    @Valid
    private PickupInfo pickupInfo;

    @Data
    @Schema(description = "就餐信息")
    public static class DiningInfo {
        @Schema(description = "桌号")
        @NotBlank(message = "{validation.tableNo.notBlank}")
        private String tableNo;

        @Schema(description = "就餐人数")
        private Integer diningNumber;
    }

    @Data
    @Schema(description = "配送信息")
    public static class DeliveryInfo {
        @Schema(description = "配送方式")
        @NotNull(message = "{validation.deliveryType.notNull}")
        private ApiDeliveryType deliveryType;

        @Schema(description = "收货人姓名")
        @NotBlank(message = "{validation.receiverName.notBlank}")
        private String receiverName;

        @Schema(description = "收货人电话")
        @NotBlank(message = "{validation.receiverPhone.notBlank}")
        private String receiverPhone;

        @Schema(description = "配送地址")
        @NotBlank(message = "{validation.deliveryAddress.notBlank}")
        private String deliveryAddress;

        @Schema(description = "配送地址纬度")
        @NotNull(message = "{validation.deliveryLatitude.notNull}")
        private Double deliveryLatitude;

        @Schema(description = "配送地址经度")
        @NotNull(message = "{validation.deliveryLongitude.notNull}")
        private Double deliveryLongitude;

        @Schema(description = "配送费")
        private BigDecimal deliveryFee;

        @Schema(description = "配送距离")
        private Double deliveryDistance;

        @Schema(description = "配送国家")
        private String deliveryCountry;

        @Schema(description = "配送城市")
        private String deliveryCity;

        @Schema(description = "配送邮编")
        private String deliveryPostalCode;
    }

    @Data
    @Schema(description = "预订信息")
    public static class ReservationInfo {
        @Schema(description = "预订时间")
        @JsonDeserialize(using = CustomDateDeserializer.class)
        @NotNull(message = "{validation.reservationTime.notNull}")
        private Long reservationTime;

        @Schema(description = "预订人姓名")
        @NotBlank(message = "{validation.reserverName.notBlank}")
        private String reserverName;

        @Schema(description = "预订人电话")
        @NotBlank(message = "{validation.reserverPhone.notBlank}")
        private String reserverPhone;

        @Schema(description = " 预定人数")
        private Integer diningNumber;

        @Schema(description = "特殊餐饮要求")
        private String dietaryRequirements;

        @Schema(description = "预订备注")
        private String remarks;
    }

    @Data
    @Schema(description = "自取信息")
    public static class PickupInfo {
        @Schema(description = "取餐时间")
        @JsonDeserialize(using = CustomDateDeserializer.class)
        @NotNull(message = "{validation.pickupTime.notNull}")
        private Long pickupTime;
    }

    /**
     * 验证配送订单信息
     */
    @Hidden
    @AssertTrue(message = "{validation.deliveryInfo.required}")
    public boolean isDeliveryInfoValid() {
        if (orderType == ApiOrderType.DELIVERY) {
            return deliveryInfo != null;
        }
        return true;
    }

    /**
     * 验证店内就餐信息
     */
    @Hidden
    @AssertTrue(message = "{validation.diningInfo.required}")
    public boolean isDiningInfoValid() {
        if (orderType == ApiOrderType.DINE_IN) {
            return diningInfo != null;
        }
        return true;
    }

    /**
     * 验证预订信息
     */
    @Hidden
    @AssertTrue(message = "{validation.reservationInfo.required}")
    public boolean isReservationInfoValid() {
        if (orderType == ApiOrderType.RESERVATION) {
            return reservationInfo != null;
        }
        return true;
    }

    /**
     * 验证自取信息
     */
    @Hidden
    @AssertTrue(message = "{validation.pickupInfo.required}")
    public boolean isPickupInfoValid() {
        if (orderType == ApiOrderType.PICKUP) {
            return pickupInfo != null;
        }
        return true;
    }

    /**
     * 验证支付相关信息
     */
    @Hidden
    @AssertTrue(message = "{validation.paymentMode.required}")
    public boolean isPaymentModeValid() {
        if (orderType == ApiOrderType.ONLINE || orderType == ApiOrderType.DELIVERY) {
            return paymentMode != null;
        }
        return true;
    }

    /**
     * 验证支付渠道
     */
    @Hidden
    @AssertTrue(message = "{validation.paymentChannel.required}")
    public boolean isPaymentChannelValid() {
        if (paymentMode == ApiPaymentModel.ONLINE) {
            return paymentChannelId != null && !paymentChannelId.isEmpty();
        }
        return true;
    }

    /**
     * 订单项命令
     */
    @Data
    @Schema(description = "订单项命令")
    public static class OrderItemCommand {
        /**
         * 商品ID
         */
        @JsonProperty("productId")
        @NotBlank(message = "商品ID不能为空")
        @Schema(description = "商品ID", requiredMode = Schema.RequiredMode.REQUIRED)
        private String productId;

        /**
         * 商品名称
         */
        @JsonProperty("productName")
        @NotBlank(message = "商品名称不能为空")
        @Schema(description = "商品名称")
        private String productName;

        /**
         * 数量
         */
        @JsonProperty("quantity")
        @Schema(description = "数量", requiredMode = Schema.RequiredMode.REQUIRED)
        private Integer quantity;

        /**
         * 单价
         */
        @JsonProperty("unitPrice")
        @Schema(description = "单价")
        private BigDecimal unitPrice;

        /**
         * 商品规格列表
         */
        @JsonProperty("specifications")
        @Schema(description = "商品规格列表")
        private List<ProductSpecificationCommand> specifications;
    }

    /**
     * 商品规格命令
     */
    @Data
    public static class ProductSpecificationCommand {
        /**
         * 规格ID
         */
        @Schema(description = "规格ID", requiredMode = Schema.RequiredMode.REQUIRED)
        private String productSpecId;

        /**
         * 规格名称
         */
        @Schema(description = "规格名称")
        private String name;

        /**
         * 规格值
         */
        @Schema(description = "规格值")
        private String value;

        /**
         * 规格价格
         */
        @Schema(description = "规格值")
        private BigDecimal price;
    }
}