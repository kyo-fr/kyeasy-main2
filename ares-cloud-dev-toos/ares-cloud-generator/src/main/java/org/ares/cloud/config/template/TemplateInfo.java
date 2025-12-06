package org.ares.cloud.config.template;

import lombok.Data;

/**
 * @author hugo  tangxkwork@163.com
 * @description 模版信息
 * @date 2024/01/23/22:47
 **/
@Data
public class TemplateInfo {
    /**
     * 模板名称
     */
    private String templateName;
    /**
     * 模板内容
     */
    private String templateContent;
    /**
     * 生成代码的路径
     */
    private String generatorPath;
}
