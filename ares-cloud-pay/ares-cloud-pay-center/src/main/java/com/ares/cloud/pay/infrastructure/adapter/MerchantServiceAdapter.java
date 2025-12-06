package com.ares.cloud.pay.infrastructure.adapter;

import jakarta.annotation.Resource;
import org.ares.cloud.api.merchant.MerchantClient;
import org.ares.cloud.api.merchant.dto.MerchantInfo;
import org.ares.cloud.common.exception.BusinessException;
import com.ares.cloud.pay.domain.enums.PaymentError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * 商户服务适配器
 * 封装商户服务的调用逻辑，提供统一的商户信息获取接口
 */
@Component
public class MerchantServiceAdapter {
    
    private static final Logger logger = LoggerFactory.getLogger(MerchantServiceAdapter.class);
    
    @Resource
    private MerchantClient merchantClient;
    
    /**
     * 根据商户ID获取商户信息
     * 
     * @param merchantId 商户ID
     * @return 商户信息
     * @throws BusinessException 当商户不存在或获取失败时抛出
     */
    public MerchantInfo getMerchantById(String merchantId) {
        if (merchantId == null || merchantId.trim().isEmpty()) {
            logger.warn("获取商户信息失败：商户ID为空");
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        
        logger.debug("开始获取商户信息，商户ID：{}", merchantId);
        
        try {
            MerchantInfo merchant = merchantClient.getMerchantInfoById(merchantId);
            
            // 检查响应内容
            if (merchant == null) {
                logger.warn("获取商户信息失败：商户不存在，商户ID：{}", merchantId);
                throw new BusinessException(PaymentError.MERCHANT_NOT_FOUND);
            }
            
            // 验证商户信息的完整性
            validateMerchantInfo(merchant);
            
            logger.debug("成功获取商户信息，商户ID：{}", merchantId);
            return merchant;
            
        } catch (BusinessException e) {
            logger.warn("获取商户信息失败：{}，商户ID：{}", e.getMessage(), merchantId);
            throw e;
        } catch (Exception e) {
            logger.error("获取商户信息异常：商户ID：{}，异常信息：{}", merchantId, e.getMessage(), e);
            throw new BusinessException(PaymentError.SYSTEM_ERROR);
        }
    }
    
    /**
     * 根据用户ID获取商户信息
     * 
     * @param userId 用户ID
     * @return 商户信息
     * @throws BusinessException 当商户不存在或获取失败时抛出
     */
    public MerchantInfo getMerchantByUserId(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            logger.warn("获取商户信息失败：用户ID为空");
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        
        logger.debug("开始根据用户ID获取商户信息，用户ID：{}", userId);
        
        try {
            MerchantInfo merchant = merchantClient.getMerchantInfoByUserId(userId);
            
            // 检查响应内容
            if (merchant == null) {
                logger.warn("获取商户信息失败：商户不存在，用户ID：{}", userId);
                throw new BusinessException(PaymentError.MERCHANT_NOT_FOUND);
            }
            
            // 验证商户信息的完整性
            validateMerchantInfo(merchant);
            
            logger.debug("成功根据用户ID获取商户信息，用户ID：{}", userId);
            return merchant;
            
        } catch (BusinessException e) {
            logger.warn("获取商户信息失败：{}，用户ID：{}", e.getMessage(), userId);
            throw e;
        } catch (Exception e) {
            logger.error("获取商户信息异常：用户ID：{}，异常信息：{}", userId, e.getMessage(), e);
            throw new BusinessException(PaymentError.SYSTEM_ERROR);
        }
    }
    
    /**
     * 验证商户信息的完整性
     * 
     * @param merchant 商户信息
     * @throws BusinessException 当商户信息不完整时抛出
     */
    private void validateMerchantInfo(MerchantInfo merchant) {
        if (merchant.getId() == null || merchant.getId().trim().isEmpty()) {
            logger.warn("商户信息验证失败：商户ID为空");
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        
        if (merchant.getName() == null || merchant.getName().trim().isEmpty()) {
            logger.warn("商户信息验证失败：商户名称为空，商户ID：{}", merchant.getId());
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        
        if (merchant.getUserId() == null || merchant.getUserId().trim().isEmpty()) {
            logger.warn("商户信息验证失败：用户ID为空，商户ID：{}", merchant.getId());
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
    }
} 