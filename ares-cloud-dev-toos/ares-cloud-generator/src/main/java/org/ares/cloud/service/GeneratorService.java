package org.ares.cloud.service;

import org.ares.cloud.dto.CodeGenerator;

import java.util.zip.ZipOutputStream;
/**
 * @author hugo  tangxkwork@163.com
 * @description 代码生成服务
 * @date 2024/01/23/22:52
 **/
public interface GeneratorService {
    /**
     * 下载
     * @param modelId
     * @param zip
     */
    void downloadCode(String modelId, ZipOutputStream zip);

    void generatorCode(String modelId);

    /**
     * 模型生成待
     * @param req 生成的参数处理
     */
    void modelCode(CodeGenerator req);
    /**
     * 根据模型生成
     * @param req 生成的参数处理
     * @param zip 压缩包
     */
    void modelDownloadCode(CodeGenerator req, ZipOutputStream zip);
}
