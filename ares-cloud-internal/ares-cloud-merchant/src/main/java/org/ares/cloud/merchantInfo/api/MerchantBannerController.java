package org.ares.cloud.merchantInfo.api;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.ares.cloud.common.dto.PageResult;
import org.ares.cloud.common.model.Result;
import org.ares.cloud.common.utils.UserAgentUtils;
import org.springdoc.core.annotations.ParameterObject;
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;

import org.ares.cloud.merchantInfo.convert.MerchantBannerConvert;
import org.ares.cloud.merchantInfo.entity.MerchantBannerEntity;
import org.ares.cloud.merchantInfo.service.MerchantBannerService;
import org.ares.cloud.merchantInfo.query.MerchantBannerQuery;
import org.ares.cloud.merchantInfo.dto.MerchantBannerDto;

/**
* @author hugo tangxkwork@163.com
* @description 轮播图 控制器
* @version 1.0.0
* @date 2025-03-18
*/
@RestController
@RequestMapping("/api/merchant/v1/banner")
@Tag(name="商户轮播图")
@AllArgsConstructor
public class MerchantBannerController {
    @Resource
    private MerchantBannerService merchantBannerService;


    @GetMapping("page")
    @Operation(summary = "分页")
    public Result<PageResult<MerchantBannerDto>> page(@ParameterObject @Valid MerchantBannerQuery query, HttpServletRequest  request){
        String domainName = UserAgentUtils.getDomainOrIpPort(request);
        //根据域名查询对应商户信息
        PageResult<MerchantBannerDto> page = merchantBannerService.loadList(query,domainName);
        return Result.success(page);
    }


    @Operation(summary = "获取所有")
    @GetMapping("/all")
    public  Result<List<MerchantBannerDto>> all(){
        List<MerchantBannerDto> all = merchantBannerService.loadAll();
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
    //@PreAuthorize("hasAuthority('merchantInfo:MerchantBanner:info')")
    public Result<MerchantBannerDto> get(@PathVariable("id") String id){
         MerchantBannerDto dto= merchantBannerService.loadById(id);
         return Result.success(dto);
    }


    @PostMapping
    @Operation(summary = "保存")
   // @PreAuthorize("hasAuthority('merchantInfo:MerchantBanner:save')")
    public Result<String> save(@RequestBody @Valid MerchantBannerDto dto){
        merchantBannerService.create(dto);
        return Result.success();
    }

    @PutMapping
    @Operation(summary = "修改")
    //@PreAuthorize("hasAuthority('merchantInfo:MerchantBanner:update')")
    public Result<String> update(@RequestBody @Valid MerchantBannerDto dto){
        merchantBannerService.update(dto);
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
    //@PreAuthorize("hasAuthority('merchantInfo:MerchantBanner:del_by_id')")
    public Result<String> del(@PathVariable("id") String id){
        merchantBannerService.deleteById(id);
        return Result.success();
    }


}