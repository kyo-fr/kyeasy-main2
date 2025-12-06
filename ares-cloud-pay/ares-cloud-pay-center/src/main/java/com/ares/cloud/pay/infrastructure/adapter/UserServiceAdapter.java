package com.ares.cloud.pay.infrastructure.adapter;

import jakarta.annotation.Resource;
import org.ares.cloud.api.user.UserServerClient;
import org.ares.cloud.api.user.dto.UserDto;
import org.ares.cloud.common.exception.BusinessException;
import com.ares.cloud.pay.domain.enums.PaymentError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * 用户服务适配器
 * 封装用户服务的调用逻辑，提供统一的用户信息获取接口
 */
@Component
public class UserServiceAdapter {
    
    private static final Logger logger = LoggerFactory.getLogger(UserServiceAdapter.class);
    
    @Resource
    private UserServerClient userServerClient;
    
    /**
     * 根据用户ID获取用户信息
     * 
     * @param userId 用户ID
     * @return 用户信息
     * @throws BusinessException 当用户不存在或获取失败时抛出
     */
    public UserDto getUserById(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            logger.warn("获取用户信息失败：用户ID为空");
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        
        logger.debug("开始获取用户信息，用户ID：{}", userId);
        
        try {
            UserDto user = userServerClient.get(userId);
            
            // 检查响应内容
            if (user == null) {
                logger.warn("获取用户信息失败：用户不存在，用户ID：{}", userId);
                throw new BusinessException(PaymentError.ACCOUNT_NOT_FOUND);
            }
            
            // 验证用户信息的完整性
            validateUserInfo(user);
            
            logger.debug("成功获取用户信息，用户ID：{}", userId);
            return user;
            
        } catch (BusinessException e) {
            logger.warn("获取用户信息失败：{}，用户ID：{}", e.getMessage(), userId);
            throw e;
        } catch (Exception e) {
            logger.error("获取用户信息异常：用户ID：{}，异常信息：{}", userId, e.getMessage(), e);
            throw new BusinessException(PaymentError.SYSTEM_ERROR);
        }
    }
    
    /**
     * 验证用户信息的完整性
     * 
     * @param user 用户信息
     * @throws BusinessException 当用户信息不完整时抛出
     */
    private void validateUserInfo(UserDto user) {
        if (user.getId() == null || user.getId().trim().isEmpty()) {
            logger.warn("用户信息验证失败：用户ID为空");
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        
        if (user.getCountryCode() == null || user.getCountryCode().trim().isEmpty()) {
            logger.warn("用户信息验证失败：国家代码为空，用户ID：{}", user.getId());
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        
        if (user.getPhone() == null || user.getPhone().trim().isEmpty()) {
            logger.warn("用户信息验证失败：手机号为空，用户ID：{}", user.getId());
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
    }
} 