package org.ares.cloud.config.template;

import lombok.Data;

import java.util.List;

/**
 * @author hugo  tangxkwork@163.com
 * @description 生成信息
 * @date 2024/01/23/22:46
 **/
@Data
public class GeneratorInfo {
    /**
     * 项目信息
     */
    private ProjectInfo project;
    /**
     * 开发者信息
     */
    private DeveloperInfo developer;
    /**
     * 模版信息
     */
    private List<TemplateInfo> templates;

}
