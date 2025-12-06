package org.ares.cloud.properties;

import lombok.Data;
import org.ares.cloud.common.constant.interfaces.AresConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2024/10/10 17:00
 */
@Data
@Configuration
public class FirebaseProperties {
    /**
     * firebase_apikey
     */
    @Value("${ares.cloud.auth.firebase.apikey:firebase_apikey}")
    private String firebase_apikey;
    /**
     * baseUrl
     */
    @Value("${ares.cloud.auth.firebase.baseUrl:''}")
    private String baseUrl;

}
