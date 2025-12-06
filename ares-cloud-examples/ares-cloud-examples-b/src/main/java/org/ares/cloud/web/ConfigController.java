package org.ares.cloud.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.ares.cloud.properties.AresProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RefreshScope
@RestController
@EnableConfigurationProperties(AresProperties.class)
@Tag(name = "配置")
public class ConfigController {
//    @Value("${spring.datasource.url}")
//    private String datasourceUrl;
    @Resource
    private AresProperties aresProperties;

    @Operation(summary = "开放路由配置")
    @GetMapping("/config")
    public List<String> getConfig() {
        return this.aresProperties.getPublicPaths();
    }
}
