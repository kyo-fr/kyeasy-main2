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
//import org.ares.cloud.product.convert.ProductKeywordsConvert;
//import org.ares.cloud.product.entity.ProductKeywordsEntity;
//import org.ares.cloud.product.service.ProductKeywordsService;
//import org.ares.cloud.product.query.ProductKeywordsQuery;
//import org.ares.cloud.product.dto.ProductKeywordsDto;
//
///**
//* @author hugo tangxkwork@163.com
//* @description 商品关键字 控制器
//* @version 1.0.0
//* @date 2025-03-18
//*/
//@RestController
//@RequestMapping("/api/product/productKeywords")
//@Tag(name="商品关键字")
//@AllArgsConstructor
//public class ProductKeywordsController {
//    @Resource
//    private ProductKeywordsService productKeywordsService;
//
//
//    @GetMapping("page")
//    @Operation(summary = "分页")
//   // @PreAuthorize("hasAuthority('product:ProductKeywords:page')")
//    public Result<PageResult<ProductKeywordsDto>> page(@ParameterObject @Valid ProductKeywordsQuery query){
//        PageResult<ProductKeywordsDto> page = productKeywordsService.loadList(query);
//
//        return Result.success(page);
//    }
//
//
//    @Operation(summary = "获取所有")
//    @GetMapping("/all")
//    // @PreAuthorize("hasAuthority('product:ProductKeywords:all')")
//    public  Result<List<ProductKeywordsDto>> all(){
//        List<ProductKeywordsDto> all = productKeywordsService.loadAll();
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
//    //@PreAuthorize("hasAuthority('product:ProductKeywords:info')")
//    public Result<ProductKeywordsDto> get(@PathVariable("id") String id){
//         ProductKeywordsDto dto= productKeywordsService.loadById(id);
//         return Result.success(dto);
//    }
//
//
//    @PostMapping
//    @Operation(summary = "保存")
//   // @PreAuthorize("hasAuthority('product:ProductKeywords:save')")
//    public Result<String> save(@RequestBody @Valid ProductKeywordsDto dto){
//        productKeywordsService.create(dto);
//        return Result.success();
//    }
//
//    @PutMapping
//    @Operation(summary = "修改")
//    //@PreAuthorize("hasAuthority('product:ProductKeywords:update')")
//    public Result<String> update(@RequestBody @Valid ProductKeywordsDto dto){
//        productKeywordsService.update(dto);
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
//    //@PreAuthorize("hasAuthority('product:ProductKeywords:del_by_id')")
//    public Result<String> del(@PathVariable("id") String id){
//        productKeywordsService.deleteById(id);
//        return Result.success();
//    }
//
//
//}