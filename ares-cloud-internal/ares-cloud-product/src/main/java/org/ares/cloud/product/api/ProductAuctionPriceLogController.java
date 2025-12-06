package org.ares.cloud.product.api;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.ares.cloud.common.dto.PageResult;
import org.ares.cloud.common.model.Result;
import org.ares.cloud.common.utils.UserAgentUtils;
import org.ares.cloud.product.dto.ProductAuctionPriceLogDto;
import org.ares.cloud.product.query.ProductAuctionPriceLogQuery;
import org.ares.cloud.product.service.ProductAuctionPriceLogService;
import org.ares.cloud.product.vo.ProductAuctionPriceLogVo;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

/**
* @author hugo tangxkwork@163.com
* @description 拍卖商品竞价记录 控制器
* @version 1.0.0
* @date 2025-09-23
*/
@RestController
@RequestMapping("/api/product/v1/productAuctionPriceLog")
@Tag(name="拍卖商品竞价记录")
@AllArgsConstructor
public class ProductAuctionPriceLogController {
    @Resource
    private ProductAuctionPriceLogService productAuctionPriceLogService;


    @GetMapping("page")
    @Operation(summary = "分页")
   // @PreAuthorize("hasAuthority('product:productAuctionPriceLog:page')")
    public Result<PageResult<ProductAuctionPriceLogVo>> page(@ParameterObject @Valid ProductAuctionPriceLogQuery query){
        PageResult<ProductAuctionPriceLogVo> page = productAuctionPriceLogService.loadList(query);

        return Result.success(page);
    }


    @Parameter(
        name = "id",
        description = "主键",
        required = true,
        in = ParameterIn.PATH,
        schema = @Schema(type = "string")
    )
    @GetMapping("{id}")
    @Operation(summary = "详情",hidden = true)
    //@PreAuthorize("hasAuthority('product:productAuctionPriceLog:info')")
    public Result<ProductAuctionPriceLogDto> get(@PathVariable("id") String id){
         ProductAuctionPriceLogDto dto= productAuctionPriceLogService.loadById(id);
         return Result.success(dto);
    }

    @PostMapping("userAddPrice")
    @Operation(summary = "用户竞价")
    public Result<String> userAddPrice(@RequestBody @Valid ProductAuctionPriceLogDto dto, HttpServletRequest request){
        String domainName = UserAgentUtils.getDomainOrIpPort(request);
        productAuctionPriceLogService.userAddPrice(dto,domainName);
        return Result.success();


    }
//    @PostMapping
//    @Operation(summary = "保存")
//    public Result<String> save(@RequestBody @Valid ProductAuctionPriceLogDto dto, HttpServletRequest request){
//        String domainName = UserAgentUtils.getDomainOrIpPort(request);
//        productAuctionPriceLogService.create(dto,domainName);
//        return Result.success();
//    }

    @PutMapping
    @Operation(summary = "修改",hidden = true)
    //@PreAuthorize("hasAuthority('product:productAuctionPriceLog:update')")
    public Result<String> update(@RequestBody @Valid ProductAuctionPriceLogDto dto){
        productAuctionPriceLogService.update(dto);
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
    //@PreAuthorize("hasAuthority('product:productAuctionPriceLog:del_by_id')")
    public Result<String> del(@PathVariable("id") String id){
        productAuctionPriceLogService.deleteById(id);
        return Result.success();
    }


}