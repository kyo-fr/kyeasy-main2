package org.ares.cloud.file.properties;

import org.ares.cloud.common.constant.interfaces.AresConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author hugo
 * @version 1.0
 * @description: 七牛云
 * @date 2024/10/11 19:58
 */
@Configuration
@ConfigurationProperties(prefix = QiniuProperties.PREFIX)
public class QiniuProperties {
    /**
     * 配置前缀
     */
    public static final String PREFIX = AresConstant.PROPERTIES_PREFIX+".file.qiniu";
    /**
     * 域名
     */
    private String domain = "";
    /**
     * 认证key
     */
    private String accessKey = "";
    /**
     * 认证的密钥
     */
    private String secretKey = "";
    /**
     * 桶
     */
    private String bucket = "";

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }
}
