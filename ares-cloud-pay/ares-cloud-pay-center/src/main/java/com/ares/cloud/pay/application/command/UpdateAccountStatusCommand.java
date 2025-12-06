package com.ares.cloud.pay.application.command;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 更新账户状态命令
 * 封装更新账户状态所需的所有参数，用于在系统中更新账户的状态
 * <p>
 * 该命令用于管理员或系统对账户状态进行管理，如激活、冻结、关闭等操作
 * <p>
 * 使用场景：
 * 1. 账户激活
 * 2. 账户冻结
 * 3. 账户关闭
 * 4. 风险控制
 */
@Data
@Schema(description = "更新账户状态命令对象", title = "更新账户状态命令")
public class UpdateAccountStatusCommand {
    
    /**
     * 账户ID
     * <p>
     * 要更新状态的账户ID，用于定位具体的账户记录。
     * 系统会验证该账户是否存在，并更新其状态。
     */
    @Schema(description = "账户ID", example = "account_123456", requiredMode = Schema.RequiredMode.REQUIRED)
    private String accountId;
    
    /**
     * 新状态
     * <p>
     * 账户的新状态，用于更新账户的当前状态。
     * 支持的状态包括：ACTIVE（激活）、FROZEN（冻结）、CLOSED（关闭）。
     */
    @Schema(description = "新状态", example = "ACTIVE", allowableValues = {"ACTIVE", "FROZEN", "CLOSED"}, requiredMode = Schema.RequiredMode.REQUIRED)
    private String newStatus;
} 