package org.ares.cloud.product.api;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.ares.cloud.common.dto.PageResult;
import org.ares.cloud.common.model.Result;
import org.springdoc.core.annotations.ParameterObject;
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;

import org.ares.cloud.product.convert.ProductSubSpecificationConvert;
import org.ares.cloud.product.entity.ProductSubSpecificationEntity;
import org.ares.cloud.product.service.ProductSubSpecificationService;
import org.ares.cloud.product.query.ProductSubSpecificationQuery;
import org.ares.cloud.product.dto.ProductSubSpecificationDto;

/**
* @author hugo tangxkwork@163.com
* @description 商品子规格 控制器
* @version 1.0.0
* @date 2025-03-24
*/
@RestController
@RequestMapping("/api/product/v1/product_sub_specification")
@Tag(name="商品子规格")
@AllArgsConstructor
public class ProductSubSpecificationController {
    @Resource
    private ProductSubSpecificationService productSubSpecificationService;


    @GetMapping("page")
    @Operation(summary = "分页")
   // @PreAuthorize("hasAuthority('product:productSubSpecification:page')")
    public Result<PageResult<ProductSubSpecificationDto>> page(@ParameterObject @Valid ProductSubSpecificationQuery query){
        PageResult<ProductSubSpecificationDto> page = productSubSpecificationService.loadList(query);

        return Result.success(page);
    }


    @Operation(summary = "获取所有")
    @GetMapping("/all")
    // @PreAuthorize("hasAuthority('product:productSubSpecification:all')")
    public  Result<List<ProductSubSpecificationDto>> all(){
        List<ProductSubSpecificationDto> all = productSubSpecificationService.loadAll();
        return Result.success(all);
    }

    @Parameter(
        name = "id",
        description = "主键",
        required = true,
        in = ParameterIn.PATH,
        schema = @Schema(type = "string")
    )
    @GetMapping("{id}")
    @Operation(summary = "详情")
    //@PreAuthorize("hasAuthority('product:productSubSpecification:info')")
    public Result<ProductSubSpecificationDto> get(@PathVariable("id") String id){
         ProductSubSpecificationDto dto= productSubSpecificationService.loadById(id);
         return Result.success(dto);
    }


    @PostMapping
    @Operation(summary = "保存")
   // @PreAuthorize("hasAuthority('product:productSubSpecification:save')")
    public Result<String> save(@RequestBody @Valid ProductSubSpecificationDto dto){
        productSubSpecificationService.create(dto);
        return Result.success();
    }

    @PutMapping
    @Operation(summary = "修改")
    //@PreAuthorize("hasAuthority('product:productSubSpecification:update')")
    public Result<String> update(@RequestBody @Valid ProductSubSpecificationDto dto){
        productSubSpecificationService.update(dto);
        return Result.success();
    }

    @Parameter(
    name = "id",
    description = "主键",
    required = true,
    in = ParameterIn.PATH,
    schema = @Schema(type = "string")
    )
    @DeleteMapping("{id}")
    @Operation(summary = "根据id删除" ,hidden = true)
    //@PreAuthorize("hasAuthority('product:productSubSpecification:del_by_id')")
    public Result<String> del(@PathVariable("id") String id){
        productSubSpecificationService.deleteById(id);
        return Result.success();
    }


}