package com.ares.cloud.base.api;


import com.ares.cloud.base.dto.SysPaymentChannelDto;
import com.ares.cloud.base.query.PaymentChannelQuery;
import com.ares.cloud.base.query.SysPaymentChannelQuery;
import com.ares.cloud.base.service.SysPaymentChannelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.ares.cloud.common.dto.PageResult;
import org.ares.cloud.common.model.Result;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 支付渠道 控制器
* @version 1.0.0
* @date 2025-05-13
*/
@RestController
@RequestMapping("/api/base/payment_channel")
@Tag(name="支付渠道")
@AllArgsConstructor
public class SysPaymentChannelController {
    @Resource
    private SysPaymentChannelService sysPaymentChannelService;


    @GetMapping("page")
    @Operation(summary = "分页")
   // @PreAuthorize("hasAuthority('base:SysPaymentType:page')")
    public Result<PageResult<SysPaymentChannelDto>> page(@ParameterObject @Valid SysPaymentChannelQuery query){
        PageResult<SysPaymentChannelDto> page = sysPaymentChannelService.loadList(query);

        return Result.success(page);
    }


    @Operation(summary = "获取所有")
    @GetMapping("/all")
    // @PreAuthorize("hasAuthority('base:SysPaymentType:all')")
    public  Result<List<SysPaymentChannelDto>> all(@ParameterObject PaymentChannelQuery query){
        List<SysPaymentChannelDto> all = sysPaymentChannelService.loadAll(query);
        return Result.success(all);
    }

    @Parameter(
        name = "id",
        description = "主键",
        required = true,
        in = ParameterIn.PATH,
        schema = @Schema(type = "string")
    )
    @GetMapping("{id}")
    @Operation(summary = "详情")
    //@PreAuthorize("hasAuthority('base:SysPaymentType:info')")
    public Result<SysPaymentChannelDto> get(@PathVariable("id") String id){
         SysPaymentChannelDto dto= sysPaymentChannelService.loadById(id);
         return Result.success(dto);
    }


    @PostMapping
    @Operation(summary = "保存")
   // @PreAuthorize("hasAuthority('base:SysPaymentType:save')")
    public Result<String> save(@RequestBody @Valid SysPaymentChannelDto dto){
        sysPaymentChannelService.create(dto);
        return Result.success();
    }

    @PutMapping
    @Operation(summary = "修改")
    //@PreAuthorize("hasAuthority('base:SysPaymentType:update')")
    public Result<String> update(@RequestBody @Valid SysPaymentChannelDto dto){
        sysPaymentChannelService.update(dto);
        return Result.success();
    }

    @Parameter(
    name = "id",
    description = "主键",
    required = true,
    in = ParameterIn.PATH,
    schema = @Schema(type = "string")
    )
    @DeleteMapping("{id}")
    @Operation(summary = "根据id删除")
    //@PreAuthorize("hasAuthority('base:SysPaymentType:del_by_id')")
    public Result<String> del(@PathVariable("id") String id){
        sysPaymentChannelService.deleteById(id);
        return Result.success();
    }


}