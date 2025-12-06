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
//import org.ares.cloud.product.convert.ProductPreferentialConvert;
//import org.ares.cloud.product.entity.ProductPreferentialEntity;
//import org.ares.cloud.product.service.ProductPreferentialService;
//import org.ares.cloud.product.query.ProductPreferentialQuery;
//import org.ares.cloud.product.dto.ProductPreferentialDto;
//
///**
//* @author hugo tangxkwork@163.com
//* @description 优惠商品 控制器
//* @version 1.0.0
//* @date 2024-11-07
//*/
//@RestController
//@RequestMapping("api/product/v1/productPreferential")
//@Tag(name="优惠商品")
//@AllArgsConstructor
//public class ProductPreferentialController {
//    @Resource
//    private ProductPreferentialService productPreferentialService;
//
//
//    @GetMapping("page")
//    @Operation(summary = "分页")
//   // @PreAuthorize("hasAuthority('product:ProductPreferential:page')")
//    public Result<PageResult<ProductPreferentialDto>> page(@ParameterObject @Valid ProductPreferentialQuery query){
//        PageResult<ProductPreferentialDto> page = productPreferentialService.loadList(query);
//
//        return Result.success(page);
//    }
//
//
//    @Operation(summary = "获取所有")
//    @GetMapping("/all")
//    // @PreAuthorize("hasAuthority('product:ProductPreferential:all')")
//    public  Result<List<ProductPreferentialDto>> all(){
//        List<ProductPreferentialDto> all = productPreferentialService.loadAll();
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
//    //@PreAuthorize("hasAuthority('product:ProductPreferential:info')")
//    public Result<ProductPreferentialDto> get(@PathVariable("id") String id){
//         ProductPreferentialDto dto= productPreferentialService.loadById(id);
//         return Result.success(dto);
//    }
//
//
//    @PostMapping
//    @Operation(summary = "保存")
//   // @PreAuthorize("hasAuthority('product:ProductPreferential:save')")
//    public Result<String> save(@RequestBody @Valid ProductPreferentialDto dto){
//        productPreferentialService.create(dto);
//        return Result.success();
//    }
//
//    @PutMapping
//    @Operation(summary = "修改")
//    //@PreAuthorize("hasAuthority('product:ProductPreferential:update')")
//    public Result<String> update(@RequestBody @Valid ProductPreferentialDto dto){
//        productPreferentialService.update(dto);
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
//    //@PreAuthorize("hasAuthority('product:ProductPreferential:del_by_id')")
//    public Result<String> del(@PathVariable("id") String id){
//        productPreferentialService.deleteById(id);
//        return Result.success();
//    }
//
//
//}