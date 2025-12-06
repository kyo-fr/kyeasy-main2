//package org.ares.cloud.product.api;
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
//import org.ares.cloud.product.convert.ProductMarkingConvert;
//import org.ares.cloud.product.entity.ProductMarkingEntity;
//import org.ares.cloud.product.service.ProductMarkingService;
//import org.ares.cloud.product.query.ProductMarkingQuery;
//import org.ares.cloud.product.dto.ProductMarkingDto;
//
///**
//* @author hugo tangxkwork@163.com
//* @description 商品标注 控制器
//* @version 1.0.0
//* @date 2024-11-08
//*/
//@RestController
//@RequestMapping("api/product/v1/productMarking")
//@Tag(name="商品标注")
//@AllArgsConstructor
//public class ProductMarkingController {
//    @Resource
//    private ProductMarkingService productMarkingService;
//
//
//    @GetMapping("page")
//    @Operation(summary = "分页")
//   // @PreAuthorize("hasAuthority('product:ProductMarking:page')")
//    public Result<PageResult<ProductMarkingDto>> page(@ParameterObject @Valid ProductMarkingQuery query){
//        PageResult<ProductMarkingDto> page = productMarkingService.loadList(query);
//
//        return Result.success(page);
//    }
//
//
//    @Operation(summary = "获取所有")
//    @GetMapping("/all")
//    // @PreAuthorize("hasAuthority('product:ProductMarking:all')")
//    public  Result<List<ProductMarkingDto>> all(){
//        List<ProductMarkingDto> all = productMarkingService.loadAll();
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
//    //@PreAuthorize("hasAuthority('product:ProductMarking:info')")
//    public Result<ProductMarkingDto> get(@PathVariable("id") String id){
//         ProductMarkingDto dto= productMarkingService.loadById(id);
//         return Result.success(dto);
//    }
//
//
//    @PostMapping
//    @Operation(summary = "保存")
//   // @PreAuthorize("hasAuthority('product:ProductMarking:save')")
//    public Result<String> save(@RequestBody @Valid ProductMarkingDto dto){
//        productMarkingService.create(dto);
//        return Result.success();
//    }
//
//    @PutMapping
//    @Operation(summary = "修改")
//    //@PreAuthorize("hasAuthority('product:ProductMarking:update')")
//    public Result<String> update(@RequestBody @Valid ProductMarkingDto dto){
//        productMarkingService.update(dto);
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
//    //@PreAuthorize("hasAuthority('product:ProductMarking:del_by_id')")
//    public Result<String> del(@PathVariable("id") String id){
//        productMarkingService.deleteById(id);
//        return Result.success();
//    }
//
//
//}