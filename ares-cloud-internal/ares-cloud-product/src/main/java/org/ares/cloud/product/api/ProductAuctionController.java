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
//import org.ares.cloud.product.convert.ProductAuctionConvert;
//import org.ares.cloud.product.entity.ProductAuctionEntity;
//import org.ares.cloud.product.service.ProductAuctionService;
//import org.ares.cloud.product.query.ProductAuctionQuery;
//import org.ares.cloud.product.dto.ProductAuctionDto;
//
///**
//* @author hugo tangxkwork@163.com
//* @description 拍卖商品 控制器
//* @version 1.0.0
//* @date 2024-11-08
//*/
//@RestController
//@RequestMapping("api/product/v1/productAuction")
//@Tag(name="拍卖商品")
//@AllArgsConstructor
//public class ProductAuctionController {
//    @Resource
//    private ProductAuctionService productAuctionService;
//
//
//    @GetMapping("page")
//    @Operation(summary = "分页")
//   // @PreAuthorize("hasAuthority('product:ProductAuction:page')")
//    public Result<PageResult<ProductAuctionDto>> page(@ParameterObject @Valid ProductAuctionQuery query){
//        PageResult<ProductAuctionDto> page = productAuctionService.loadList(query);
//
//        return Result.success(page);
//    }
//
//
//    @Operation(summary = "获取所有")
//    @GetMapping("/all")
//    // @PreAuthorize("hasAuthority('product:ProductAuction:all')")
//    public  Result<List<ProductAuctionDto>> all(){
//        List<ProductAuctionDto> all = productAuctionService.loadAll();
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
//    //@PreAuthorize("hasAuthority('product:ProductAuction:info')")
//    public Result<ProductAuctionDto> get(@PathVariable("id") String id){
//         ProductAuctionDto dto= productAuctionService.loadById(id);
//         return Result.success(dto);
//    }
//
//
//    @PostMapping
//    @Operation(summary = "保存")
//   // @PreAuthorize("hasAuthority('product:ProductAuction:save')")
//    public Result<String> save(@RequestBody @Valid ProductAuctionDto dto){
//        productAuctionService.create(dto);
//        return Result.success();
//    }
//
//    @PutMapping
//    @Operation(summary = "修改")
//    //@PreAuthorize("hasAuthority('product:ProductAuction:update')")
//    public Result<String> update(@RequestBody @Valid ProductAuctionDto dto){
//        productAuctionService.update(dto);
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
//    //@PreAuthorize("hasAuthority('product:ProductAuction:del_by_id')")
//    public Result<String> del(@PathVariable("id") String id){
//        productAuctionService.deleteById(id);
//        return Result.success();
//    }
//
//
//}