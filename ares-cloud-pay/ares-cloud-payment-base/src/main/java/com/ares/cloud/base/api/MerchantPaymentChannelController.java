package com.ares.cloud.base.api;


import com.ares.cloud.base.command.MerchantPaymentChannelCommand;
import com.ares.cloud.base.dto.MerchantPaymentChannelDto;
import com.ares.cloud.base.query.MerchantPaymentChannelQuery;
import com.ares.cloud.base.service.MerchantPaymentChannelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.common.model.Result;
import org.ares.cloud.common.utils.StringUtils;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 商户支付渠道 控制器
* @version 1.0.0
* @date 2025-05-13
*/
@RestController
@RequestMapping("/api/message/merchant_payment_channel")
@Tag(name="商户支付渠道")
@AllArgsConstructor
public class MerchantPaymentChannelController {
    @Resource
    private MerchantPaymentChannelService merchantPaymentChannelService;


    @Operation(summary = "获取商户支付渠道")
    @GetMapping("/all")
    public  Result<List<MerchantPaymentChannelDto>> all(@ParameterObject MerchantPaymentChannelQuery query){
        String tenantId = ApplicationContext.getTenantId();
        if (StringUtils.isNotBlank(tenantId)){
            query.setMerchantId(tenantId);
        }
        List<MerchantPaymentChannelDto> all = merchantPaymentChannelService.loadAll(query);
        return Result.success(all);
    }



    @PostMapping
    @Operation(summary = "保存")
    public Result<String> save(@RequestBody @Valid MerchantPaymentChannelCommand dto){
        String tenantId = ApplicationContext.getTenantId();
        if (StringUtils.isNotBlank(tenantId)){
            dto.setMerchantId(tenantId);
        }
        merchantPaymentChannelService.saveChannel(dto);
        return Result.success();
    }
}