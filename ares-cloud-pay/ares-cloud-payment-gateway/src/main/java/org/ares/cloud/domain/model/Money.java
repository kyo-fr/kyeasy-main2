package org.ares.cloud.domain.model;

import lombok.Value;
import java.math.BigDecimal;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 金额值对象
 * 
 * @author hugo
 * @version 1.0.0
 * @date 2024/03/15
 */
@Value
@Schema(description = "金额")
public class Money {
    /**
     * 金额
     */
    BigDecimal amount;
    
    /**
     * 货币类型
     */
    String currency;
    
    /**
     * 创建金额对象
     * 
     * @param amount 金额
     * @param currency 货币类型
     * @return Money对象
     * @throws IllegalArgumentException 当金额无效时抛出异常
     */
    public static Money of(BigDecimal amount, String currency) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Invalid amount");
        }
        return new Money(amount, currency != null ? currency : "CNY");
    }
    
    /**
     * 金额相加
     * 
     * @param other 其他金额
     * @return 相加后的新金额对象
     * @throws IllegalArgumentException 当货币类型不匹配时抛出异常
     */
    public Money add(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Currency mismatch");
        }
        return new Money(this.amount.add(other.amount), this.currency);
    }
    
    /**
     * 金额乘以数量
     * 
     * @param multiplier 乘数
     * @return 新的金额对象
     */
    public Money multiply(BigDecimal multiplier) {
        return new Money(this.amount.multiply(multiplier), this.currency);
    }
} 