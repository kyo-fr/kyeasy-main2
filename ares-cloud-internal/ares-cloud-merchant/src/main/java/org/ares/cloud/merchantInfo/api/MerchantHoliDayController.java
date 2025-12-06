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
import jakarta.validation.Valid;

import org.ares.cloud.merchantInfo.convert.MerchantHoliDayConvert;
import org.ares.cloud.merchantInfo.entity.MerchantHoliDayEntity;
import org.ares.cloud.merchantInfo.service.MerchantHoliDayService;
import org.ares.cloud.merchantInfo.query.MerchantHoliDayQuery;
import org.ares.cloud.merchantInfo.dto.MerchantHoliDayDto;

/**
* @author hugo tangxkwork@163.com
* @description 商户休假 控制器
* @version 1.0.0
* @date 2025-01-03
*/
@RestController
@RequestMapping("/api/merchant/v1/merchantHoliday")
@Tag(name="商户休假")
@AllArgsConstructor
public class MerchantHoliDayController {
    @Resource
    private MerchantHoliDayService merchantHoliDayService;


    @GetMapping("page")
    @Operation(summary = "分页")
    public Result<PageResult<MerchantHoliDayDto>> page(@ParameterObject @Valid MerchantHoliDayQuery query){
        PageResult<MerchantHoliDayDto> page = merchantHoliDayService.loadList(query);

        return Result.success(page);
    }


    @Operation(summary = "获取所有")
    @GetMapping("/all")
    public  Result<List<MerchantHoliDayDto>> all(){
        List<MerchantHoliDayDto> all = merchantHoliDayService.loadAll();
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
    public Result<MerchantHoliDayDto> get(@PathVariable("id") String id){
         MerchantHoliDayDto dto= merchantHoliDayService.loadById(id);
         return Result.success(dto);
    }


    @PostMapping
    @Operation(summary = "保存")
   // @PreAuthorize("hasAuthority('merchantInfo:MerchantHoliDay:save')")
    public Result<String> save(@RequestBody @Valid MerchantHoliDayDto dto){
        merchantHoliDayService.create(dto);
        return Result.success();
    }

    @PutMapping
    @Operation(summary = "修改")
    //@PreAuthorize("hasAuthority('merchantInfo:MerchantHoliDay:update')")
    public Result<String> update(@RequestBody @Valid MerchantHoliDayDto dto){
        merchantHoliDayService.update(dto);
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
    //@PreAuthorize("hasAuthority('merchantInfo:MerchantHoliDay:del_by_id')")
    public Result<String> del(@PathVariable("id") String id){
        merchantHoliDayService.deleteById(id);
        return Result.success();
    }


}