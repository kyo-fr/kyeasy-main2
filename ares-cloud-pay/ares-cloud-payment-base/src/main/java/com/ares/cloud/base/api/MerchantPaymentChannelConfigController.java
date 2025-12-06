package com.ares.cloud.base.api;


import com.ares.cloud.base.command.MerchantBraintreePaymentConfigCommand;
import com.ares.cloud.base.dto.MerchantPaymentChannelConfigDto;
import com.ares.cloud.base.service.MerchantPaymentChannelConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.common.model.Result;
import org.ares.cloud.common.utils.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
* @author hugo tangxkwork@163.com
* @description 商户支付渠道配置 控制器
* @version 1.0.0
* @date 2025-05-13
*/
@RestController
@RequestMapping("/api/message/merchant_payment_channel_config")
@Tag(name="商户支付渠道配置")
public class MerchantPaymentChannelConfigController {
    @Resource
    private MerchantPaymentChannelConfigService merchantPaymentChannelConfigService;



    @Parameter(
        name = "id",
        description = "主键",
        required = true,
        in = ParameterIn.PATH,
        schema = @Schema(type = "string")
    )
    @GetMapping("{id}")
    @Operation(summary = "详情")
    //@PreAuthorize("hasAuthority('base:merchant_payment_channel_config:info')")
    public Result<MerchantPaymentChannelConfigDto> get(@PathVariable("id") String id){
         MerchantPaymentChannelConfigDto dto= merchantPaymentChannelConfigService.loadById(id);
         return Result.success(dto);
    }


    @PostMapping("/braintree")
    @Operation(summary = "保存商户braintree配置")
   // @PreAuthorize("hasAuthority('base:merchant_payment_channel_config:save')")
    public Result<String> saveBraintreePaymentConfig(@RequestBody @Valid MerchantBraintreePaymentConfigCommand  command){
        String tenantId = ApplicationContext.getTenantId();
        if (StringUtils.isNotBlank(tenantId)){
            command.setMerchantId(tenantId);
        }
        merchantPaymentChannelConfigService.saveBraintreePaymentConfig(command);
        return Result.success();
    }

    @GetMapping("/paymentMerchant")
    @Operation(summary = "根据商户类型获取配置")
    public Result<MerchantPaymentChannelConfigDto> getBraintreePaymentConfig(@RequestParam String paymentMerchant){
        String merchantId = ApplicationContext.getTenantId();
        MerchantPaymentChannelConfigDto dto= merchantPaymentChannelConfigService.selectByMerchantIdAndPaymentMerchant(merchantId,paymentMerchant);
        return Result.success(dto);
    }
}