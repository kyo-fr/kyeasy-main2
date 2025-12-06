package org.ares.cloud.file.service;

import org.ares.cloud.file.domain.BasicFile;

/**
 * @author hugo
 * @version 1.0
 * @description: 文件服务
 * @date 2024/10/11 23:54
 */
public interface FileService {
    /**
     * 根据给到文件名
     * @param path 地址
     * @return 文件信息
     */
    BasicFile getFileInfo(String path);

    /**
     * 保存文件
     * @param file 文件信息
     */
    void  saveFile(BasicFile file);

    /**
     * 文件删除
     *
     * @param objectName 对象的名称
     */
    void deleteFile(String objectName);
}
