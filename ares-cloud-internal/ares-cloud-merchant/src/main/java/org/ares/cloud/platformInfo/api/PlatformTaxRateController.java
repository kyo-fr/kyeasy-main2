package org.ares.cloud.platformInfo.api;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ares.cloud.common.dto.PageResult;
import org.ares.cloud.common.model.Result;
import org.ares.cloud.common.utils.UserAgentUtils;
import org.ares.cloud.platformInfo.dto.PlatformTaxRateDto;
import org.ares.cloud.platformInfo.query.PlatformTaxRateQuery;
import org.ares.cloud.platformInfo.service.PlatformTaxRateService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 税率 控制器
* @version 1.0.0
* @date 2024-10-15
*/
@RestController
@RequestMapping("/api/platform/v1/taxRate")
@Tag(name="税率主数据")
@AllArgsConstructor
@Slf4j
public class PlatformTaxRateController {
    @Resource
    private PlatformTaxRateService platformTaxRateService;


    @GetMapping("page")
    @Operation(summary = "分页")
   // @PreAuthorize("hasAuthority('platformInfo:PlatformTaxRate:page')")
    public Result<PageResult<PlatformTaxRateDto>> page(@ParameterObject @Valid PlatformTaxRateQuery query , HttpServletRequest request){
        String domainName = UserAgentUtils.getDomainOrIpPort(request);
        log.info("taxRate...page...domainName:{}",domainName);
        PageResult<PlatformTaxRateDto> page = platformTaxRateService.loadList(query,domainName);
        return Result.success(page);
    }


    @Operation(summary = "获取所有",hidden = true)
    @GetMapping("/all")
    // @PreAuthorize("hasAuthority('platformInfo:PlatformTaxRate:all')")
    public  Result<List<PlatformTaxRateDto>> all(){
        List<PlatformTaxRateDto> all = platformTaxRateService.loadAll();
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
    //@PreAuthorize("hasAuthority('platformInfo:PlatformTaxRate:info')")
    public Result<PlatformTaxRateDto> get(@PathVariable("id") String id){
         PlatformTaxRateDto dto= platformTaxRateService.loadById(id);
         return Result.success(dto);
    }


    @PostMapping
    @Operation(summary = "保存")
   // @PreAuthorize("hasAuthority('platformInfo:PlatformTaxRate:save')")
    public Result<String> save(@RequestBody @Valid PlatformTaxRateDto dto){
        platformTaxRateService.create(dto);
        return Result.success();
    }

    @PutMapping
    @Operation(summary = "修改")
    //@PreAuthorize("hasAuthority('platformInfo:PlatformTaxRate:update')")
    public Result<String> update(@RequestBody @Valid PlatformTaxRateDto dto){
        platformTaxRateService.update(dto);
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
    @Operation(summary = "根据id删除",hidden = true)
    //@PreAuthorize("hasAuthority('platformInfo:PlatformTaxRate:del_by_id')")
    public Result<String> del(@PathVariable("id") String id){
        platformTaxRateService.deleteById(id);
        return Result.success();
    }


}