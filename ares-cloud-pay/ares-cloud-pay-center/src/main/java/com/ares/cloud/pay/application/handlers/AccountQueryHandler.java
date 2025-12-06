package com.ares.cloud.pay.application.handlers;

import com.ares.cloud.pay.application.dto.AccountDetailDTO;
import com.ares.cloud.pay.application.dto.AccountSummaryDTO;
import com.ares.cloud.pay.application.dto.AccountStatistics;
import com.ares.cloud.pay.application.dto.TransactionTypeStatistics;
import com.ares.cloud.pay.application.dto.WalletDTO;
import com.ares.cloud.pay.application.queries.AccountStatisticsQuery;
import com.ares.cloud.pay.application.queries.EntityQuery;
import com.ares.cloud.pay.domain.enums.FlowType;
import com.ares.cloud.pay.domain.enums.TransactionType;
import com.ares.cloud.pay.domain.model.Account;
import com.ares.cloud.pay.domain.model.Wallet;
import com.ares.cloud.pay.domain.repository.AccountRepository;
import com.ares.cloud.pay.domain.repository.WalletRepository;
import com.ares.cloud.pay.domain.enums.PaymentError;
import com.ares.cloud.pay.infrastructure.persistence.entity.AccountEntity;
import com.ares.cloud.pay.infrastructure.persistence.entity.AccountStatisticsResult;
import com.ares.cloud.pay.infrastructure.persistence.entity.TransactionTypeStatisticsResult;
import com.ares.cloud.pay.infrastructure.persistence.mapper.AccountFlowMapper;
import com.ares.cloud.pay.infrastructure.persistence.mapper.AccountMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.common.dto.PageResult;
import org.ares.cloud.common.exception.BusinessException;
import org.ares.cloud.common.model.Money;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 账户查询处理器
 * 处理所有账户相关的查询操作
 */
@Component
@Slf4j
public class AccountQueryHandler {

    @Resource
    private AccountRepository accountRepository;
    
    @Resource
    private WalletRepository walletRepository;
    
    @Resource
    private AccountMapper accountMapper;
    
    @Resource
    private AccountFlowMapper accountFlowMapper;

    /**
     * 根据账户ID查询账户
     */
    public AccountDetailDTO getAccountById(String accountId) {
        Account account = accountRepository.findById(accountId);
        if (account == null) {
            return null;
        }
        return convertToDetailDTO(account);
    }

    /**
     * 根据用户ID查询账户
     */
    public AccountDetailDTO getAccountByUserId(String userId) {
        Account account = accountRepository.findByUserId(userId);
        if (account == null) {
            return null;
        }
        return convertToDetailDTO(account);
    }

    /**
     * 根据手机号查询账户
     */
    public AccountDetailDTO getAccountByPhone(String phone) {
        Account account = accountRepository.findByPhone(phone);
        if (account == null) {
            return null;
        }
        return convertToDetailDTO(account);
    }

    /**
     * 根据账号查询账户
     */
    public AccountDetailDTO getAccountByAccount(String account) {
        Account accountEntity = accountRepository.findByAccount(account);
        if (accountEntity == null) {
            return null;
        }
        return convertToDetailDTO(accountEntity);
    }

    /**
     * 根据国家编号和手机号查询账户
     */
    public AccountDetailDTO getAccountByCountryCodeAndPhone(String countryCode, String phone) {
        if (!StringUtils.hasText(countryCode) || !StringUtils.hasText(phone)) {
            return null;
        }
        if (!StringUtils.startsWithIgnoreCase(countryCode, "+")){
            countryCode = "+" + countryCode.trim();
        }
        Account account = accountRepository.findByCountryCodeAndPhone(countryCode, phone);
        if (account == null) {
            return null;
        }
        return convertToDetailDTO(account);
    }

    /**
     * 查询账户列表
     */
    public PageResult<AccountSummaryDTO> getAccountList(EntityQuery query) {
        validateQuery(query);
        
        try {
            // 构建查询条件
            LambdaQueryWrapper<AccountEntity> wrapper = buildBaseWrapper(query);
            
            // 如果指定了实体ID作为用户ID，则按用户ID查询
            if (StringUtils.hasText(query.getId())) {
                wrapper.eq(AccountEntity::getUserId, query.getId());
            }
            
            // 执行分页查询
            Page<AccountEntity> page = new Page<>(query.getPage(), query.getLimit());
            Page<AccountEntity> accountPage = accountMapper.selectPage(page, wrapper);

            if (accountPage.getRecords().isEmpty()) {
                return new PageResult<>(Collections.emptyList(), 0L);
            }

            // 转换为DTO列表
            List<AccountSummaryDTO> dtos = accountPage.getRecords().stream()
                .map(entity -> {
                    Account account = accountRepository.findById(entity.getId());
                    return convertToSummaryDTO(account);
                })
                .collect(Collectors.toList());

            return new PageResult<>(dtos, accountPage.getTotal());
        } catch (Exception e) {
            log.error("查询账户列表失败", e);
            throw new BusinessException(PaymentError.SYSTEM_ERROR);
        }
    }

    /**
     * 验证查询参数的有效性
     *
     * @param query 查询参数对象
     * @throws BusinessException 当参数无效时抛出
     */
    private void validateQuery(EntityQuery query) {
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
     * 构建基础查询条件
     *
     * @param query 查询参数
     * @return 查询条件包装器
     */
    private LambdaQueryWrapper<AccountEntity> buildBaseWrapper(EntityQuery query) {
        LambdaQueryWrapper<AccountEntity> wrapper = Wrappers.lambdaQuery();
        
        // 添加时间范围条件
        if (query.getStartTime() != null && query.getEndTime() != null) {
            wrapper.between(AccountEntity::getCreateTime, query.getStartTime(), query.getEndTime());
        } else if (query.getStartTime() != null) {
            wrapper.ge(AccountEntity::getCreateTime, query.getStartTime());
        } else if (query.getEndTime() != null) {
            wrapper.le(AccountEntity::getCreateTime, query.getEndTime());
        }
        
        // 添加状态条件
        if (StringUtils.hasText(query.getStatus())) {
            wrapper.eq(AccountEntity::getStatus, query.getStatus());
        }
        
        // 添加手机号条件
        if (StringUtils.hasText(query.getPhone())) {
            wrapper.eq(AccountEntity::getPhone, query.getPhone());
        }
        
        // 添加国家代码条件
        if (StringUtils.hasText(query.getCountryCode())) {
            wrapper.eq(AccountEntity::getCountryCode, query.getCountryCode());
        }
        
        // 添加账号条件
        if (StringUtils.hasText(query.getAccount())) {
            wrapper.eq(AccountEntity::getAccount, query.getAccount());
        }
        
        // 添加关键字搜索
        if (StringUtils.hasText(query.getKeyword())) {
            wrapper.and(w -> w
                .like(AccountEntity::getPhone, query.getKeyword())
                .or()
                .like(AccountEntity::getAccount, query.getKeyword())
                .or()
                .like(AccountEntity::getUserId, query.getKeyword())
            );
        }
        
        // 默认按创建时间降序
        wrapper.orderByDesc(AccountEntity::getCreateTime);
        
        return wrapper;
    }

    /**
     * 转换为账户详情DTO
     */
    private AccountDetailDTO convertToDetailDTO(Account account) {
        AccountDetailDTO dto = new AccountDetailDTO();
        dto.setId(account.getId());
        dto.setTenantId(account.getTenantId());
        dto.setUserId(account.getUserId());
        dto.setCountryCode(account.getCountryCode());
        dto.setPhone(account.getPhone());
        dto.setAccount(account.getAccount());
        dto.setStatus(account.getStatus());
        dto.setCreator(account.getCreator());
        dto.setCreateTime(account.getCreateTime());
        dto.setUpdater(account.getUpdater());
        dto.setUpdateTime(account.getUpdateTime());
        dto.setVersion(account.getVersion());
        
        // 查询账户的钱包信息
        List<Wallet> wallets = walletRepository.findByOwnerId(account.getId());
        List<WalletDTO> walletDTOs = wallets.stream()
                .map(WalletDTO::from)
                .collect(Collectors.toList());
        dto.setWallets(walletDTOs);
        
        return dto;
    }
    
    /**
     * 转换为账户摘要DTO
     */
    private AccountSummaryDTO convertToSummaryDTO(Account account) {
        AccountSummaryDTO dto = new AccountSummaryDTO();
        dto.setId(account.getId());
        dto.setUserId(account.getUserId());
        dto.setCountryCode(account.getCountryCode());
        dto.setPhone(account.getPhone());
        dto.setAccount(account.getAccount());
        dto.setStatus(account.getStatus());
        dto.setCreateTime(account.getCreateTime());
        return dto;
    }

    /**
     * 查询账户统计信息
     */
    public AccountStatistics getAccountStatistics(AccountStatisticsQuery query) {
        try {
            // 验证查询参数
            validateStatisticsQuery(query);
            
            // 计算时间范围
            Long startTime = null;
            Long endTime = null;
            
            if ("DAY".equals(query.getQueryType())) {
                // 按天查询，使用开始时间和结束时间
                // 如果没有设置时间，则使用当天的开始和结束时间
                if (query.getStartTime() == null || query.getEndTime() == null) {
                    startTime = getTodayStartTime();
                    endTime = getTodayEndTime();
                } else {
                    startTime = query.getStartTime();
                    endTime = query.getEndTime();
                }
            } else if ("MONTH".equals(query.getQueryType())) {
                // 按月查询，解析查询值
                // 如果没有提供queryValue，则使用当前月份
                if (StringUtils.hasText(query.getQueryValue())) {
                    String[] parts = query.getQueryValue().split("-");
                    if (parts.length == 2) {
                        int year = Integer.parseInt(parts[0]);
                        int month = Integer.parseInt(parts[1]);
                        startTime = getMonthStartTime(year, month);
                        endTime = getMonthEndTime(year, month);
                    }
                } else {
                    // 使用当前月份
                    java.time.LocalDate now = java.time.LocalDate.now();
                    startTime = getMonthStartTime(now.getYear(), now.getMonthValue());
                    endTime = getMonthEndTime(now.getYear(), now.getMonthValue());
                }
            } else if ("YEAR".equals(query.getQueryType())) {
                // 按年查询，解析查询值
                // 如果没有提供queryValue，则使用当前年份
                if (StringUtils.hasText(query.getQueryValue())) {
                    int year = Integer.parseInt(query.getQueryValue());
                    startTime = getYearStartTime(year);
                    endTime = getYearEndTime(year);
                } else {
                    // 使用当前年份
                    int currentYear = java.time.LocalDate.now().getYear();
                    startTime = getYearStartTime(currentYear);
                    endTime = getYearEndTime(currentYear);
                }
            }
            
            // 获取用户账户
            Account account = accountRepository.findByUserId(ApplicationContext.getUserId());
            if (account == null) {
                // 如果账户不存在，返回空统计数据
                return convertToStatisticsDTO(null, query.getPaymentRegion(), startTime, endTime);
            }
            
            // 查询统计结果
            AccountStatisticsResult result = accountFlowMapper.getAccountStatistics(
                account.getId(), startTime, endTime, query.getPaymentRegion());
            
            // 查询按交易类型分组的统计结果
            List<TransactionTypeStatisticsResult> typeResults = accountFlowMapper.getTransactionTypeStatistics(
                account.getId(), startTime, endTime, query.getPaymentRegion());
            
            // 转换为DTO
            return convertToStatisticsDTO(result, typeResults, query.getPaymentRegion(), startTime, endTime);
            
        } catch (Exception e) {
            log.error("查询账户统计信息失败", e);
            throw new BusinessException(PaymentError.SYSTEM_ERROR);
        }
    }
    
    /**
     * 验证统计查询参数
     */
    private void validateStatisticsQuery(AccountStatisticsQuery query) {
        if (query == null) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        if (!StringUtils.hasText(query.getQueryType())) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        if (!"DAY".equals(query.getQueryType()) && !"MONTH".equals(query.getQueryType()) && !"YEAR".equals(query.getQueryType())) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
    }
    
    /**
     * 获取月份开始时间戳
     */
    private Long getMonthStartTime(int year, int month) {
        java.time.LocalDateTime start = java.time.LocalDateTime.of(year, month, 1, 0, 0, 0);
        return start.atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
    
    /**
     * 获取月份结束时间戳
     */
    private Long getMonthEndTime(int year, int month) {
        java.time.LocalDateTime end = java.time.LocalDateTime.of(year, month, 1, 0, 0, 0)
            .plusMonths(1).minusSeconds(1);
        return end.atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
    
    /**
     * 获取年份开始时间戳
     */
    private Long getYearStartTime(int year) {
        java.time.LocalDateTime start = java.time.LocalDateTime.of(year, 1, 1, 0, 0, 0);
        return start.atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
    
    /**
     * 获取年份结束时间戳
     */
    private Long getYearEndTime(int year) {
        java.time.LocalDateTime end = java.time.LocalDateTime.of(year, 12, 31, 23, 59, 59);
        return end.atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
    
    /**
     * 获取当天开始时间戳
     */
    private Long getTodayStartTime() {
        java.time.LocalDateTime start = java.time.LocalDate.now().atStartOfDay();
        return start.atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
    
    /**
     * 获取当天结束时间戳
     */
    private Long getTodayEndTime() {
        java.time.LocalDateTime end = java.time.LocalDate.now().atTime(23, 59, 59);
        return end.atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
    
    /**
     * 转换为统计DTO
     */
    private AccountStatistics convertToStatisticsDTO(AccountStatisticsResult result, 
                                                      List<TransactionTypeStatisticsResult> typeResults,
                                                      String paymentRegion, 
                                                      Long startTime, 
                                                      Long endTime) {
        AccountStatistics dto = new AccountStatistics();
        
        // 设置时间范围
        dto.setStartTime(startTime);
        dto.setEndTime(endTime);
        dto.setPaymentRegion(paymentRegion);
        
        // 格式化时间字符串
        if (startTime != null) {
            dto.setStartTimeStr(formatTime(startTime));
        }
        if (endTime != null) {
            dto.setEndTimeStr(formatTime(endTime));
        }
        
        String currency = paymentRegion != null ? paymentRegion : "CNY";
        
        if (result == null) {
            // 如果没有数据，返回默认值
            dto.setTotal(0);
            dto.setIncome(Money.zero(currency, 2));
            dto.setOutcome(Money.zero(currency, 2));
            dto.setShoppingPayment(Money.zero(currency, 2));
            dto.setActivityRebateIncome(Money.zero(currency, 2));
            dto.setUserTransferIncome(Money.zero(currency, 2));
            dto.setUserTransferExpense(Money.zero(currency, 2));
            dto.setMerchantDiscountIncome(Money.zero(currency, 2));
            dto.setMerchantPriceReductionIncome(Money.zero(currency, 2));
            dto.setFeeExpense(Money.zero(currency, 2));
            dto.setTransactionTypeStatistics(new ArrayList<>());
            return dto;
        }
        
        // 设置基础统计数据
        dto.setTotal(result.getTotal() != null ? result.getTotal() : 0);
        dto.setIncome(Money.create(result.getIncome() != null ? result.getIncome() : 0L, currency, 2));
        dto.setOutcome(Money.create(result.getOutcome() != null ? result.getOutcome() : 0L, currency, 2));
        
        // 设置详细分类统计数据
        dto.setShoppingPayment(Money.create(result.getShoppingPayment() != null ? result.getShoppingPayment() : 0L, currency, 2));
        dto.setActivityRebateIncome(Money.create(result.getActivityRebateIncome() != null ? result.getActivityRebateIncome() : 0L, currency, 2));
        dto.setUserTransferIncome(Money.create(result.getUserTransferIncome() != null ? result.getUserTransferIncome() : 0L, currency, 2));
        dto.setUserTransferExpense(Money.create(result.getUserTransferExpense() != null ? result.getUserTransferExpense() : 0L, currency, 2));
        dto.setMerchantDiscountIncome(Money.create(result.getMerchantDiscountIncome() != null ? result.getMerchantDiscountIncome() : 0L, currency, 2));
        dto.setMerchantPriceReductionIncome(Money.create(result.getMerchantPriceReductionIncome() != null ? result.getMerchantPriceReductionIncome() : 0L, currency, 2));
        dto.setFeeExpense(Money.create(result.getFeeExpense() != null ? result.getFeeExpense() : 0L, currency, 2));
        
        // 转换按交易类型分组的统计
        if (typeResults != null && !typeResults.isEmpty()) {
            List<TransactionTypeStatistics> typeStatsList = typeResults.stream()
                .map(typeResult -> convertToTransactionTypeStatistics(typeResult, currency))
                .collect(Collectors.toList());
            dto.setTransactionTypeStatistics(typeStatsList);
        } else {
            dto.setTransactionTypeStatistics(new ArrayList<>());
        }
        
        return dto;
    }
    
    /**
     * 转换为统计DTO（重载方法，用于无数据时）
     */
    private AccountStatistics convertToStatisticsDTO(AccountStatisticsResult result, String paymentRegion, Long startTime, Long endTime) {
        return convertToStatisticsDTO(result, null, paymentRegion, startTime, endTime);
    }
    
    /**
     * 转换交易类型统计结果
     */
    private TransactionTypeStatistics convertToTransactionTypeStatistics(TransactionTypeStatisticsResult result, String currency) {
        TransactionTypeStatistics dto = new TransactionTypeStatistics();
        
        // 设置交易类型信息
        dto.setTransactionType(result.getTransactionType());
        TransactionType transactionType = TransactionType.fromCode(result.getTransactionType());
        dto.setTransactionTypeDesc(transactionType != null ? transactionType.getDescription() : result.getTransactionType());
        
        // 设置流水类型信息
        dto.setFlowType(result.getFlowType());
        FlowType flowType = FlowType.fromCode(result.getFlowType());
        dto.setFlowTypeDesc(flowType != null ? flowType.getDesc() : result.getFlowType());
        
        // 设置金额和数量
        dto.setAmount(Money.create(result.getAmount() != null ? result.getAmount() : 0L, currency, 2));
        dto.setTransactionCount(result.getTransactionCount() != null ? result.getTransactionCount() : 0);
        dto.setFeeAmount(Money.create(result.getFeeAmount() != null ? result.getFeeAmount() : 0L, currency, 2));
        
        // 计算平均费率（百分比）
        if (result.getTotalFeeRate() != null && result.getTransactionCount() != null && result.getTransactionCount() > 0) {
            // fee_rate 是以万分比为单位，如100表示1%
            BigDecimal avgFeeRate = BigDecimal.valueOf(result.getTotalFeeRate())
                .divide(BigDecimal.valueOf(result.getTransactionCount()), 2, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            dto.setAvgFeeRate(avgFeeRate);
        } else {
            dto.setAvgFeeRate(BigDecimal.ZERO);
        }
        
        return dto;
    }
    
    /**
     * 格式化时间为 dd/MM/yyyy 格式
     */
    private String formatTime(Long timestamp) {
        if (timestamp == null) {
            return null;
        }
        java.time.Instant instant = java.time.Instant.ofEpochMilli(timestamp);
        java.time.LocalDate date = instant.atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formatter);
    }

} 