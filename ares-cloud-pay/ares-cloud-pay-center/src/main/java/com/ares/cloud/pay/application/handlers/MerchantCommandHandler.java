package com.ares.cloud.pay.application.handlers;

import com.ares.cloud.pay.application.command.*;
import com.ares.cloud.pay.domain.model.Merchant;
import com.ares.cloud.pay.domain.service.MerchantDomainService;
import com.ares.cloud.pay.domain.enums.PaymentError;
import com.ares.cloud.pay.domain.repository.MerchantRepository;
import com.ares.cloud.pay.infrastructure.adapter.MerchantServiceAdapter;
import jakarta.annotation.Resource;
import org.ares.cloud.api.merchant.dto.MerchantInfo;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.common.exception.BusinessException;
import org.ares.cloud.common.utils.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 商户命令处理器
 * 处理所有商户相关的命令操作
 */
@Component
public class MerchantCommandHandler {

    @Resource
    private MerchantDomainService merchantDomainService;
    
    @Resource
    private MerchantServiceAdapter merchantServiceAdapter;
    
    @Resource
    private MerchantRepository merchantRepository;



    /**
     * 处理开通商户命令
     * 通过商户ID、支持的支付区域和支付密码开通商户，系统会自动从商户服务获取商户信息
     */
    @Transactional
    public String handleOpenMerchant(OpenMerchantCommand command) {
        String merchantId = ApplicationContext.getTenantId();
        if (!StringUtils.hasText(merchantId)) {
            merchantId = command.getMerchantId();
        }
        // 参数校验
        if (!StringUtils.hasText(merchantId)) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        if (command.getSupportedRegions() == null || command.getSupportedRegions().isEmpty()) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        if (!StringUtils.hasText(command.getPayPassword())) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        if (!StringUtils.hasText(command.getConfirmPayPassword())) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        
        // 验证密码一致性
        if (!command.isPasswordMatch()) {
            throw new BusinessException(PaymentError.PASSWORD_MISMATCH);
        }
        
        // 验证密码格式
        if (!command.isPasswordFormatValid()) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        
        // 验证密码强度
        if (!command.isPasswordStrong()) {
            throw new BusinessException(PaymentError.PASSWORD_TOO_WEAK);
        }

        // 通过商户服务适配器获取商户信息
        MerchantInfo merchantInfo = merchantServiceAdapter.getMerchantById(merchantId);
        
        // 构建商户领域模型，使用商户信息填充
        Merchant merchant = merchantInfoToMerchant(merchantInfo);
        merchant.setSupportedRegions(command.getSupportedRegions());
        merchant.setPayPassword(command.getPayPassword());
        // 调用领域服务创建商户
        Merchant createdMerchant = merchantDomainService.createMerchant(merchant);
        
        return createdMerchant.getId();
    }
    /**
     * 同步更新商户信息
     * 当商户服务中的商户信息发生变化时，调用此方法同步更新支付系统中的商户信息
     *
     * @param merchantId 商户ID
     * @return 更新后的商户ID
     */
    @Transactional
    public String syncMerchantInfo(String merchantId) {
        // 参数校验
        if (!StringUtils.hasText(merchantId)) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }

        // 通过商户服务适配器获取最新的商户信息
        MerchantInfo merchantInfo = merchantServiceAdapter.getMerchantById(merchantId);

        // 检查支付系统中是否已存在该商户
        Merchant existingMerchant = merchantRepository.findById(merchantId);

        if (existingMerchant == null) {
            // 如果商户不存在，则创建新商户
            Merchant merchant = merchantInfoToMerchant(merchantInfo);

            // 调用领域服务创建商户
            Merchant createdMerchant = merchantDomainService.createMerchant(merchant);
            return createdMerchant.getId();
        } else {
            // 如果商户已存在，则更新商户信息
            merchantDomainService.updateMerchantBasicInfo(
                    merchantId,
                    merchantInfo.getName(),                    // 商户名称
                    merchantInfo.getName(),                    // 联系人（使用企业名称）
                    merchantInfo.getPhone(),                   // 联系电话
                    merchantInfo.getEnterpriseEmail(),         // 联系邮箱
                    merchantInfo.getAddress(),                 // 地址
                    merchantInfo.getTaxNum(),                  // 营业执照号（使用税务号）
                    merchantInfo.getName()                     // 法人代表（使用企业名称）
            );

            return merchantId;
        }
    }

    /**
     * 处理更新商户状态命令
     */
    @Transactional
    public void handleUpdateMerchantStatus(UpdateMerchantStatusCommand command) {
        merchantDomainService.updateMerchantStatus(command.getMerchantId(), command.getNewStatus());
    }

    /**
     * 处理更新商户密码命令
     */
    @Transactional
    public void handleUpdatePassword(UpdatePasswordCommand command) {
        if (!UpdatePasswordCommand.EntityType.MERCHANT.equals(command.getEntityType())) {
            throw new IllegalArgumentException("Invalid entity type for merchant command handler");
        }
        merchantDomainService.updatePayPassword(
            command.getEntityId(), 
            command.getOldPassword(), 
            command.getNewPassword()
        );
    }

    /**
     * 处理更新商户区域命令
     */
    @Transactional
    public void handleUpdateMerchantRegions(UpdateMerchantRegionsCommand command) {
        merchantDomainService.updateSupportedRegions(
            command.getMerchantId(), 
            command.getSupportedRegions()
        );
    }

    /**
     * 处理重新生成商户密钥命令
     */
    @Transactional
    public void handleRegenerateMerchantKey(RegenerateMerchantKeyCommand command) {
        merchantDomainService.regenerateMerchantKey(command.getMerchantId());
    }
    /**
     * 将商户信息转换为账户信息
     */
    private Merchant merchantInfoToMerchant(MerchantInfo merchantInfo) {
        Merchant merchant = new Merchant();
        merchant.setId(merchantInfo.getId());
        merchant.setMerchantNo(merchantInfo.getId()); // 使用商户ID作为商户号
        merchant.setMerchantName(merchantInfo.getName());
        merchant.setMerchantType("BUSINESS"); // 默认为企业类型
        merchant.setContactPerson(merchantInfo.getName()); // 使用企业名称作为联系人
        merchant.setContactPhone(merchantInfo.getRegisterPhone());
        merchant.setContactEmail(merchantInfo.getEnterpriseEmail());
        merchant.setAddress(merchantInfo.getAddress());
        merchant.setBusinessLicense(merchantInfo.getTaxNum()); // 使用税务号作为营业执照号
        merchant.setLegalRepresentative(merchantInfo.getName()); // 使用企业名称作为法人代表
        merchant.setCreateTime(System.currentTimeMillis());
        merchant.setUpdateTime(System.currentTimeMillis());
        return merchant;
    }
} 