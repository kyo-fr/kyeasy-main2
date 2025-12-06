package org.ares.cloud.common.model;

import org.ares.cloud.common.utils.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * 金额值对象
 * 不可变对象，用于表示货币金额
 * 内部使用Long存储整数金额，避免浮点数精度问题
 */

public class Money implements Comparable<Money> {
    
    public static final int DEFAULT_SCALE = 2; // 默认精度(分)
    public static final String DEFAULT_CURRENCY = "CNY"; // 默认币种
   // public static final Money ZERO = new Money(0L, DEFAULT_CURRENCY, DEFAULT_SCALE);

    private final Long amount; // 整数金额
    private final String currency; // 币种
    private final Integer scale; // 币种精度
    
    /**
     * 构造函数
     *
     * @param amount   整数金额
     * @param currency 币种
     * @param scale    精度
     */
    public Money(Long amount, String currency, Integer scale) {
        this.amount = amount != null ? amount : 0L;
        if (StringUtils.isBlank(currency) || "-".equals(currency)){
            currency = DEFAULT_CURRENCY;
        }
        this.currency = currency;
        this.scale = scale != null ? scale : DEFAULT_SCALE;
    }

    /**
     * 构造函数（使用默认精度）
     *
     * @param amount   整数金额
     * @param currency 币种
     */
    public Money(Long amount, String currency) {
        this(amount, currency, DEFAULT_SCALE);
    }

    /**
     * 创建金额零值
     *
     * @param currency 币种
     * @param scale    精度
     * @return 金额对象
     */
    public static Money zeroMoney(String currency, Integer scale) {
        return new Money(0L, currency, scale);
    }

  /**
     * 创建金额对象
     *
     * @param amount   整数金额
     * @param currency 币种
     * @param scale    精度
     * @return 金额对象
     */
    public static Money create(Long amount, String currency, Integer scale) {
        return new Money(amount, currency, scale);
    }

    /**
     * 创建金额对象
     *
     * @param amount   整数金额
     * @param currency 币种
     * @return 金额对象
     */
    public static Money create(Long amount, String currency) {
        return new Money(amount, currency);
    }

    /**
     * 创建零金额
     *
     * @param currency 币种
     * @param scale    精度
     * @return 零金额对象
     */
    public static Money zero(String currency, Integer scale) {
        return new Money(0L, currency, scale);
    }

    /**
     * 创建零金额（使用默认精度）
     *
     * @param currency 币种
     * @return 零金额对象
     */
    public static Money zeroMoney(String currency) {
        return zeroMoney(currency, DEFAULT_SCALE);
    }

    /**
     * 从BigDecimal创建金额对象
     *
     * @param decimal  十进制数
     * @param currency 币种
     * @param scale    精度
     * @return 金额对象
     */
    public static Money of(BigDecimal decimal, String currency, Integer scale) {
        if (decimal == null || decimal.compareTo(BigDecimal.ZERO) < 0) {
            decimal = BigDecimal.ZERO;
        }
        if (StringUtils.isBlank(currency) || "-".equals(currency)){
            currency = DEFAULT_CURRENCY;
        }
        return new Money(
                decimal.multiply(BigDecimal.TEN.pow(scale)).longValue(),
                currency,
                scale
        );
    }

    /**
     * 从BigDecimal创建金额对象（使用默认精度）
     *
     * @param decimal  十进制数
     * @param currency 币种
     * @return 金额对象
     */
    public static Money of(BigDecimal decimal, String currency) {
        return of(decimal, currency, DEFAULT_SCALE);
    }

    /**
     * 从BigDecimal创建金额对象（使用默认币种和精度）
     *
     * @param decimal 十进制数
     * @return 金额对象
     */
    public static Money of(BigDecimal decimal) {
        return of(decimal, DEFAULT_CURRENCY, DEFAULT_SCALE);
    }

    /**
     * 从double创建金额对象
     *
     * @param value    数值
     * @param currency 币种
     * @param scale    精度
     * @return 金额对象
     */
    public static Money of(double value, String currency, Integer scale) {
        return of(BigDecimal.valueOf(value), currency, scale);
    }

    /**
     * 从double创建金额对象（使用默认精度）
     *
     * @param value    数值
     * @param currency 币种
     * @return 金额对象
     */
    public static Money of(double value, String currency) {
        return of(BigDecimal.valueOf(value), currency, DEFAULT_SCALE);
    }

    /**
     * 从double创建金额对象（使用默认币种和精度）
     *
     * @param value 数值
     * @return 金额对象
     */
    public static Money of(double value) {
        return of(BigDecimal.valueOf(value), DEFAULT_CURRENCY, DEFAULT_SCALE);
    }
    
    /**
     * 转换为BigDecimal
     *
     * @return BigDecimal表示的金额
     */
    public BigDecimal toDecimal() {
        return new BigDecimal(amount)
                .divide(BigDecimal.TEN.pow(scale), scale, RoundingMode.HALF_UP);
    }

    /**
     * 加法运算
     *
     * @param other 被加数
     * @return 计算结果
     */
    public Money add(Money other) {
        validateSameCurrency(other);
        return new Money(this.amount + other.amount, currency, scale);
    }

    /**
     * 减法运算
     *
     * @param other 被减数
     * @return 计算结果
     */
    public Money subtract(Money other) {
        validateSameCurrency(other);
        return new Money(this.amount - other.amount, currency, scale);
    }

    /**
     * 乘法运算（整数）
     *
     * @param multiplier 乘数
     * @return 计算结果
     */
    public Money multiply(int multiplier) {
        return new Money(this.amount * multiplier, currency, scale);
    }

    /**
     * 乘法运算（浮点数）
     *
     * @param rate 费率
     * @return 计算结果
     */
    public Money multiply(Double rate) {
        if (rate == null) {
            throw new IllegalArgumentException("Rate cannot be null");
        }
        return new Money(
                Math.round(this.amount * rate),
                this.currency,
                this.scale
        );
    }

    /**
     * 乘法运算（BigDecimal）
     *
     * @param rate 费率
     * @return 计算结果
     */
    public Money multiply(BigDecimal rate) {
        if (rate == null) {
            throw new IllegalArgumentException("Rate cannot be null");
        }
        return new Money(
                rate.multiply(new BigDecimal(amount)).longValue(),
                this.currency,
                this.scale
        );
    }
    
    /**
     * 乘法运算（Money）
     * 将当前金额与另一个Money对象的金额相乘
     *
     * @param other 另一个Money对象
     * @return 计算结果
     */
    public Money multiply(Money other) {
        if (other == null) {
            throw new IllegalArgumentException("Other Money cannot be null");
        }
        // 使用BigDecimal进行精确计算
        BigDecimal thisDecimal = this.toDecimal();
        BigDecimal otherDecimal = other.toDecimal();
        BigDecimal resultDecimal = thisDecimal.multiply(otherDecimal);
        
        return Money.of(resultDecimal, this.currency, this.scale);
    }

    /**
     * 除法运算（整数）
     *
     * @param divisor 除数
     * @return 计算结果
     */
    public Money divide(int divisor) {
        if (divisor == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return new Money(
                this.amount / divisor,
                this.currency,
                this.scale
        );
    }

    /**
     * 除法运算（BigDecimal）
     *
     * @param divisor 除数
     * @param roundingMode 舍入模式
     * @return 计算结果
     */
    public Money divide(BigDecimal divisor, RoundingMode roundingMode) {
        if (divisor == null || divisor.compareTo(BigDecimal.ZERO) == 0) {
            throw new ArithmeticException("Division by zero or null");
        }
        BigDecimal result = new BigDecimal(amount).divide(divisor, scale, roundingMode);
        return new Money(
                result.multiply(BigDecimal.TEN.pow(scale)).longValue(),
                this.currency,
                this.scale
        );
    }

    /**
     * 除法运算（BigDecimal，使用HALF_UP舍入）
     *
     * @param divisor 除数
     * @return 计算结果
     */
    public Money divide(BigDecimal divisor) {
        return divide(divisor, RoundingMode.HALF_UP);
    }

    /**
     * 判断金额是否为零
     *
     * @return 是否为零
     */
    public boolean isZero() {
        return amount == 0;
    }

    /**
     * 判断金额是否为正数
     *
     * @return 是否为正数
     */
    public boolean isPositive() {
        return amount > 0;
    }

    /**
     * 判断金额是否为负数
     *
     * @return 是否为负数
     */
    public boolean isNegative() {
        return amount < 0;
    }

    /**
     * 判断金额是否大于另一个金额
     *
     * @param other 另一个金额
     * @return 是否大于
     */
    public boolean isGreaterThan(Money other) {
        validateSameCurrency(other);
        return this.amount > other.amount;
    }

    /**
     * 判断金额是否等于另一个金额
     *
     * @param other 另一个金额
     * @return 是否等于
     */
    public boolean isEqual(Money other) {
        validateSameCurrency(other);
        return this.amount.equals(other.amount);
    }
    
    /**
     * 判断金额是否大于等于另一个金额
     *
     * @param other 另一个金额
     * @return 是否大于等于
     */
    public boolean isGreaterThanOrEqual(Money other) {
        return compareTo(other) >= 0;
    }

    /**
     * 判断金额是否小于另一个金额
     *
     * @param other 另一个金额
     * @return 是否小于
     */
    public boolean isLessThan(Money other) {
        return compareTo(other) < 0;
    }

    /**
     * 判断金额是否小于等于另一个金额
     *
     * @param other 另一个金额
     * @return 是否小于等于
     */
    public boolean isLessThanOrEqual(Money other) {
        return compareTo(other) <= 0;
    }

    /**
     * 获取金额的绝对值
     *
     * @return 绝对值
     */
    public Money abs() {
        return isNegative() ? new Money(-amount, currency, scale) : this;
    }

    /**
     * 获取金额的相反数
     *
     * @return 相反数
     */
    public Money negate() {
        return new Money(-amount, currency, scale);
    }

    /**
     * 格式化金额为字符串（使用默认区域设置）
     *
     * @return 格式化后的字符串
     */
    public String format() {
        return format(Locale.getDefault());
    }

    /**
     * 格式化金额为字符串（使用指定区域设置）
     *
     * @param locale 区域设置
     * @return 格式化后的字符串
     */
    public String format(Locale locale) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
        return formatter.format(toDecimal());
    }

    /**
     * 验证两个金额的币种和精度是否相同
     *
     * @param other 另一个金额
     */
    private void validateSameCurrency(Money other) {
        if (!this.currency.equals(other.currency) || !this.scale.equals(other.scale)) {
            throw new IllegalArgumentException("Currency mismatch");
        }
    }

    /**
     * 比较金额大小
     *
     * @param other 另一个金额
     * @return 比较结果
     */
    @Override
    public int compareTo(Money other) {
        validateSameCurrency(other);
        return this.amount.compareTo(other.amount);
    }

    /**
     * 判断对象是否相等
     *
     * @param obj 另一个对象
     * @return 是否相等
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Money other = (Money) obj;
        return this.amount.equals(other.amount) && 
               this.currency.equals(other.currency) && 
               this.scale.equals(other.scale);
    }

    /**
     * 获取哈希码
     *
     * @return 哈希码
     */
    @Override
    public int hashCode() {
        int result = amount.hashCode();
        result = 31 * result + currency.hashCode();
        result = 31 * result + scale.hashCode();
        return result;
    }

    /**
     * 转换为字符串
     *
     * @return 字符串表示
     */
    @Override
    public String toString() {
        return toDecimal().toString() + " " + currency;
    }

    /**
     * 转换为金额字符串（按照精度转换）
     * 例如：amount=10000, scale=2, currency=EUR 返回 "100.00"
     *
     * @return 金额字符串
     */
    public String toStringMoney() {
        return toDecimal().toString();
    }

    /**
     * 转换为金额字符串（按照精度转换，指定小数位数）
     * 例如：amount=10000, scale=2, currency=EUR, minFractionDigits=2 返回 "100.00"
     *
     * @param minFractionDigits 最小小数位数
     * @return 金额字符串
     */
    public String toStringMoney(int minFractionDigits) {
        BigDecimal decimal = toDecimal();
        return decimal.setScale(minFractionDigits, RoundingMode.HALF_UP).toString();
    }

    /**
     * 转换为金额字符串（按照精度转换，包含币种）
     * 例如：amount=10000, scale=2, currency=EUR 返回 "100.00 EUR"
     *
     * @return 金额字符串（包含币种）
     */
    public String toStringMoneyWithCurrency() {
        return toDecimal().toString() + " " + currency;
    }

    public Long getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public Integer getScale() {
        return scale;
    }
}