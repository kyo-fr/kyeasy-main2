package com.ares.cloud.pay.application.handlers;

import com.ares.cloud.pay.application.command.*;
import com.ares.cloud.pay.domain.enums.TransactionType;
import org.ares.cloud.api.payment.dto.TransferResultDTO;
import com.ares.cloud.pay.domain.command.*;
import com.ares.cloud.pay.domain.constant.PaymentConstants;
import com.ares.cloud.pay.domain.enums.PaymentError;
import com.ares.cloud.pay.domain.model.Account;
import com.ares.cloud.pay.domain.repository.AccountRepository;
import com.ares.cloud.pay.domain.service.TransferDomainService;
import com.ares.cloud.pay.domain.valueobject.TransferResult;
import com.ares.cloud.pay.infrastructure.adapter.MerchantServiceAdapter;
import jakarta.annotation.Resource;
import org.ares.cloud.api.merchant.dto.MerchantInfo;
import org.ares.cloud.api.payment.command.GenericTransferCommand;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.common.exception.BusinessException;
import org.ares.cloud.common.model.Money;
import org.springframework.stereotype.Component;

/**
 * 转账命令处理器
 * 处理用户间转账、用户向商户付款、商户充值、商户回收、商户向用户发放等命令
 */
@Component
public class TransferCommandHandler {
    
    @Resource
    private TransferDomainService transferDomainService;

    @Resource
    private AccountRepository accountRepository;
    @Resource
    private MerchantServiceAdapter merchantServiceAdapter;
    
    /**
     * 处理用户间转账命令
     *
     * @param command 用户间转账命令
     * @return 转账结果DTO
     */
    public TransferResultDTO handleUserTransfer(UserTransferCommand command) {
        Account account = accountRepository.findByCountryCodeAndPhone(command.getCountryCode(), command.getPhone());
        if (account == null) {
            throw new BusinessException(PaymentError.ACCOUNT_NOT_FOUND);
        }
        // 构建领域命令
        TransferDomainCommand domainCommand = TransferDomainCommand.create(
            ApplicationContext.getUserId(),
            account.getId(),
            Money.of(command.getAmount(),command.getPaymentRegion()),
            command.getPaymentRegion(),
            command.getDescription()
        );
        
        // 调用领域服务执行转账，传入支付密码
        TransferResult result = transferDomainService.transferBetweenUsers(domainCommand, command.getPaymentPassword());
        
        // 转换为DTO
        return convertToTransferResultDTO(result, account.getId(), account.getId(),
            Money.of(command.getAmount(), command.getPaymentRegion()), command.getPaymentRegion(), 
            command.getDescription());
    }
    
    /**
     * 处理用户向商户付款命令
     *
     * @param userId 用户ID
     * @param command 用户向商户付款命令
     * @return 付款结果DTO
     */
    public TransferResultDTO handleUserToMerchantPayment(String userId, UserToMerchantPaymentCommand command) {
        // 构建领域命令
        MerchantPaymentDomainCommand domainCommand = MerchantPaymentDomainCommand.create(userId,
            command.getMerchantId(),
            Money.of(command.getAmount(),command.getPaymentRegion()),
            command.getPaymentRegion(),
            command.getDescription()
        );
        
        // 调用领域服务执行向商户付款，传入支付密码
        TransferResult result = transferDomainService.payToMerchant(domainCommand, command.getPaymentPassword());
        
        // 转换为DTO
        return convertToTransferResultDTO(result, userId, command.getMerchantId(), 
            Money.of(command.getAmount(), command.getPaymentRegion()), command.getPaymentRegion(), 
            command.getDescription());
    }

    /**
     * 处理商户充值命令
     *
     * @param command 商户充值命令
     * @return 充值结果DTO
     */
    public TransferResultDTO handleMerchantRecharge(MerchantRechargeCommand command) {
        // 获取当前商户信息
        MerchantInfo merchant = getMerchant();
        
        // 构建领域命令
        MerchantRechargeDomainCommand domainCommand =
            MerchantRechargeDomainCommand.create(
                merchant.getId(),
                Money.of(command.getAmount(), command.getPaymentRegion()),
                command.getPaymentRegion(),
                command.getDescription()
            );
        
        // 调用领域服务执行商户充值
        TransferResult result = transferDomainService.merchantRecharge(domainCommand);
        
        // 转换为DTO
        return convertToTransferResultDTO(result, PaymentConstants.PLATFORM_MERCHANT_ID, merchant.getId(),
            Money.of(command.getAmount(), command.getPaymentRegion()), command.getPaymentRegion(), 
            command.getDescription());
    }
    
    /**
     * 处理商户回收命令
     *
     * @param command 商户回收命令
     * @return 回收结果DTO
     */
    public TransferResultDTO handleMerchantDeduction(MerchantDeductionCommand command) {
        // 获取当前商户信息
        MerchantInfo merchant = getMerchant();
        
        // 构建领域命令
        MerchantDeductionDomainCommand domainCommand =
            MerchantDeductionDomainCommand.create(
                merchant.getId(),
                Money.of(command.getAmount(), command.getPaymentRegion()),
                command.getPaymentRegion(),
                command.getDescription()
            );
        
        // 调用领域服务执行商户回收
        TransferResult result = transferDomainService.merchantDeduction(domainCommand);
        
        // 转换为DTO
        return convertToTransferResultDTO(result, merchant.getId(), PaymentConstants.PLATFORM_MERCHANT_ID,
            Money.of(command.getAmount(), command.getPaymentRegion()), command.getPaymentRegion(), 
            command.getDescription());
    }
    
    /**
     * 处理商户向用户发放礼物点命令
     *
     * @param command 商户向用户发放命令
     * @return 发放结果DTO
     */
    public TransferResultDTO handleMerchantToUserPayment(MerchantToUserPaymentCommand command) {
        // 获取当前商户信息
        MerchantInfo merchant = getMerchant();
        
        // 根据国家代码和手机号查找用户账户
        Account userAccount = accountRepository.findByCountryCodeAndPhone(command.getCountryCode(), command.getPhone());
        if (userAccount == null) {
            throw new BusinessException(PaymentError.ACCOUNT_NOT_FOUND);
        }
        
        // 构建领域命令
        MerchantToUserPaymentDomainCommand domainCommand =
            MerchantToUserPaymentDomainCommand.create(
                merchant.getId(),
                userAccount.getId(),
                Money.of(command.getAmount(), command.getPaymentRegion()),
                command.getPaymentRegion(),
                command.getDescription()
            );
        
        // 调用领域服务执行商户向用户发放
        TransferResult result = transferDomainService.merchantToUserPayment(domainCommand);
        
        // 转换为DTO
        return convertToTransferResultDTO(result, merchant.getId(), userAccount.getId(), 
            Money.of(command.getAmount(), command.getPaymentRegion()), command.getPaymentRegion(), 
            command.getDescription());
    }
    
    /**
     * 处理通用转账命令
     *
     * @param command 通用转账命令
     * @return 转账结果DTO
     */
    public TransferResultDTO handleGenericTransfer(GenericTransferCommand command) {
        // 构建领域命令
        GenericTransferDomainCommand domainCommand = GenericTransferDomainCommand.create(
            command.getFromAccountId(),
            command.getToAccountId(),
            Money.of(command.getAmount(), command.getPaymentRegion()),
            command.getPaymentRegion(),
            command.getTransactionType(),
            command.getDescription(),
            command.getRequirePaymentPassword(),
            command.getFeeBearer()
        );
        
        // 调用领域服务执行通用转账
        TransferResult result = transferDomainService.genericTransfer(domainCommand, command.getPaymentPassword());
        
        // 转换为DTO
        return convertToTransferResultDTO(result, command.getFromAccountId(), command.getToAccountId(),
            Money.of(command.getAmount(), command.getPaymentRegion()), command.getPaymentRegion(), 
            command.getDescription());
    }


    /**
     * 获取当前商户信息
     *
     * @return 商户信息
     */
    private MerchantInfo getMerchant() {
        MerchantInfo merchant = merchantServiceAdapter.getMerchantByUserId(ApplicationContext.getUserId());
        if (merchant == null) {
            throw new BusinessException(PaymentError.MERCHANT_NOT_FOUND);
        }
        return merchant;
    }
    
    /**
     * 处理商户优惠减免命令（内部调用，无需验证支付密码）
     * 商户结算时给用户的百分比优惠减免，手续费5%由用户承担
     * 本质是商户向用户转账
     * 
     * 注意：此方法用于系统内部调用，不验证支付密码
     * 调用方应该确保已经通过其他方式验证了操作的合法性
     *
     * @param command 商户优惠减免命令
     * @return 转账结果DTO
     */
    public TransferResultDTO handleMerchantDiscount(org.ares.cloud.api.payment.command.MerchantDiscountCommand command) {
        // 构建商户向用户转账命令
        MerchantToUserPaymentDomainCommand domainCommand = MerchantToUserPaymentDomainCommand.create(
            command.getMerchantId(),
            command.getUserId(),
            Money.of(command.getAmount(), command.getPaymentRegion()),
            command.getPaymentRegion(),
            command.getDescription() != null ? command.getDescription() : "商户优惠减免"
        );
        
        // 调用商户向用户转账领域服务（内部调用，无需支付密码）
        // 注意：商户优惠减免使用特定的交易类型和手续费规则
        TransferResult result = transferDomainService.merchantDiscountToUser(
            domainCommand, 
            TransactionType.MERCHANT_DISCOUNT_REDUCTION
        );
        
        // 转换为DTO
        return convertToTransferResultDTO(result, command.getMerchantId(), command.getUserId(),
            Money.of(command.getAmount(), command.getPaymentRegion()), command.getPaymentRegion(), 
            command.getDescription());
    }
    
    /**
     * 处理商户减价命令（内部调用，无需验证支付密码）
     * 商户结算时给用户的具体金额减价，手续费5%由用户承担
     * 本质是商户向用户转账
     * 
     * 注意：此方法用于系统内部调用，不验证支付密码
     * 调用方应该确保已经通过其他方式验证了操作的合法性
     *
     * @param command 商户减价命令
     * @return 转账结果DTO
     */
    public TransferResultDTO handleMerchantPriceReduction(org.ares.cloud.api.payment.command.MerchantPriceReductionCommand command) {
        // 构建商户向用户转账命令
        MerchantToUserPaymentDomainCommand domainCommand = MerchantToUserPaymentDomainCommand.create(
            command.getMerchantId(),
            command.getUserId(),
            Money.of(command.getAmount(), command.getPaymentRegion()),
            command.getPaymentRegion(),
            command.getDescription() != null ? command.getDescription() : "商户减价"
        );
        
        // 调用商户向用户转账领域服务（内部调用，无需支付密码）
        // 注意：商户减价使用特定的交易类型和手续费规则
        TransferResult result = transferDomainService.merchantPriceReductionToUser(
            domainCommand,
            TransactionType.MERCHANT_PRICE_REDUCTION
        );
        
        // 转换为DTO
        return convertToTransferResultDTO(result, command.getMerchantId(), command.getUserId(),
            Money.of(command.getAmount(), command.getPaymentRegion()), command.getPaymentRegion(), 
            command.getDescription());
    }
    
    /**
     * 将TransferResult转换为TransferResultDTO
     */
    private TransferResultDTO convertToTransferResultDTO(TransferResult result, String fromId, String toId, 
                                                        Money amount, String paymentRegion, String description) {
        TransferResultDTO dto = new TransferResultDTO();
        dto.setTransactionId(result.getTransactionId());
        dto.setAmount(amount);
        dto.setPaymentRegion(paymentRegion);
        dto.setDescription(description);
        dto.setStatus("SUCCESS");
        dto.setMessage(result.getMessage());
        dto.setFromId(fromId);
        dto.setToId(toId);
        dto.setTransactionTime(System.currentTimeMillis());
        return dto;
    }
} 