package org.ares.cloud.product.internal;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.annotation.Resource;
import org.ares.cloud.product.dto.ProductDto;
import org.ares.cloud.product.service.ProductBaseInfoService;
import org.ares.cloud.product.vo.ProductBaseInfoVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/product/v1/product")
public class ProductInternalController {

    @Resource
    private ProductBaseInfoService productBaseInfoService;

    /**
     * 根据标注、关键字、规格 id查询商品是否下架
     * @param id 产品id
     * @param type 类型
     * @return 是否删除
     */
    @Hidden
    @GetMapping("/checkProductIsDelete")
    public String checkProductIsDelete(@RequestParam("id") String id, @RequestParam("type") String type) {
        Boolean isDeleted = productBaseInfoService.checkProductIsDelete(id, type);
        return isDeleted != null && isDeleted ? "deleted" : "active";
    }

    /**
     * 根据id查询商品信息
     * @param productId 产品id
     * @return 产品基础信息
     */
    @Hidden
    @GetMapping("/getProductInfoByProductId")
    public ProductBaseInfoVo getProductInfoByProductId(@RequestParam("productId") String productId) {
        return productBaseInfoService.loadById(productId);
    }


    /**
     * 监听商品id变更 修改商品是否下架状态
     */
    @Hidden
    @GetMapping("/updateProductIsEnable")
    public void updateProductIsEnable(@RequestParam("productId") String productId) {
        productBaseInfoService.updateProductIsEnable(productId);
    }
}
