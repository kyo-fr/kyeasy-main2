package org.ares.cloud.merchantInfo.api;


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
import org.ares.cloud.merchantInfo.dto.MerchantDistributionDto;
import org.ares.cloud.merchantInfo.query.MerchantDistributionQuery;
import org.ares.cloud.merchantInfo.service.MerchantDistributionService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 商户配送设置 控制器
* @version 1.0.0
* @date 2024-11-05
*/
@RestController
@RequestMapping("/api/merchant/v1/distribution")
@Tag(name="商户配送设置")
@AllArgsConstructor
public class MerchantDistributionController {
    @Resource
    private MerchantDistributionService merchantDistributionService;


    @GetMapping("page")
    @Operation(summary = "分页",hidden = true)
   // @PreAuthorize("hasAuthority('merchantInfo:MerchantDistribution:page')")
    public Result<PageResult<MerchantDistributionDto>> page(@ParameterObject @Valid MerchantDistributionQuery query){
        PageResult<MerchantDistributionDto> page = merchantDistributionService.loadList(query);

        return Result.success(page);
    }


    @Operation(summary = "获取所有",hidden = true)
    @GetMapping("/all")
    // @PreAuthorize("hasAuthority('merchantInfo:MerchantDistribution:all')")
    public  Result<List<MerchantDistributionDto>> all(){
        List<MerchantDistributionDto> all = merchantDistributionService.loadAll();
        return Result.success(all);
    }


    @GetMapping("getInfoByTenantId/{tenantId}")
    @Operation(summary = "根据商户id查询详情")
    // @PreAuthorize("hasAuthority('merchantInfo:MerchantDistribution:page')")
    public Result<MerchantDistributionDto>  getInfoByTenantId(@PathVariable("tenantId") String tenantId){
        MerchantDistributionDto dto= merchantDistributionService.getInfoByTenantId(tenantId);
        return Result.success(dto);
    }


//    @Parameter(
//        name = "id",
//        description = "主键",
//        required = true,
//        in = ParameterIn.PATH,
//        schema = @Schema(type = "string")
//    )
//    @GetMapping("{id}")
//    @Operation(summary = "详情",hidden = true)
//    //@PreAuthorize("hasAuthority('merchantInfo:MerchantDistribution:info')")
//    public Result<MerchantDistributionDto> get(@PathVariable("id") String id){
//         MerchantDistributionDto dto= merchantDistributionService.loadById(id);
//         return Result.success(dto);
//    }


    @PostMapping
    @Operation(summary = "保存")
   // @PreAuthorize("hasAuthority('merchantInfo:MerchantDistribution:save')")
    public Result<String> save(@RequestBody @Valid MerchantDistributionDto dto){
        merchantDistributionService.create(dto);
        return Result.success();
    }

    @PutMapping
    @Operation(summary = "修改",hidden = true)
    //@PreAuthorize("hasAuthority('merchantInfo:MerchantDistribution:update')")
    public Result<String> update(@RequestBody @Valid MerchantDistributionDto dto){
        merchantDistributionService.update(dto);
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
    //@PreAuthorize("hasAuthority('merchantInfo:MerchantDistribution:del_by_id')")
    public Result<String> del(@PathVariable("id") String id){
        merchantDistributionService.deleteById(id);
        return Result.success();
    }


}