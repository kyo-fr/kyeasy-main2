package org.ares.cloud.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author hugo  tangxkwork@163.com
 * @description 配置
 * @date 2024/01/24/01:04
 **/
@Data
@ConfigurationProperties("generator")
public class GeneratorProperties {
    /**
     * 模板路径
     */
    private String template = "/template/";
}
