package org.ares.cloud.merchantInfo.api;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.ares.cloud.common.dto.PageResult;
import org.ares.cloud.common.model.Result;
import org.ares.cloud.merchantInfo.dto.MerchantDistributionDto;
import org.ares.cloud.merchantInfo.dto.OpeningHoursDto;
import org.springdoc.core.annotations.ParameterObject;
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;

import org.ares.cloud.merchantInfo.convert.MerchantFreightConvert;
import org.ares.cloud.merchantInfo.entity.MerchantFreightEntity;
import org.ares.cloud.merchantInfo.service.MerchantFreightService;
import org.ares.cloud.merchantInfo.query.MerchantFreightQuery;
import org.ares.cloud.merchantInfo.dto.MerchantFreightDto;

/**
* @author hugo tangxkwork@163.com
* @description 货运配送费用 控制器
* @version 1.0.0
* @date 2024-11-05
*/
@RestController
@RequestMapping("/api/merchant/v1/freight")
@Tag(name="货运配送费用")
@AllArgsConstructor
public class MerchantFreightController {
    @Resource
    private MerchantFreightService merchantFreightService;


    @GetMapping("page")
    @Operation(summary = "分页",hidden = true)
   // @PreAuthorize("hasAuthority('merchantInfo:MerchantFreight:page')")
    public Result<PageResult<MerchantFreightDto>> page(@ParameterObject @Valid MerchantFreightQuery query){
        PageResult<MerchantFreightDto> page = merchantFreightService.loadList(query);

        return Result.success(page);
    }


    @Operation(summary = "获取所有",hidden = true)
    @GetMapping("/all")
    // @PreAuthorize("hasAuthority('merchantInfo:MerchantFreight:all')")
    public  Result<List<MerchantFreightDto>> all(){
        List<MerchantFreightDto> all = merchantFreightService.loadAll();
        return Result.success(all);
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
//    //@PreAuthorize("hasAuthority('merchantInfo:MerchantFreight:info')")
//    public Result<MerchantFreightDto> get(@PathVariable("id") String id){
//         MerchantFreightDto dto= merchantFreightService.loadById(id);
//         return Result.success(dto);
//    }


//    @PostMapping
//    @Operation(summary = "保存")
//   // @PreAuthorize("hasAuthority('merchantInfo:MerchantFreight:save')")
//    public Result<String> save(@RequestBody @Valid MerchantFreightDto dto){
//        merchantFreightService.create(dto);
//        return Result.success();
//    }
//
//    @PutMapping
//    @Operation(summary = "修改")
//    //@PreAuthorize("hasAuthority('merchantInfo:MerchantFreight:update')")
//    public Result<String> update(@RequestBody @Valid MerchantFreightDto dto){
//        merchantFreightService.update(dto);
//        return Result.success();
//    }
    @PostMapping
    @Operation(summary = "货运配送费用批量保存/更新")
    // @PreAuthorize("hasAuthority('merchantInfo:openingHours:save')")
    public Result<String> save(@RequestBody List<MerchantFreightDto> dto){
        merchantFreightService.create(dto);
        return Result.success();
    }

        @PutMapping
        @Operation(summary = "货运配送费用批量修改",hidden = true)
        //@PreAuthorize("hasAuthority('merchantInfo:openingHours:update')")
        public Result<String> update(@RequestBody @Valid  List<MerchantFreightDto> dto){
            merchantFreightService.update(dto);
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
    //@PreAuthorize("hasAuthority('merchantInfo:MerchantFreight:del_by_id')")
    public Result<String> del(@PathVariable("id") String id){
        merchantFreightService.deleteById(id);
        return Result.success();
    }

    @GetMapping("getInfoByTenantId/{tenantId}")
    @Operation(summary = "根据商户id查询详情")
    public Result<List<MerchantFreightDto>>  getInfoByTenantId(@PathVariable("tenantId")  String tenantId){
        List<MerchantFreightDto> all = merchantFreightService.getInfoByTenantId(tenantId);
        return Result.success(all);
    }
}