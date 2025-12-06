package com.ares.cloud.pay.domain.service;

import com.ares.cloud.pay.domain.constant.PaymentConstants;
import com.ares.cloud.pay.domain.enums.PaymentError;
import com.ares.cloud.pay.domain.event.MerchantCreatedEvent;
import com.ares.cloud.pay.domain.event.MerchantStatusChangedEvent;
import com.ares.cloud.pay.domain.model.Merchant;
import com.ares.cloud.pay.domain.model.Wallet;
import com.ares.cloud.pay.domain.repository.MerchantRepository;
import com.ares.cloud.pay.domain.repository.WalletRepository;
import com.ares.cloud.pay.infrastructure.utils.MerchantCryptoUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.ares.cloud.common.exception.BusinessException;
import org.ares.cloud.common.model.Money;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 商户领域服务
 */
@Slf4j
@Service
public class MerchantDomainService {

    @Resource
    private MerchantRepository merchantRepository;

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
     * 商户加密工具
     */
    @Resource
    private MerchantCryptoUtil merchantCryptoUtil;
    /**
     * 创建商户
     */
    @Transactional
    public Merchant createMerchant(Merchant merchant) {
        // 参数校验
        if (merchant == null) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        if (merchant.getMerchantNo() == null || merchant.getMerchantNo().trim().isEmpty()) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        if (merchant.getMerchantName() == null || merchant.getMerchantName().trim().isEmpty()) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        if (merchant.getMerchantType() == null || merchant.getMerchantType().trim().isEmpty()) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        
        // 检查商户支持的支付区域是否合法
        if (merchant.getSupportedRegions() == null || merchant.getSupportedRegions().isEmpty()) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        
        if (!merchant.areRegionsValid()) {
            throw new BusinessException(PaymentError.INVALID_MERCHANT_REGION);
        }
        
        // 检查商户号是否已存在
        Merchant existingMerchant = merchantRepository.findByMerchantNo(merchant.getMerchantNo());
        if (existingMerchant != null) {
            throw new BusinessException(PaymentError.MERCHANT_ALREADY_EXISTS);
        }
        merchant.setStatus(PaymentConstants.AccountStatus.ACTIVE);
        try {
            // 如果没有提供商户号，则自动生成
            if (merchant.getMerchantNo() == null || merchant.getMerchantNo().trim().isEmpty()) {
                String merchantNo = merchantCryptoUtil.generateMerchantNo();
                merchant.setMerchantNo(merchantNo);
            }
            
            // 生成商户密钥
            String merchantKey = merchantCryptoUtil.generateMerchantKey();
            merchant.setPaySecret(merchantKey);
            
            // 对支付密码进行加密
            if (merchant.getPayPassword() != null && !merchant.getPayPassword().trim().isEmpty()) {
                String encryptedPayPassword = passwordEncoder.encode(merchant.getPayPassword());
                merchant.setPayPassword(encryptedPayPassword);
            }
            
            // 保存商户
            merchantRepository.save(merchant);
            
            // 为每个支持的支付区域创建钱包
            for (String region : merchant.getSupportedRegions()) {
                Wallet wallet = new Wallet();
                wallet.setOwnerId(merchant.getId());
                wallet.setOwnerType(PaymentConstants.AccountType.MERCHANT);
                wallet.setPaymentRegion(region);
                wallet.setBalance(Money.zeroMoney(region));
                wallet.setFrozenAmount(Money.zeroMoney(region));
                wallet.setStatus(PaymentConstants.AccountStatus.ACTIVE);
                wallet.setCreateTime(System.currentTimeMillis());
                wallet.setUpdateTime(System.currentTimeMillis());
                
                walletRepository.save(wallet);
            }
            
            // 发布商户创建事件
            eventPublisher.publishEvent(new MerchantCreatedEvent(merchant));
            
            return merchant;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("创建商户失败", e);
            throw new BusinessException(PaymentError.SYSTEM_ERROR);
        }
    }
    
    /**
     * 更新商户状态
     */
    @Transactional
    public void updateMerchantStatus(String merchantId, String newStatus) {
        // 参数校验
        if (merchantId == null || merchantId.trim().isEmpty()) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        if (newStatus == null || newStatus.trim().isEmpty()) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        if (!isValidStatus(newStatus)) {
            throw new BusinessException(PaymentError.INVALID_MERCHANT_STATUS);
        }
        
        try {
            // 获取商户
            Merchant merchant = merchantRepository.findById(merchantId);
            if (merchant == null) {
                throw new BusinessException(PaymentError.MERCHANT_NOT_FOUND);
            }
            
            // 检查状态是否有效
            if (!isValidStatusTransition(merchant.getStatus(), newStatus)) {
                throw new BusinessException(PaymentError.INVALID_MERCHANT_STATUS);
            }
            
            // 保存旧状态
            String oldStatus = merchant.getStatus();
            
            // 更新状态
            merchant.setStatus(newStatus);
            merchantRepository.updateStatus(merchantId, newStatus);
            
            // 同步更新该商户下所有钱包的状态
            List<Wallet> wallets = walletRepository.findByOwnerId(merchantId);
            for (Wallet wallet : wallets) {
                walletRepository.updateStatus(wallet.getId(), newStatus);
            }
            
            // 发布状态变更事件
            eventPublisher.publishEvent(new MerchantStatusChangedEvent(merchant, oldStatus, newStatus));
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(PaymentError.SYSTEM_ERROR);
        }
    }
    
    /**
     * 更新商户支持的支付区域
     */
    @Transactional
    public void updateSupportedRegions(String merchantId, List<String> supportedRegions) {
        // 参数校验
        if (merchantId == null || merchantId.trim().isEmpty()) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        if (supportedRegions == null || supportedRegions.isEmpty()) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        for (String region : supportedRegions) {
            if (!PaymentConstants.SUPPORTED_REGIONS.contains(region)) {
                throw new BusinessException(PaymentError.INVALID_MERCHANT_REGION);
            }
        }
        
        try {
            // 获取商户
            Merchant merchant = merchantRepository.findById(merchantId);
            if (merchant == null) {
                throw new BusinessException(PaymentError.MERCHANT_NOT_FOUND);
            }
            
            // 更新支持的支付区域
            merchant.setSupportedRegions(supportedRegions);
            merchantRepository.updateSupportedRegions(merchantId, supportedRegions);
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
        return PaymentConstants.MerchantStatus.PENDING.equals(status) ||
               PaymentConstants.MerchantStatus.ACTIVE.equals(status) ||
               PaymentConstants.MerchantStatus.FROZEN.equals(status) ||
               PaymentConstants.MerchantStatus.CLOSED.equals(status) ||
               PaymentConstants.MerchantStatus.REJECTED.equals(status);
    }
    
    /**
     * 检查状态转换是否有效
     */
    private boolean isValidStatusTransition(String oldStatus, String newStatus) {
        // 定义有效的状态转换规则
        if (PaymentConstants.MerchantStatus.PENDING.equals(oldStatus)) {
            return PaymentConstants.MerchantStatus.ACTIVE.equals(newStatus) || 
                   PaymentConstants.MerchantStatus.REJECTED.equals(newStatus);
        }
        if (PaymentConstants.MerchantStatus.ACTIVE.equals(oldStatus)) {
            return PaymentConstants.MerchantStatus.FROZEN.equals(newStatus) || 
                   PaymentConstants.MerchantStatus.CLOSED.equals(newStatus);
        }
        if (PaymentConstants.MerchantStatus.FROZEN.equals(oldStatus)) {
            return PaymentConstants.MerchantStatus.ACTIVE.equals(newStatus) || 
                   PaymentConstants.MerchantStatus.CLOSED.equals(newStatus);
        }
        return false;
    }
    
    /**
     * 验证商户支付密码
     */
    public boolean validatePayPassword(String merchantId, String rawPayPassword) {
        // 参数校验
        if (merchantId == null || merchantId.trim().isEmpty()) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        if (rawPayPassword == null || rawPayPassword.trim().isEmpty()) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        
        try {
            // 获取商户
            Merchant merchant = merchantRepository.findById(merchantId);
            if (merchant == null) {
                throw new BusinessException(PaymentError.MERCHANT_NOT_FOUND);
            }
            
            // 验证支付密码
            return passwordEncoder.matches(rawPayPassword, merchant.getPayPassword());
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(PaymentError.SYSTEM_ERROR);
        }
    }
    
    /**
     * 更新商户支付密码
     */
    @Transactional
    public void updatePayPassword(String merchantId, String oldPayPassword, String newPayPassword) {
        // 参数校验
        if (merchantId == null || merchantId.trim().isEmpty()) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        if (oldPayPassword == null || oldPayPassword.trim().isEmpty()) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        if (newPayPassword == null || newPayPassword.trim().isEmpty()) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        
        try {
            // 获取商户
            Merchant merchant = merchantRepository.findById(merchantId);
            if (merchant == null) {
                throw new BusinessException(PaymentError.MERCHANT_NOT_FOUND);
            }
            
            // 验证旧支付密码
            if (!passwordEncoder.matches(oldPayPassword, merchant.getPayPassword())) {
                throw new BusinessException(PaymentError.INVALID_PASSWORD);
            }
            
            // 加密新支付密码
            String encryptedNewPayPassword = passwordEncoder.encode(newPayPassword);
            
            // 更新支付密码
            merchantRepository.updatePayPassword(merchantId, encryptedNewPayPassword);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(PaymentError.SYSTEM_ERROR);
        }
    }
    
    /**
     * 重新生成商户密钥
     */
    @Transactional
    public void regenerateMerchantKey(String merchantId) {
        // 参数校验
        if (merchantId == null || merchantId.trim().isEmpty()) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        
        try {
            // 获取商户
            Merchant merchant = merchantRepository.findById(merchantId);
            if (merchant == null) {
                throw new BusinessException(PaymentError.MERCHANT_NOT_FOUND);
            }
            
            // 生成新的商户密钥
            String newMerchantKey = merchantCryptoUtil.generateMerchantKey();
            merchant.setPaySecret(newMerchantKey);
            
            // 更新商户密钥
            merchantRepository.updatePaySecret(merchantId, newMerchantKey);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(PaymentError.SYSTEM_ERROR);
        }
    }
    
    /**
     * 加密商户参数
     */
    public String encryptMerchantParameter(String merchantId, String plainText) {
        // 参数校验
        if (merchantId == null || merchantId.trim().isEmpty()) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        if (plainText == null || plainText.trim().isEmpty()) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        
        try {
            // 获取商户
            Merchant merchant = merchantRepository.findById(merchantId);
            if (merchant == null) {
                throw new BusinessException(PaymentError.MERCHANT_NOT_FOUND);
            }
            
            // 获取商户密钥
            String merchantKey = merchant.getPaySecret();
            if (merchantKey == null || merchantKey.trim().isEmpty()) {
                throw new BusinessException(PaymentError.INVALID_MERCHANT_KEY);
            }
            
            // 加密参数
            return merchantCryptoUtil.encryptParameter(plainText, merchantKey);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(PaymentError.ENCRYPTION_FAILED);
        }
    }
    
    /**
     * 解密商户参数
     */
    public String decryptMerchantParameter(String merchantId, String encryptedText) {
        // 参数校验
        if (merchantId == null || merchantId.trim().isEmpty()) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        if (encryptedText == null || encryptedText.trim().isEmpty()) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        
        try {
            // 获取商户
            Merchant merchant = merchantRepository.findById(merchantId);
            if (merchant == null) {
                throw new BusinessException(PaymentError.MERCHANT_NOT_FOUND);
            }
            
            // 获取商户密钥
            String merchantKey = merchant.getPaySecret();
            if (merchantKey == null || merchantKey.trim().isEmpty()) {
                throw new BusinessException(PaymentError.INVALID_MERCHANT_KEY);
            }
            
            // 解密参数
            return merchantCryptoUtil.decryptParameter(encryptedText, merchantKey);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(PaymentError.DECRYPTION_FAILED);
        }
    }
    
    /**
     * 生成商户参数签名
     */
    public String generateMerchantSignature(String merchantId, Map<String, String> params) {
        // 参数校验
        if (merchantId == null || merchantId.trim().isEmpty()) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        if (params == null || params.isEmpty()) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        
        try {
            // 获取商户
            Merchant merchant = merchantRepository.findById(merchantId);
            if (merchant == null) {
                throw new BusinessException(PaymentError.MERCHANT_NOT_FOUND);
            }
            
            // 获取商户密钥
            String merchantKey = merchant.getPaySecret();
            if (merchantKey == null || merchantKey.trim().isEmpty()) {
                throw new BusinessException(PaymentError.INVALID_MERCHANT_KEY);
            }
            
            // 生成签名
            return merchantCryptoUtil.buildParamsAndSign(params, merchantKey);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(PaymentError.SYSTEM_ERROR);
        }
    }
    

    /**
     * 更新商户基本信息
     */
    @Transactional
    public void updateMerchantBasicInfo(String merchantId, String merchantName, String contactPerson,
                                       String contactPhone, String contactEmail, String address,
                                       String businessLicense, String legalRepresentative) {
        // 参数校验
        if (merchantId == null || merchantId.trim().isEmpty()) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        if (merchantName == null || merchantName.trim().isEmpty()) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        
        try {
            // 获取商户
            Merchant merchant = merchantRepository.findById(merchantId);
            if (merchant == null) {
                throw new BusinessException(PaymentError.MERCHANT_NOT_FOUND);
            }
            
            // 更新商户基本信息
            merchantRepository.updateBasicInfo(merchantId, merchantName, contactPerson,
                    contactPhone, contactEmail, address, businessLicense, legalRepresentative);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(PaymentError.SYSTEM_ERROR);
        }
    }
} 