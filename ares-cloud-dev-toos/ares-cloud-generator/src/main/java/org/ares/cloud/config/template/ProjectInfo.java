package org.ares.cloud.config.template;

import lombok.Data;
import org.ares.cloud.dto.CodeGenerator;

/**
 * @author hugo  tangxkwork@163.com
 * @description 项目信息
 * @date 2024/01/23/22:47
 **/
@Data
public class ProjectInfo {
    /**
     * 项目包名
     */
    private String packageName;
    /**
     * 模块名称
     */
    private String moduleName;
    /**
     * 项目版本号
     */
    private String version;
    /**
     * 后端生成路径
     */
    private String backendPath;
    /**
     * 前端生成路径
     */
    private String frontendPath;

}
