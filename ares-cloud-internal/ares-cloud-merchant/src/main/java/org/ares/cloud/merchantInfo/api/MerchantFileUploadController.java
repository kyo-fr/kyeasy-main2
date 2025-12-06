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

import org.ares.cloud.merchantInfo.convert.MerchantFileUploadConvert;
import org.ares.cloud.merchantInfo.entity.MerchantFileUploadEntity;
import org.ares.cloud.merchantInfo.service.MerchantFileUploadService;
import org.ares.cloud.merchantInfo.query.MerchantFileUploadQuery;
import org.ares.cloud.merchantInfo.dto.MerchantFileUploadDto;

/**
* @author hugo tangxkwork@163.com
* @description 商户文件上传 控制器
* @version 1.0.0
* @date 2024-10-09
*/
@RestController
@RequestMapping("/api/merchant/v1/fileUpload")
@Tag(name="商户文件上传")
@AllArgsConstructor
public class MerchantFileUploadController {
    @Resource
    private MerchantFileUploadService merchantFileUploadService;


    @GetMapping("page")
    @Operation(summary = "分页")
   // @PreAuthorize("hasAuthority('merchantInfo:MerchantFileUpload:page')")
    public Result<PageResult<MerchantFileUploadDto>> page(@ParameterObject @Valid MerchantFileUploadQuery query){
        PageResult<MerchantFileUploadDto> page = merchantFileUploadService.loadList(query);
        return Result.success(page);
    }


    @Operation(summary = "获取所有")
    @GetMapping("/all")
    // @PreAuthorize("hasAuthority('merchantInfo:MerchantFileUpload:all')")
    public  Result<List<MerchantFileUploadDto>> all(){
        List<MerchantFileUploadDto> all = merchantFileUploadService.loadAll();
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
    //@PreAuthorize("hasAuthority('merchantInfo:MerchantFileUpload:info')")
    public Result<MerchantFileUploadDto> get(@PathVariable("id") String id){
         MerchantFileUploadDto dto= merchantFileUploadService.loadById(id);
         return Result.success(dto);
    }


    @PostMapping
    @Operation(summary = "文件补充保存")
   // @PreAuthorize("hasAuthority('merchantInfo:MerchantFileUpload:save')")
    public Result<String> save(@RequestBody MerchantFileUploadDto dto){
        merchantFileUploadService.create(dto);
        return Result.success();
    }

    @PutMapping
    @Operation(summary = "修改")
    //@PreAuthorize("hasAuthority('merchantInfo:MerchantFileUpload:update')")
    public Result<String> update(@RequestBody @Valid MerchantFileUploadDto dto){
        merchantFileUploadService.update(dto);
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
    //@PreAuthorize("hasAuthority('merchantInfo:MerchantFileUpload:del_by_id')")
    public Result<String> del(@PathVariable("id") String id){
        merchantFileUploadService.deleteById(id);
        return Result.success();
    }

    @DeleteMapping
    @Operation(summary = "删除" ,hidden = true)
    //@PreAuthorize("hasAuthority('merchantInfo:MerchantFileUpload:delete')")
    public Result<String> delete(@RequestBody List<String> idList){

        merchantFileUploadService.deleteByIds(idList);
        return Result.success();
    }

}