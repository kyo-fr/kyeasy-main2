package org.ares.cloud.file.properties;

import org.ares.cloud.common.constant.interfaces.AresConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author hugo
 * @version 1.0
 * @description: cos
 * @date 2024/10/11 19:58
 */
@Configuration
@ConfigurationProperties(prefix = CosProperties.PREFIX)
public class CosProperties {
    /**
     * 配置前缀
     */
    public static final String PREFIX = AresConstant.PROPERTIES_PREFIX+".file.tencent";
    /**
     *地区
     */
    private String region="";
    /**
     * 密钥id
     */
    private String secretId="";
    /**
     * 密钥
     */
    private String secretKey="";
    /**
     * 桶
     */
    private String bucketName="";

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSecretId() {
        return secretId;
    }

    public void setSecretId(String secretId) {
        this.secretId = secretId;
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
}
