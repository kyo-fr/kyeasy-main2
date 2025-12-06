package org.ares.cloud.file.properties;


import org.ares.cloud.common.constant.interfaces.AresConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author hugo
 * @version 1.0
 * @description: minio
 * @date 2024/10/11 19:59
 */
@Configuration
@ConfigurationProperties(prefix = MinioProperties.PREFIX)
public class MinioProperties {
    /**
     * 配置前缀
     */
    public static final String PREFIX = AresConstant.PROPERTIES_PREFIX+".file.minio";
    /**
     * 接入端点
     */
    private String endpoint = "";
    /**
     * 认证的key
     */
    private String accessKey = "";
    /**
     * 认证的密钥
     */
    private String secretKey = "";
    /**
     * 桶的名称
     */
    private String bucketName = "";
    /**
     * 链接的有效时间
     */
    private int expiryIn = 60*60*24;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
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

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public int getExpiryIn() {
        return expiryIn;
    }

    public void setExpiryIn(int expiryIn) {
        this.expiryIn = expiryIn;
    }
}
