package org.ares.cloud.api.product.fallback;

import org.ares.cloud.api.product.ProductClient;
import org.ares.cloud.api.product.dto.ProductBaseInfoVo;
import org.ares.cloud.exception.ServiceUnavailableException;
import org.springframework.stereotype.Component;

/**
 * @author hugo
 * @description: ProductClient 的降级处理
 * 当产品服务不可用时抛出 ServiceUnavailableException
 */
@Component
public class ProductClientFallback implements ProductClient {

    private static final String SERVICE_NAME = "product-service";

    @Override
    public String checkProductIsDelete(String id, String type) {
        throw new ServiceUnavailableException(SERVICE_NAME, "checkProductIsDelete");
    }

    @Override
    public ProductBaseInfoVo getProductInfoByProductId(String productId) {
        throw new ServiceUnavailableException(SERVICE_NAME, "getProductInfoByProductId");
    }

    @Override
    public void updateProductIsEnable(String productId) {
        throw new ServiceUnavailableException(SERVICE_NAME, "updateProductIsEnable");
    }
}
