package org.ares.cloud.message.web;


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

import org.ares.cloud.message.convert.UserConvert;
import org.ares.cloud.message.entity.UserEntity;
import org.ares.cloud.message.service.UserService;
import org.ares.cloud.message.query.UserQuery;
import org.ares.cloud.message.dto.UserDto;

/**
* @author hugo tangxkwork@163.com
* @description 用户 控制器
* @version 1.0.0
* @date 2024-10-07
*/
@RestController
@RequestMapping("/api/message/ares-users")
@Tag(name="用户")
@AllArgsConstructor
public class UserController {
    @Resource
    private UserService userService;


    @GetMapping("page")
    @Operation(summary = "分页")
   // @PreAuthorize("hasAuthority('message:ares-users:page')")
    public Result<PageResult<UserDto>> page(@ParameterObject @Valid UserQuery query){
        PageResult<UserDto> page = userService.loadList(query);

        return Result.success(page);
    }


    @Parameter(
        name = "id",
        description = "主键",
        required = true,
        in = ParameterIn.PATH,
        schema = @Schema(type = "integer", format = "int64")
    )
    @GetMapping("{id}")
    @Operation(summary = "详情")
    //@PreAuthorize("hasAuthority('message:ares-users:info')")
    public Result<UserDto> get(@PathVariable("id") Long id){
         UserDto dto= userService.loadById(id);
         return Result.success(dto);
    }


    @PostMapping
    @Operation(summary = "保存")
   // @PreAuthorize("hasAuthority('message:ares-users:save')")
    public Result<String> save(@RequestBody UserDto dto){
        userService.create(dto);
        return Result.success();
    }

    @PutMapping
    @Operation(summary = "修改")
    //@PreAuthorize("hasAuthority('message:ares-users:update')")
    public Result<String> update(@RequestBody @Valid UserDto dto){
        userService.update(dto);
        return Result.success();
    }

    @Parameter(
    name = "id",
    description = "主键",
    required = true,
    in = ParameterIn.PATH,
    schema = @Schema(type = "integer", format = "int64")
    )
    @DeleteMapping("{id}")
    @Operation(summary = "根据id删除")
    //@PreAuthorize("hasAuthority('message:ares-users:del_by_id')")
    public Result<String> del(@PathVariable("id") Long id){
        userService.deleteById(id);
        return Result.success();
    }


}