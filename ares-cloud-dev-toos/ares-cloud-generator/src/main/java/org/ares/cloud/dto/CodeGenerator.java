package org.ares.cloud.dto;

import lombok.Data;

import java.util.List;

/**
 * @author hugo
 * @version 1.0
 * @description: 代码生成请求实体
 * @date 2024/9/25 23:15
 */
@Data
public class CodeGenerator {
    /**
     * 墨香主键
     */
    private String id;
    /**
     * 作者
     */
    private String author;
    /**
     * 邮件
     */
    private String email;
    /**
     * 后台代码生成地址
     */
    private String backendPath;
    /**
     * 类名
     */
    private String className;
    /**
     * 基类ID
     */
    private String baseclassId;
    /**
     * 模块名
     */
    private String moduleName;
    /**
     * 方法名
     */
    private String functionName;
    /**
     * 包名
     */
    private String packageName;
    /**
     * 表名
     */
    private String tableName;
    /**
     * 描述
     */
    private String dis;
    /**
     * 需要的模版名称
     */
    private List<String> templateNames;
    /**
     * 控制器开放的接口
     */
    private List<String> apis;
    /**
     * 生成类型
     */
    private String genType;
    /**
     * 基础路由地址
     */
    private String baseApi;
    /**
     * 版本
     */
    private String version;
}
