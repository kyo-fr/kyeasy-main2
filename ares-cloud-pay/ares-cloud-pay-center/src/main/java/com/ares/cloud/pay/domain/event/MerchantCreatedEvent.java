package com.ares.cloud.pay.domain.event;

import com.ares.cloud.pay.domain.model.Merchant;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * 商户创建事件
 */
@Getter
@EqualsAndHashCode(callSuper = false)
public class MerchantCreatedEvent extends PaymentEvent {
    private final Merchant merchant;
    private final String merchantNo;
    private final String merchantName;
    private final String merchantType;
    private final String supportedRegions;

    public MerchantCreatedEvent(Merchant merchant) {
        super(merchant.getId(), merchant.getId());
        this.merchant = merchant;
        this.merchantNo = merchant.getMerchantNo();
        this.merchantName = merchant.getMerchantName();
        this.merchantType = merchant.getMerchantType();
        this.supportedRegions = merchant.getSupportedRegions().toString();
    }
} 