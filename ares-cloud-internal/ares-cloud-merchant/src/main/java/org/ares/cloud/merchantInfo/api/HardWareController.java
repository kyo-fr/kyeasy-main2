//package org.ares.cloud.merchantInfo.api;
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
//import org.ares.cloud.merchantInfo.convert.HardWareConvert;
//import org.ares.cloud.merchantInfo.entity.HardWareEntity;
//import org.ares.cloud.merchantInfo.service.HardWareService;
//import org.ares.cloud.merchantInfo.query.HardWareQuery;
//import org.ares.cloud.merchantInfo.dto.HardWareDto;
//
///**
//* @author hugo tangxkwork@163.com
//* @description 硬件管理 控制器
//* @version 1.0.0
//* @date 2024-10-12
//*/
//@RestController
//@RequestMapping("/api/merchant/v1/hardware")
//@Tag(name="硬件管理")
//@AllArgsConstructor
//public class HardWareController {
//    @Resource
//    private HardWareService hardWareService;
//
//
//    @GetMapping("page")
//    @Operation(summary = "分页")
//   // @PreAuthorize("hasAuthority('merchantInfo:HardWare:page')")
//    public Result<PageResult<HardWareDto>> page(@ParameterObject @Valid HardWareQuery query){
//        PageResult<HardWareDto> page = hardWareService.loadList(query);
//
//        return Result.success(page);
//    }
//
//
//    @Operation(summary = "获取所有")
//    @GetMapping("/all")
//    // @PreAuthorize("hasAuthority('merchantInfo:HardWare:all')")
//    public  Result<List<HardWareDto>> all(){
//        List<HardWareDto> all = hardWareService.loadAll();
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
//    //@PreAuthorize("hasAuthority('merchantInfo:HardWare:info')")
//    public Result<HardWareDto> get(@PathVariable("id") String id){
//         HardWareDto dto= hardWareService.loadById(id);
//         return Result.success(dto);
//    }
//
//
//    @PostMapping
//    @Operation(summary = "保存")
//   // @PreAuthorize("hasAuthority('merchantInfo:HardWare:save')")
//    public Result<String> save(@RequestBody @Valid HardWareDto dto){
//        hardWareService.create(dto);
//        return Result.success();
//    }
//
//    @PutMapping
//    @Operation(summary = "修改")
//    //@PreAuthorize("hasAuthority('merchantInfo:HardWare:update')")
//    public Result<String> update(@RequestBody @Valid HardWareDto dto){
//        hardWareService.update(dto);
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
//    //@PreAuthorize("hasAuthority('merchantInfo:HardWare:del_by_id')")
//    public Result<String> del(@PathVariable("id") String id){
//        hardWareService.deleteById(id);
//        return Result.success();
//    }
//
//
//}