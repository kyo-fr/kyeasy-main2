package com.ares.cloud.pay.domain.service;

import com.ares.cloud.pay.domain.command.TransferDomainCommand;
import com.ares.cloud.pay.domain.command.MerchantPaymentDomainCommand;
import com.ares.cloud.pay.domain.command.MerchantRechargeDomainCommand;
import com.ares.cloud.pay.domain.command.MerchantDeductionDomainCommand;
import com.ares.cloud.pay.domain.command.MerchantToUserPaymentDomainCommand;
import com.ares.cloud.pay.domain.command.GenericTransferDomainCommand;
import com.ares.cloud.pay.domain.constant.PaymentConstants;
import com.ares.cloud.pay.domain.enums.PaymentError;
import com.ares.cloud.pay.domain.model.Account;
import com.ares.cloud.pay.domain.model.Merchant;
import com.ares.cloud.pay.domain.model.Wallet;
import com.ares.cloud.pay.domain.model.Transaction;
import com.ares.cloud.pay.domain.repository.AccountRepository;
import com.ares.cloud.pay.domain.repository.MerchantRepository;
import com.ares.cloud.pay.domain.repository.WalletRepository;
import com.ares.cloud.pay.domain.repository.TransactionRepository;
import com.ares.cloud.pay.domain.repository.AccountFlowRepository;
import com.ares.cloud.pay.domain.model.AccountFlow;
import com.ares.cloud.pay.domain.valueobject.TransferResult;
import jakarta.annotation.Resource;
import org.ares.cloud.api.LockApi;
import org.ares.cloud.common.exception.BusinessException;
import org.ares.cloud.common.model.Money;
import org.ares.cloud.common.utils.IdUtils;
import org.ares.cloud.common.utils.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ares.cloud.pay.domain.event.TransferSuccessEvent;
import com.ares.cloud.pay.domain.event.WalletBalanceChangedEvent;
import org.springframework.context.ApplicationEventPublisher;
import com.ares.cloud.pay.domain.enums.TransactionType;
import com.ares.cloud.pay.domain.enums.FlowType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;


/**
 * 转账领域服务
 * 负责处理用户之间的转账、商户付款、商户充值、商户回收、商户向用户发放等业务逻辑
 */
@Service
public class TransferDomainService {
    
    private static final Logger log = LoggerFactory.getLogger(TransferDomainService.class);
    
    @Resource
    private AccountRepository accountRepository;
    
    @Resource
    private MerchantRepository merchantRepository;
    
    @Resource
    private WalletRepository walletRepository;
    
    @Resource
    private TransactionRepository transactionRepository;
    
    @Resource
    private AccountFlowRepository accountFlowRepository;
    
    @Resource
    private LockApi lockApi;
    
    @Resource
    private PasswordEncoder passwordEncoder;
    
    @Resource
    private ApplicationEventPublisher eventPublisher;
    
    // ==================== 公有方法 ====================
    
    /**
     * 用户间转账
     * 使用严格的双方都加锁确保资金安全，锁粒度细化到paymentRegion
     * 
     * @param command 转账命令
     * @param paymentPassword 支付密码
     * @return 转账结果
     */
    @Transactional
    public TransferResult transferBetweenUsers(TransferDomainCommand command, String paymentPassword) {
        
        // 生成锁key，按paymentRegion加锁，提高并发性能
        String fromLockKey = "transfer:user:" + command.getFromUserId() + ":" + command.getPaymentRegion();
        String toLockKey = "transfer:user:" + command.getToUserId() + ":" + command.getPaymentRegion();
        
        // 按照用户ID排序，避免死锁
        String firstLockKey, secondLockKey;
        
        if (command.getFromUserId().compareTo(command.getToUserId()) < 0) {
            firstLockKey = fromLockKey;
            secondLockKey = toLockKey;
        } else {
            firstLockKey = toLockKey;
            secondLockKey = fromLockKey;
        }
        
        return lockApi.lockExecute(firstLockKey, new LockApi.LockExecute<TransferResult>() {
            @Override
            public TransferResult execute() {
                // 获取第一个锁后，尝试获取第二个锁
                return lockApi.lockExecute(secondLockKey, new LockApi.LockExecute<TransferResult>() {
                    @Override
                    public TransferResult execute() {
                        // 两个锁都获取成功，执行转账业务逻辑
                        return doTransferBetweenUsers(command, paymentPassword);
                    }
                    
                    @Override
                    public TransferResult waitTimeOut() {
                        throw new BusinessException(PaymentError.TRANSACTION_TIMEOUT);
                    }
                });
            }
            
            @Override
            public TransferResult waitTimeOut() {
                throw new BusinessException(PaymentError.TRANSACTION_TIMEOUT);
            }
        });
    }
    
    /**
     * 向商户付款
     * 使用分布式锁确保资金安全，锁粒度细化到paymentRegion
     * 
     * @param command 向商户付款命令
     * @param paymentPassword 支付密码
     * @return 付款结果
     */
    @Transactional
    public TransferResult payToMerchant(MerchantPaymentDomainCommand command, String paymentPassword) {
        
        // 生成锁key，按paymentRegion加锁，提高并发性能
        String fromLockKey = "payment:user:" + command.getFromUserId() + ":" + command.getPaymentRegion();
        String merchantLockKey = "payment:merchant:" + command.getMerchantId() + ":" + command.getPaymentRegion();
        
        // 按照ID排序，避免死锁
        String firstLockKey, secondLockKey;
        
        if (command.getFromUserId().compareTo(command.getMerchantId()) < 0) {
            firstLockKey = fromLockKey;
            secondLockKey = merchantLockKey;
        } else {
            firstLockKey = merchantLockKey;
            secondLockKey = fromLockKey;
        }
        
        return lockApi.lockExecute(firstLockKey, new LockApi.LockExecute<TransferResult>() {
            @Override
            public TransferResult execute() {
                return lockApi.lockExecute(secondLockKey, new LockApi.LockExecute<TransferResult>() {
                    @Override
                    public TransferResult execute() {
                        return doPayToMerchant(command,paymentPassword);
                    }
                    
                    @Override
                    public TransferResult waitTimeOut() {
                        throw new BusinessException(PaymentError.TRANSACTION_TIMEOUT);
                    }
                });
            }
            
            @Override
            public TransferResult waitTimeOut() {
                throw new BusinessException(PaymentError.TRANSACTION_TIMEOUT);
            }
        });
    }

    /**
     * 商户向用户发放
     * 使用分布式锁确保资金安全，锁粒度细化到paymentRegion
     * 
     * @param command 商户向用户发放命令
     * @return 发放结果
     */
    @Transactional
    public TransferResult merchantToUserPayment(MerchantToUserPaymentDomainCommand command) {
        // 生成锁key，按paymentRegion加锁，提高并发性能
        String merchantLockKey = "payment:merchant:" + command.getMerchantId() + ":" + command.getPaymentRegion();
        String userLockKey = "payment:user:" + command.getUserId() + ":" + command.getPaymentRegion();
        
        // 按照ID排序，避免死锁
        String firstLockKey, secondLockKey;
        
        if (command.getMerchantId().compareTo(command.getUserId()) < 0) {
            firstLockKey = merchantLockKey;
            secondLockKey = userLockKey;
        } else {
            firstLockKey = userLockKey;
            secondLockKey = merchantLockKey;
        }
        
        return lockApi.lockExecute(firstLockKey, new LockApi.LockExecute<TransferResult>() {
            @Override
            public TransferResult execute() {
                return lockApi.lockExecute(secondLockKey, new LockApi.LockExecute<TransferResult>() {
                    @Override
                    public TransferResult execute() {
                        return doMerchantToUserPayment(command);
                    }
                    
                    @Override
                    public TransferResult waitTimeOut() {
                        throw new BusinessException(PaymentError.TRANSACTION_TIMEOUT);
                    }
                });
            }
            
            @Override
            public TransferResult waitTimeOut() {
                throw new BusinessException(PaymentError.TRANSACTION_TIMEOUT);
            }
        });
    }
    
    /**
     * 商户充值（从平台商户划拨到普通商户）
     * 使用分布式锁确保资金安全，锁粒度细化到paymentRegion
     * 
     * @param command 商户充值命令
     * @return 充值结果
     */
    @Transactional
    public TransferResult merchantRecharge(MerchantRechargeDomainCommand command) {
        // 生成锁key，按paymentRegion加锁，提高并发性能
        String platformLockKey = "recharge:platform:" + command.getPaymentRegion();
        String userLockKey = "recharge:user:" + command.getUserId() + ":" + command.getPaymentRegion();
        
        // 按照ID排序，避免死锁
        String firstLockKey, secondLockKey;
        
        if (PaymentConstants.PLATFORM_MERCHANT_ID.compareTo(command.getUserId()) < 0) {
            firstLockKey = platformLockKey;
            secondLockKey = userLockKey;
        } else {
            firstLockKey = userLockKey;
            secondLockKey = platformLockKey;
        }
        
        return lockApi.lockExecute(firstLockKey, new LockApi.LockExecute<TransferResult>() {
            @Override
            public TransferResult execute() {
                return lockApi.lockExecute(secondLockKey, new LockApi.LockExecute<TransferResult>() {
                    @Override
                    public TransferResult execute() {
                        return doMerchantRecharge(command);
                    }
                    
                    @Override
                    public TransferResult waitTimeOut() {
                        throw new BusinessException(PaymentError.TRANSACTION_TIMEOUT);
                    }
                });
            }
            
            @Override
            public TransferResult waitTimeOut() {
                throw new BusinessException(PaymentError.TRANSACTION_TIMEOUT);
            }
        });
    }
    
    /**
     * 商户回收（从普通商户划拨到平台商户）
     * 使用分布式锁确保资金安全，锁粒度细化到paymentRegion
     * 
     * @param command 商户回收命令
     * @return 回收结果
     */
    @Transactional
    public TransferResult merchantDeduction(MerchantDeductionDomainCommand command) {
        // 生成锁key，按paymentRegion加锁，提高并发性能
        String platformLockKey = "deduction:platform:" + command.getPaymentRegion();
        String userLockKey = "deduction:user:" + command.getUserId() + ":" + command.getPaymentRegion();
        
        // 按照ID排序，避免死锁
        String firstLockKey, secondLockKey;
        
        if (PaymentConstants.PLATFORM_MERCHANT_ID.compareTo(command.getUserId()) < 0) {
            firstLockKey = platformLockKey;
            secondLockKey = userLockKey;
        } else {
            firstLockKey = userLockKey;
            secondLockKey = platformLockKey;
        }
        
        return lockApi.lockExecute(firstLockKey, new LockApi.LockExecute<TransferResult>() {
            @Override
            public TransferResult execute() {
                return lockApi.lockExecute(secondLockKey, new LockApi.LockExecute<TransferResult>() {
                    @Override
                    public TransferResult execute() {
                        return doMerchantDeduction(command);
                    }
                    
                    @Override
                    public TransferResult waitTimeOut() {
                        throw new BusinessException(PaymentError.TRANSACTION_TIMEOUT);
                    }
                });
            }
            
            @Override
            public TransferResult waitTimeOut() {
                throw new BusinessException(PaymentError.TRANSACTION_TIMEOUT);
            }
        });
    }
    
    /**
     * 商户优惠减免给用户（内部调用，无需验证支付密码）
     * 商户结算时给用户的百分比优惠减免，手续费5%由用户（接收方）承担
     * 
     * 注意：此方法用于系统内部调用，不验证支付密码
     * 
     * @param command 商户向用户转账命令
     * @param transactionType 交易类型
     * @return 转账结果
     */
    @Transactional
    public TransferResult merchantDiscountToUser(MerchantToUserPaymentDomainCommand command, 
                                                TransactionType transactionType) {
        // 生成锁key，按paymentRegion加锁，提高并发性能
        String merchantLockKey = "payment:merchant:" + command.getMerchantId() + ":" + command.getPaymentRegion();
        String userLockKey = "payment:user:" + command.getUserId() + ":" + command.getPaymentRegion();
        
        // 按照ID排序，避免死锁
        String firstLockKey, secondLockKey;
        
        if (command.getMerchantId().compareTo(command.getUserId()) < 0) {
            firstLockKey = merchantLockKey;
            secondLockKey = userLockKey;
        } else {
            firstLockKey = userLockKey;
            secondLockKey = merchantLockKey;
        }
        
        return lockApi.lockExecute(firstLockKey, new LockApi.LockExecute<TransferResult>() {
            @Override
            public TransferResult execute() {
                return lockApi.lockExecute(secondLockKey, new LockApi.LockExecute<TransferResult>() {
                    @Override
                    public TransferResult execute() {
                        return doMerchantDiscountOrReductionToUser(command, transactionType, true);
                    }
                    
                    @Override
                    public TransferResult waitTimeOut() {
                        throw new BusinessException(PaymentError.TRANSACTION_TIMEOUT);
                    }
                });
            }
            
            @Override
            public TransferResult waitTimeOut() {
                throw new BusinessException(PaymentError.TRANSACTION_TIMEOUT);
            }
        });
    }
    
    /**
     * 商户减价给用户（内部调用，无需验证支付密码）
     * 商户结算时给用户的具体金额减价，手续费5%由用户（接收方）承担
     * 
     * 注意：此方法用于系统内部调用，不验证支付密码
     * 
     * @param command 商户向用户转账命令
     * @param transactionType 交易类型
     * @return 转账结果
     */
    @Transactional
    public TransferResult merchantPriceReductionToUser(MerchantToUserPaymentDomainCommand command,
                                                       TransactionType transactionType) {
        // 生成锁key，按paymentRegion加锁，提高并发性能
        String merchantLockKey = "payment:merchant:" + command.getMerchantId() + ":" + command.getPaymentRegion();
        String userLockKey = "payment:user:" + command.getUserId() + ":" + command.getPaymentRegion();
        
        // 按照ID排序，避免死锁
        String firstLockKey, secondLockKey;
        
        if (command.getMerchantId().compareTo(command.getUserId()) < 0) {
            firstLockKey = merchantLockKey;
            secondLockKey = userLockKey;
        } else {
            firstLockKey = userLockKey;
            secondLockKey = merchantLockKey;
        }
        
        return lockApi.lockExecute(firstLockKey, new LockApi.LockExecute<TransferResult>() {
            @Override
            public TransferResult execute() {
                return lockApi.lockExecute(secondLockKey, new LockApi.LockExecute<TransferResult>() {
                    @Override
                    public TransferResult execute() {
                        return doMerchantDiscountOrReductionToUser(command, transactionType, false);
                    }
                    
                    @Override
                    public TransferResult waitTimeOut() {
                        throw new BusinessException(PaymentError.TRANSACTION_TIMEOUT);
                    }
                });
            }
            
            @Override
            public TransferResult waitTimeOut() {
                throw new BusinessException(PaymentError.TRANSACTION_TIMEOUT);
            }
        });
    }
    
    /**
     * 通用转账方法（已废弃，不推荐使用）
     * 
     * ⚠️ 警告：此方法绕过了业务规则约束，可能导致不符合业务逻辑的转账
     * 
     * 建议使用专门的转账方法：
     * - 用户向用户：transferBetweenUsers
     * - 用户向商户：payToMerchant / shoppingPayment
     * - 商户向用户：merchantToUserPayment / merchantDiscountToUser / merchantPriceReductionToUser
     * - 商户购买：merchantRecharge
     * - 商户出售：merchantDeduction
     * 
     * @param command 通用转账命令
     * @param paymentPassword 支付密码（如果需要验证）
     * @return 转账结果
     * @deprecated 不推荐使用，请使用具体的转账方法
     */
    @Deprecated
    @Transactional
    public TransferResult genericTransfer(GenericTransferDomainCommand command, String paymentPassword) {
        // 生成锁key，按paymentRegion加锁，提高并发性能
        String fromLockKey = "transfer:generic:" + command.getFromAccountId() + ":" + command.getPaymentRegion();
        String toLockKey = "transfer:generic:" + command.getToAccountId() + ":" + command.getPaymentRegion();
        
        // 按照账户ID排序，避免死锁
        String firstLockKey, secondLockKey;
        
        if (command.getFromAccountId().compareTo(command.getToAccountId()) < 0) {
            firstLockKey = fromLockKey;
            secondLockKey = toLockKey;
        } else {
            firstLockKey = toLockKey;
            secondLockKey = fromLockKey;
        }
        
        return lockApi.lockExecute(firstLockKey, new LockApi.LockExecute<TransferResult>() {
            @Override
            public TransferResult execute() {
                return lockApi.lockExecute(secondLockKey, new LockApi.LockExecute<TransferResult>() {
                    @Override
                    public TransferResult execute() {
                        return doGenericTransfer(command, paymentPassword);
                    }
                    
                    @Override
                    public TransferResult waitTimeOut() {
                        throw new BusinessException(PaymentError.TRANSACTION_TIMEOUT);
                    }
                });
            }
            
            @Override
            public TransferResult waitTimeOut() {
                throw new BusinessException(PaymentError.TRANSACTION_TIMEOUT);
            }
        });
    }
    
    // ==================== 私有方法 ====================
    
    /**
     * 验证支付密码
     */
    private void validatePaymentPassword(Account account, String paymentPassword) {
        if (!StringUtils.hasText(paymentPassword)) {
            throw new BusinessException(PaymentError.PAYMENT_PASSWORD_REQUIRED);
        }

        // 验证密码 - 如果没有设置支付密码，则使用登录密码
        String storedPassword = account.getPayPassword() != null ? account.getPayPassword() : account.getPassword();
        if (storedPassword == null) {
            throw new BusinessException(PaymentError.INVALID_PAYMENT_PASSWORD);
        }
        
        if (!passwordEncoder.matches(paymentPassword, storedPassword)) {
            throw new BusinessException(PaymentError.INVALID_PAYMENT_PASSWORD);
        }
    }
    
    /**
     * 计算手续费
     * 直接使用 TransactionType 枚举中定义的手续费率
     * 注意：手续费需要写死，税率会变动
     */
    private Money calculateFee(Money amount, String transactionTypeCode, String paymentRegion) {
        // 从枚举中获取交易类型
        TransactionType transactionType = TransactionType.fromCode(transactionTypeCode);
        if (transactionType == null) {
            log.warn("未知的交易类型: {}, 使用免手续费", transactionTypeCode);
            return Money.zeroMoney(paymentRegion);
        }
        
        // 如果没有手续费，直接返回零
        if (!transactionType.hasFee()) {
            log.info("交易类型无手续费 - 交易类型: {}, 支付区域: {}", transactionTypeCode, paymentRegion);
            return Money.zeroMoney(paymentRegion);
        }
        
        // 使用枚举中定义的手续费率计算
        BigDecimal feeRate = transactionType.getFeeRate(); // 百分比，如 0.10 表示 0.10%
        Money feeAmount = amount.multiply(feeRate.divide(new BigDecimal("100"), 10, java.math.RoundingMode.HALF_UP));
        
        log.info("手续费计算完成 - 交易类型: {}, 支付区域: {}, 手续费率: {}%, 手续费金额: {}", 
                transactionTypeCode, paymentRegion, feeRate, feeAmount);
        
        return feeAmount;
    }
    
    /**
     * 获取交易类型的手续费率（万分比）
     * 从 TransactionType 枚举中获取
     */
    private Integer getFeeRate(String transactionTypeCode) {
        TransactionType transactionType = TransactionType.fromCode(transactionTypeCode);
        if (transactionType == null) {
            return 0;
        }
        return transactionType.getFeeRateInBasisPoints();
    }
    
    /**
     * 根据交易类型确定流水类型
     * @param transactionType 交易类型
     * @param isFromAccount 是否为转出账户
     * @return 流水类型
     */
    private String determineFlowType(String transactionType, boolean isFromAccount) {
        // 对于转出账户，总是 OUT
        if (isFromAccount) {
            return FlowType.OUT.getCode();
        }
        
        // 对于转入账户，总是 IN
        return FlowType.IN.getCode();
    }
    
    /**
     * 执行通用转账业务逻辑
     */
    private TransferResult doGenericTransfer(GenericTransferDomainCommand command, String paymentPassword) {
        // 查询转出账户
        Account fromAccount = accountRepository.findById(command.getFromAccountId());
        if (fromAccount == null) {
            throw new BusinessException(PaymentError.ACCOUNT_NOT_FOUND);
        }
        
        // 如果需要验证支付密码，则验证
        if (command.getRequirePaymentPassword()) {
            validatePaymentPassword(fromAccount, paymentPassword);
        }
        
        // 查询转入账户
        Account toAccount = accountRepository.findById(command.getToAccountId());
        if (toAccount == null) {
            throw new BusinessException(PaymentError.ACCOUNT_NOT_FOUND);
        }
        
        // 查询转出钱包
        Wallet fromWallet = walletRepository.findByOwnerIdAndRegion(fromAccount.getId(), command.getPaymentRegion());
        if (fromWallet == null) {
            throw new BusinessException(PaymentError.WALLET_NOT_FOUND);
        }
        
        // 查询转入钱包
        Wallet toWallet = walletRepository.findByOwnerIdAndRegion(toAccount.getId(), command.getPaymentRegion());
        if (toWallet == null) {
            throw new BusinessException(PaymentError.WALLET_NOT_FOUND);
        }
        
        // 校验账户状态
        validateAccountStatus(fromAccount, "转出账户");
        validateAccountStatus(toAccount, "转入账户");
        
        // 校验钱包状态
        validateWalletStatus(fromWallet, "转出钱包");
        validateWalletStatus(toWallet, "转入钱包");
        
        // 校验余额
        Money transferAmount = command.getAmount();
        if (!fromWallet.hasEnoughBalance(transferAmount)) {
            throw new BusinessException(PaymentError.ACCOUNT_BALANCE_INSUFFICIENT);
        }
        
        // 计算手续费
        Money feeAmount = calculateFee(transferAmount, command.getTransactionType(), command.getPaymentRegion());
        
        // 根据手续费承担方计算实际到账金额
        Money actualAmount;
        Money fromActualAmount;
        Money toActualAmount;
        
        if ("FROM".equals(command.getFeeBearer())) {
            // 转出方承担手续费
            fromActualAmount = transferAmount.add(feeAmount);
            toActualAmount = transferAmount;
            actualAmount = transferAmount;
        } else {
            // 转入方承担手续费（默认）
            fromActualAmount = transferAmount;
            toActualAmount = transferAmount.subtract(feeAmount);
            actualAmount = toActualAmount;
        }
        
        // 再次校验转出方余额（考虑手续费）
        if (!fromWallet.hasEnoughBalance(fromActualAmount)) {
            throw new BusinessException(PaymentError.ACCOUNT_BALANCE_INSUFFICIENT);
        }
        
        // 保存旧余额用于事件发布
        Money oldFromBalance = fromWallet.getBalance();
        Money oldToBalance = toWallet.getBalance();
        
        // 执行转账
        fromWallet.decreaseBalance(fromActualAmount);
        toWallet.increaseBalance(toActualAmount);
        
        // 保存钱包
        walletRepository.updateBalance(fromWallet.getId(), fromWallet.getBalance());
        walletRepository.updateBalance(toWallet.getId(), toWallet.getBalance());
        
        // 创建交易记录
        Integer feeRate = getFeeRate(command.getTransactionType());
        Transaction transaction = createTransaction(
            fromAccount.getId(), 
            toAccount.getId(), 
            transferAmount, 
            command.getPaymentRegion(),
            command.getDescription(),
            TransactionType.fromCode(command.getTransactionType()),
            feeAmount,
            feeRate,
            actualAmount
        );
        
        transactionRepository.save(transaction);
        
        // 保存账户流水（转出、转入）
        AccountFlow outFlow = AccountFlow.create(
            fromAccount.getId(),
            transaction.getId(),
            determineFlowType(command.getTransactionType(), true), // 转出账户
            command.getTransactionType(),
            fromActualAmount,
            oldFromBalance,
            fromWallet.getBalance(),
            "FROM".equals(command.getFeeBearer()) ? feeAmount : Money.zeroMoney(command.getPaymentRegion()),
            "FROM".equals(command.getFeeBearer()) ? feeRate : 0,
            fromActualAmount
        );
        AccountFlow inFlow = AccountFlow.create(
            toAccount.getId(),
            transaction.getId(),
            determineFlowType(command.getTransactionType(), false), // 转入账户
            command.getTransactionType(),
            transferAmount,
            oldToBalance,
            toWallet.getBalance(),
            "TO".equals(command.getFeeBearer()) ? feeAmount : Money.zeroMoney(command.getPaymentRegion()),
            "TO".equals(command.getFeeBearer()) ? feeRate : 0,
            toActualAmount
        );
        accountFlowRepository.saveBatch(java.util.Arrays.asList(outFlow, inFlow));
        
        // 发布转账成功事件
        eventPublisher.publishEvent(new TransferSuccessEvent(
            transaction.getId(),
            fromAccount.getId(),
            toAccount.getId(),
            transferAmount,
            command.getPaymentRegion(),
            command.getTransactionType(),
            command.getDescription()
        ));
        
        // 发布钱包余额变更事件
        eventPublisher.publishEvent(new WalletBalanceChangedEvent(
            fromWallet, oldFromBalance, fromActualAmount, "DECREASE", transaction.getId()));
        eventPublisher.publishEvent(new WalletBalanceChangedEvent(
            toWallet, oldToBalance, toActualAmount, "INCREASE", transaction.getId()));
        
        return TransferResult.success(transaction.getId(), "转账成功");
    }
    
    /**
     * 执行用户间转账业务逻辑
     */
    private TransferResult doTransferBetweenUsers(TransferDomainCommand command, String paymentPassword) {
        // 查询转出账户
        Account fromAccount = accountRepository.findById(command.getFromUserId());
        if (fromAccount == null) {
            throw new BusinessException(PaymentError.ACCOUNT_NOT_FOUND);
        }
        validatePaymentPassword(fromAccount,paymentPassword);
        
        // 查询转入账户
        Account toAccount = accountRepository.findById(command.getToUserId());
        if (toAccount == null) {
            throw new BusinessException(PaymentError.ACCOUNT_NOT_FOUND);
        }
        
        // 查询转出钱包
        Wallet fromWallet = walletRepository.findByOwnerIdAndRegion(fromAccount.getId(), command.getPaymentRegion());
        if (fromWallet == null) {
            throw new BusinessException(PaymentError.WALLET_NOT_FOUND);
        }
        
        // 查询转入钱包
        Wallet toWallet = walletRepository.findByOwnerIdAndRegion(toAccount.getId(), command.getPaymentRegion());
        if (toWallet == null) {
            throw new BusinessException(PaymentError.WALLET_NOT_FOUND);
        }
        
        // 校验账户状态
        validateAccountStatus(fromAccount, "转出账户");
        validateAccountStatus(toAccount, "转入账户");
        
        // 校验钱包状态
        validateWalletStatus(fromWallet, "转出钱包");
        validateWalletStatus(toWallet, "转入钱包");
        
        // 校验余额
        Money transferAmount = command.getAmount();
        if (!fromWallet.hasEnoughBalance(transferAmount)) {
            throw new BusinessException(PaymentError.ACCOUNT_BALANCE_INSUFFICIENT);
        }
        
        // 计算手续费（0.10%，提前扣除，由转出方承担）
        Money feeAmount = calculateFee(transferAmount, TransactionType.USER_TRANSFER.getCode(), command.getPaymentRegion());
        Money actualDeductAmount = transferAmount.add(feeAmount); // 转出方需要扣除的总金额
        
        // 再次校验转出方余额（包含手续费）
        if (!fromWallet.hasEnoughBalance(actualDeductAmount)) {
            throw new BusinessException(PaymentError.ACCOUNT_BALANCE_INSUFFICIENT);
        }
        
        // 保存旧余额用于事件发布
        Money oldFromBalance = fromWallet.getBalance();
        Money oldToBalance = toWallet.getBalance();
        
        // 执行转账（转出方扣除转账金额+手续费，接收方全额到账）
        fromWallet.decreaseBalance(actualDeductAmount);
        toWallet.increaseBalance(transferAmount); // 接收方全额到账
        
        // 保存钱包
        walletRepository.updateBalance(fromWallet.getId(), fromWallet.getBalance());
        walletRepository.updateBalance(toWallet.getId(), toWallet.getBalance());
        
        // 创建交易记录
        Integer feeRate = getFeeRate(TransactionType.USER_TRANSFER.getCode());
        Transaction transaction = createTransaction(
            fromAccount.getId(), 
            toAccount.getId(), 
            transferAmount, 
            command.getPaymentRegion(),
            "用户转账",
            TransactionType.USER_TRANSFER,
            feeAmount,
            feeRate,
            transferAmount // 接收方实际到账金额（全额）
        );
        
        transactionRepository.save(transaction);
        
        // 保存账户流水（转出、转入）
        AccountFlow outFlow = AccountFlow.createOutFlow(
            fromAccount.getId(),
            transaction.getId(),
            TransactionType.USER_TRANSFER.getCode(),
            actualDeductAmount, // 转出方实际扣款（含手续费）
            oldFromBalance,
            fromWallet.getBalance(),
            feeAmount, // 转出方承担手续费
            feeRate,
            transferAmount // 转账金额
        );
        AccountFlow inFlow = AccountFlow.createInFlow(
            toAccount.getId(),
            transaction.getId(),
            TransactionType.USER_TRANSFER.getCode(),
            transferAmount,
            oldToBalance,
            toWallet.getBalance(),
            Money.zeroMoney(command.getPaymentRegion()), // 接收方不承担手续费
            0,
            transferAmount // 接收方实际到账（全额）
        );
        accountFlowRepository.saveBatch(java.util.Arrays.asList(outFlow, inFlow));
        
        // 发布转账成功事件
        eventPublisher.publishEvent(new TransferSuccessEvent(
            transaction.getId(),
            fromAccount.getId(),
            toAccount.getId(),
            transferAmount,
            command.getPaymentRegion(),
            TransactionType.USER_TRANSFER.getCode(),
            "用户转账"
        ));
        
        // 发布钱包余额变更事件
        eventPublisher.publishEvent(new WalletBalanceChangedEvent(
            fromWallet, oldFromBalance, actualDeductAmount, "DECREASE", transaction.getId()));
        eventPublisher.publishEvent(new WalletBalanceChangedEvent(
            toWallet, oldToBalance, transferAmount, "INCREASE", transaction.getId()));
        
        return TransferResult.success(transaction.getId(), "转账成功");
    }
    
    /**
     * 执行向商户付款业务逻辑
     */
    private TransferResult doPayToMerchant(MerchantPaymentDomainCommand command, String paymentPassword) {
        // 查询付款方账户
        Account fromAccount = accountRepository.findById(command.getFromUserId());
        if (fromAccount == null) {
            throw new BusinessException(PaymentError.ACCOUNT_NOT_FOUND);
        }
        validatePaymentPassword(fromAccount, paymentPassword);
        
        // 查询商户
        Merchant merchant = merchantRepository.findById(command.getMerchantId());
        if (merchant == null) {
            throw new BusinessException(PaymentError.MERCHANT_NOT_FOUND);
        }
        
        // 查询付款方钱包
        Wallet fromWallet = walletRepository.findByOwnerIdAndRegion(fromAccount.getId(), command.getPaymentRegion());
        if (fromWallet == null) {
            throw new BusinessException(PaymentError.WALLET_NOT_FOUND);
        }
        
        // 查询商户钱包
        Wallet merchantWallet = walletRepository.findByOwnerIdAndRegion(merchant.getId(), command.getPaymentRegion());
        if (merchantWallet == null) {
            throw new BusinessException(PaymentError.WALLET_NOT_FOUND);
        }
        
        // 校验账户状态
        validateAccountStatus(fromAccount, "付款账户");
        validateMerchantStatus(merchant);
        
        // 校验钱包状态
        validateWalletStatus(fromWallet, "付款钱包");
        validateWalletStatus(merchantWallet, "商户钱包");
        
        // 校验余额
        Money paymentAmount = command.getAmount();
        if (!fromWallet.hasEnoughBalance(paymentAmount)) {
            throw new BusinessException(PaymentError.ACCOUNT_BALANCE_INSUFFICIENT);
        }
        
        // 计算手续费（商户收款0.50%，收入后扣除，由商户承担）
        Money feeAmount = calculateFee(paymentAmount, TransactionType.MERCHANT_COLLECTION.getCode(), command.getPaymentRegion());
        Money actualAmount = paymentAmount.subtract(feeAmount);
        
        // 保存旧余额用于事件发布
        Money oldFromBalance = fromWallet.getBalance();
        Money oldToBalance = merchantWallet.getBalance();
        
        // 执行付款
        fromWallet.decreaseBalance(paymentAmount);
        merchantWallet.increaseBalance(actualAmount); // 收入方实际到账金额（扣除手续费）
        
        // 保存钱包
        walletRepository.updateBalance(fromWallet.getId(), fromWallet.getBalance());
        walletRepository.updateBalance(merchantWallet.getId(), merchantWallet.getBalance());
        
        // 创建交易记录
        Integer feeRate = getFeeRate(TransactionType.MERCHANT_COLLECTION.getCode());
        Transaction transaction = createTransaction(
            fromAccount.getId(), 
            merchant.getId(), 
            paymentAmount, 
            command.getPaymentRegion(),
            "向商户付款",
            TransactionType.MERCHANT_COLLECTION,
            feeAmount,
            feeRate,
            actualAmount
        );
        
        transactionRepository.save(transaction);
        
        // 保存账户流水（转出、转入）
        AccountFlow outFlow = AccountFlow.createOutFlow(
            fromAccount.getId(),
            transaction.getId(),
            TransactionType.MERCHANT_COLLECTION.getCode(),
            paymentAmount,
            oldFromBalance,
            fromWallet.getBalance(),
            Money.zeroMoney(command.getPaymentRegion()), // 转出方不承担手续费
            0,
            paymentAmount
        );
        AccountFlow inFlow = AccountFlow.createInFlow(
            merchant.getId(),
            transaction.getId(),
            TransactionType.MERCHANT_COLLECTION.getCode(),
            paymentAmount,
            oldToBalance,
            merchantWallet.getBalance(),
            feeAmount, // 商户承担手续费（收入后扣除）
            feeRate,
            actualAmount
        );
        accountFlowRepository.saveBatch(java.util.Arrays.asList(outFlow, inFlow));
        
        // 发布转账成功事件
        eventPublisher.publishEvent(new TransferSuccessEvent(
            transaction.getId(),
            fromAccount.getId(),
            merchant.getId(),
            paymentAmount,
            command.getPaymentRegion(),
            TransactionType.MERCHANT_COLLECTION.getCode(),
            "向商户付款"
        ));
        
        // 发布钱包余额变更事件
        eventPublisher.publishEvent(new WalletBalanceChangedEvent(
            fromWallet, oldFromBalance, paymentAmount, "DECREASE", transaction.getId()));
        eventPublisher.publishEvent(new WalletBalanceChangedEvent(
            merchantWallet, oldToBalance, paymentAmount, "INCREASE", transaction.getId()));
        
        return TransferResult.success(transaction.getId(), "付款成功");
    }
    
    /**
     * 购物支付
     * 用户购物支付，无手续费（0.00%）
     * 
     * @param command 购物支付命令
     * @param paymentPassword 支付密码
     * @return 支付结果
     */
    @Transactional
    public TransferResult shoppingPayment(MerchantPaymentDomainCommand command, String paymentPassword) {
        // 查询付款方账户
        Account fromAccount = accountRepository.findById(command.getFromUserId());
        if (fromAccount == null) {
            throw new BusinessException(PaymentError.ACCOUNT_NOT_FOUND);
        }
        validatePaymentPassword(fromAccount, paymentPassword);
        
        // 查询商户
        Merchant merchant = merchantRepository.findById(command.getMerchantId());
        if (merchant == null) {
            throw new BusinessException(PaymentError.MERCHANT_NOT_FOUND);
        }
        
        // 查询付款方钱包
        Wallet fromWallet = walletRepository.findByOwnerIdAndRegion(fromAccount.getId(), command.getPaymentRegion());
        if (fromWallet == null) {
            throw new BusinessException(PaymentError.WALLET_NOT_FOUND);
        }
        
        // 查询商户钱包
        Wallet merchantWallet = walletRepository.findByOwnerIdAndRegion(merchant.getId(), command.getPaymentRegion());
        if (merchantWallet == null) {
            throw new BusinessException(PaymentError.WALLET_NOT_FOUND);
        }
        
        // 校验账户状态
        validateAccountStatus(fromAccount, "付款账户");
        validateMerchantStatus(merchant);
        
        // 校验钱包状态
        validateWalletStatus(fromWallet, "付款钱包");
        validateWalletStatus(merchantWallet, "商户钱包");
        
        // 校验余额
        Money paymentAmount = command.getAmount();
        if (!fromWallet.hasEnoughBalance(paymentAmount)) {
            throw new BusinessException(PaymentError.ACCOUNT_BALANCE_INSUFFICIENT);
        }
        
        // 购物支付无手续费（0.00%）
        Money feeAmount = Money.zeroMoney(command.getPaymentRegion());
        Money actualAmount = paymentAmount;
        
        // 保存旧余额用于事件发布
        Money oldFromBalance = fromWallet.getBalance();
        Money oldToBalance = merchantWallet.getBalance();
        
        // 执行付款
        fromWallet.decreaseBalance(paymentAmount);
        merchantWallet.increaseBalance(actualAmount); // 商户全额到账
        
        // 保存钱包
        walletRepository.updateBalance(fromWallet.getId(), fromWallet.getBalance());
        walletRepository.updateBalance(merchantWallet.getId(), merchantWallet.getBalance());
        
        // 创建交易记录
        Transaction transaction = createTransaction(
            fromAccount.getId(), 
            merchant.getId(), 
            paymentAmount, 
            command.getPaymentRegion(),
            "购物支付",
            TransactionType.SHOPPING,
            feeAmount,
            0,
            actualAmount
        );
        
        transactionRepository.save(transaction);
        
        // 保存账户流水（转出、转入）
        AccountFlow outFlow = AccountFlow.createOutFlow(
            fromAccount.getId(),
            transaction.getId(),
            TransactionType.SHOPPING.getCode(),
            paymentAmount,
            oldFromBalance,
            fromWallet.getBalance(),
            Money.zeroMoney(command.getPaymentRegion()),
            0,
            paymentAmount
        );
        AccountFlow inFlow = AccountFlow.createInFlow(
            merchant.getId(),
            transaction.getId(),
            TransactionType.SHOPPING.getCode(),
            paymentAmount,
            oldToBalance,
            merchantWallet.getBalance(),
            feeAmount,
            0,
            actualAmount
        );
        accountFlowRepository.saveBatch(java.util.Arrays.asList(outFlow, inFlow));
        
        // 发布转账成功事件
        eventPublisher.publishEvent(new TransferSuccessEvent(
            transaction.getId(),
            fromAccount.getId(),
            merchant.getId(),
            paymentAmount,
            command.getPaymentRegion(),
            "SHOPPING",
            "购物支付"
        ));
        
        // 发布钱包余额变更事件
        eventPublisher.publishEvent(new WalletBalanceChangedEvent(
            fromWallet, oldFromBalance, paymentAmount, "DECREASE", transaction.getId()));
        eventPublisher.publishEvent(new WalletBalanceChangedEvent(
            merchantWallet, oldToBalance, paymentAmount, "INCREASE", transaction.getId()));
        
        return TransferResult.success(transaction.getId(), "购物支付成功");
    }
    
    /**
     * 执行商户优惠减免或减价给用户的业务逻辑（内部调用，无需验证密码）
     * 商户向用户转账，手续费由用户（接收方）承担
     * 
     * 注意：此方法用于系统内部调用，不验证支付密码
     * 调用方应该确保已经通过其他方式验证了操作的合法性
     * 
     * @param command 商户向用户转账命令
     * @param transactionType 交易类型
     * @param isDiscount true=优惠减免, false=减价
     * @return 转账结果
     */
    private TransferResult doMerchantDiscountOrReductionToUser(MerchantToUserPaymentDomainCommand command, 
                                                               TransactionType transactionType, 
                                                               boolean isDiscount) {
        // 查询商户
        Merchant merchant = merchantRepository.findById(command.getMerchantId());
        if (merchant == null) {
            throw new BusinessException(PaymentError.MERCHANT_NOT_FOUND);
        }
        
        // 查询用户账户
        Account userAccount = accountRepository.findById(command.getUserId());
        if (userAccount == null) {
            throw new BusinessException(PaymentError.ACCOUNT_NOT_FOUND);
        }
        
        // 查询商户钱包
        Wallet merchantWallet = walletRepository.findByOwnerIdAndRegion(merchant.getId(), command.getPaymentRegion());
        if (merchantWallet == null) {
            throw new BusinessException(PaymentError.WALLET_NOT_FOUND);
        }
        
        // 查询用户钱包
        Wallet userWallet = walletRepository.findByOwnerIdAndRegion(userAccount.getId(), command.getPaymentRegion());
        if (userWallet == null) {
            throw new BusinessException(PaymentError.WALLET_NOT_FOUND);
        }
        
        // 校验商户状态
        validateMerchantStatus(merchant);
        
        // 校验账户状态
        validateAccountStatus(userAccount, "用户账户");
        
        // 校验钱包状态
        validateWalletStatus(merchantWallet, "商户钱包");
        validateWalletStatus(userWallet, "用户钱包");
        
        // 校验商户余额
        Money transferAmount = command.getAmount();
        if (!merchantWallet.hasEnoughBalance(transferAmount)) {
            throw new BusinessException(PaymentError.MERCHANT_BALANCE_INSUFFICIENT);
        }
        
        // 计算手续费（由用户/接收方承担，收入后扣除）
        Money feeAmount = calculateFee(transferAmount, transactionType.getCode(), command.getPaymentRegion());
        Money actualUserAmount = transferAmount.subtract(feeAmount); // 用户实际收到的金额
        
        // 保存旧余额用于事件发布
        Money oldMerchantBalance = merchantWallet.getBalance();
        Money oldUserBalance = userWallet.getBalance();
        
        // 执行转账（商户扣除全额，用户收到扣除手续费后的金额）
        merchantWallet.decreaseBalance(transferAmount);
        userWallet.increaseBalance(actualUserAmount); // 用户实际到账（扣除手续费）
        
        // 保存钱包
        walletRepository.updateBalance(merchantWallet.getId(), merchantWallet.getBalance());
        walletRepository.updateBalance(userWallet.getId(), userWallet.getBalance());
        
        // 创建交易记录
        Integer feeRate = getFeeRate(transactionType.getCode());
        String description = isDiscount ? "商户优惠减免" : "商户减价";
        Transaction transaction = createTransaction(
            merchant.getId(), 
            userAccount.getId(), 
            transferAmount, 
            command.getPaymentRegion(),
            command.getDescription() != null ? command.getDescription() : description,
            transactionType,
            feeAmount,
            feeRate,
            actualUserAmount
        );
        
        transactionRepository.save(transaction);
        
        // 保存账户流水（转出、转入）
        AccountFlow outFlow = AccountFlow.createOutFlow(
            merchant.getId(),
            transaction.getId(),
            transactionType.getCode(),
            transferAmount,
            oldMerchantBalance,
            merchantWallet.getBalance(),
            Money.zeroMoney(command.getPaymentRegion()), // 商户不承担手续费
            0,
            transferAmount
        );
        AccountFlow inFlow = AccountFlow.createInFlow(
            userAccount.getId(),
            transaction.getId(),
            transactionType.getCode(),
            transferAmount,
            oldUserBalance,
            userWallet.getBalance(),
            feeAmount, // 用户承担手续费（收入后扣除）
            feeRate,
            actualUserAmount
        );
        accountFlowRepository.saveBatch(java.util.Arrays.asList(outFlow, inFlow));
        
        // 发布转账成功事件
        eventPublisher.publishEvent(new TransferSuccessEvent(
            transaction.getId(),
            merchant.getId(),
            userAccount.getId(),
            transferAmount,
            command.getPaymentRegion(),
            transactionType.getCode(),
            description
        ));
        
        // 发布钱包余额变更事件
        eventPublisher.publishEvent(new WalletBalanceChangedEvent(
            merchantWallet, oldMerchantBalance, transferAmount, "DECREASE", transaction.getId()));
        eventPublisher.publishEvent(new WalletBalanceChangedEvent(
            userWallet, oldUserBalance, actualUserAmount, "INCREASE", transaction.getId()));
        
        return TransferResult.success(transaction.getId(), description + "成功，实际到账: " + actualUserAmount);
    }
    
    /**
     * 执行商户向用户发放业务逻辑（商户赠送，无手续费）
     */
    private TransferResult doMerchantToUserPayment(MerchantToUserPaymentDomainCommand command) {
        // 查询商户
        Merchant merchant = merchantRepository.findById(command.getMerchantId());
        if (merchant == null) {
            throw new BusinessException(PaymentError.MERCHANT_NOT_FOUND);
        }
        
        // 查询用户账户
        Account userAccount = accountRepository.findById(command.getUserId());
        if (userAccount == null) {
            throw new BusinessException(PaymentError.ACCOUNT_NOT_FOUND);
        }
        
        // 查询商户钱包
        Wallet merchantWallet = walletRepository.findByOwnerIdAndRegion(merchant.getId(), command.getPaymentRegion());
        if (merchantWallet == null) {
            throw new BusinessException(PaymentError.WALLET_NOT_FOUND);
        }
        
        // 查询用户钱包
        Wallet userWallet = walletRepository.findByOwnerIdAndRegion(userAccount.getId(), command.getPaymentRegion());
        if (userWallet == null) {
            throw new BusinessException(PaymentError.WALLET_NOT_FOUND);
        }
        
        // 校验商户状态
        validateMerchantStatus(merchant);
        
        // 校验账户状态
        validateAccountStatus(userAccount, "用户账户");
        
        // 校验钱包状态
        validateWalletStatus(merchantWallet, "商户钱包");
        validateWalletStatus(userWallet, "用户钱包");
        
        // 校验商户余额
        Money paymentAmount = command.getAmount();
        if (!merchantWallet.hasEnoughBalance(paymentAmount)) {
            throw new BusinessException(PaymentError.MERCHANT_BALANCE_INSUFFICIENT);
        }
        
        // 商户赠送无手续费（0.00%）
        Money feeAmount = Money.zeroMoney(command.getPaymentRegion());
        Money actualAmount = paymentAmount;
        
        // 保存旧余额用于事件发布
        Money oldMerchantBalance = merchantWallet.getBalance();
        Money oldUserBalance = userWallet.getBalance();
        
        // 执行发放（从商户钱包扣款，向用户钱包充值）
        merchantWallet.decreaseBalance(paymentAmount);
        userWallet.increaseBalance(actualAmount); // 用户全额到账
        
        // 保存钱包
        walletRepository.updateBalance(merchantWallet.getId(), merchantWallet.getBalance());
        walletRepository.updateBalance(userWallet.getId(), userWallet.getBalance());
        
        // 创建交易记录（使用 MERCHANT_ACTIVITY_GIFT 交易类型）
        Transaction transaction = createTransaction(
            merchant.getId(), 
            userAccount.getId(), 
            paymentAmount, 
            command.getPaymentRegion(),
            "商户活动赠送",
            TransactionType.MERCHANT_ACTIVITY_GIFT,
            feeAmount,
            0,
            actualAmount
        );
        
        transactionRepository.save(transaction);
        
        // 保存账户流水（转出、转入）
        AccountFlow outFlow = AccountFlow.createOutFlow(
            merchant.getId(),
            transaction.getId(),
            TransactionType.MERCHANT_ACTIVITY_GIFT.getCode(),
            paymentAmount,
            oldMerchantBalance,
            merchantWallet.getBalance(),
            Money.zeroMoney(command.getPaymentRegion()), // 商户活动赠送无手续费
            0,
            paymentAmount
        );
        AccountFlow inFlow = AccountFlow.createInFlow(
            userAccount.getId(),
            transaction.getId(),
            TransactionType.MERCHANT_ACTIVITY_GIFT.getCode(),
            paymentAmount,
            oldUserBalance,
            userWallet.getBalance(),
            feeAmount,
            0,
            actualAmount
        );
        accountFlowRepository.saveBatch(java.util.Arrays.asList(outFlow, inFlow));
        
        // 发布转账成功事件
        eventPublisher.publishEvent(new TransferSuccessEvent(
            transaction.getId(),
            merchant.getId(),
            userAccount.getId(),
            paymentAmount,
            command.getPaymentRegion(),
            TransactionType.MERCHANT_ACTIVITY_GIFT.getCode(),
            "商户活动赠送"
        ));
        
        // 发布钱包余额变更事件
        eventPublisher.publishEvent(new WalletBalanceChangedEvent(
            merchantWallet, oldMerchantBalance, paymentAmount, "DECREASE", transaction.getId()));
        eventPublisher.publishEvent(new WalletBalanceChangedEvent(
            userWallet, oldUserBalance, paymentAmount, "INCREASE", transaction.getId()));
        
        return TransferResult.success(transaction.getId(), "赠送成功");
    }
    
    /**
     * 执行商户充值业务逻辑（从平台商户划拨到普通商户）
     */
    private TransferResult doMerchantRecharge(MerchantRechargeDomainCommand command) {
        // 查询平台商户
        Merchant platformMerchant = merchantRepository.findById(PaymentConstants.PLATFORM_MERCHANT_ID);
        if (platformMerchant == null) {
            throw new BusinessException(PaymentError.MERCHANT_NOT_FOUND);
        }
        
        // 查询普通商户
        Merchant userMerchant = merchantRepository.findById(command.getUserId());
        if (userMerchant == null) {
            throw new BusinessException(PaymentError.MERCHANT_NOT_FOUND);
        }
        
        // 查询平台商户钱包
        Wallet platformWallet = walletRepository.findByOwnerIdAndRegion(platformMerchant.getId(), command.getPaymentRegion());
        if (platformWallet == null) {
            throw new BusinessException(PaymentError.WALLET_NOT_FOUND);
        }
        
        // 查询普通商户钱包
        Wallet userWallet = walletRepository.findByOwnerIdAndRegion(userMerchant.getId(), command.getPaymentRegion());
        if (userWallet == null) {
            throw new BusinessException(PaymentError.WALLET_NOT_FOUND);
        }
        
        // 校验商户状态
        validateMerchantStatus(platformMerchant);
        validateMerchantStatus(userMerchant);
        
        // 校验钱包状态
        validateWalletStatus(platformWallet, "平台商户钱包");
        validateWalletStatus(userWallet, "普通商户钱包");
        
        // 校验平台商户余额
        Money rechargeAmount = command.getAmount();
        if (!platformWallet.hasEnoughBalance(rechargeAmount)) {
            throw new BusinessException(PaymentError.MERCHANT_BALANCE_INSUFFICIENT);
        }
        
        // 商户购买礼物点无手续费（0.00%）
        Money feeAmount = Money.zeroMoney(command.getPaymentRegion());
        Money actualAmount = rechargeAmount;
        
        // 保存旧余额用于事件发布
        Money oldPlatformBalance = platformWallet.getBalance();
        Money oldUserBalance = userWallet.getBalance();
        
        // 执行充值（从平台商户划拨到普通商户）
        platformWallet.decreaseBalance(rechargeAmount);
        userWallet.increaseBalance(actualAmount); // 商户全额到账
        
        // 保存钱包
        walletRepository.updateBalance(platformWallet.getId(), platformWallet.getBalance());
        walletRepository.updateBalance(userWallet.getId(), userWallet.getBalance());
        
        // 创建交易记录（商户购买礼物点）
        Transaction transaction = createTransaction(
            platformMerchant.getId(), 
            userMerchant.getId(), 
            rechargeAmount, 
            command.getPaymentRegion(),
            "商户购买礼物点",
            TransactionType.MERCHANT_PURCHASE,
            feeAmount,
            0,
            actualAmount
        );
        
        transactionRepository.save(transaction);
        
        // 保存账户流水（转出、转入）
        // 系统侧：SYSTEM_SALE（系统出售）
        AccountFlow outFlow = AccountFlow.createOutFlow(
            platformMerchant.getId(),
            transaction.getId(),
            TransactionType.SYSTEM_SALE.getCode(),
            rechargeAmount,
            oldPlatformBalance,
            platformWallet.getBalance(),
            Money.zeroMoney(command.getPaymentRegion()), // 系统出售无手续费
            0,
            rechargeAmount
        );
        // 商户侧：MERCHANT_PURCHASE（商户购买）
        AccountFlow inFlow = AccountFlow.createInFlow(
            userMerchant.getId(),
            transaction.getId(),
            TransactionType.MERCHANT_PURCHASE.getCode(),
            rechargeAmount,
            oldUserBalance,
            userWallet.getBalance(),
            feeAmount, // 商户购买无手续费
            0,
            actualAmount
        );
        accountFlowRepository.saveBatch(java.util.Arrays.asList(outFlow, inFlow));
        
        // 发布转账成功事件
        eventPublisher.publishEvent(new TransferSuccessEvent(
            transaction.getId(),
            platformMerchant.getId(),
            userMerchant.getId(),
            rechargeAmount,
            command.getPaymentRegion(),
            TransactionType.MERCHANT_PURCHASE.getCode(),
            "商户购买礼物点"
        ));
        
        // 发布钱包余额变更事件
        eventPublisher.publishEvent(new WalletBalanceChangedEvent(
            platformWallet, oldPlatformBalance, rechargeAmount, "DECREASE", transaction.getId()));
        eventPublisher.publishEvent(new WalletBalanceChangedEvent(
            userWallet, oldUserBalance, rechargeAmount, "INCREASE", transaction.getId()));
        
        return TransferResult.success(transaction.getId(), "充值成功");
    }
    
    /**
     * 执行商户售卖给系统业务逻辑
     * 商户将礼物点出售给平台，手续费1.50%提前扣除（商户承担）
     */
    private TransferResult doMerchantDeduction(MerchantDeductionDomainCommand command) {
        // 查询平台商户
        Merchant platformMerchant = merchantRepository.findById(PaymentConstants.PLATFORM_MERCHANT_ID);
        if (platformMerchant == null) {
            throw new BusinessException(PaymentError.MERCHANT_NOT_FOUND);
        }
        
        // 查询普通商户
        Merchant userMerchant = merchantRepository.findById(command.getUserId());
        if (userMerchant == null) {
            throw new BusinessException(PaymentError.MERCHANT_NOT_FOUND);
        }
        
        // 查询平台商户钱包
        Wallet platformWallet = walletRepository.findByOwnerIdAndRegion(platformMerchant.getId(), command.getPaymentRegion());
        if (platformWallet == null) {
            throw new BusinessException(PaymentError.WALLET_NOT_FOUND);
        }
        
        // 查询普通商户钱包
        Wallet userWallet = walletRepository.findByOwnerIdAndRegion(userMerchant.getId(), command.getPaymentRegion());
        if (userWallet == null) {
            throw new BusinessException(PaymentError.WALLET_NOT_FOUND);
        }
        
        // 校验商户状态
        validateMerchantStatus(platformMerchant);
        validateMerchantStatus(userMerchant);
        
        // 校验钱包状态
        validateWalletStatus(platformWallet, "平台商户钱包");
        validateWalletStatus(userWallet, "普通商户钱包");
        
        // 校验普通商户余额
        Money deductionAmount = command.getAmount();
        if (!userWallet.hasEnoughBalance(deductionAmount)) {
            throw new BusinessException(PaymentError.MERCHANT_BALANCE_INSUFFICIENT);
        }
        
        // 计算手续费（1.50%，提前扣除，商户承担）
        Money feeAmount = calculateFee(deductionAmount, TransactionType.MERCHANT_SELL.getCode(), command.getPaymentRegion());
        Money actualSellAmount = deductionAmount.subtract(feeAmount); // 商户实际收到的金额
        
        // 再次校验商户余额（需要有足够的礼物点出售）
        if (!userWallet.hasEnoughBalance(deductionAmount)) {
            throw new BusinessException(PaymentError.MERCHANT_BALANCE_INSUFFICIENT);
        }
        
        // 保存旧余额用于事件发布
        Money oldPlatformBalance = platformWallet.getBalance();
        Money oldUserBalance = userWallet.getBalance();
        
        // 执行售卖（商户扣除礼物点，平台接收礼物点，商户实际收到现金=deductionAmount-手续费）
        userWallet.decreaseBalance(deductionAmount); // 商户扣除礼物点
        platformWallet.increaseBalance(deductionAmount); // 平台全额接收礼物点
        
        // 保存钱包
        walletRepository.updateBalance(userWallet.getId(), userWallet.getBalance());
        walletRepository.updateBalance(platformWallet.getId(), platformWallet.getBalance());
        
        // 创建交易记录（商户出售礼物点）
        Integer feeRate = getFeeRate(TransactionType.MERCHANT_SELL.getCode());
        Transaction transaction = createTransaction(
            userMerchant.getId(), 
            platformMerchant.getId(), 
            deductionAmount, 
            command.getPaymentRegion(),
            "商户出售礼物点给系统",
            TransactionType.MERCHANT_SELL,
            feeAmount,
            feeRate,
            actualSellAmount // 商户实际收到的金额（扣除手续费后）
        );
        
        transactionRepository.save(transaction);
        
        // 保存账户流水（转出、转入）
        // 商户侧：MERCHANT_SELL（商户出售，提前扣除手续费）
        AccountFlow outFlow = AccountFlow.createOutFlow(
            userMerchant.getId(),
            transaction.getId(),
            TransactionType.MERCHANT_SELL.getCode(),
            deductionAmount,
            oldUserBalance,
            userWallet.getBalance(),
            feeAmount, // 商户承担手续费（提前扣除）
            feeRate,
            actualSellAmount // 商户实际收到的金额
        );
        // 系统侧：SYSTEM_RECYCLE（系统回收）
        AccountFlow inFlow = AccountFlow.createInFlow(
            platformMerchant.getId(),
            transaction.getId(),
            TransactionType.SYSTEM_RECYCLE.getCode(),
            deductionAmount,
            oldPlatformBalance,
            platformWallet.getBalance(),
            Money.zeroMoney(command.getPaymentRegion()), // 系统回收无手续费
            0,
            deductionAmount // 平台全额接收礼物点
        );
        accountFlowRepository.saveBatch(java.util.Arrays.asList(outFlow, inFlow));
        
        // 发布转账成功事件
        eventPublisher.publishEvent(new TransferSuccessEvent(
            transaction.getId(),
            userMerchant.getId(),
            platformMerchant.getId(),
            deductionAmount,
            command.getPaymentRegion(),
            TransactionType.MERCHANT_SELL.getCode(),
            "商户出售礼物点给系统"
        ));
        
        // 发布钱包余额变更事件
        eventPublisher.publishEvent(new WalletBalanceChangedEvent(
            userWallet, oldUserBalance, deductionAmount, "DECREASE", transaction.getId()));
        eventPublisher.publishEvent(new WalletBalanceChangedEvent(
            platformWallet, oldPlatformBalance, deductionAmount, "INCREASE", transaction.getId()));
        
        return TransferResult.success(transaction.getId(), "出售成功，实际到账: " + actualSellAmount);
    }
    
    /**
     * 校验账户状态
     */
    private void validateAccountStatus(Account account, String accountType) {
        if (account == null) {
            throw new BusinessException(PaymentError.ACCOUNT_NOT_FOUND);
        }
        
        if (!account.isActive()) {
            throw new BusinessException(PaymentError.ACCOUNT_FROZEN);
        }
    }
    
    /**
     * 校验钱包状态
     */
    private void validateWalletStatus(Wallet wallet, String walletType) {
        if (wallet == null) {
            throw new BusinessException(PaymentError.WALLET_NOT_FOUND);
        }
        
        if (!wallet.isActive()) {
            throw new BusinessException(PaymentError.WALLET_FROZEN);
        }
    }
    
    /**
     * 校验商户状态
     */
    private void validateMerchantStatus(Merchant merchant) {
        if (merchant == null) {
            throw new BusinessException(PaymentError.MERCHANT_NOT_FOUND);
        }
        
        if (!merchant.isActive()) {
            throw new BusinessException(PaymentError.MERCHANT_FROZEN);
        }
    }
    
    /**
     * 创建交易记录
     */
    private Transaction createTransaction(String fromId, String toId, Money amount, 
                                        String paymentRegion, String description, TransactionType type,
                                        Money feeAmount, Integer feeRate, Money actualAmount) {
        Transaction transaction = new Transaction();
        transaction.setId(IdUtils.fastSimpleUUID());
        transaction.setFromAccountId(fromId);
        transaction.setToAccountId(toId);
        transaction.setAmount(amount);
        transaction.setPaymentRegion(paymentRegion);
        transaction.setDescription(description);
        transaction.setType(type.getCode());
        transaction.setStatus("SUCCESS");
        transaction.setCreateTime(System.currentTimeMillis());
        transaction.setUpdateTime(System.currentTimeMillis());
        transaction.setFeeAmount(feeAmount);
        transaction.setFeeRate(feeRate);
        transaction.setActualAmount(actualAmount);
        return transaction;
    }
} 