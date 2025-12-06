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
//import org.ares.cloud.platformInfo.convert.MerchantTypeConvert;
//import org.ares.cloud.platformInfo.entity.MerchantTypeEntity;
//import org.ares.cloud.platformInfo.service.MerchantTypeService;
//import org.ares.cloud.platformInfo.query.MerchantTypeQuery;
//import org.ares.cloud.platformInfo.dto.MerchantTypeDto;
//
///**
//* @author hugo tangxkwork@163.com
//* @description 商户类型 控制器
//* @version 1.0.0
//* @date 2024-10-15
//*/
//@RestController
//@RequestMapping("/api/platform/v1/merchantType")
//@Tag(name="商户类型")
//@AllArgsConstructor
//public class MerchantTypeController {
//    @Resource
//    private MerchantTypeService merchantTypeService;
//
//
//    @GetMapping("page")
//    @Operation(summary = "分页")
//   // @PreAuthorize("hasAuthority('platformInfo:MerchantType:page')")
//    public Result<PageResult<MerchantTypeDto>> page(@ParameterObject @Valid MerchantTypeQuery query){
//        PageResult<MerchantTypeDto> page = merchantTypeService.loadList(query);
//
//        return Result.success(page);
//    }
//
//
//    @Operation(summary = "获取所有")
//    @GetMapping("/all")
//    // @PreAuthorize("hasAuthority('platformInfo:MerchantType:all')")
//    public  Result<List<MerchantTypeDto>> all(){
//        List<MerchantTypeDto> all = merchantTypeService.loadAll();
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
//    //@PreAuthorize("hasAuthority('platformInfo:MerchantType:info')")
//    public Result<MerchantTypeDto> get(@PathVariable("id") String id){
//         MerchantTypeDto dto= merchantTypeService.loadById(id);
//         return Result.success(dto);
//    }
//
//
//    @PostMapping
//    @Operation(summary = "保存")
//   // @PreAuthorize("hasAuthority('platformInfo:MerchantType:save')")
//    public Result<String> save(@RequestBody @Valid MerchantTypeDto dto){
//        merchantTypeService.create(dto);
//        return Result.success();
//    }
//
//    @PutMapping
//    @Operation(summary = "修改")
//    //@PreAuthorize("hasAuthority('platformInfo:MerchantType:update')")
//    public Result<String> update(@RequestBody @Valid MerchantTypeDto dto){
//         merchantTypeService.update(dto);
//         return Result.success();
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
//    @Operation(summary = "根据id删除",hidden = true)
//    //@PreAuthorize("hasAuthority('platformInfo:MerchantType:del_by_id')")
//    public Result<String> del(@PathVariable("id") String id){
//        merchantTypeService.deleteById(id);
//        return Result.success();
//    }
//
//
//}