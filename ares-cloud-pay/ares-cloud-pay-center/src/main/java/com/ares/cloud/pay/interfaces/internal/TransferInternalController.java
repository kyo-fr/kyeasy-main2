package com.ares.cloud.pay.interfaces.internal;

import com.ares.cloud.pay.domain.enums.TransactionType;
import org.ares.cloud.api.payment.command.GenericTransferCommand;
import org.ares.cloud.api.payment.command.MerchantDiscountCommand;
import org.ares.cloud.api.payment.command.MerchantPriceReductionCommand;
import org.ares.cloud.api.payment.dto.TransferResultDTO;
import com.ares.cloud.pay.application.handlers.TransferCommandHandler;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.ares.cloud.exception.RpcCallException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2025/9/11 23:22
 */
@Slf4j
@RestController
@RequestMapping("/internal/v1/transfer")
public class TransferInternalController {
    @Resource
    private TransferCommandHandler transferCommandHandler;
    /**
     * 通用转账
     * @param command 转账命令
     * @return 转账结果
     */
    @Hidden
    @PostMapping("/generic")
    public TransferResultDTO genericTransfer(
            @Parameter(description = "通用转账请求参数", required = true)
            @Valid @RequestBody GenericTransferCommand command) {
        try {
            return transferCommandHandler.handleGenericTransfer(command);
        } catch (Exception e) {
            log.error("内部转账失败", e);
            throw new RpcCallException(e);
        }
    }

    /**
     * 商户优惠减免
     * 商户结算时给用户的百分比优惠减免，手续费5%由用户承担
     * 
     * @param command 优惠减免命令
     * @return 转账结果
     */
    @Hidden
    @PostMapping("/merchant-discount")
    public TransferResultDTO merchantDiscount(
            @Parameter(description = "商户优惠减免请求参数", required = true)
            @Valid @RequestBody MerchantDiscountCommand command) {
        try {
            return transferCommandHandler.handleMerchantDiscount(command);
        } catch (Exception e) {
            log.error("商户优惠减免失败", e);
            throw new RpcCallException(e);
        }
    }

    /**
     * 商户减价
     * 商户结算时给用户的具体金额减价，手续费5%由用户承担
     * 
     * @param command 减价命令
     * @return 转账结果
     */
    @Hidden
    @PostMapping("/merchant-price-reduction")
    public TransferResultDTO merchantPriceReduction(
            @Parameter(description = "商户减价请求参数", required = true)
            @Valid @RequestBody MerchantPriceReductionCommand command) {
        try {
            return transferCommandHandler.handleMerchantPriceReduction(command);
        } catch (Exception e) {
            log.error("商户减价失败", e);
            throw new RpcCallException(e);
        }
    }

}
