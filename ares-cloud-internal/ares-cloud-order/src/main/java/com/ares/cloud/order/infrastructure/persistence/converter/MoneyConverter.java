package com.ares.cloud.order.infrastructure.persistence.converter;

import org.ares.cloud.common.model.Money;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2025/1/7 18:26
 */
public interface MoneyConverter {

    /**
     * 创建Money对象
     */
    default Money createMoney(Long amount, String currency, Integer scale) {
        return amount != null ? new Money(amount, currency, scale) : Money.zeroMoney(currency, scale);
    }

    /**
     * 创建BigDecimal对象
     */
    default BigDecimal toDecimal(Long amount, String currency, Integer scale) {
        return amount != null ? new Money(amount, currency, scale).toDecimal() : Money.zeroMoney(currency, scale).toDecimal();
    }

    /**
     * 获取Money金额
     */
    default Long getMoneyAmount(Money money) {
        return Optional.ofNullable(money).map(Money::getAmount).orElse(null);
    }

    /**
     * 获取Money币种
     */
    default String getMoneyCurrency(Money money) {
        return Optional.ofNullable(money).map(Money::getCurrency).orElse(null);
    }

    /**
     * 获取Money精度
     */
    default Integer getMoneyScale(Money money) {
        return Optional.ofNullable(money).map(Money::getScale).orElse(null);
    }

}
