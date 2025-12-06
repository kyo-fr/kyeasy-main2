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
//import org.ares.cloud.product.convert.ProductWholesaleConvert;
//import org.ares.cloud.product.entity.ProductWholesaleEntity;
//import org.ares.cloud.product.service.ProductWholesaleService;
//import org.ares.cloud.product.query.ProductWholesaleQuery;
//import org.ares.cloud.product.dto.ProductWholesaleDto;
//
///**
//* @author hugo tangxkwork@163.com
//* @description 批发商品 控制器
//* @version 1.0.0
//* @date 2024-11-08
//*/
//@RestController
//@RequestMapping("api/product/v1/productWholesale")
//@Tag(name="批发商品")
//@AllArgsConstructor
//public class ProductWholesaleController {
//    @Resource
//    private ProductWholesaleService productWholesaleService;
//
//
//    @GetMapping("page")
//    @Operation(summary = "分页")
//   // @PreAuthorize("hasAuthority('product:ProductWholesale:page')")
//    public Result<PageResult<ProductWholesaleDto>> page(@ParameterObject @Valid ProductWholesaleQuery query){
//        PageResult<ProductWholesaleDto> page = productWholesaleService.loadList(query);
//
//        return Result.success(page);
//    }
//
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
//    //@PreAuthorize("hasAuthority('product:ProductWholesale:info')")
//    public Result<ProductWholesaleDto> get(@PathVariable("id") String id){
//         ProductWholesaleDto dto= productWholesaleService.loadById(id);
//         return Result.success(dto);
//    }
//
//
//    @PostMapping
//    @Operation(summary = "保存")
//   // @PreAuthorize("hasAuthority('product:ProductWholesale:save')")
//    public Result<String> save(@RequestBody @Valid ProductWholesaleDto dto){
//        productWholesaleService.create(dto);
//        return Result.success();
//    }
//
//    @PutMapping
//    @Operation(summary = "修改")
//    //@PreAuthorize("hasAuthority('product:ProductWholesale:update')")
//    public Result<String> update(@RequestBody @Valid ProductWholesaleDto dto){
//        productWholesaleService.update(dto);
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
//    //@PreAuthorize("hasAuthority('product:ProductWholesale:del_by_id')")
//    public Result<String> del(@PathVariable("id") String id){
//        productWholesaleService.deleteById(id);
//        return Result.success();
//    }
//
//
//}