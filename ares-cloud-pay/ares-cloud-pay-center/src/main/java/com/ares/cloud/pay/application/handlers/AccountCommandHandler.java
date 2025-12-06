package com.ares.cloud.pay.application.handlers;

import com.ares.cloud.pay.application.command.*;
import com.ares.cloud.pay.domain.model.Account;
import com.ares.cloud.pay.domain.service.AccountDomainService;
import com.ares.cloud.pay.domain.enums.PaymentError;
import com.ares.cloud.pay.infrastructure.adapter.UserServiceAdapter;
import jakarta.annotation.Resource;
import org.ares.cloud.api.user.dto.UserDto;
import org.ares.cloud.common.exception.BusinessException;
import org.ares.cloud.common.utils.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 账户命令处理器
 * 处理所有账户相关的命令操作
 */
@Component
public class AccountCommandHandler {

    @Resource
    private AccountDomainService accountDomainService;
    
    @Resource
    private UserServiceAdapter userServiceAdapter;

    /**
     * 处理开通账户命令
     * 通过用户ID和支付密码开通账户，系统会自动从用户服务获取用户信息
     */
    public String handleOpenAccount(String userId, OpenAccountCommand command) {
        // 参数校验
        if (!StringUtils.hasText(userId)) {
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
        
        // 通过用户服务适配器获取用户信息
        UserDto user = userServiceAdapter.getUserById(userId);
        
        // 构建账户领域模型，使用用户信息填充
        Account account = Account.create(
            user.getId(),                    // 用户ID
            command.getPayPassword(),        // 支付密码
            user.getCountryCode(),           // 国家代码
            user.getPhone(),                 // 手机号
            user.getAccount()                // 账号
        );

        // 调用领域服务创建账户
        Account createdAccount = accountDomainService.createAccount(account);
        
        return createdAccount.getId();
    }

    /**
     * 处理更新账户状态命令
     */
    @Transactional
    public void handleUpdateAccountStatus(UpdateAccountStatusCommand command) {
        accountDomainService.updateAccountStatus(command.getAccountId(), command.getNewStatus());
    }

    /**
     * 处理更新账户密码命令
     */
    @Transactional
    public void handleUpdatePassword(UpdatePasswordCommand command) {
        if (!UpdatePasswordCommand.EntityType.ACCOUNT.equals(command.getEntityType())) {
            throw new IllegalArgumentException("Invalid entity type for account command handler");
        }
        accountDomainService.updatePassword(
            command.getEntityId(), 
            command.getOldPassword(), 
            command.getNewPassword()
        );
    }
} 