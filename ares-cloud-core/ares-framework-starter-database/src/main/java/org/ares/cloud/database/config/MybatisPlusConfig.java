package org.ares.cloud.database.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import org.ares.cloud.database.handler.FieldMetaHandler;
import org.ares.cloud.database.handler.TenantHandler;
import org.ares.cloud.database.interceptor.DataScopeInnerInterceptor;
import org.ares.cloud.database.properties.MybatisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author hugo  tangxkwork@163.com
 * @description mybatis pilus配置
 * @date 2024/01/17/18:07
 **/
@Configuration
@EnableConfigurationProperties(MybatisProperties.class)
public class MybatisPlusConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(MybatisProperties properties) {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        // 数据权限
        mybatisPlusInterceptor.addInnerInterceptor(new DataScopeInnerInterceptor());
        // 租户插件
        mybatisPlusInterceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new TenantHandler(properties)));
        // 分页插件
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        // 乐观锁
        mybatisPlusInterceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        // 防止全表更新与删除
        mybatisPlusInterceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());

        return mybatisPlusInterceptor;
    }

    @Bean
    @Primary
    public FieldMetaHandler fieldMetaObjectHandler(){
        return new FieldMetaHandler();
    }
}
