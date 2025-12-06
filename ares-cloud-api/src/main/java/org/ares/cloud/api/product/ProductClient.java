package org.ares.cloud.api.product;


import org.ares.cloud.api.product.dto.ProductBaseInfoVo;
import org.ares.cloud.api.product.fallback.ProductClientFallback;
import org.ares.cloud.feign.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "product-service",configuration = FeignConfig.class,fallback = ProductClientFallback.class)
public interface ProductClient {

    /**
     * 检查产品是否删除
     * @param id 产品id
     * @param type 类型
     * @return 结果
     */
    @GetMapping("/internal/product/checkProductIsDelete")
    String checkProductIsDelete(@RequestParam String id, @RequestParam String type);

    /**
     * 根据产品ID获取产品信息
     * @param productId 产品id
     * @return 产品基础信息
     */
    @GetMapping("/internal/product/v1/product/getProductInfoByProductId")
    ProductBaseInfoVo getProductInfoByProductId(@RequestParam String productId);

    /**
     * 根据商品id修改商品是否下架
     * @param productId 产品id
     */
    @GetMapping("/internal/product/v1/product/updateProductIsEnable")
    void updateProductIsEnable(@RequestParam String productId);
}
