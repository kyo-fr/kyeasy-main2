package com.ares.cloud.pay.infrastructure.init;

import com.ares.cloud.pay.domain.constant.PaymentConstants;
import com.ares.cloud.pay.domain.model.Merchant;
import com.ares.cloud.pay.domain.model.Wallet;
import com.ares.cloud.pay.domain.repository.MerchantRepository;
import com.ares.cloud.pay.domain.repository.WalletRepository;
import com.ares.cloud.pay.infrastructure.utils.MerchantCryptoUtil;
import lombok.extern.slf4j.Slf4j;
import org.ares.cloud.common.model.Money;
import org.ares.cloud.common.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 平台商户初始化器
 * 在应用启动时自动初始化平台商户，每个区域都有500万的余额
 */
@Slf4j
@Component
public class PlatformMerchantInitializer implements CommandLineRunner {

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private MerchantCryptoUtil merchantCryptoUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 平台商户ID
     */
    private static final String PLATFORM_MERCHANT_ID = PaymentConstants.PLATFORM_MERCHANT_ID;

    /**
     * 平台商户号
     */
    private static final String PLATFORM_MERCHANT_NO = "PLATFORM001";

    /**
     * 平台商户名称
     */
    private static final String PLATFORM_MERCHANT_NAME = "平台商户";

    /**
     * 平台商户类型
     */
    private static final String PLATFORM_MERCHANT_TYPE = "PLATFORM";

    /**
     * 初始余额（500万，以分为单位）
     */
    private static final Long INITIAL_BALANCE = 500000000L; // 500万 * 100分

    @Override
    public void run(String... args) throws Exception {
        try {
            log.info("开始初始化平台商户...");
            
            // 检查平台商户是否已存在
            Merchant existingMerchant = merchantRepository.findById(PLATFORM_MERCHANT_ID);
            if (existingMerchant != null) {
                log.info("平台商户已存在，跳过初始化");
                return;
            }

            // 创建平台商户
            Merchant platformMerchant = createPlatformMerchant();
            merchantRepository.save(platformMerchant);
            log.info("平台商户创建成功: {}", platformMerchant.getMerchantNo());

            // 为每个支持的支付区域创建钱包并设置初始余额
            for (String region : PaymentConstants.SUPPORTED_REGIONS) {
                Wallet wallet = createPlatformWallet(region);
                walletRepository.save(wallet);
                log.info("平台商户{}区域钱包创建成功，初始余额: {}", region, wallet.getBalance());
            }

            log.info("平台商户初始化完成");
        } catch (Exception e) {
            log.error("平台商户初始化失败", e);
            // 不要抛出异常，避免阻止应用启动
        }
    }

    /**
     * 创建平台商户
     */
    private Merchant createPlatformMerchant() {
        Merchant merchant = new Merchant();
        merchant.setId(PLATFORM_MERCHANT_ID);
        merchant.setMerchantNo(PLATFORM_MERCHANT_NO);
        merchant.setMerchantName(PLATFORM_MERCHANT_NAME);
        merchant.setMerchantType(PLATFORM_MERCHANT_TYPE);
        merchant.setStatus(PaymentConstants.MerchantStatus.ACTIVE);
        merchant.setSupportedRegions(PaymentConstants.SUPPORTED_REGIONS);
        
        // 生成商户密钥
        String merchantKey = merchantCryptoUtil.generateMerchantKey();
        merchant.setPaySecret(merchantKey);
        
        // 设置默认支付密码（加密）
        String defaultPassword = "platform123456";
        String encryptedPassword = passwordEncoder.encode(defaultPassword);
        merchant.setPayPassword(encryptedPassword);
        
        // 设置基本信息
        merchant.setContactPerson("平台管理员");
        merchant.setContactPhone("+86-400-000-0000");
        merchant.setContactEmail("platform@ares.cloud");
        merchant.setAddress("平台总部");
        merchant.setBusinessLicense("PLATFORM_LICENSE_001");
        merchant.setLegalRepresentative("平台法人");
        
        // 设置时间戳
        long currentTime = System.currentTimeMillis();
        merchant.setCreateTime(currentTime);
        merchant.setUpdateTime(currentTime);
        
        return merchant;
    }

    /**
     * 创建平台钱包
     */
    private Wallet createPlatformWallet(String paymentRegion) {
        Wallet wallet = new Wallet();
        wallet.setId(IdUtils.fastSimpleUUID());
        wallet.setOwnerId(PLATFORM_MERCHANT_ID);
        wallet.setOwnerType(PaymentConstants.AccountType.MERCHANT);
        wallet.setPaymentRegion(paymentRegion);
        
        // 设置初始余额（500万）
        Money initialBalance = Money.create(INITIAL_BALANCE, paymentRegion, Money.DEFAULT_SCALE);
        wallet.setBalance(initialBalance);
        wallet.setFrozenAmount(Money.zeroMoney(paymentRegion));
        wallet.setStatus(PaymentConstants.AccountStatus.ACTIVE);
        
        // 设置时间戳
        long currentTime = System.currentTimeMillis();
        wallet.setCreateTime(currentTime);
        wallet.setUpdateTime(currentTime);
        
        return wallet;
    }
} 