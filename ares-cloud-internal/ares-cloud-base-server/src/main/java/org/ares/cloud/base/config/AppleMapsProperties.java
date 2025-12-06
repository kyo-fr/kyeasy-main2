package org.ares.cloud.base.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2025/8/9 12:53
 */
@Component
@ConfigurationProperties(prefix = "apple.maps")
@Data
public class AppleMapsProperties {
    /**
     * 苹果地图的配置信息
     */
    private String teamId;
    private String keyId;
    private String privateKeyPath;
    private String origin;
    private long expireSeconds;
}
