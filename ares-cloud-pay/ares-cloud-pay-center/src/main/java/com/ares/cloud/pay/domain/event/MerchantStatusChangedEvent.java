package com.ares.cloud.pay.domain.event;

import com.ares.cloud.pay.domain.model.Merchant;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * 商户状态变更事件
 */
@Getter
@EqualsAndHashCode(callSuper = false)
public class MerchantStatusChangedEvent extends PaymentEvent {
    private final Merchant merchant;
    private final String oldStatus;
    private final String newStatus;
    private final String merchantNo;
    private final String merchantName;
    private final String merchantType;
    private final String supportedRegions;

    public MerchantStatusChangedEvent(Merchant merchant, String oldStatus, String newStatus) {
        super(merchant.getId(), merchant.getId());
        this.merchant = merchant;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.merchantNo = merchant.getMerchantNo();
        this.merchantName = merchant.getMerchantName();
        this.merchantType = merchant.getMerchantType();
         this.supportedRegions = merchant.getSupportedRegions().toString();
    }
} 