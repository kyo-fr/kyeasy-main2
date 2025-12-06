package ${package}.${moduleName}.api;


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

import ${package}.${moduleName}.convert.${ClassName}Convert;
import ${package}.${moduleName}.entity.${ClassName}Entity;
import ${package}.${moduleName}.service.${ClassName}Service;
import ${package}.${moduleName}.query.${ClassName}Query;
import ${package}.${moduleName}.dto.${ClassName}Dto;

/**
* @author ${author} ${email}
* @description ${dis} 控制器
* @version ${version}
* @date ${date}
*/
@RestController
@RequestMapping("${baseApi}")
@Tag(name="${dis}")
@AllArgsConstructor
public class ${ClassName}Controller {
    @Resource
    private ${ClassName}Service ${className}Service;

<#if apis?seq_contains("page")>

    @GetMapping("page")
    @Operation(summary = "分页")
   // @PreAuthorize("hasAuthority('${moduleName}:${functionName}:page')")
    public Result<PageResult<${ClassName}Dto>> page(@ParameterObject @Valid ${ClassName}Query query){
        PageResult<${ClassName}Dto> page = ${className}Service.loadList(query);

        return Result.success(page);
    }
</#if>

<#if apis?seq_contains("all")>

    @Operation(summary = "获取所有")
    @GetMapping("/all")
    // @PreAuthorize("hasAuthority('${moduleName}:${functionName}:all')")
    public  Result<List<${ClassName}Dto>> all(){
        List<${ClassName}Dto> all = ${className}Service.loadAll();
        return Result.success(all);
    }
</#if>

<#if apis?seq_contains("details")>
    @Parameter(
        name = "id",
        description = "主键",
        required = true,
        in = ParameterIn.PATH,
        schema = @Schema(type = "string")
    )
    @GetMapping("{id}")
    @Operation(summary = "详情")
    //@PreAuthorize("hasAuthority('${moduleName}:${functionName}:info')")
    public Result<${ClassName}Dto> get(@PathVariable("id") String id){
         ${ClassName}Dto dto= ${className}Service.loadById(id);
         return Result.success(dto);
    }
</#if>

<#if apis?seq_contains("add")>

    @PostMapping
    @Operation(summary = "保存")
   // @PreAuthorize("hasAuthority('${moduleName}:${functionName}:save')")
    public Result<String> save(@RequestBody @Valid ${ClassName}Dto dto){
        ${className}Service.create(dto);
        return Result.success();
    }
</#if>

<#if apis?seq_contains("update")>
    @PutMapping
    @Operation(summary = "修改")
    //@PreAuthorize("hasAuthority('${moduleName}:${functionName}:update')")
    public Result<String> update(@RequestBody @Valid ${ClassName}Dto dto){
        ${className}Service.update(dto);
        return Result.success();
    }
</#if>

<#if apis?seq_contains("del")>
    @Parameter(
    name = "id",
    description = "主键",
    required = true,
    in = ParameterIn.PATH,
    schema = @Schema(type = "string")
    )
    @DeleteMapping("{id}")
    @Operation(summary = "根据id删除")
    //@PreAuthorize("hasAuthority('${moduleName}:${functionName}:del_by_id')")
    public Result<String> del(@PathVariable("id") String id){
        ${className}Service.deleteById(id);
        return Result.success();
    }
</#if>

<#if apis?seq_contains("batchDel")>
    @DeleteMapping
    @Operation(summary = "删除")
    //@PreAuthorize("hasAuthority('${moduleName}:${functionName}:delete')")
    public Result<String> delete(@RequestBody List<String> idList){
        ${className}Service.deleteByIds(idList);
        return Result.success();
    }
</#if>

}