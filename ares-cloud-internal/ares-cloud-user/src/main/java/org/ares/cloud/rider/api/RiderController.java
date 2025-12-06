package org.ares.cloud.rider.api;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.ares.cloud.common.dto.PageResult;
import org.ares.cloud.common.model.Result;
import org.ares.cloud.rider.dto.CreateRiderDto;
import org.springdoc.core.annotations.ParameterObject;
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;

import org.ares.cloud.rider.service.RiderService;
import org.ares.cloud.rider.query.RiderQuery;
import org.ares.cloud.api.user.dto.RiderDto;

/**
* @author hugo tangxkwork@163.com
* @description 骑手 控制器
* @version 1.0.0
* @date 2025-08-26
*/
@RestController
@RequestMapping("/api/rider")
@Tag(name="骑手")
@AllArgsConstructor
public class RiderController {
    @Resource
    private RiderService riderService;


    @GetMapping("page")
    @Operation(summary = "分页")
   // @PreAuthorize("hasAuthority('rider:rider:page')")
    public Result<PageResult<RiderDto>> page(@ParameterObject @Valid RiderQuery query){
        PageResult<RiderDto> page = riderService.loadList(query);

        return Result.success(page);
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
    //@PreAuthorize("hasAuthority('rider:rider:info')")
    public Result<RiderDto> get(@PathVariable("id") String id){
         RiderDto dto= riderService.loadById(id);
         return Result.success(dto);
    }


    @PostMapping
    @Operation(summary = "新增骑士")
   // @PreAuthorize("hasAuthority('rider:rider:save')")
    public Result<String> save(@RequestBody @Valid CreateRiderDto dto){
        riderService.create(dto);
        return Result.success();
    }

    @PutMapping
    @Operation(summary = "修改")
    //@PreAuthorize("hasAuthority('rider:rider:update')")
    public Result<String> update(@RequestBody @Valid RiderDto dto){
        riderService.update(dto);
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
    //@PreAuthorize("hasAuthority('rider:rider:del_by_id')")
    public Result<String> del(@PathVariable("id") String id){
        riderService.deleteById(id);
        return Result.success();
    }


}