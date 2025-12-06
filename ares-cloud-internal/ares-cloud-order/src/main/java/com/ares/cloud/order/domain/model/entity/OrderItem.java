package com.ares.cloud.order.domain.model.entity;

import lombok.*;
import org.ares.cloud.common.exception.RequestBadException;
import com.ares.cloud.order.domain.enums.OrderError;
import com.ares.cloud.order.domain.enums.PaymentStatus;
import org.ares.cloud.common.model.Money;
import com.ares.cloud.order.domain.model.valueobject.ProductSpecification;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单项实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    /**
     * 订单项ID
     */
    private String id;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 商品ID
     */
    private String productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品单价
     */
    private Money unitPrice;
    
    /**
     * 优惠价格
     */
    private Money discountedPrice;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 总价
     */
    private Money totalPrice;

    /**
     * 币种
     */
    private String currency;

    /**
     * 币种精度
     */
    private Integer currencyScale;

    /**
     * 删除标记
     */
    private Boolean deleted;

    /**
     * 支付状态
     */
    @Builder.Default
    private PaymentStatus paymentStatus = PaymentStatus.UNPAID;
    
    /**
     * 商品规格列表
     */
    @Builder.Default
    private List<ProductSpecification> specifications = new ArrayList<>();

    /**
     * 计算总价
     */
    public void calculateTotalPrice() {
        if (quantity == null || quantity <= 0) {
            throw new RequestBadException(OrderError.INVALID_QUANTITY);
        }
        if (unitPrice == null || unitPrice.compareTo(Money.zeroMoney(currency, currencyScale)) <= 0) {
            throw new RequestBadException(OrderError.INVALID_UNIT_PRICE);
        }
        
        // 确定使用的价格：优先使用优惠价格，如果没有则使用原价
        Money priceToUse = (discountedPrice != null && !discountedPrice.isZero()) 
            ? discountedPrice 
            : unitPrice;
        
        // 基础价格
        Money basePrice = priceToUse.multiply(new BigDecimal(quantity));
        
        // 计算规格附加价格
        Money specificationPrice = Money.zeroMoney(currency, currencyScale);
        if (specifications != null && !specifications.isEmpty()) {
            for (ProductSpecification spec : specifications) {
                if (spec.getPrice() != null) {
                    // 计算规格价格时考虑规格数量
                    BigDecimal specQuantity = new BigDecimal(spec.getQuantity() * quantity);
                    specificationPrice = specificationPrice.add(spec.getPrice().multiply(specQuantity));
                }
            }
        }
        
        // 总价 = 基础价格 + 规格附加价格
        this.totalPrice = basePrice.add(specificationPrice);
    }

    /**
     * 获取订单项总金额
     */
    public Money getTotalAmount() {
        return totalPrice;
    }

    /**
     * 判断是否已完全支付
     */
    public boolean isFullyPaid() {
        return paymentStatus == PaymentStatus.PAID;
    }

    /**
     * 判断是否可以支付
     */
    public boolean canPay() {
        return paymentStatus != PaymentStatus.PAID;
    }
    /**
     * 完成支付
     */
    public void completePay() {
        if (paymentStatus != PaymentStatus.PAID) {
            paymentStatus = PaymentStatus.PAID;
        }
    }
}