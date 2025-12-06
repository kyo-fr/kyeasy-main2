package org.ares.cloud.merchantInfo.api;


import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.ares.cloud.common.dto.PageResult;
import org.ares.cloud.common.model.Result;
import org.ares.cloud.exception.RpcCallException;
import org.springdoc.core.annotations.ParameterObject;
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;

import org.ares.cloud.merchantInfo.convert.MerchantSubscribeConvert;
import org.ares.cloud.merchantInfo.entity.MerchantSubscribeEntity;
import org.ares.cloud.merchantInfo.service.MerchantSubscribeService;
import org.ares.cloud.merchantInfo.query.MerchantSubscribeQuery;
import org.ares.cloud.merchantInfo.dto.MerchantSubscribeDto;

/**
* @author hugo tangxkwork@163.com
* @description 商户订阅 控制器
* @version 1.0.0
* @date 2024-10-11
*/
@RestController
@RequestMapping("/api/merchant/v1/subscribe")
@Tag(name="商户订阅")
@AllArgsConstructor
@Hidden
public class MerchantSubscribeController {
    @Resource
    private MerchantSubscribeService merchantSubscribeService;


    @GetMapping("page")
    @Operation(summary = "分页")
   // @PreAuthorize("hasAuthority('merchantInfo:MerchantSubscribe:page')")
    public Result<PageResult<MerchantSubscribeDto>> page(@ParameterObject @Valid MerchantSubscribeQuery query){
        PageResult<MerchantSubscribeDto> page = merchantSubscribeService.loadList(query);

        return Result.success(page);
    }


    @Operation(summary = "获取所有")
    @GetMapping("/all")
    // @PreAuthorize("hasAuthority('merchantInfo:MerchantSubscribe:all')")
    public  Result<List<MerchantSubscribeDto>> all(){
        List<MerchantSubscribeDto> all = merchantSubscribeService.loadAll();
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
    //@PreAuthorize("hasAuthority('merchantInfo:MerchantSubscribe:info')")
    public Result<MerchantSubscribeDto> get(@PathVariable("id") String id){
         MerchantSubscribeDto dto= merchantSubscribeService.loadById(id);
         return Result.success(dto);
    }


    @PostMapping
    @Operation(summary = "保存",hidden = true)
   // @PreAuthorize("hasAuthority('merchantInfo:MerchantSubscribe:save')")
    public Result<String> save(@RequestBody @Valid MerchantSubscribeDto dto){
        try {
            merchantSubscribeService.create(dto);
            return Result.success();
        } catch (Exception e) {
            throw new RpcCallException(e);
        }
    }

    @PutMapping
    @Operation(summary = "修改",hidden = true)
    //@PreAuthorize("hasAuthority('merchantInfo:MerchantSubscribe:update')")
    public Result<String> update(@RequestBody @Valid MerchantSubscribeDto dto){
        merchantSubscribeService.update(dto);
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
    @Operation(summary = "根据id删除" ,hidden = true)
    //@PreAuthorize("hasAuthority('merchantInfo:MerchantSubscribe:del_by_id')")
    public Result<String> del(@PathVariable("id") String id){
        merchantSubscribeService.deleteById(id);
        return Result.success();
    }


}