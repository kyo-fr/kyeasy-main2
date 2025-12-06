package org.ares.cloud.config.template;

import cn.hutool.core.util.StrUtil;
import org.ares.cloud.common.exception.BusinessException;
import org.ares.cloud.common.utils.JsonUtils;
import org.ares.cloud.dto.CodeGenerator;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author hugo  tangxkwork@163.com
 * @description 代码生成内容配置
 * @date 2024/01/23/22:45
 **/
public class GeneratorConfig {
    private String template;

    public GeneratorConfig(String template) {
        this.template = template;
    }

    public GeneratorInfo getGeneratorConfig()  {
        // 模板路径，如果不是以/结尾，则添加/
        if (!StrUtil.endWith(template, '/')) {
            template = template + "/";
        }

        // 模板配置文件
        InputStream isConfig = this.getClass().getResourceAsStream(template + "config.json");
        if (isConfig == null) {
            throw new BusinessException("模板配置文件，config.json不存在");
        }

        try {
            // 读取模板配置文件
            String configContent = StreamUtils.copyToString(isConfig, StandardCharsets.UTF_8);
            GeneratorInfo generator = JsonUtils.parseObject(configContent, GeneratorInfo.class);
            assert generator != null;
            for (TemplateInfo templateInfo : generator.getTemplates()) {
                // 模板文件
                InputStream isTemplate = this.getClass().getResourceAsStream(template + templateInfo.getTemplateName());
                if (isTemplate == null) {
                    throw new BusinessException("模板文件 " + templateInfo.getTemplateName() + " 不存在");
                }
                // 读取模板内容
                String templateContent = StreamUtils.copyToString(isTemplate, StandardCharsets.UTF_8);

                templateInfo.setTemplateContent(templateContent);
            }
            return generator;
        } catch (IOException e) {
            throw new BusinessException("读取config.json配置文件失败");
        }
    }
}
