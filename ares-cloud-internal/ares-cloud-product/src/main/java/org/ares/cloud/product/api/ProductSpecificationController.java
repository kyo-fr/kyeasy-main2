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
//import org.ares.cloud.product.convert.ProductSpecificationConvert;
//import org.ares.cloud.product.entity.ProductSpecificationEntity;
//import org.ares.cloud.product.service.ProductSpecificationService;
//import org.ares.cloud.product.query.ProductSpecificationQuery;
//import org.ares.cloud.product.dto.ProductSpecificationDto;
//
///**
//* @author hugo tangxkwork@163.com
//* @description 商品规格 控制器
//* @version 1.0.0
//* @date 2025-03-18
//*/
//@RestController
//@RequestMapping("/api/product/productSpecification")
//@Tag(name="商品规格")
//@AllArgsConstructor
//public class ProductSpecificationController {
//    @Resource
//    private ProductSpecificationService productSpecificationService;
//
//
//    @GetMapping("page")
//    @Operation(summary = "分页")
//   // @PreAuthorize("hasAuthority('product:ProductSpecification:page')")
//    public Result<PageResult<ProductSpecificationDto>> page(@ParameterObject @Valid ProductSpecificationQuery query){
//        PageResult<ProductSpecificationDto> page = productSpecificationService.loadList(query);
//
//        return Result.success(page);
//    }
//
//
//    @Operation(summary = "获取所有")
//    @GetMapping("/all")
//    // @PreAuthorize("hasAuthority('product:ProductSpecification:all')")
//    public  Result<List<ProductSpecificationDto>> all(){
//        List<ProductSpecificationDto> all = productSpecificationService.loadAll();
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
//    //@PreAuthorize("hasAuthority('product:ProductSpecification:info')")
//    public Result<ProductSpecificationDto> get(@PathVariable("id") String id){
//         ProductSpecificationDto dto= productSpecificationService.loadById(id);
//         return Result.success(dto);
//    }
//
//
//    @PostMapping
//    @Operation(summary = "保存")
//   // @PreAuthorize("hasAuthority('product:ProductSpecification:save')")
//    public Result<String> save(@RequestBody @Valid ProductSpecificationDto dto){
//        productSpecificationService.create(dto);
//        return Result.success();
//    }
//
//    @PutMapping
//    @Operation(summary = "修改")
//    //@PreAuthorize("hasAuthority('product:ProductSpecification:update')")
//    public Result<String> update(@RequestBody @Valid ProductSpecificationDto dto){
//        productSpecificationService.update(dto);
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
//    //@PreAuthorize("hasAuthority('product:ProductSpecification:del_by_id')")
//    public Result<String> del(@PathVariable("id") String id){
//        productSpecificationService.deleteById(id);
//        return Result.success();
//    }
//
//
//}