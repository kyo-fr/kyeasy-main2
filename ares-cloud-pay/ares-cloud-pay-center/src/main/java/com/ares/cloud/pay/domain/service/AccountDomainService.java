package com.ares.cloud.pay.domain.service;

import com.ares.cloud.pay.domain.constant.PaymentConstants;
import com.ares.cloud.pay.domain.enums.PaymentError;
import com.ares.cloud.pay.domain.event.AccountCreatedEvent;
import com.ares.cloud.pay.domain.event.AccountStatusChangedEvent;
import com.ares.cloud.pay.domain.model.Account;
import com.ares.cloud.pay.domain.model.Wallet;
import com.ares.cloud.pay.domain.repository.AccountRepository;
import com.ares.cloud.pay.domain.repository.WalletRepository;
import jakarta.annotation.Resource;
import org.ares.cloud.common.exception.BusinessException;
import org.ares.cloud.common.model.Money;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 账户领域服务
 */
@Service
public class AccountDomainService {
    
    @Resource
    private AccountRepository accountRepository;

    @Resource
    private WalletRepository walletRepository;

    @Resource
    private ApplicationEventPublisher eventPublisher;
    /**
     * 密码加密工具
     */
    @Resource
    private PasswordEncoder passwordEncoder;
    /**
     * 创建账户
     */
    @Transactional
    public Account createAccount(Account account) {
        // 参数校验
        if (account == null) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        if (account.getUserId() == null || account.getUserId().trim().isEmpty()) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }

        // 检查账户是否已存在
        Account existingAccount = accountRepository.findByUserId(account.getUserId());
        if (existingAccount != null) {
            throw new BusinessException(PaymentError.ACCOUNT_ALREADY_EXISTS);
        }
        
        // 检查手机号是否已存在
        if (account.getPhone() != null && !account.getPhone().trim().isEmpty()) {
            Account existingAccountByPhone = accountRepository.findByPhone(account.getPhone());
            if (existingAccountByPhone != null) {
                throw new BusinessException(PaymentError.ACCOUNT_ALREADY_EXISTS);
            }
        }
        
        // 检查账号是否已存在
        if (account.getAccount() != null && !account.getAccount().trim().isEmpty()) {
            Account existingAccountByAccount = accountRepository.findByAccount(account.getAccount());
            if (existingAccountByAccount != null) {
                throw new BusinessException(PaymentError.ACCOUNT_ALREADY_EXISTS);
            }
        }
        
        try {
            // 对密码进行加密
            if (account.getPassword() != null && !account.getPassword().trim().isEmpty()) {
                String encryptedPassword = passwordEncoder.encode(account.getPassword());
                account.setPassword(encryptedPassword);
            }
            
            // 保存账户
            accountRepository.save(account);
            
            // 为每个支持的支付区域创建钱包
            for (String region : PaymentConstants.SUPPORTED_REGIONS) {
                Wallet wallet = new Wallet();
                wallet.setOwnerId(account.getId());
                wallet.setOwnerType(PaymentConstants.AccountType.USER);
                wallet.setPaymentRegion(region);
                wallet.setBalance(Money.zeroMoney(region));
                wallet.setFrozenAmount(Money.zeroMoney(region));
                wallet.setStatus(PaymentConstants.AccountStatus.ACTIVE);
                wallet.setCreateTime(System.currentTimeMillis());
                wallet.setUpdateTime(System.currentTimeMillis());
                
                walletRepository.save(wallet);
            }
            
            // 发布账户创建事件
            eventPublisher.publishEvent(new AccountCreatedEvent(account));
            
            return account;
        } catch (Exception e) {
            throw new BusinessException(PaymentError.SYSTEM_ERROR);
        }
    }
    
    /**
     * 更新账户状态
     */
    @Transactional
    public void updateAccountStatus(String accountId, String newStatus) {
        // 参数校验
        if (accountId == null || accountId.trim().isEmpty()) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        if (newStatus == null || newStatus.trim().isEmpty()) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        if (!isValidStatus(newStatus)) {
            throw new BusinessException(PaymentError.INVALID_ACCOUNT_STATUS);
        }
        
        try {
            // 获取账户
            Account account = accountRepository.findById(accountId);
            if (account == null) {
                throw new BusinessException(PaymentError.ACCOUNT_NOT_FOUND);
            }
            
            // 检查状态是否有效
            if (!isValidStatusTransition(account.getStatus(), newStatus)) {
                throw new BusinessException(PaymentError.INVALID_ACCOUNT_STATUS);
            }
            
            // 保存旧状态
            String oldStatus = account.getStatus();
            
            // 更新状态
            account.setStatus(newStatus);
            accountRepository.updateStatus(accountId, newStatus);
            
            // 同步更新该账户下所有钱包的状态
            List<Wallet> wallets = walletRepository.findByOwnerId(accountId);
            for (Wallet wallet : wallets) {
                walletRepository.updateStatus(wallet.getId(), newStatus);
            }
            
            // 发布状态变更事件
            eventPublisher.publishEvent(new AccountStatusChangedEvent(account, oldStatus, newStatus));
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(PaymentError.SYSTEM_ERROR);
        }
    }
    
    /**
     * 检查状态是否有效
     */
    private boolean isValidStatus(String status) {
        return PaymentConstants.AccountStatus.ACTIVE.equals(status) ||
               PaymentConstants.AccountStatus.FROZEN.equals(status) ||
               PaymentConstants.AccountStatus.CLOSED.equals(status);
    }
    
    /**
     * 检查状态转换是否有效
     */
    private boolean isValidStatusTransition(String oldStatus, String newStatus) {
        // 定义有效的状态转换规则
        if (PaymentConstants.AccountStatus.ACTIVE.equals(oldStatus)) {
            return PaymentConstants.AccountStatus.FROZEN.equals(newStatus) || 
                   PaymentConstants.AccountStatus.CLOSED.equals(newStatus);
        }
        if (PaymentConstants.AccountStatus.FROZEN.equals(oldStatus)) {
            return PaymentConstants.AccountStatus.ACTIVE.equals(newStatus) || 
                   PaymentConstants.AccountStatus.CLOSED.equals(newStatus);
        }
        return false;
    }
    
    /**
     * 验证账户密码
     */
    public boolean validatePassword(String accountId, String rawPassword) {
        // 参数校验
        if (accountId == null || accountId.trim().isEmpty()) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        if (rawPassword == null || rawPassword.trim().isEmpty()) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        
        try {
            // 获取账户
            Account account = accountRepository.findById(accountId);
            if (account == null) {
                throw new BusinessException(PaymentError.ACCOUNT_NOT_FOUND);
            }
            
            // 验证密码
            return passwordEncoder.matches(rawPassword, account.getPassword());
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(PaymentError.SYSTEM_ERROR);
        }
    }
    
    /**
     * 更新账户密码
     */
    @Transactional
    public void updatePassword(String accountId, String oldPassword, String newPassword) {
        // 参数校验
        if (accountId == null || accountId.trim().isEmpty()) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        if (oldPassword == null || oldPassword.trim().isEmpty()) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        if (newPassword == null || newPassword.trim().isEmpty()) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        
        try {
            // 获取账户
            Account account = accountRepository.findById(accountId);
            if (account == null) {
                throw new BusinessException(PaymentError.ACCOUNT_NOT_FOUND);
            }
            
            // 验证旧密码
            if (!passwordEncoder.matches(oldPassword, account.getPassword())) {
                throw new BusinessException(PaymentError.INVALID_PASSWORD);
            }
            
            // 加密新密码
            String encryptedNewPassword = passwordEncoder.encode(newPassword);
            
            // 更新密码
            accountRepository.updatePassword(accountId, encryptedNewPassword);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(PaymentError.SYSTEM_ERROR);
        }
    }
} 