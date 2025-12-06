//package org.ares.cloud.platformInfo.api;
//
//
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.enums.ParameterIn;
//import io.swagger.v3.oas.annotations.media.Schema;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import jakarta.validation.Valid;
//import lombok.AllArgsConstructor;
//import org.ares.cloud.common.dto.PageResult;
//import org.ares.cloud.common.model.Result;
//import org.springdoc.core.annotations.ParameterObject;
//// import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//import java.util.List;
//import jakarta.annotation.Resource;
//import jakarta.validation.Valid;
//
//import org.ares.cloud.platformInfo.convert.PlatformPayWayConvert;
//import org.ares.cloud.platformInfo.entity.PlatformPayWayEntity;
//import org.ares.cloud.platformInfo.service.PlatformPayWayService;
//import org.ares.cloud.platformInfo.query.PlatformPayWayQuery;
//import org.ares.cloud.platformInfo.dto.PlatformPayWayDto;
//
///**
//* @author hugo tangxkwork@163.com
//* @description 平台支付类型 控制器
//* @version 1.0.0
//* @date 2024-10-15
//*/
//@RestController
//@RequestMapping("/api/platform/v1/payWay")
//@Tag(name="平台支付类型")
//@AllArgsConstructor
//public class PlatformPayWayController {
//    @Resource
//    private PlatformPayWayService platformPayWayService;
//
//
//    @GetMapping("page")
//    @Operation(summary = "分页")
//   // @PreAuthorize("hasAuthority('platformInfo:PlatformPayWay:page')")
//    public Result<PageResult<PlatformPayWayDto>> page(@ParameterObject @Valid PlatformPayWayQuery query){
//        PageResult<PlatformPayWayDto> page = platformPayWayService.loadList(query);
//
//        return Result.success(page);
//    }
//
//
//    @Operation(summary = "获取所有")
//    @GetMapping("/all")
//    // @PreAuthorize("hasAuthority('platformInfo:PlatformPayWay:all')")
//    public  Result<List<PlatformPayWayDto>> all(){
//        List<PlatformPayWayDto> all = platformPayWayService.loadAll();
//        return Result.success(all);
//    }
//
//    @Parameter(
//        name = "id",
//        description = "主键",
//        required = true,
//        in = ParameterIn.PATH,
//        schema = @Schema(type = "string")
//    )
//    @GetMapping("{id}")
//    @Operation(summary = "详情")
//    //@PreAuthorize("hasAuthority('platformInfo:PlatformPayWay:info')")
//    public Result<PlatformPayWayDto> get(@PathVariable("id") String id){
//         PlatformPayWayDto dto= platformPayWayService.loadById(id);
//         return Result.success(dto);
//    }
//
//
//    @PostMapping
//    @Operation(summary = "保存")
//   // @PreAuthorize("hasAuthority('platformInfo:PlatformPayWay:save')")
//    public Result<String> save(@RequestBody @Valid PlatformPayWayDto dto){
//        platformPayWayService.create(dto);
//        return Result.success();
//    }
//
//    @PutMapping
//    @Operation(summary = "修改")
//    //@PreAuthorize("hasAuthority('platformInfo:PlatformPayWay:update')")
//    public Result<String> update(@RequestBody @Valid PlatformPayWayDto dto){
//        platformPayWayService.update(dto);
//        return Result.success();
//    }
//
//    @Parameter(
//    name = "id",
//    description = "主键",
//    required = true,
//    in = ParameterIn.PATH,
//    schema = @Schema(type = "string")
//    )
//    @DeleteMapping("{id}")
//    @Operation(summary = "根据id删除" ,hidden = true)
//    //@PreAuthorize("hasAuthority('platformInfo:PlatformPayWay:del_by_id')")
//    public Result<String> del(@PathVariable("id") String id){
//        platformPayWayService.deleteById(id);
//        return Result.success();
//    }
//
//
//}