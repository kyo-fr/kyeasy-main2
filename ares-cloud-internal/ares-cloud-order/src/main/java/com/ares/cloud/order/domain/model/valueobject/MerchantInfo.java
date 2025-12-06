package com.ares.cloud.order.domain.model.valueobject;

import lombok.Getter;
import org.ares.cloud.common.model.Money;
import org.ares.cloud.common.utils.StringUtils;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2025/3/22 11:07
 */
public class MerchantInfo {
    /**
     * 商户ID
     */
    @Getter
    private String id;
    /**
     * 币种
     */
    private String currency;
    /**
     * 商户名称
     */
    private String name;

    /**
     * 币种精度
     */
    @Getter
    private Integer currencyScale;

    /**
     * 时区
     */
    @Getter
    private String timezone;

    public void setId(String id) {
        this.id = id;
    }

    public String getCurrency() {
        if (StringUtils.isBlank(currency) || "-".equals(currency)) {
            return Money.DEFAULT_CURRENCY;
        }
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setCurrencyScale(Integer currencyScale) {
        this.currencyScale = currencyScale;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
