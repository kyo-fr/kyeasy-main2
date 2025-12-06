package com.ares.cloud.pay.application.dto;

import com.ares.cloud.pay.domain.model.Wallet;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 钱包DTO
 */
@Data
@Schema(description = "钱包信息")
public class WalletDTO {
    /**
     * 支付区域（EUR/USD/CNY/CHF/GBP）
     */
    @Schema(description = "支付区域", example = "EUR", allowableValues = {"EUR", "USD", "CNY", "CHF", "GBP"})
    private String paymentRegion;

    /**
     * 余额
     */
    @Schema(description = "钱包余额")
    private BigDecimal balance;

    /**
     * 钱包状态（ACTIVE:激活, FROZEN:冻结, CLOSED:关闭）
     */
    @Schema(description = "钱包状态", example = "ACTIVE", allowableValues = {"ACTIVE", "FROZEN", "CLOSED"})
    private String status;
    /**
     * 转换为DTO
     *
     * @param wallet 钱包
     * @return DTO
     */
    public static WalletDTO from(Wallet wallet) {
        WalletDTO walletDTO = new WalletDTO();
        walletDTO.setPaymentRegion(wallet.getPaymentRegion());
        walletDTO.setBalance(wallet.getBalance().toDecimal());
        walletDTO.setStatus(wallet.getStatus());
        return walletDTO;
    }
}