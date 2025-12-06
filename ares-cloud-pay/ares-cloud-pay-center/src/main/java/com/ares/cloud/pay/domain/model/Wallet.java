package com.ares.cloud.pay.domain.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.ares.cloud.common.model.Money;

/**
 * 钱包领域模型
 */
@Data
@Accessors(chain = true)
public class Wallet {
    
    /**
     * 钱包ID
     */
    private String id;
    
    /**
     * 账户ID或商户ID
     */
    private String ownerId;
    
    /**
     * 所有者类型（ACCOUNT:账户, MERCHANT:商户）
     */
    private String ownerType;
    
    /**
     * 支付区域（EUR/USD/CNY/CHF/GBP）
     */
    private String paymentRegion;
    
    /**
     * 余额
     */
    private Money balance;
    
    /**
     * 冻结金额
     */
    private Money frozenAmount;
    
    /**
     * 钱包状态（ACTIVE:激活, FROZEN:冻结, CLOSED:关闭）
     */
    private String status;
    
    /**
     * 创建时间
     */
    private Long createTime;
    
    /**
     * 更新时间
     */
    private Long updateTime;
    
    /**
     * 检查钱包是否可用
     */
    public boolean isActive() {
        return "ACTIVE".equals(status);
    }
    
    /**
     * 检查钱包是否冻结
     */
    public boolean isFrozen() {
        return "FROZEN".equals(status);
    }
    
    /**
     * 检查钱包是否关闭
     */
    public boolean isClosed() {
        return "CLOSED".equals(status);
    }
    
    /**
     * 获取可用余额
     */
    public Money getAvailableBalance() {
        Money frozen = frozenAmount != null ? frozenAmount : Money.zeroMoney(paymentRegion);
        return balance.subtract(frozen);
    }
    
    /**
     * 检查余额是否足够
     */
    public boolean hasEnoughBalance(Money amount) {
        return getAvailableBalance().isGreaterThanOrEqual(amount);
    }
    
    /**
     * 增加余额
     */
    public void increaseBalance(Money amount) {
        if (this.balance == null) {
            this.balance = Money.zeroMoney(paymentRegion);
        }
        this.balance = this.balance.add(amount);
    }
    
    /**
     * 减少余额
     */
    public void decreaseBalance(Money amount) {
        if (this.balance == null) {
            this.balance = Money.zeroMoney(paymentRegion);
        }
        this.balance = this.balance.subtract(amount);
    }
    
    /**
     * 增加冻结金额
     */
    public void increaseFrozenAmount(Money amount) {
        if (this.frozenAmount == null) {
            this.frozenAmount = Money.zeroMoney(paymentRegion);
        }
        this.frozenAmount = this.frozenAmount.add(amount);
    }
    
    /**
     * 减少冻结金额
     */
    public void decreaseFrozenAmount(Money amount) {
        if (this.frozenAmount == null) {
            this.frozenAmount = Money.zeroMoney(paymentRegion);
        }
        this.frozenAmount = this.frozenAmount.subtract(amount);
    }
} 