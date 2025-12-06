package com.ares.cloud.pay.interfaces.rest;

import com.ares.cloud.pay.application.command.*;
import com.ares.cloud.pay.application.dto.AccountDetailDTO;
import com.ares.cloud.pay.application.dto.AccountSummaryDTO;
import com.ares.cloud.pay.application.dto.AccountFlowDTO;
import com.ares.cloud.pay.application.dto.AccountStatistics;
import com.ares.cloud.pay.application.handlers.AccountCommandHandler;
import com.ares.cloud.pay.application.handlers.AccountQueryHandler;
import com.ares.cloud.pay.application.handlers.AccountFlowQueryHandler;
import com.ares.cloud.pay.application.queries.EntityQuery;
import com.ares.cloud.pay.application.queries.AccountFlowQuery;
import com.ares.cloud.pay.application.queries.AccountStatisticsQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.common.dto.PageResult;
import org.ares.cloud.common.model.Result;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

/**
 * 账户管理控制器
 */
@RestController
@RequestMapping("/api/accounts")
@Tag(name = "账户管理", description = "账户相关接口")
public class AccountController {

    @Resource
    private AccountCommandHandler accountCommandHandler;

    @Resource
    private AccountQueryHandler accountQueryHandler;
    
    @Resource
    private AccountFlowQueryHandler accountFlowQueryHandler;

    // ==================== 命令操作 ====================

    @PostMapping("/open")
    @Operation(summary = "开通账户", description = "通过用户ID和支付密码开通账户，需要提供确认密码。密码要求：至少6位字符，包含数字和字母，不能包含特殊字符")
    public Result<String> openAccount(@RequestBody OpenAccountCommand command) {
        String userId = ApplicationContext.getUserId();
        String accountId = accountCommandHandler.handleOpenAccount(userId, command);
        return Result.success(accountId);
    }

    @PutMapping("/{accountId}/status")
    @Operation(summary = "更新账户状态")
    public Result<String> updateAccountStatus(@PathVariable String accountId, @RequestBody UpdateAccountStatusCommand command) {
        command.setAccountId(accountId);
        accountCommandHandler.handleUpdateAccountStatus(command);
        return Result.success("状态更新成功");
    }

    @PutMapping("password")
    @Operation(summary = "更新账户密码")
    public Result<String> updateAccountPassword(@RequestBody UpdatePasswordCommand command) {
        command.setEntityType(UpdatePasswordCommand.EntityType.ACCOUNT);
        command.setEntityId(ApplicationContext.getTenantId());
        accountCommandHandler.handleUpdatePassword(command);
        return Result.success("密码更新成功");
    }

    // ==================== 查询操作 ====================

    @GetMapping("/{accountId}")
    @Operation(summary = "根据账户ID查询账户详情", description = "返回账户的详细信息，包括五个区域的钱包余额信息")
    public Result<AccountDetailDTO> getAccountById(@PathVariable String accountId) {
        AccountDetailDTO account = accountQueryHandler.getAccountById(accountId);
        return Result.success(account);
    }

    @GetMapping("/info")
    @Operation(summary = "获取账户详情", description = "返回当前用户的账户详细信息，包括五个区域的钱包余额信息")
    public Result<AccountDetailDTO> getAccountByUserId() {
        String userId = ApplicationContext.getUserId();
        AccountDetailDTO account = accountQueryHandler.getAccountByUserId(userId);
        return Result.success(account);
    }

    @GetMapping("/by-phone")
    @Operation(summary = "根据国家编号和手机号查询账户详情", description = "返回账户的详细信息，包括五个区域的钱包余额信息")
    public Result<AccountDetailDTO> getAccountByCountryCodeAndPhone(
            @RequestParam String countryCode, 
            @RequestParam String phone) {
        AccountDetailDTO account = accountQueryHandler.getAccountByCountryCodeAndPhone(countryCode, phone);
        return Result.success(account);
    }

    @GetMapping
    @Operation(summary = "查询账户列表", description = "返回账户的摘要信息列表，不包含钱包余额信息")
    public Result<PageResult<AccountSummaryDTO>> getAccountList(@ParameterObject @Valid EntityQuery query) {
        PageResult<AccountSummaryDTO> result = accountQueryHandler.getAccountList(query);
        return Result.success(result);
    }
    
    // ==================== 交易流水查询 ====================
    
    @GetMapping("/flows")
    @Operation(summary = "查询用户交易流水", description = "查询当前用户的交易流水记录，包含交易对方的国家编号和手机号信息")
    public Result<PageResult<AccountFlowDTO>> getUserAccountFlows(@ParameterObject @Valid AccountFlowQuery query) {
        PageResult<AccountFlowDTO> result = accountFlowQueryHandler.getUserAccountFlows(query);
        return Result.success(result);
    }
    
    @GetMapping("/{accountId}/flows")
    @Operation(summary = "根据账户ID查询交易流水", description = "查询指定账户的交易流水记录，包含交易对方的国家编号和手机号信息")
    public Result<PageResult<AccountFlowDTO>> getAccountFlowsByAccountId(@ParameterObject @Valid @PathVariable String accountId, AccountFlowQuery query) {
        PageResult<AccountFlowDTO> result = accountFlowQueryHandler.getAccountFlowsByAccountId(accountId, query);
        return Result.success(result);
    }
    
    // ==================== 账户统计查询 ====================
    
    @GetMapping("/statistics")
    @Operation(summary = "查询账户统计信息", description = "查询当前用户的账户统计信息，包括交易总数、收入支出、转账、购物、商户优惠、手续费等统计")
    public Result<AccountStatistics> getAccountStatistics(@ParameterObject @Valid AccountStatisticsQuery query) {
        AccountStatistics result = accountQueryHandler.getAccountStatistics(query);
        return Result.success(result);
    }
} 