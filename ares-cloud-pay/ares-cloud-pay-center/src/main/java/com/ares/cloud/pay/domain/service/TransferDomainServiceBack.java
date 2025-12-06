package com.ares.cloud.pay.domain.service;

// import com.ares.cloud.pay.domain.command.*;
// import com.ares.cloud.pay.domain.constant.PaymentConstants;
// import com.ares.cloud.pay.domain.enums.FlowType;
// import com.ares.cloud.pay.domain.enums.PaymentError;
// import com.ares.cloud.pay.domain.enums.TransactionType;
// import com.ares.cloud.pay.domain.event.TransferSuccessEvent;
// import com.ares.cloud.pay.domain.event.WalletBalanceChangedEvent;
// import com.ares.cloud.pay.domain.model.*;
// import com.ares.cloud.pay.domain.repository.*;
// import com.ares.cloud.pay.domain.valueobject.TransferResult;
// import org.ares.cloud.api.LockApi;
// import org.ares.cloud.common.exception.BusinessException;
// import org.ares.cloud.common.model.Money;
// import org.ares.cloud.common.utils.IdUtils;
// import org.ares.cloud.common.utils.StringUtils;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.ApplicationEventPublisher;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;


// /**
//  * 转账领域服务
//  * 负责处理用户之间的转账、商户付款、商户充值、商户回收、商户向用户发放等业务逻辑
//  */
// @Service
// public class TransferDomainServiceBack {
    
//     private static final Logger log = LoggerFactory.getLogger(TransferDomainServiceBack.class);
    
//     @Autowired
//     private AccountRepository accountRepository;
    
//     @Autowired
//     private MerchantRepository merchantRepository;
    
//     @Autowired
//     private WalletRepository walletRepository;
    
//     @Autowired
//     private TransactionRepository transactionRepository;
    
//     @Autowired
//     private AccountFlowRepository accountFlowRepository;
    
//     @Autowired
//     private FeeConfigRepository feeConfigRepository;
    
//     @Autowired
//     private LockApi lockApi;
    
//     @Autowired
//     private PasswordEncoder passwordEncoder;
    
//     @Autowired
//     private ApplicationEventPublisher eventPublisher;
    
//     // ==================== 公有方法 ====================
    
//     /**
//      * 用户间转账
//      * 使用严格的双方都加锁确保资金安全，锁粒度细化到paymentRegion
//      * 
//      * @param command 转账命令
//      * @param paymentPassword 支付密码
//      * @return 转账结果
//      */
//     @Transactional
//     public TransferResult transferBetweenUsers(TransferDomainCommand command, String paymentPassword) {
        
//         // 生成锁key，按paymentRegion加锁，提高并发性能
//         String fromLockKey = "transfer:user:" + command.getFromUserId() + ":" + command.getPaymentRegion();
//         String toLockKey = "transfer:user:" + command.getToUserId() + ":" + command.getPaymentRegion();
        
//         // 按照用户ID排序，避免死锁
//         String firstLockKey, secondLockKey;
        
//         if (command.getFromUserId().compareTo(command.getToUserId()) < 0) {
//             firstLockKey = fromLockKey;
//             secondLockKey = toLockKey;
//         } else {
//             firstLockKey = toLockKey;
//             secondLockKey = fromLockKey;
//         }
        
//         return lockApi.lockExecute(firstLockKey, new LockApi.LockExecute<TransferResult>() {
//             @Override
//             public TransferResult execute() {
//                 // 获取第一个锁后，尝试获取第二个锁
//                 return lockApi.lockExecute(secondLockKey, new LockApi.LockExecute<TransferResult>() {
//                     @Override
//                     public TransferResult execute() {
//                         // 两个锁都获取成功，执行转账业务逻辑
//                         return doTransferBetweenUsers(command, paymentPassword);
//                     }
                    
//                     @Override
//                     public TransferResult waitTimeOut() {
//                         throw new BusinessException(PaymentError.TRANSACTION_TIMEOUT);
//                     }
//                 });
//             }
            
//             @Override
//             public TransferResult waitTimeOut() {
//                 throw new BusinessException(PaymentError.TRANSACTION_TIMEOUT);
//             }
//         });
//     }
    
//     /**
//      * 向商户付款
//      * 使用分布式锁确保资金安全，锁粒度细化到paymentRegion
//      * 
//      * @param command 向商户付款命令
//      * @param paymentPassword 支付密码
//      * @return 付款结果
//      */
//     @Transactional
//     public TransferResult payToMerchant(MerchantPaymentDomainCommand command, String paymentPassword) {
        
//         // 生成锁key，按paymentRegion加锁，提高并发性能
//         String fromLockKey = "payment:user:" + command.getFromUserId() + ":" + command.getPaymentRegion();
//         String merchantLockKey = "payment:merchant:" + command.getMerchantId() + ":" + command.getPaymentRegion();
        
//         // 按照ID排序，避免死锁
//         String firstLockKey, secondLockKey;
        
//         if (command.getFromUserId().compareTo(command.getMerchantId()) < 0) {
//             firstLockKey = fromLockKey;
//             secondLockKey = merchantLockKey;
//         } else {
//             firstLockKey = merchantLockKey;
//             secondLockKey = fromLockKey;
//         }
        
//         return lockApi.lockExecute(firstLockKey, new LockApi.LockExecute<TransferResult>() {
//             @Override
//             public TransferResult execute() {
//                 return lockApi.lockExecute(secondLockKey, new LockApi.LockExecute<TransferResult>() {
//                     @Override
//                     public TransferResult execute() {
//                         return doPayToMerchant(command,paymentPassword);
//                     }
                    
//                     @Override
//                     public TransferResult waitTimeOut() {
//                         throw new BusinessException(PaymentError.TRANSACTION_TIMEOUT);
//                     }
//                 });
//             }
            
//             @Override
//             public TransferResult waitTimeOut() {
//                 throw new BusinessException(PaymentError.TRANSACTION_TIMEOUT);
//             }
//         });
//     }

//     /**
//      * 商户向用户发放
//      * 使用分布式锁确保资金安全，锁粒度细化到paymentRegion
//      * 
//      * @param command 商户向用户发放命令
//      * @return 发放结果
//      */
//     @Transactional
//     public TransferResult merchantToUserPayment(MerchantToUserPaymentDomainCommand command) {
//         // 生成锁key，按paymentRegion加锁，提高并发性能
//         String merchantLockKey = "payment:merchant:" + command.getMerchantId() + ":" + command.getPaymentRegion();
//         String userLockKey = "payment:user:" + command.getUserId() + ":" + command.getPaymentRegion();
        
//         // 按照ID排序，避免死锁
//         String firstLockKey, secondLockKey;
        
//         if (command.getMerchantId().compareTo(command.getUserId()) < 0) {
//             firstLockKey = merchantLockKey;
//             secondLockKey = userLockKey;
//         } else {
//             firstLockKey = userLockKey;
//             secondLockKey = merchantLockKey;
//         }
        
//         return lockApi.lockExecute(firstLockKey, new LockApi.LockExecute<TransferResult>() {
//             @Override
//             public TransferResult execute() {
//                 return lockApi.lockExecute(secondLockKey, new LockApi.LockExecute<TransferResult>() {
//                     @Override
//                     public TransferResult execute() {
//                         return doMerchantToUserPayment(command);
//                     }
                    
//                     @Override
//                     public TransferResult waitTimeOut() {
//                         throw new BusinessException(PaymentError.TRANSACTION_TIMEOUT);
//                     }
//                 });
//             }
            
//             @Override
//             public TransferResult waitTimeOut() {
//                 throw new BusinessException(PaymentError.TRANSACTION_TIMEOUT);
//             }
//         });
//     }
    
//     /**
//      * 商户充值（从平台商户划拨到普通商户）
//      * 使用分布式锁确保资金安全，锁粒度细化到paymentRegion
//      * 
//      * @param command 商户充值命令
//      * @return 充值结果
//      */
//     @Transactional
//     public TransferResult merchantRecharge(MerchantRechargeDomainCommand command) {
//         // 生成锁key，按paymentRegion加锁，提高并发性能
//         String platformLockKey = "recharge:platform:" + command.getPaymentRegion();
//         String userLockKey = "recharge:user:" + command.getUserId() + ":" + command.getPaymentRegion();
        
//         // 按照ID排序，避免死锁
//         String firstLockKey, secondLockKey;
        
//         if (PaymentConstants.PLATFORM_MERCHANT_ID.compareTo(command.getUserId()) < 0) {
//             firstLockKey = platformLockKey;
//             secondLockKey = userLockKey;
//         } else {
//             firstLockKey = userLockKey;
//             secondLockKey = platformLockKey;
//         }
        
//         return lockApi.lockExecute(firstLockKey, new LockApi.LockExecute<TransferResult>() {
//             @Override
//             public TransferResult execute() {
//                 return lockApi.lockExecute(secondLockKey, new LockApi.LockExecute<TransferResult>() {
//                     @Override
//                     public TransferResult execute() {
//                         return doMerchantRecharge(command);
//                     }
                    
//                     @Override
//                     public TransferResult waitTimeOut() {
//                         throw new BusinessException(PaymentError.TRANSACTION_TIMEOUT);
//                     }
//                 });
//             }
            
//             @Override
//             public TransferResult waitTimeOut() {
//                 throw new BusinessException(PaymentError.TRANSACTION_TIMEOUT);
//             }
//         });
//     }
    
//     /**
//      * 商户回收（从普通商户划拨到平台商户）
//      * 使用分布式锁确保资金安全，锁粒度细化到paymentRegion
//      * 
//      * @param command 商户回收命令
//      * @return 回收结果
//      */
//     @Transactional
//     public TransferResult merchantDeduction(MerchantDeductionDomainCommand command) {
//         // 生成锁key，按paymentRegion加锁，提高并发性能
//         String platformLockKey = "deduction:platform:" + command.getPaymentRegion();
//         String userLockKey = "deduction:user:" + command.getUserId() + ":" + command.getPaymentRegion();
        
//         // 按照ID排序，避免死锁
//         String firstLockKey, secondLockKey;
        
//         if (PaymentConstants.PLATFORM_MERCHANT_ID.compareTo(command.getUserId()) < 0) {
//             firstLockKey = platformLockKey;
//             secondLockKey = userLockKey;
//         } else {
//             firstLockKey = userLockKey;
//             secondLockKey = platformLockKey;
//         }
        
//         return lockApi.lockExecute(firstLockKey, new LockApi.LockExecute<TransferResult>() {
//             @Override
//             public TransferResult execute() {
//                 return lockApi.lockExecute(secondLockKey, new LockApi.LockExecute<TransferResult>() {
//                     @Override
//                     public TransferResult execute() {
//                         return doMerchantDeduction(command);
//                     }
                    
//                     @Override
//                     public TransferResult waitTimeOut() {
//                         throw new BusinessException(PaymentError.TRANSACTION_TIMEOUT);
//                     }
//                 });
//             }
            
//             @Override
//             public TransferResult waitTimeOut() {
//                 throw new BusinessException(PaymentError.TRANSACTION_TIMEOUT);
//             }
//         });
//     }
    
//     /**
//      * 通用转账方法
//      * 支持指定交易类型的灵活转账操作
//      * 
//      * @param command 通用转账命令
//      * @param paymentPassword 支付密码（如果需要验证）
//      * @return 转账结果
//      */
//     @Transactional
//     public TransferResult genericTransfer(GenericTransferDomainCommand command, String paymentPassword) {
//         // 生成锁key，按paymentRegion加锁，提高并发性能
//         String fromLockKey = "transfer:generic:" + command.getFromAccountId() + ":" + command.getPaymentRegion();
//         String toLockKey = "transfer:generic:" + command.getToAccountId() + ":" + command.getPaymentRegion();
        
//         // 按照账户ID排序，避免死锁
//         String firstLockKey, secondLockKey;
        
//         if (command.getFromAccountId().compareTo(command.getToAccountId()) < 0) {
//             firstLockKey = fromLockKey;
//             secondLockKey = toLockKey;
//         } else {
//             firstLockKey = toLockKey;
//             secondLockKey = fromLockKey;
//         }
        
//         return lockApi.lockExecute(firstLockKey, new LockApi.LockExecute<TransferResult>() {
//             @Override
//             public TransferResult execute() {
//                 return lockApi.lockExecute(secondLockKey, new LockApi.LockExecute<TransferResult>() {
//                     @Override
//                     public TransferResult execute() {
//                         return doGenericTransfer(command, paymentPassword);
//                     }
                    
//                     @Override
//                     public TransferResult waitTimeOut() {
//                         throw new BusinessException(PaymentError.TRANSACTION_TIMEOUT);
//                     }
//                 });
//             }
            
//             @Override
//             public TransferResult waitTimeOut() {
//                 throw new BusinessException(PaymentError.TRANSACTION_TIMEOUT);
//             }
//         });
//     }
    
//     // ==================== 私有方法 ====================
    
//     /**
//      * 验证支付密码
//      */
//     private void validatePaymentPassword(Account account, String paymentPassword) {
//         if (!StringUtils.hasText(paymentPassword)) {
//             throw new BusinessException(PaymentError.PAYMENT_PASSWORD_REQUIRED);
//         }

//         // 验证密码 - 如果没有设置支付密码，则使用登录密码
//         String storedPassword = account.getPayPassword() != null ? account.getPayPassword() : account.getPassword();
//         if (storedPassword == null) {
//             throw new BusinessException(PaymentError.INVALID_PAYMENT_PASSWORD);
//         }
        
//         if (!passwordEncoder.matches(paymentPassword, storedPassword)) {
//             throw new BusinessException(PaymentError.INVALID_PAYMENT_PASSWORD);
//         }
//     }
    
//     /**
//      * 安全地获取手续费配置，如果配置不存在则返回默认的免手续费配置
//      */
//     private FeeConfig getFeeConfigSafely(String transactionType, String paymentRegion) {
//         FeeConfig feeConfig = feeConfigRepository.findByTransactionTypeAndRegion(transactionType, paymentRegion);
//         if (feeConfig == null) {
//             log.info("未找到手续费配置，使用默认免手续费配置 - 交易类型: {}, 支付区域: {}", transactionType, paymentRegion);
//             return FeeConfig.createDefaultNoFeeConfig(transactionType, paymentRegion);
//         }
//         return feeConfig;
//     }
    
//     /**
//      * 计算手续费
//      * 如果没有获取到手续费配置，返回默认的免手续费配置，确保领域计算不会出现问题
//      */
//     private Money calculateFee(Money amount, String transactionType, String paymentRegion) {
//         FeeConfig feeConfig = getFeeConfigSafely(transactionType, paymentRegion);
        
//         // 如果手续费配置未启用，返回零手续费
//         if (!feeConfig.getEnabled()) {
//             log.info("手续费配置未启用，使用免手续费 - 交易类型: {}, 支付区域: {}", transactionType, paymentRegion);
//             return Money.zeroMoney(paymentRegion);
//         }
        
//         // 如果手续费比例无效，返回零手续费
//         if (feeConfig.getFeeRate() == null || feeConfig.getFeeRate() <= 0) {
//             log.info("手续费比例无效，使用免手续费 - 交易类型: {}, 支付区域: {}, 手续费比例: {}", 
//                     transactionType, paymentRegion, feeConfig.getFeeRate());
//             return Money.zeroMoney(paymentRegion);
//         }
        
//         // 计算手续费金额
//         Long feeAmount = feeConfig.calculateFeeAmount(amount.getAmount());
//         Money feeMoney = Money.create(feeAmount, paymentRegion);
        
//         log.info("手续费计算完成 - 交易类型: {}, 支付区域: {}, 手续费比例: {}, 手续费金额: {}", 
//                 transactionType, paymentRegion, feeConfig.getFeeRate(), feeMoney);
        
//         return feeMoney;
//     }
    
//     /**
//      * 根据交易类型确定流水类型
//      * @param transactionType 交易类型
//      * @param isFromAccount 是否为转出账户
//      * @return 流水类型
//      */
//     private String determineFlowType(String transactionType, boolean isFromAccount) {
//         // 对于转出账户，总是 OUT
//         if (isFromAccount) {
//             return FlowType.OUT.getCode();
//         }
        
//         // 对于转入账户，总是 IN
//         return FlowType.IN.getCode();
//     }
    
//     /**
//      * 执行通用转账业务逻辑
//      */
//     private TransferResult doGenericTransfer(GenericTransferDomainCommand command, String paymentPassword) {
//         // 查询转出账户
//         Account fromAccount = accountRepository.findById(command.getFromAccountId());
//         if (fromAccount == null) {
//             throw new BusinessException(PaymentError.ACCOUNT_NOT_FOUND);
//         }
        
//         // 如果需要验证支付密码，则验证
//         if (command.getRequirePaymentPassword()) {
//             validatePaymentPassword(fromAccount, paymentPassword);
//         }
        
//         // 查询转入账户
//         Account toAccount = accountRepository.findById(command.getToAccountId());
//         if (toAccount == null) {
//             throw new BusinessException(PaymentError.ACCOUNT_NOT_FOUND);
//         }
        
//         // 查询转出钱包
//         Wallet fromWallet = walletRepository.findByOwnerIdAndRegion(fromAccount.getId(), command.getPaymentRegion());
//         if (fromWallet == null) {
//             throw new BusinessException(PaymentError.WALLET_NOT_FOUND);
//         }
        
//         // 查询转入钱包
//         Wallet toWallet = walletRepository.findByOwnerIdAndRegion(toAccount.getId(), command.getPaymentRegion());
//         if (toWallet == null) {
//             throw new BusinessException(PaymentError.WALLET_NOT_FOUND);
//         }
        
//         // 校验账户状态
//         validateAccountStatus(fromAccount, "转出账户");
//         validateAccountStatus(toAccount, "转入账户");
        
//         // 校验钱包状态
//         validateWalletStatus(fromWallet, "转出钱包");
//         validateWalletStatus(toWallet, "转入钱包");
        
//         // 校验余额
//         Money transferAmount = command.getAmount();
//         if (!fromWallet.hasEnoughBalance(transferAmount)) {
//             throw new BusinessException(PaymentError.ACCOUNT_BALANCE_INSUFFICIENT);
//         }
        
//         // 计算手续费
//         Money feeAmount = calculateFee(transferAmount, command.getTransactionType(), command.getPaymentRegion());
        
//         // 根据手续费承担方计算实际到账金额
//         Money actualAmount;
//         Money fromActualAmount;
//         Money toActualAmount;
        
//         if ("FROM".equals(command.getFeeBearer())) {
//             // 转出方承担手续费
//             fromActualAmount = transferAmount.add(feeAmount);
//             toActualAmount = transferAmount;
//             actualAmount = transferAmount;
//         } else {
//             // 转入方承担手续费（默认）
//             fromActualAmount = transferAmount;
//             toActualAmount = transferAmount.subtract(feeAmount);
//             actualAmount = toActualAmount;
//         }
        
//         // 再次校验转出方余额（考虑手续费）
//         if (!fromWallet.hasEnoughBalance(fromActualAmount)) {
//             throw new BusinessException(PaymentError.ACCOUNT_BALANCE_INSUFFICIENT);
//         }
        
//         // 保存旧余额用于事件发布
//         Money oldFromBalance = fromWallet.getBalance();
//         Money oldToBalance = toWallet.getBalance();
        
//         // 执行转账
//         fromWallet.decreaseBalance(fromActualAmount);
//         toWallet.increaseBalance(toActualAmount);
        
//         // 保存钱包
//         walletRepository.updateBalance(fromWallet.getId(), fromWallet.getBalance());
//         walletRepository.updateBalance(toWallet.getId(), toWallet.getBalance());
        
//         // 创建交易记录
//         FeeConfig feeConfig = getFeeConfigSafely(command.getTransactionType(), command.getPaymentRegion());
//         Integer feeRate = feeConfig.getFeeRate();
//         Transaction transaction = createTransaction(
//             fromAccount.getId(), 
//             toAccount.getId(), 
//             transferAmount, 
//             command.getPaymentRegion(),
//             command.getDescription(),
//             TransactionType.fromCode(command.getTransactionType()),
//             feeAmount,
//             feeRate,
//             actualAmount
//         );
        
//         transactionRepository.save(transaction);
        
//         // 保存账户流水（转出、转入）
//         AccountFlow outFlow = AccountFlow.create(
//             fromAccount.getId(),
//             transaction.getId(),
//             determineFlowType(command.getTransactionType(), true), // 转出账户
//             command.getTransactionType(),
//             fromActualAmount,
//             oldFromBalance,
//             fromWallet.getBalance(),
//             "FROM".equals(command.getFeeBearer()) ? feeAmount : Money.zeroMoney(command.getPaymentRegion()),
//             "FROM".equals(command.getFeeBearer()) ? feeRate : 0,
//             fromActualAmount
//         );
//         AccountFlow inFlow = AccountFlow.create(
//             toAccount.getId(),
//             transaction.getId(),
//             determineFlowType(command.getTransactionType(), false), // 转入账户
//             command.getTransactionType(),
//             transferAmount,
//             oldToBalance,
//             toWallet.getBalance(),
//             "TO".equals(command.getFeeBearer()) ? feeAmount : Money.zeroMoney(command.getPaymentRegion()),
//             "TO".equals(command.getFeeBearer()) ? feeRate : 0,
//             toActualAmount
//         );
//         accountFlowRepository.saveBatch(java.util.Arrays.asList(outFlow, inFlow));
        
//         // 发布转账成功事件
//         eventPublisher.publishEvent(new TransferSuccessEvent(
//             transaction.getId(),
//             fromAccount.getId(),
//             toAccount.getId(),
//             transferAmount,
//             command.getPaymentRegion(),
//             command.getTransactionType(),
//             command.getDescription()
//         ));
        
//         // 发布钱包余额变更事件
//         eventPublisher.publishEvent(new WalletBalanceChangedEvent(
//             fromWallet, oldFromBalance, fromActualAmount, "DECREASE", transaction.getId()));
//         eventPublisher.publishEvent(new WalletBalanceChangedEvent(
//             toWallet, oldToBalance, toActualAmount, "INCREASE", transaction.getId()));
        
//         return TransferResult.success(transaction.getId(), "转账成功");
//     }
    
//     /**
//      * 执行用户间转账业务逻辑
//      */
//     private TransferResult doTransferBetweenUsers(TransferDomainCommand command, String paymentPassword) {
//         // 查询转出账户
//         Account fromAccount = accountRepository.findById(command.getFromUserId());
//         if (fromAccount == null) {
//             throw new BusinessException(PaymentError.ACCOUNT_NOT_FOUND);
//         }
//         validatePaymentPassword(fromAccount,paymentPassword);
        
//         // 查询转入账户
//         Account toAccount = accountRepository.findById(command.getToUserId());
//         if (toAccount == null) {
//             throw new BusinessException(PaymentError.ACCOUNT_NOT_FOUND);
//         }
        
//         // 查询转出钱包
//         Wallet fromWallet = walletRepository.findByOwnerIdAndRegion(fromAccount.getId(), command.getPaymentRegion());
//         if (fromWallet == null) {
//             throw new BusinessException(PaymentError.WALLET_NOT_FOUND);
//         }
        
//         // 查询转入钱包
//         Wallet toWallet = walletRepository.findByOwnerIdAndRegion(toAccount.getId(), command.getPaymentRegion());
//         if (toWallet == null) {
//             throw new BusinessException(PaymentError.WALLET_NOT_FOUND);
//         }
        
//         // 校验账户状态
//         validateAccountStatus(fromAccount, "转出账户");
//         validateAccountStatus(toAccount, "转入账户");
        
//         // 校验钱包状态
//         validateWalletStatus(fromWallet, "转出钱包");
//         validateWalletStatus(toWallet, "转入钱包");
        
//         // 校验余额
//         Money transferAmount = command.getAmount();
//         if (!fromWallet.hasEnoughBalance(transferAmount)) {
//             throw new BusinessException(PaymentError.ACCOUNT_BALANCE_INSUFFICIENT);
//         }
        
//         // 计算手续费（由收入方承担）
//         Money feeAmount = calculateFee(transferAmount, TransactionType.USER_TO_USER.getCode(), command.getPaymentRegion());
//         Money actualAmount = transferAmount.subtract(feeAmount);
        
//         // 保存旧余额用于事件发布
//         Money oldFromBalance = fromWallet.getBalance();
//         Money oldToBalance = toWallet.getBalance();
        
//         // 执行转账
//         fromWallet.decreaseBalance(transferAmount);
//         toWallet.increaseBalance(actualAmount); // 收入方实际到账金额
        
//         // 保存钱包
//         walletRepository.updateBalance(fromWallet.getId(), fromWallet.getBalance());
//         walletRepository.updateBalance(toWallet.getId(), toWallet.getBalance());
        
//         // 创建交易记录
//         FeeConfig feeConfig = getFeeConfigSafely(TransactionType.USER_TO_USER.getCode(), command.getPaymentRegion());
//         Integer feeRate = feeConfig.getFeeRate();
//         Transaction transaction = createTransaction(
//             fromAccount.getId(), 
//             toAccount.getId(), 
//             transferAmount, 
//             command.getPaymentRegion(),
//             "用户转账",
//             TransactionType.USER_TO_USER,
//             feeAmount,
//             feeRate,
//             actualAmount
//         );
        
//         transactionRepository.save(transaction);
        
//         // 保存账户流水（转出、转入）
//         AccountFlow outFlow = AccountFlow.createOutFlow(
//             fromAccount.getId(),
//             transaction.getId(),
//             TransactionType.USER_TO_USER.getCode(),
//             transferAmount,
//             oldFromBalance,
//             fromWallet.getBalance(),
//             Money.zeroMoney(command.getPaymentRegion()), // 转出方不承担手续费
//             0,
//             transferAmount
//         );
//         AccountFlow inFlow = AccountFlow.createInFlow(
//             toAccount.getId(),
//             transaction.getId(),
//             TransactionType.USER_TO_USER.getCode(),
//             transferAmount,
//             oldToBalance,
//             toWallet.getBalance(),
//             feeAmount,
//             feeRate,
//             actualAmount
//         );
//         accountFlowRepository.saveBatch(java.util.Arrays.asList(outFlow, inFlow));
        
//         // 发布转账成功事件
//         eventPublisher.publishEvent(new TransferSuccessEvent(
//             transaction.getId(),
//             fromAccount.getId(),
//             toAccount.getId(),
//             transferAmount,
//             command.getPaymentRegion(),
//             "USER_TO_USER",
//             "用户转账"
//         ));
        
//         // 发布钱包余额变更事件
//         eventPublisher.publishEvent(new WalletBalanceChangedEvent(
//             fromWallet, oldFromBalance, transferAmount, "DECREASE", transaction.getId()));
//         eventPublisher.publishEvent(new WalletBalanceChangedEvent(
//             toWallet, oldToBalance, transferAmount, "INCREASE", transaction.getId()));
        
//         return TransferResult.success(transaction.getId(), "转账成功");
//     }
    
//     /**
//      * 执行向商户付款业务逻辑
//      */
//     private TransferResult doPayToMerchant(MerchantPaymentDomainCommand command, String paymentPassword) {
//         // 查询付款方账户
//         Account fromAccount = accountRepository.findById(command.getFromUserId());
//         if (fromAccount == null) {
//             throw new BusinessException(PaymentError.ACCOUNT_NOT_FOUND);
//         }
//         validatePaymentPassword(fromAccount, paymentPassword);
        
//         // 查询商户
//         Merchant merchant = merchantRepository.findById(command.getMerchantId());
//         if (merchant == null) {
//             throw new BusinessException(PaymentError.MERCHANT_NOT_FOUND);
//         }
        
//         // 查询付款方钱包
//         Wallet fromWallet = walletRepository.findByOwnerIdAndRegion(fromAccount.getId(), command.getPaymentRegion());
//         if (fromWallet == null) {
//             throw new BusinessException(PaymentError.WALLET_NOT_FOUND);
//         }
        
//         // 查询商户钱包
//         Wallet merchantWallet = walletRepository.findByOwnerIdAndRegion(merchant.getId(), command.getPaymentRegion());
//         if (merchantWallet == null) {
//             throw new BusinessException(PaymentError.WALLET_NOT_FOUND);
//         }
        
//         // 校验账户状态
//         validateAccountStatus(fromAccount, "付款账户");
//         validateMerchantStatus(merchant);
        
//         // 校验钱包状态
//         validateWalletStatus(fromWallet, "付款钱包");
//         validateWalletStatus(merchantWallet, "商户钱包");
        
//         // 校验余额
//         Money paymentAmount = command.getAmount();
//         if (!fromWallet.hasEnoughBalance(paymentAmount)) {
//             throw new BusinessException(PaymentError.ACCOUNT_BALANCE_INSUFFICIENT);
//         }
        
//         // 计算手续费（由收入方承担）
//         Money feeAmount = calculateFee(paymentAmount, TransactionType.USER_TO_MERCHANT.getCode(), command.getPaymentRegion());
//         Money actualAmount = paymentAmount.subtract(feeAmount);
        
//         // 保存旧余额用于事件发布
//         Money oldFromBalance = fromWallet.getBalance();
//         Money oldToBalance = merchantWallet.getBalance();
        
//         // 执行付款
//         fromWallet.decreaseBalance(paymentAmount);
//         merchantWallet.increaseBalance(actualAmount); // 收入方实际到账金额
        
//         // 保存钱包
//         walletRepository.updateBalance(fromWallet.getId(), fromWallet.getBalance());
//         walletRepository.updateBalance(merchantWallet.getId(), merchantWallet.getBalance());
        
//         // 创建交易记录
//         FeeConfig feeConfig = getFeeConfigSafely(TransactionType.USER_TO_MERCHANT.getCode(), command.getPaymentRegion());
//         Integer feeRate = feeConfig.getFeeRate();
//         Transaction transaction = createTransaction(
//             fromAccount.getId(), 
//             merchant.getId(), 
//             paymentAmount, 
//             command.getPaymentRegion(),
//             "向商户付款",
//             TransactionType.USER_TO_MERCHANT,
//             feeAmount,
//             feeRate,
//             actualAmount
//         );
        
//         transactionRepository.save(transaction);
        
//         // 新增：保存账户流水（转出、转入）
//         AccountFlow outFlow = AccountFlow.createOutFlow(
//             fromAccount.getId(),
//             transaction.getId(),
//             TransactionType.USER_TO_MERCHANT.getCode(),
//             paymentAmount,
//             oldFromBalance,
//             fromWallet.getBalance(),
//             Money.zeroMoney(command.getPaymentRegion()), // 转出方不承担手续费
//             0,
//             paymentAmount
//         );
//         AccountFlow inFlow = AccountFlow.createInFlow(
//             merchant.getId(),
//             transaction.getId(),
//             TransactionType.USER_TO_MERCHANT.getCode(),
//             paymentAmount,
//             oldToBalance,
//             merchantWallet.getBalance(),
//             feeAmount,
//             feeRate,
//             actualAmount
//         );
//         accountFlowRepository.saveBatch(java.util.Arrays.asList(outFlow, inFlow));
        
//         // 发布转账成功事件
//         eventPublisher.publishEvent(new TransferSuccessEvent(
//             transaction.getId(),
//             fromAccount.getId(),
//             merchant.getId(),
//             paymentAmount,
//             command.getPaymentRegion(),
//             "USER_TO_MERCHANT",
//             "向商户付款"
//         ));
        
//         // 发布钱包余额变更事件
//         eventPublisher.publishEvent(new WalletBalanceChangedEvent(
//             fromWallet, oldFromBalance, paymentAmount, "DECREASE", transaction.getId()));
//         eventPublisher.publishEvent(new WalletBalanceChangedEvent(
//             merchantWallet, oldToBalance, paymentAmount, "INCREASE", transaction.getId()));
        
//         return TransferResult.success(transaction.getId(), "付款成功");
//     }
    
//     /**
//      * 执行商户付款业务逻辑
//      */
//     private TransferResult doMerchantPayment(MerchantPaymentDomainCommand command) {
//         // 查询商户
//         Merchant merchant = merchantRepository.findById(command.getMerchantId());
//         if (merchant == null) {
//             throw new BusinessException(PaymentError.MERCHANT_NOT_FOUND);
//         }
        
//         // 查询商户钱包
//         Wallet merchantWallet = walletRepository.findByOwnerIdAndRegion(merchant.getId(), command.getPaymentRegion());
//         if (merchantWallet == null) {
//             throw new BusinessException(PaymentError.WALLET_NOT_FOUND);
//         }
        
//         // 校验商户状态
//         validateMerchantStatus(merchant);
        
//         // 校验钱包状态
//         validateWalletStatus(merchantWallet, "商户钱包");
        
//         // 校验商户余额
//         Money paymentAmount = command.getAmount();
//         if (!merchantWallet.hasEnoughBalance(paymentAmount)) {
//             throw new BusinessException(PaymentError.MERCHANT_BALANCE_INSUFFICIENT);
//         }
        
//         // 计算手续费（由收入方承担）
//         Money feeAmount = calculateFee(paymentAmount, TransactionType.MERCHANT_TO_USER.getCode(), command.getPaymentRegion());
//         Money actualAmount = paymentAmount.subtract(feeAmount);
        
//         // 保存旧余额用于事件发布
//         Money oldBalance = merchantWallet.getBalance();
        
//         // 执行付款（简化版本，直接扣减商户余额）
//         merchantWallet.decreaseBalance(paymentAmount);
        
//         // 保存钱包
//         walletRepository.updateBalance(merchantWallet.getId(), merchantWallet.getBalance());
        
//         // 创建交易记录
//         FeeConfig feeConfig = getFeeConfigSafely(TransactionType.MERCHANT_TO_USER.getCode(), command.getPaymentRegion());
//         Integer feeRate = feeConfig.getFeeRate();
//         Transaction transaction = createTransaction(
//             merchant.getId(), 
//             "SYSTEM", 
//             paymentAmount, 
//             command.getPaymentRegion(),
//             "商户付款",
//             TransactionType.MERCHANT_TO_USER,
//             feeAmount,
//             feeRate,
//             actualAmount
//         );
        
//         transactionRepository.save(transaction);
        
//         // 新增：保存账户流水（转出、转入）
//         AccountFlow outFlow = AccountFlow.createOutFlow(
//             merchant.getId(),
//             transaction.getId(),
//             TransactionType.MERCHANT_TO_USER.getCode(),
//             paymentAmount,
//             oldBalance,
//             merchantWallet.getBalance(),
//             Money.zeroMoney(command.getPaymentRegion()), // 转出方不承担手续费
//             0,
//             paymentAmount
//         );
//         AccountFlow inFlow = AccountFlow.createInFlow(
//             "SYSTEM",
//             transaction.getId(),
//             TransactionType.MERCHANT_TO_USER.getCode(),
//             paymentAmount,
//             oldBalance,
//             merchantWallet.getBalance(),
//             feeAmount,
//             feeRate,
//             actualAmount
//         );
//         accountFlowRepository.saveBatch(java.util.Arrays.asList(outFlow, inFlow));
        
//         // 发布转账成功事件
//         eventPublisher.publishEvent(new TransferSuccessEvent(
//             transaction.getId(),
//             merchant.getId(),
//             "SYSTEM",
//             paymentAmount,
//             command.getPaymentRegion(),
//             "MERCHANT_TO_USER",
//             "商户付款"
//         ));
        
//         // 发布钱包余额变更事件
//         eventPublisher.publishEvent(new WalletBalanceChangedEvent(
//             merchantWallet, oldBalance, paymentAmount, "DECREASE", transaction.getId()));
        
//         return TransferResult.success(transaction.getId(), "付款成功");
//     }
    
//     /**
//      * 执行商户向用户发放业务逻辑
//      */
//     private TransferResult doMerchantToUserPayment(MerchantToUserPaymentDomainCommand command) {
//         // 查询商户
//         Merchant merchant = merchantRepository.findById(command.getMerchantId());
//         if (merchant == null) {
//             throw new BusinessException(PaymentError.MERCHANT_NOT_FOUND);
//         }
        
//         // 查询用户账户
//         Account userAccount = accountRepository.findById(command.getUserId());
//         if (userAccount == null) {
//             throw new BusinessException(PaymentError.ACCOUNT_NOT_FOUND);
//         }
        
//         // 查询商户钱包
//         Wallet merchantWallet = walletRepository.findByOwnerIdAndRegion(merchant.getId(), command.getPaymentRegion());
//         if (merchantWallet == null) {
//             throw new BusinessException(PaymentError.WALLET_NOT_FOUND);
//         }
        
//         // 查询用户钱包
//         Wallet userWallet = walletRepository.findByOwnerIdAndRegion(userAccount.getId(), command.getPaymentRegion());
//         if (userWallet == null) {
//             throw new BusinessException(PaymentError.WALLET_NOT_FOUND);
//         }
        
//         // 校验商户状态
//         validateMerchantStatus(merchant);
        
//         // 校验账户状态
//         validateAccountStatus(userAccount, "用户账户");
        
//         // 校验钱包状态
//         validateWalletStatus(merchantWallet, "商户钱包");
//         validateWalletStatus(userWallet, "用户钱包");
        
//         // 校验商户余额
//         Money paymentAmount = command.getAmount();
//         if (!merchantWallet.hasEnoughBalance(paymentAmount)) {
//             throw new BusinessException(PaymentError.MERCHANT_BALANCE_INSUFFICIENT);
//         }
        
//         // 计算手续费（由收入方承担）
//         Money feeAmount = calculateFee(paymentAmount, TransactionType.MERCHANT_TO_USER.getCode(), command.getPaymentRegion());
//         Money actualAmount = paymentAmount.subtract(feeAmount);
        
//         // 保存旧余额用于事件发布
//         Money oldMerchantBalance = merchantWallet.getBalance();
//         Money oldUserBalance = userWallet.getBalance();
        
//         // 执行发放（从商户钱包扣款，向用户钱包充值）
//         merchantWallet.decreaseBalance(paymentAmount);
//         userWallet.increaseBalance(actualAmount); // 收入方实际到账金额
        
//         // 保存钱包
//         walletRepository.updateBalance(merchantWallet.getId(), merchantWallet.getBalance());
//         walletRepository.updateBalance(userWallet.getId(), userWallet.getBalance());
        
//         // 创建交易记录
//         FeeConfig feeConfig = getFeeConfigSafely(TransactionType.MERCHANT_TO_USER.getCode(), command.getPaymentRegion());
//         Integer feeRate = feeConfig.getFeeRate();
//         Transaction transaction = createTransaction(
//             merchant.getId(), 
//             userAccount.getId(), 
//             paymentAmount, 
//             command.getPaymentRegion(),
//             "商户向用户发放",
//             TransactionType.MERCHANT_TO_USER,
//             feeAmount,
//             feeRate,
//             actualAmount
//         );
        
//         transactionRepository.save(transaction);
        
//         // 新增：保存账户流水（转出、转入）
//         AccountFlow outFlow = AccountFlow.createOutFlow(
//             merchant.getId(),
//             transaction.getId(),
//             TransactionType.MERCHANT_TO_USER.getCode(),
//             paymentAmount,
//             oldMerchantBalance,
//             merchantWallet.getBalance(),
//             Money.zeroMoney(command.getPaymentRegion()), // 转出方不承担手续费
//             0,
//             paymentAmount
//         );
//         AccountFlow inFlow = AccountFlow.createInFlow(
//             userAccount.getId(),
//             transaction.getId(),
//             TransactionType.MERCHANT_TO_USER.getCode(),
//             paymentAmount,
//             oldUserBalance,
//             userWallet.getBalance(),
//             feeAmount,
//             feeRate,
//             actualAmount
//         );
//         accountFlowRepository.saveBatch(java.util.Arrays.asList(outFlow, inFlow));
        
//         // 发布转账成功事件
//         eventPublisher.publishEvent(new TransferSuccessEvent(
//             transaction.getId(),
//             merchant.getId(),
//             userAccount.getId(),
//             paymentAmount,
//             command.getPaymentRegion(),
//             "MERCHANT_TO_USER",
//             "商户向用户发放"
//         ));
        
//         // 发布钱包余额变更事件
//         eventPublisher.publishEvent(new WalletBalanceChangedEvent(
//             merchantWallet, oldMerchantBalance, paymentAmount, "DECREASE", transaction.getId()));
//         eventPublisher.publishEvent(new WalletBalanceChangedEvent(
//             userWallet, oldUserBalance, paymentAmount, "INCREASE", transaction.getId()));
        
//         return TransferResult.success(transaction.getId(), "发放成功");
//     }
    
//     /**
//      * 执行商户充值业务逻辑（从平台商户划拨到普通商户）
//      */
//     private TransferResult doMerchantRecharge(MerchantRechargeDomainCommand command) {
//         // 查询平台商户
//         Merchant platformMerchant = merchantRepository.findById(PaymentConstants.PLATFORM_MERCHANT_ID);
//         if (platformMerchant == null) {
//             throw new BusinessException(PaymentError.MERCHANT_NOT_FOUND);
//         }
        
//         // 查询普通商户
//         Merchant userMerchant = merchantRepository.findById(command.getUserId());
//         if (userMerchant == null) {
//             throw new BusinessException(PaymentError.MERCHANT_NOT_FOUND);
//         }
        
//         // 查询平台商户钱包
//         Wallet platformWallet = walletRepository.findByOwnerIdAndRegion(platformMerchant.getId(), command.getPaymentRegion());
//         if (platformWallet == null) {
//             throw new BusinessException(PaymentError.WALLET_NOT_FOUND);
//         }
        
//         // 查询普通商户钱包
//         Wallet userWallet = walletRepository.findByOwnerIdAndRegion(userMerchant.getId(), command.getPaymentRegion());
//         if (userWallet == null) {
//             throw new BusinessException(PaymentError.WALLET_NOT_FOUND);
//         }
        
//         // 校验商户状态
//         validateMerchantStatus(platformMerchant);
//         validateMerchantStatus(userMerchant);
        
//         // 校验钱包状态
//         validateWalletStatus(platformWallet, "平台商户钱包");
//         validateWalletStatus(userWallet, "普通商户钱包");
        
//         // 校验平台商户余额
//         Money rechargeAmount = command.getAmount();
//         if (!platformWallet.hasEnoughBalance(rechargeAmount)) {
//             throw new BusinessException(PaymentError.MERCHANT_BALANCE_INSUFFICIENT);
//         }
        
//         // 计算手续费（由收入方承担）
//         Money feeAmount = calculateFee(rechargeAmount, TransactionType.PLATFORM_RECHARGE.getCode(), command.getPaymentRegion());
//         Money actualAmount = rechargeAmount.subtract(feeAmount);
        
//         // 保存旧余额用于事件发布
//         Money oldPlatformBalance = platformWallet.getBalance();
//         Money oldUserBalance = userWallet.getBalance();
        
//         // 执行充值（从平台商户划拨到普通商户）
//         platformWallet.decreaseBalance(rechargeAmount);
//         userWallet.increaseBalance(actualAmount); // 收入方实际到账金额
        
//         // 保存钱包
//         walletRepository.updateBalance(platformWallet.getId(), platformWallet.getBalance());
//         walletRepository.updateBalance(userWallet.getId(), userWallet.getBalance());
        
//         // 创建交易记录
//         FeeConfig feeConfig = getFeeConfigSafely(TransactionType.PLATFORM_RECHARGE.getCode(), command.getPaymentRegion());
//         Integer feeRate = feeConfig.getFeeRate();
//         Transaction transaction = createTransaction(
//             platformMerchant.getId(), 
//             userMerchant.getId(), 
//             rechargeAmount, 
//             command.getPaymentRegion(),
//             "商户充值（平台商户划拨）",
//             TransactionType.PLATFORM_RECHARGE,
//             feeAmount,
//             feeRate,
//             actualAmount
//         );
        
//         transactionRepository.save(transaction);
        
//         // 新增：保存账户流水（转出、转入）
//         AccountFlow outFlow = AccountFlow.createOutFlow(
//             platformMerchant.getId(),
//             transaction.getId(),
//             TransactionType.PLATFORM_RECHARGE.getCode(),
//             rechargeAmount,
//             oldPlatformBalance,
//             platformWallet.getBalance(),
//             Money.zeroMoney(command.getPaymentRegion()), // 转出方不承担手续费
//             0,
//             rechargeAmount
//         );
//         AccountFlow inFlow = AccountFlow.createInFlow(
//             userMerchant.getId(),
//             transaction.getId(),
//             TransactionType.PLATFORM_RECHARGE.getCode(),
//             rechargeAmount,
//             oldUserBalance,
//             userWallet.getBalance(),
//             feeAmount,
//             feeRate,
//             actualAmount
//         );
//         accountFlowRepository.saveBatch(java.util.Arrays.asList(outFlow, inFlow));
        
//         // 发布转账成功事件
//         eventPublisher.publishEvent(new TransferSuccessEvent(
//             transaction.getId(),
//             platformMerchant.getId(),
//             userMerchant.getId(),
//             rechargeAmount,
//             command.getPaymentRegion(),
//             "PLATFORM_RECHARGE",
//             "商户充值（平台商户划拨）"
//         ));
        
//         // 发布钱包余额变更事件
//         eventPublisher.publishEvent(new WalletBalanceChangedEvent(
//             platformWallet, oldPlatformBalance, rechargeAmount, "DECREASE", transaction.getId()));
//         eventPublisher.publishEvent(new WalletBalanceChangedEvent(
//             userWallet, oldUserBalance, rechargeAmount, "INCREASE", transaction.getId()));
        
//         return TransferResult.success(transaction.getId(), "充值成功");
//     }
    
//     /**
//      * 执行商户回收业务逻辑（从普通商户划拨到平台商户）
//      */
//     private TransferResult doMerchantDeduction(MerchantDeductionDomainCommand command) {
//         // 查询平台商户
//         Merchant platformMerchant = merchantRepository.findById(PaymentConstants.PLATFORM_MERCHANT_ID);
//         if (platformMerchant == null) {
//             throw new BusinessException(PaymentError.MERCHANT_NOT_FOUND);
//         }
        
//         // 查询普通商户
//         Merchant userMerchant = merchantRepository.findById(command.getUserId());
//         if (userMerchant == null) {
//             throw new BusinessException(PaymentError.MERCHANT_NOT_FOUND);
//         }
        
//         // 查询平台商户钱包
//         Wallet platformWallet = walletRepository.findByOwnerIdAndRegion(platformMerchant.getId(), command.getPaymentRegion());
//         if (platformWallet == null) {
//             throw new BusinessException(PaymentError.WALLET_NOT_FOUND);
//         }
        
//         // 查询普通商户钱包
//         Wallet userWallet = walletRepository.findByOwnerIdAndRegion(userMerchant.getId(), command.getPaymentRegion());
//         if (userWallet == null) {
//             throw new BusinessException(PaymentError.WALLET_NOT_FOUND);
//         }
        
//         // 校验商户状态
//         validateMerchantStatus(platformMerchant);
//         validateMerchantStatus(userMerchant);
        
//         // 校验钱包状态
//         validateWalletStatus(platformWallet, "平台商户钱包");
//         validateWalletStatus(userWallet, "普通商户钱包");
        
//         // 校验普通商户余额
//         Money deductionAmount = command.getAmount();
//         if (!userWallet.hasEnoughBalance(deductionAmount)) {
//             throw new BusinessException(PaymentError.MERCHANT_BALANCE_INSUFFICIENT);
//         }
        
//         // 计算手续费（由收入方承担）
//         Money feeAmount = calculateFee(deductionAmount, TransactionType.PLATFORM_DEDUCTION.getCode(), command.getPaymentRegion());
//         Money actualAmount = deductionAmount.subtract(feeAmount);
        
//         // 保存旧余额用于事件发布
//         Money oldPlatformBalance = platformWallet.getBalance();
//         Money oldUserBalance = userWallet.getBalance();
        
//         // 执行回收（从普通商户划拨到平台商户）
//         userWallet.decreaseBalance(deductionAmount);
//         platformWallet.increaseBalance(actualAmount); // 收入方实际到账金额
        
//         // 保存钱包
//         walletRepository.updateBalance(userWallet.getId(), userWallet.getBalance());
//         walletRepository.updateBalance(platformWallet.getId(), platformWallet.getBalance());
        
//         // 创建交易记录
//         FeeConfig feeConfig = getFeeConfigSafely(TransactionType.PLATFORM_DEDUCTION.getCode(), command.getPaymentRegion());
//         Integer feeRate = feeConfig.getFeeRate();
//         Transaction transaction = createTransaction(
//             userMerchant.getId(), 
//             platformMerchant.getId(), 
//             deductionAmount, 
//             command.getPaymentRegion(),
//             "商户回收（划拨到平台商户）",
//             TransactionType.PLATFORM_DEDUCTION,
//             feeAmount,
//             feeRate,
//             actualAmount
//         );
        
//         transactionRepository.save(transaction);
        
//         // 新增：保存账户流水（转出、转入）
//         AccountFlow outFlow = AccountFlow.createOutFlow(
//             userMerchant.getId(),
//             transaction.getId(),
//             TransactionType.PLATFORM_DEDUCTION.getCode(),
//             deductionAmount,
//             oldUserBalance,
//             userWallet.getBalance(),
//             Money.zeroMoney(command.getPaymentRegion()), // 转出方不承担手续费
//             0,
//             deductionAmount
//         );
//         AccountFlow inFlow = AccountFlow.createInFlow(
//             platformMerchant.getId(),
//             transaction.getId(),
//             TransactionType.PLATFORM_DEDUCTION.getCode(),
//             deductionAmount,
//             oldPlatformBalance,
//             platformWallet.getBalance(),
//             feeAmount,
//             feeRate,
//             actualAmount
//         );
//         accountFlowRepository.saveBatch(java.util.Arrays.asList(outFlow, inFlow));
        
//         // 发布转账成功事件
//         eventPublisher.publishEvent(new TransferSuccessEvent(
//             transaction.getId(),
//             userMerchant.getId(),
//             platformMerchant.getId(),
//             deductionAmount,
//             command.getPaymentRegion(),
//             "PLATFORM_DEDUCTION",
//             "商户回收（划拨到平台商户）"
//         ));
        
//         // 发布钱包余额变更事件
//         eventPublisher.publishEvent(new WalletBalanceChangedEvent(
//             userWallet, oldUserBalance, deductionAmount, "DECREASE", transaction.getId()));
//         eventPublisher.publishEvent(new WalletBalanceChangedEvent(
//             platformWallet, oldPlatformBalance, deductionAmount, "INCREASE", transaction.getId()));
        
//         return TransferResult.success(transaction.getId(), "回收成功");
//     }
    
//     /**
//      * 校验账户状态
//      */
//     private void validateAccountStatus(Account account, String accountType) {
//         if (account == null) {
//             throw new BusinessException(PaymentError.ACCOUNT_NOT_FOUND);
//         }
        
//         if (!account.isActive()) {
//             throw new BusinessException(PaymentError.ACCOUNT_FROZEN);
//         }
//     }
    
//     /**
//      * 校验钱包状态
//      */
//     private void validateWalletStatus(Wallet wallet, String walletType) {
//         if (wallet == null) {
//             throw new BusinessException(PaymentError.WALLET_NOT_FOUND);
//         }
        
//         if (!wallet.isActive()) {
//             throw new BusinessException(PaymentError.WALLET_FROZEN);
//         }
//     }
    
//     /**
//      * 校验商户状态
//      */
//     private void validateMerchantStatus(Merchant merchant) {
//         if (merchant == null) {
//             throw new BusinessException(PaymentError.MERCHANT_NOT_FOUND);
//         }
        
//         if (!merchant.isActive()) {
//             throw new BusinessException(PaymentError.MERCHANT_FROZEN);
//         }
//     }
    
//     /**
//      * 创建交易记录
//      */
//     private Transaction createTransaction(String fromId, String toId, Money amount, 
//                                         String paymentRegion, String description, TransactionType type,
//                                         Money feeAmount, Integer feeRate, Money actualAmount) {
//         Transaction transaction = new Transaction();
//         transaction.setId(IdUtils.fastSimpleUUID());
//         transaction.setFromAccountId(fromId);
//         transaction.setToAccountId(toId);
//         transaction.setAmount(amount);
//         transaction.setPaymentRegion(paymentRegion);
//         transaction.setDescription(description);
//         transaction.setType(type.getCode());
//         transaction.setStatus("SUCCESS");
//         transaction.setCreateTime(System.currentTimeMillis());
//         transaction.setUpdateTime(System.currentTimeMillis());
//         transaction.setFeeAmount(feeAmount);
//         transaction.setFeeRate(feeRate);
//         transaction.setActualAmount(actualAmount);
//         return transaction;
//     }
// } 