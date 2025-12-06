package org.ares.cloud.properties;

import lombok.Data;
import org.ares.cloud.common.constant.interfaces.AresConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2024/10/9 00:33
 */
@Data
@Configuration
@ConfigurationProperties(prefix = SecurityProperties.PREFIX)
public class SecurityProperties {
    /**
     * 配置前缀
     */
    public static final String PREFIX = AresConstant.PROPERTIES_PREFIX+".security";
    /**
     * 签名的key
     */
    private String key="your-32-characters-or-more-secret-key";
    /**
     * accessToken 过期时间(单位：秒)，默认2小时
     */
    private long accessTokenExpire = 60 * 60 * 24;
    //private long accessTokenExpire = 60;
    /**
     * refreshToken 过期时间(单位：秒)，默认14天
     */
    private long refreshTokenExpire = 60 * 60 * 24 * 30;
    /**
     * 签名过期时间(单位：秒)，默认5分钟
     */
    private long signExpire = 60 * 5;
}
