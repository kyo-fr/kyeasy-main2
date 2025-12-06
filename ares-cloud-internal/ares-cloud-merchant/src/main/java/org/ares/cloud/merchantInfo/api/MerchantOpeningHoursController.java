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
import org.springdoc.core.annotations.ParameterObject;
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import jakarta.annotation.Resource;

import org.ares.cloud.merchantInfo.service.OpeningHoursService;
import org.ares.cloud.merchantInfo.query.OpeningHoursQuery;
import org.ares.cloud.merchantInfo.dto.OpeningHoursDto;

/**
* @author hugo tangxkwork@163.com
* @description 商户营业时间 控制器
* @version 1.0.0
* @date 2024-10-08
*/
@RestController
@RequestMapping("/api/merchant/v1/openingHours")
@Tag(name="营业时间")
@AllArgsConstructor
public class MerchantOpeningHoursController {
    @Resource
    private OpeningHoursService openingHoursService;


    @GetMapping("page")
    @Operation(summary = "分页")
   // @PreAuthorize("hasAuthority('merchantInfo:openingHours:page')")
    public Result<PageResult<OpeningHoursDto>> page(@ParameterObject @Valid OpeningHoursQuery query){
        PageResult<OpeningHoursDto> page = openingHoursService.loadList(query);

        return Result.success(page);
    }


    @Operation(summary = "获取所有",hidden = true)
    @GetMapping("/all")
    // @PreAuthorize("hasAuthority('merchantInfo:openingHours:all')")
    public  Result<List<OpeningHoursDto>> all(){
        List<OpeningHoursDto> all = openingHoursService.loadAll();
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
    @Operation(summary = "详情",hidden = true)
    //@PreAuthorize("hasAuthority('merchantInfo:openingHours:info')")
    public Result<OpeningHoursDto> get(@PathVariable("id") String id){
         OpeningHoursDto dto= openingHoursService.loadById(id);
         return Result.success(dto);
    }


    @PostMapping
    @Operation(summary = "营业时间批量保存")
   // @PreAuthorize("hasAuthority('merchantInfo:openingHours:save')")
    public Result<String> save(@RequestBody List<OpeningHoursDto> dto){
        openingHoursService.create(dto);
        return Result.success();
    }

    @PutMapping
    @Operation(summary = "营业时间批量修改")
    //@PreAuthorize("hasAuthority('merchantInfo:openingHours:update')")
    public Result<String> update(@RequestBody @Valid  List<OpeningHoursDto> dto){
        openingHoursService.update(dto);
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
    //@PreAuthorize("hasAuthority('merchantInfo:openingHours:del_by_id')")
    public Result<String> del(@PathVariable("id") String id){
        openingHoursService.deleteById(id);
        return Result.success();
    }

    @Parameter(
            name = "tenantId",
            description = "商户id",
            in = ParameterIn.QUERY,
            schema = @Schema(type = "string")
    )
    @GetMapping("/getOpeningHoursByTenantId")
    @Operation(summary = "根据商户id获取对应营业时间")
    public  Result<List<OpeningHoursDto>>  getOpeningHoursByTenantId(@RequestParam(value = "tenantId",required = false)  String tenantId){
        return Result.success( openingHoursService.getOpeningHoursByTenantId(tenantId));
    }

//    @GetMapping("getOpeningHoursByRoles")
//    @Operation(summary = "根据登录用户获取对应营业时间")
//    public  Result<List<OpeningHoursDto>>  getOpeningHoursByRoles(){
//        return Result.success( openingHoursService.getOpeningHoursByRoles());
//    }

}