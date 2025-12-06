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
//import org.ares.cloud.product.convert.ProductListConvert;
//import org.ares.cloud.product.entity.ProductListEntity;
//import org.ares.cloud.product.service.ProductListService;
//import org.ares.cloud.product.query.ProductListQuery;
//import org.ares.cloud.product.dto.ProductListDto;
//
///**
//* @author hugo tangxkwork@163.com
//* @description 商品清单 控制器
//* @version 1.0.0
//* @date 2025-04-03
//*/
//@RestController
//@RequestMapping("/api/product/v1/productList")
//@Tag(name="商品清单")
//@AllArgsConstructor
//public class ProductListController {
//    @Resource
//    private ProductListService productListService;
//
//
//    @GetMapping("page")
//    @Operation(summary = "分页")
//   // @PreAuthorize("hasAuthority('product:ProductList:page')")
//    public Result<PageResult<ProductListDto>> page(@ParameterObject @Valid ProductListQuery query){
//        PageResult<ProductListDto> page = productListService.loadList(query);
//
//        return Result.success(page);
//    }
//
//
//    @Operation(summary = "获取所有")
//    @GetMapping("/all")
//    // @PreAuthorize("hasAuthority('product:ProductList:all')")
//    public  Result<List<ProductListDto>> all(){
//        List<ProductListDto> all = productListService.loadAll();
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
//    //@PreAuthorize("hasAuthority('product:ProductList:info')")
//    public Result<ProductListDto> get(@PathVariable("id") String id){
//         ProductListDto dto= productListService.loadById(id);
//         return Result.success(dto);
//    }
//
//
//    @PostMapping
//    @Operation(summary = "保存")
//   // @PreAuthorize("hasAuthority('product:ProductList:save')")
//    public Result<String> save(@RequestBody @Valid ProductListDto dto){
//        productListService.create(dto);
//        return Result.success();
//    }
//
//    @PutMapping
//    @Operation(summary = "修改")
//    //@PreAuthorize("hasAuthority('product:ProductList:update')")
//    public Result<String> update(@RequestBody @Valid ProductListDto dto){
//        productListService.update(dto);
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
//    //@PreAuthorize("hasAuthority('product:ProductList:del_by_id')")
//    public Result<String> del(@PathVariable("id") String id){
//        productListService.deleteById(id);
//        return Result.success();
//    }
//
//
//}