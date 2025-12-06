package org.ares.cloud.file.properties;

import org.ares.cloud.common.constant.interfaces.AresConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2024/10/11 20:00
 */
@Configuration
@ConfigurationProperties(prefix = LocalProperties.PREFIX)
public class LocalProperties {
    /**
     * 配置前缀
     */
    public static final String PREFIX = AresConstant.PROPERTIES_PREFIX+".file.local";
    /**
     * 文件上传路径
     */
    private String uploadDir = "/ares/uploads";
    /**
     * 域名
     */
    private String domain = "http://localhost";

    public String getUploadDir() {
        return uploadDir;
    }
    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
    public String getDomain() {
        return domain;
    }
    public void setDomain(String domain) {
        this.domain = domain;
    }
}
