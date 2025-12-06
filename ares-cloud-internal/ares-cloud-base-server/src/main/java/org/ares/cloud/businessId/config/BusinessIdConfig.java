package org.ares.cloud.businessId.config;

import org.ares.cloud.businessId.convert.BusinessIdConvert;
import org.ares.cloud.businessId.convert.BusinessIdConvertImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 业务ID配置类
 * 确保转换器 Bean 能被正确创建和注入
 * 
 * @author ares-cloud
 * @version 1.0.0
 * @date 2024-10-01
 */
@Configuration
public class BusinessIdConfig {

    /**
     * 创建 BusinessIdConvert Bean
     * 手动创建转换器实例，确保依赖注入正常工作
     * 
     * @return BusinessIdConvert 实例
     */
    @Bean
    public BusinessIdConvert businessIdConvert() {
        return new BusinessIdConvertImpl();
    }
}
