package org.ares.cloud.businessId.api;


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

import org.ares.cloud.businessId.convert.BusinessIdConvert;
import org.ares.cloud.businessId.entity.BusinessIdEntity;
import org.ares.cloud.businessId.service.BusinessIdService;
import org.ares.cloud.businessId.query.BusinessIdQuery;
import org.ares.cloud.businessId.dto.BusinessIdDto;

/**
* @author hugo tangxkwork@163.com
* @description 系统业务id 控制器
* @version 1.0.0
* @date 2024-10-13
*/
@RestController
@RequestMapping("/api/base/sys_business_id")
@Tag(name="系统业务id")
@AllArgsConstructor
public class BusinessIdController {
    @Resource
    private BusinessIdService businessIdService;


    @GetMapping("page")
    @Operation(summary = "分页")
   // @PreAuthorize("hasAuthority('businessId:sys_business_id:page')")
    public Result<PageResult<BusinessIdDto>> page(@ParameterObject @Valid BusinessIdQuery query){
        PageResult<BusinessIdDto> page = businessIdService.loadList(query);

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
    //@PreAuthorize("hasAuthority('businessId:sys_business_id:info')")
    public Result<BusinessIdDto> get(@PathVariable("id") String id){
         BusinessIdDto dto= businessIdService.loadById(id);
         return Result.success(dto);
    }


    @PostMapping
    @Operation(summary = "保存")
   // @PreAuthorize("hasAuthority('businessId:sys_business_id:save')")
    public Result<String> save(@RequestBody @Valid BusinessIdDto dto){
        businessIdService.create(dto);
        return Result.success();
    }

    @PutMapping
    @Operation(summary = "修改")
    //@PreAuthorize("hasAuthority('businessId:sys_business_id:update')")
    public Result<String> update(@RequestBody @Valid BusinessIdDto dto){
        businessIdService.update(dto);
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
    //@PreAuthorize("hasAuthority('businessId:sys_business_id:del_by_id')")
    public Result<String> del(@PathVariable("id") String id){
        businessIdService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/generate/snowflake")
    @Operation(summary = "生成16位短雪花ID", description = "生成全局唯一的16位数字ID，格式：秒级时间戳(10位)+机器ID(2位)+序列号(4位)")
    public Result<String> generateSnowflakeId() {
        String id = businessIdService.generateSnowflakeId();
        return Result.success(id);
    }

    @GetMapping("/generate/random")
    @Operation(summary = "生成随机业务ID", description = "生成基于日期和随机数的业务ID")
    public Result<String> generateRandomBusinessId() {
        String id = businessIdService.generateRandomBusinessId();
        return Result.success(id);
    }

    @GetMapping("/generate/business/{moduleName}")
    @Operation(summary = "根据模块名生成业务ID", description = "根据业务模块生成带流水号的业务ID")
    @Parameter(name = "moduleName", description = "业务模块名称", required = true)
    public Result<String> generateBusinessId(@PathVariable("moduleName") String moduleName) {
        String id = businessIdService.generateBusinessId(moduleName);
        return Result.success(id);
    }

}