package org.ares.cloud.s3.api;


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

import org.ares.cloud.s3.convert.ObjectStorageConvert;
import org.ares.cloud.s3.entity.ObjectStorageEntity;
import org.ares.cloud.s3.service.ObjectStorageService;
import org.ares.cloud.s3.query.ObjectStorageQuery;
import org.ares.cloud.s3.dto.ObjectStorageDto;

/**
* @author hugo tangxkwork@163.com
* @description s3存储 控制器
* @version 1.0.0
* @date 2024-10-12
*/
@RestController
@RequestMapping("/api/base/storage")
@Tag(name="s3存储")
@AllArgsConstructor
public class ObjectStorageController {
    @Resource
    private ObjectStorageService objectStorageService;


    @GetMapping("page")
    @Operation(summary = "分页")
   // @PreAuthorize("hasAuthority('s3:sys_s3_storage:page')")
    public Result<PageResult<ObjectStorageDto>> page(@ParameterObject @Valid ObjectStorageQuery query){
        PageResult<ObjectStorageDto> page = objectStorageService.loadList(query);

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
    //@PreAuthorize("hasAuthority('s3:sys_s3_storage:info')")
    public Result<ObjectStorageDto> get(@PathVariable("id") String id){
         ObjectStorageDto dto= objectStorageService.loadById(id);
         return Result.success(dto);
    }





}