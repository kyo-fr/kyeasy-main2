package org.ares.cloud.file.config;

import org.ares.cloud.file.service.FileService;
import org.ares.cloud.file.service.impl.DefaultFileServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hugo
 * @version 1.0
 * @description: 文件配置
 * @date 2024/10/12 00:32
 */
@Configuration
public class FileConfig {
    /**
     * 默认file服务的实现
     * @return file服务
     */
    @Bean
    @ConditionalOnMissingBean(FileService.class)
    public FileService defaultFileService() {
        return new DefaultFileServiceImpl();
    }
}
