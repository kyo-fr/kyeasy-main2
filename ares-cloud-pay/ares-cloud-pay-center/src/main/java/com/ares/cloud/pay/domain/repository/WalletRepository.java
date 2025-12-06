package com.ares.cloud.pay.domain.repository;

import com.ares.cloud.pay.domain.model.Wallet;
import org.ares.cloud.common.model.Money;

import java.util.List;

/**
 * 钱包仓储接口
 */
public interface WalletRepository {
    
    /**
     * 保存钱包
     */
    void save(Wallet wallet);
    
    /**
     * 根据ID查询钱包
     */
    Wallet findById(String id);
    
    /**
     * 根据所有者ID和支付区域查询钱包
     */
    Wallet findByOwnerIdAndRegion(String ownerId, String paymentRegion);
    
    /**
     * 根据所有者ID查询钱包列表
     */
    List<Wallet> findByOwnerId(String ownerId);
    
    /**
     * 根据所有者类型查询钱包列表
     */
    List<Wallet> findByOwnerType(String ownerType);
    
    /**
     * 根据支付区域查询钱包列表
     */
    List<Wallet> findByPaymentRegion(String paymentRegion);
    
    /**
     * 更新钱包状态
     */
    void updateStatus(String id, String status);
    
    /**
     * 更新钱包余额
     */
    void updateBalance(String id, Money balance);
    
    /**
     * 更新钱包冻结金额
     */
    void updateFrozenAmount(String id, Money frozenAmount);
} 