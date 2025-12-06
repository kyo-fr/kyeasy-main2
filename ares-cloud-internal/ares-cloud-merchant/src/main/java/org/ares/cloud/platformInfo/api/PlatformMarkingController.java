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
//import org.ares.cloud.platformInfo.convert.PlatformMarkingConvert;
//import org.ares.cloud.platformInfo.entity.PlatformMarkingEntity;
//import org.ares.cloud.platformInfo.service.PlatformMarkingService;
//import org.ares.cloud.platformInfo.query.PlatformMarkingQuery;
//import org.ares.cloud.platformInfo.dto.PlatformMarkingDto;
//
///**
//* @author hugo platformInfo
//* @description 商品标注 控制器
//* @version 1.0.0
//* @date 2024-11-04
//*/
//@RestController
//@RequestMapping("/api/platform/v1/marking")
//@Tag(name="商品标注主数据")
//@AllArgsConstructor
//public class PlatformMarkingController {
//    @Resource
//    private PlatformMarkingService platformMarkingService;
//
//
//    @GetMapping("page")
//    @Operation(summary = "分页")
//   // @PreAuthorize("hasAuthority('platformInfo:PlatformMarking:page')")
//    public Result<PageResult<PlatformMarkingDto>> page(@ParameterObject @Valid PlatformMarkingQuery query){
//        PageResult<PlatformMarkingDto> page = platformMarkingService.loadList(query);
//
//        return Result.success(page);
//    }
//
//
//    @Operation(summary = "获取所有")
//    @GetMapping("/all")
//    // @PreAuthorize("hasAuthority('platformInfo:PlatformMarking:all')")
//    public  Result<List<PlatformMarkingDto>> all(){
//        List<PlatformMarkingDto> all = platformMarkingService.loadAll();
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
//    //@PreAuthorize("hasAuthority('platformInfo:PlatformMarking:info')")
//    public Result<PlatformMarkingDto> get(@PathVariable("id") String id){
//         PlatformMarkingDto dto= platformMarkingService.loadById(id);
//         return Result.success(dto);
//    }
//
//
//    @PostMapping
//    @Operation(summary = "保存")
//   // @PreAuthorize("hasAuthority('platformInfo:PlatformMarking:save')")
//    public Result<String> save(@RequestBody @Valid PlatformMarkingDto dto){
//        platformMarkingService.create(dto);
//        return Result.success();
//    }
//
//    @PutMapping
//    @Operation(summary = "修改")
//    //@PreAuthorize("hasAuthority('platformInfo:PlatformMarking:update')")
//    public Result<String> update(@RequestBody @Valid PlatformMarkingDto dto){
//        platformMarkingService.update(dto);
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
//    @Operation(summary = "根据id删除")
//    //@PreAuthorize("hasAuthority('platformInfo:PlatformMarking:del_by_id')")
//    public Result<String> del(@PathVariable("id") String id){
//        platformMarkingService.deleteById(id);
//        return Result.success();
//    }
//
//
//}