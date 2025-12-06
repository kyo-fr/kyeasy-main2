package org.ares.cloud.file.properties;


import org.ares.cloud.common.constant.interfaces.AresConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author hugo
 * @version 1.0
 * @description: oss
 * @date 2024/10/11 19:57
 */

@Configuration
@ConfigurationProperties(prefix = OssProperties.PREFIX)
public class OssProperties {
    /**
     * 配置前缀
     */
    public static final String PREFIX = AresConstant.PROPERTIES_PREFIX+".file.aliyun";
    /**
     * 端点
     */
    private String endpoint = "";
    /**
     * key
     */
    private String accessKeyId = "";
    /**
     * 密钥
     */
    private String accessKeySecret = "";
    /**
     * 桶
     */
    private String bucketName = "";

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
}
