package com.ares.cloud.pay.interfaces.rest;

import com.ares.cloud.pay.application.service.FeeConfigApplicationService;
import com.ares.cloud.pay.application.dto.FeeConfigDTO;
import com.ares.cloud.pay.application.dto.CreateFeeConfigCommand;
import com.ares.cloud.pay.application.dto.UpdateFeeConfigCommand;
import org.ares.cloud.common.model.Result;
import org.ares.cloud.common.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 手续费配置控制器
 */
@RestController
@RequestMapping("/api/v1/fee-configs")
public class FeeConfigController {
    
    @Autowired
    private FeeConfigApplicationService feeConfigApplicationService;
    
    /**
     * 创建手续费配置
     */
    @PostMapping
    public Result<String> createFeeConfig(@RequestBody CreateFeeConfigCommand command) {
        try {
            FeeConfigDTO feeConfig = feeConfigApplicationService.createFeeConfig(command);
            return Result.success(JsonUtils.toJsonString(feeConfig));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 更新手续费配置
     */
    @PutMapping("/{id}")
    public Result<String> updateFeeConfig(@PathVariable String id, @RequestBody UpdateFeeConfigCommand command) {
        try {
            command.setId(id);
            feeConfigApplicationService.updateFeeConfig(command);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 删除手续费配置
     */
    @DeleteMapping("/{id}")
    public Result<String> deleteFeeConfig(@PathVariable String id) {
        try {
            feeConfigApplicationService.deleteFeeConfig(id);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 根据ID查询手续费配置
     */
    @GetMapping("/{id}")
    public Result<String> getFeeConfigById(@PathVariable String id) {
        try {
            FeeConfigDTO feeConfig = feeConfigApplicationService.getFeeConfigById(id);
            if (feeConfig == null) {
                return Result.error("手续费配置不存在");
            }
            return Result.success(JsonUtils.toJsonString(feeConfig));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 根据交易类型和支付区域查询手续费配置
     */
    @GetMapping("/query")
    public Result<String> getFeeConfig(@RequestParam String transactionType, @RequestParam String paymentRegion) {
        try {
            FeeConfigDTO feeConfig = feeConfigApplicationService.getFeeConfig(transactionType, paymentRegion);
            return Result.success(JsonUtils.toJsonString(feeConfig));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 查询所有启用的手续费配置
     */
    @GetMapping("/enabled")
    public Result<String> getAllEnabledFeeConfigs() {
        try {
            List<FeeConfigDTO> feeConfigs = feeConfigApplicationService.getAllEnabledFeeConfigs();
            return Result.success(JsonUtils.toJsonString(feeConfigs));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 根据交易类型查询手续费配置
     */
    @GetMapping("/transaction-type/{transactionType}")
    public Result<String> getFeeConfigsByTransactionType(@PathVariable String transactionType) {
        try {
            List<FeeConfigDTO> feeConfigs = feeConfigApplicationService.getFeeConfigsByTransactionType(transactionType);
            return Result.success(JsonUtils.toJsonString(feeConfigs));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    /**
     * 根据支付区域查询手续费配置
     */
    @GetMapping("/payment-region/{paymentRegion}")
    public Result<String> getFeeConfigsByPaymentRegion(@PathVariable String paymentRegion) {
        try {
            List<FeeConfigDTO> feeConfigs = feeConfigApplicationService.getFeeConfigsByPaymentRegion(paymentRegion);
            return Result.success(JsonUtils.toJsonString(feeConfigs));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
} 