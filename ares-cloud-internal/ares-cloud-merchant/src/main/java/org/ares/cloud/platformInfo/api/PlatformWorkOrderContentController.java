package org.ares.cloud.platformInfo.api;


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
import org.springdoc.core.annotations.ParameterObject;
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;

import org.ares.cloud.platformInfo.convert.PlatformWorkOrderContentConvert;
import org.ares.cloud.platformInfo.entity.PlatformWorkOrderContentEntity;
import org.ares.cloud.platformInfo.service.PlatformWorkOrderContentService;
import org.ares.cloud.platformInfo.query.PlatformWorkOrderContentQuery;
import org.ares.cloud.platformInfo.dto.PlatformWorkOrderContentDto;

/**
* @author hugo tangxkwork@163.com
* @description 工单内容 控制器
* @version 1.0.0
* @date 2024-10-17
*/
@RestController
@RequestMapping("/api/platform/v1/workOrderContent")
@Tag(name="平台工单内容-废弃")
@Hidden
@AllArgsConstructor
public class PlatformWorkOrderContentController {
    @Resource
    private PlatformWorkOrderContentService platformWorkOrderContentService;


    @GetMapping("page")
    @Operation(summary = "分页")
   // @PreAuthorize("hasAuthority('platformInfo:PlatformWorkOrderContent:page')")
    public Result<PageResult<PlatformWorkOrderContentDto>> page(@ParameterObject @Valid PlatformWorkOrderContentQuery query){
        PageResult<PlatformWorkOrderContentDto> page = platformWorkOrderContentService.loadList(query);

        return Result.success(page);
    }


    @Operation(summary = "获取所有")
    @GetMapping("/all")
    // @PreAuthorize("hasAuthority('platformInfo:PlatformWorkOrderContent:all')")
    public  Result<List<PlatformWorkOrderContentDto>> all(){
        List<PlatformWorkOrderContentDto> all = platformWorkOrderContentService.loadAll();
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
    //@PreAuthorize("hasAuthority('platformInfo:PlatformWorkOrderContent:info')")
    public Result<PlatformWorkOrderContentDto> get(@PathVariable("id") String id){
         PlatformWorkOrderContentDto dto= platformWorkOrderContentService.loadById(id);
         return Result.success(dto);
    }


    @PostMapping
    @Operation(summary = "保存")
   // @PreAuthorize("hasAuthority('platformInfo:PlatformWorkOrderContent:save')")
    public Result<String> save(@RequestBody @Valid PlatformWorkOrderContentDto dto){
        platformWorkOrderContentService.create(dto);
        return Result.success();
    }

    @PutMapping
    @Operation(summary = "修改")
    //@PreAuthorize("hasAuthority('platformInfo:PlatformWorkOrderContent:update')")
    public Result<String> update(@RequestBody @Valid PlatformWorkOrderContentDto dto){
        platformWorkOrderContentService.update(dto);
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
    //@PreAuthorize("hasAuthority('platformInfo:PlatformWorkOrderContent:del_by_id')")
    public Result<String> del(@PathVariable("id") String id){
        platformWorkOrderContentService.deleteById(id);
        return Result.success();
    }


}