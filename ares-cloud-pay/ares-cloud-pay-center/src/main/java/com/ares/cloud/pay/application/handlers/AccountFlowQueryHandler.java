package com.ares.cloud.pay.application.handlers;

import com.ares.cloud.pay.application.dto.AccountFlowDTO;
import com.ares.cloud.pay.application.queries.AccountFlowQuery;
import com.ares.cloud.pay.application.queries.PlatformFlowQuery;
import com.ares.cloud.pay.domain.constant.PaymentConstants;
import com.ares.cloud.pay.domain.model.Account;
import com.ares.cloud.pay.domain.model.Merchant;
import com.ares.cloud.pay.domain.model.Transaction;
import com.ares.cloud.pay.domain.repository.AccountFlowRepository;
import com.ares.cloud.pay.domain.repository.AccountRepository;
import com.ares.cloud.pay.domain.repository.MerchantRepository;
import com.ares.cloud.pay.domain.repository.TransactionRepository;
import com.ares.cloud.pay.domain.enums.PaymentError;
import com.ares.cloud.pay.infrastructure.persistence.entity.AccountFlowEntity;
import com.ares.cloud.pay.infrastructure.persistence.mapper.AccountFlowMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.common.dto.PageResult;
import org.ares.cloud.common.exception.BusinessException;
import org.ares.cloud.common.model.Money;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 账户流水查询处理器
 * 处理所有账户流水相关的查询操作
 */
@Component
@Slf4j
public class AccountFlowQueryHandler {

    @Resource
    private AccountFlowRepository accountFlowRepository;
    
    @Resource
    private TransactionRepository transactionRepository;
    
    @Resource
    private AccountRepository accountRepository;
    
    @Resource
    private MerchantRepository merchantRepository;
    
    @Resource
    private AccountFlowMapper accountFlowMapper;

    /**
     * 查询用户交易流水
     */
    public PageResult<AccountFlowDTO> getPlatformFlows(PlatformFlowQuery params) {
        try {
           AccountFlowQuery query = new AccountFlowQuery();
           query.setAccountId(PaymentConstants.PLATFORM_MERCHANT_ID);
           query.setFlowType(params.getFlowType());
           query.setTransactionId(params.getTransactionId());
           query.setStartTime(params.getStartTime());
           query.setLimit(params.getLimit());
           query.setEndTime(params.getEndTime());
           query.setKeyword(params.getKeyword());
           query.setAsc(params.isAsc());
           query.setOrder(params.getOrder());
           query.setPage(params.getPage());
           query.setTransactionType(params.getTransactionType());
           query.setPaymentRegion(params.getPaymentRegion());
           query.setMaxAmount(params.getMaxAmount());
           query.setMaxAmount(params.getMinAmount());



            // 执行分页查询，直接获取Entity列表
            List<AccountFlowEntity> accountFlowEntities = accountFlowMapper.findByQuery(
                    new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(query.getPage(), query.getLimit()),
                    query
            ).getRecords();

            if (accountFlowEntities.isEmpty()) {
                return new PageResult<>(Collections.emptyList(), 0L);
            }

            // 统计总数
            long total = accountFlowRepository.countByQuery(query);

            // 直接转换为DTO并填充交易对方信息
            List<AccountFlowDTO> dtos = convertEntitiesToDTOsWithCounterpartyInfo(accountFlowEntities);

            return new PageResult<>(dtos, total);
        } catch (Exception e) {
            log.error("查询账户交易流水失败", e);
            throw new BusinessException(PaymentError.SYSTEM_ERROR);
        }
    }

    /**
     * 查询用户交易流水
     */
    public PageResult<AccountFlowDTO> getUserAccountFlows(AccountFlowQuery query) {
        validateQuery(query);
        
        try {
            // 获取当前用户ID
            String userId = ApplicationContext.getUserId();
            if (!StringUtils.hasText(userId)) {
                throw new BusinessException(PaymentError.ACCOUNT_NOT_FOUND);
            }
            
            // 根据用户ID查找账户
            Account account = accountRepository.findByUserId(userId);
            if (account == null) {
                return new PageResult<>(Collections.emptyList(), 0L);
            }
            
            return getAccountFlowsByAccountId(account.getId(), query);
        } catch (Exception e) {
            log.error("查询用户交易流水失败", e);
            throw new BusinessException(PaymentError.SYSTEM_ERROR);
        }
    }

    /**
     * 查询商户交易流水
     */
    public PageResult<AccountFlowDTO> getMerchantAccountFlows(AccountFlowQuery query) {
        validateQuery(query);

        // 获取当前商户ID
        String merchantId = ApplicationContext.getTenantId();
        if (!StringUtils.hasText(merchantId)) {
            throw new BusinessException(PaymentError.MERCHANT_NOT_FOUND);
        }

        return getAccountFlowsByAccountId(merchantId, query);
    }

    /**
     * 根据账户ID查询交易流水
     */
    public PageResult<AccountFlowDTO> getAccountFlowsByAccountId(String accountId, AccountFlowQuery query) {
        validateQuery(query);
        
        try {
            // 设置账户ID到查询对象
            query.setAccountId(accountId);
            
            // 执行分页查询，直接获取Entity列表
            List<AccountFlowEntity> accountFlowEntities = accountFlowMapper.findByQuery(
                new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(query.getPage(), query.getLimit()), 
                query
            ).getRecords();
            
            if (accountFlowEntities.isEmpty()) {
                return new PageResult<>(Collections.emptyList(), 0L);
            }
            
            // 统计总数
            long total = accountFlowRepository.countByQuery(query);
            
            // 直接转换为DTO并填充交易对方信息
            List<AccountFlowDTO> dtos = convertEntitiesToDTOsWithCounterpartyInfo(accountFlowEntities);
            
            return new PageResult<>(dtos, total);
        } catch (Exception e) {
            log.error("查询账户交易流水失败", e);
            throw new BusinessException(PaymentError.SYSTEM_ERROR);
        }
    }

    /**
     * 验证查询参数的有效性
     */
    private void validateQuery(AccountFlowQuery query) {
        if (query == null) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        if (query.getPage() < 1) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        if (query.getLimit() < 1) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
    }

    /**
     * 直接转换Entity为DTO并填充交易对方信息
     */
    private List<AccountFlowDTO> convertEntitiesToDTOsWithCounterpartyInfo(List<AccountFlowEntity> accountFlowEntities) {
        // 获取所有交易ID
        Set<String> transactionIds = accountFlowEntities.stream()
            .map(AccountFlowEntity::getTransactionId)
            .collect(Collectors.toSet());
        
        // 批量查询交易信息
        Map<String, Transaction> transactionMap = transactionIds.stream()
            .map(transactionRepository::findById)
            .filter(transaction -> transaction != null)
            .collect(Collectors.toMap(Transaction::getId, transaction -> transaction));
        
        // 获取所有交易对方账户ID
        Set<String> counterpartyAccountIds = transactionMap.values().stream()
            .flatMap(transaction -> {
                Set<String> ids = new java.util.HashSet<>();
                if (StringUtils.hasText(transaction.getFromAccountId())) {
                    ids.add(transaction.getFromAccountId());
                }
                if (StringUtils.hasText(transaction.getToAccountId())) {
                    ids.add(transaction.getToAccountId());
                }
                return ids.stream();
            })
            .collect(Collectors.toSet());
        
        // 批量查询账户信息
        Map<String, Account> accountMap = counterpartyAccountIds.stream()
            .map(accountRepository::findById)
            .filter(account -> account != null)
            .collect(Collectors.toMap(Account::getId, account -> account));
        
        // 批量查询商户信息
        Map<String, Merchant> merchantMap = counterpartyAccountIds.stream()
            .map(merchantRepository::findById)
            .filter(merchant -> merchant != null)
            .collect(Collectors.toMap(Merchant::getId, merchant -> merchant));
        
        // 直接转换为DTO
        return accountFlowEntities.stream()
            .map(entity -> convertEntityToDTO(entity, transactionMap, accountMap, merchantMap))
            .collect(Collectors.toList());
    }

    /**
     * 直接转换Entity为DTO
     */
    private AccountFlowDTO convertEntityToDTO(AccountFlowEntity entity, 
                                            Map<String, Transaction> transactionMap,
                                            Map<String, Account> accountMap,
                                            Map<String, Merchant> merchantMap) {
        AccountFlowDTO dto = new AccountFlowDTO();
        dto.setId(entity.getId());
        dto.setAccountId(entity.getAccountId());
        dto.setTransactionId(entity.getTransactionId());
        dto.setFlowType(entity.getFlowType());
        dto.setTransactionType(entity.getTransactionType());
        
        // 转换金额字段
        if (entity.getAmount() != null) {
            dto.setAmount(Money.of(entity.getAmount(), entity.getCurrency(), entity.getScale()));
        }
        if (entity.getFeeAmount() != null) {
            dto.setFeeAmount(Money.of(entity.getFeeAmount(), entity.getCurrency(), entity.getScale()));
        }
        if (entity.getActualAmount() != null) {
            dto.setActualAmount(Money.of(entity.getActualAmount(), entity.getCurrency(), entity.getScale()));
        }
        if (entity.getBalanceBefore() != null) {
            dto.setBalanceBefore(Money.of(entity.getBalanceBefore(), entity.getCurrency(), entity.getScale()));
        }
        if (entity.getBalanceAfter() != null) {
            dto.setBalanceAfter(Money.of(entity.getBalanceAfter(), entity.getCurrency(), entity.getScale()));
        }
        
        dto.setFeeRate(entity.getFeeRate());
        dto.setPaymentRegion(entity.getCurrency());
        dto.setCreateTime(entity.getCreateTime());
        
        // 填充交易信息
        Transaction transaction = transactionMap.get(entity.getTransactionId());
        if (transaction != null) {
            dto.setDescription(transaction.getDescription());
            // 优先使用 AccountFlowEntity 中的 transactionType，如果为空则从 Transaction 中获取
            if (dto.getTransactionType() == null || dto.getTransactionType().isEmpty()) {
                dto.setTransactionType(transaction.getType());
            }
            
            // 确定交易对方账户ID
            String counterpartyAccountId = null;
            if (entity.getAccountId().equals(transaction.getFromAccountId())) {
                counterpartyAccountId = transaction.getToAccountId();
            } else if (entity.getAccountId().equals(transaction.getToAccountId())) {
                counterpartyAccountId = transaction.getFromAccountId();
            }
            
            if (StringUtils.hasText(counterpartyAccountId)) {
                dto.setCounterpartyAccountId(counterpartyAccountId);
                
                // 填充交易对方信息
                fillCounterpartyInfo(dto, counterpartyAccountId, accountMap, merchantMap);
            }
        }
        
        return dto;
    }

    /**
     * 填充交易对方信息
     */
    private void fillCounterpartyInfo(AccountFlowDTO dto, String counterpartyAccountId,
                                    Map<String, Account> accountMap,
                                    Map<String, Merchant> merchantMap) {
        // 先查找账户信息
        Account account = accountMap.get(counterpartyAccountId);
        if (account != null) {
            dto.setCounterpartyType("ACCOUNT");
            dto.setCounterpartyCountryCode(account.getCountryCode());
            dto.setCounterpartyPhone(account.getPhone());
            dto.setCounterpartyAccount(account.getAccount());
            dto.setCounterpartyName(account.getAccount()); // 使用账号作为名称
            return;
        }
        
        // 再查找商户信息
        Merchant merchant = merchantMap.get(counterpartyAccountId);
        if (merchant != null) {
            dto.setCounterpartyType("MERCHANT");
            // 商户模型中没有countryCode字段，使用contactPhone作为手机号
            dto.setCounterpartyCountryCode(""); // 商户暂时不设置国家代码
            dto.setCounterpartyPhone(merchant.getContactPhone());
            dto.setCounterpartyAccount(merchant.getMerchantNo());
            dto.setCounterpartyName(merchant.getMerchantName());
            return;
        }
        
        // 如果都找不到，可能是系统账户
        if ("SYSTEM".equals(counterpartyAccountId)) {
            dto.setCounterpartyType("SYSTEM");
            dto.setCounterpartyName("系统账户");
        }
    }
} 