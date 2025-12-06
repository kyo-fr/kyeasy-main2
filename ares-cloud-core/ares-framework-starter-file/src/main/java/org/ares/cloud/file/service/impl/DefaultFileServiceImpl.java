package org.ares.cloud.file.service.impl;

import org.ares.cloud.file.domain.BasicFile;
import org.ares.cloud.file.service.FileService;
import org.ares.cloud.file.utils.FileUtils;

/**
 * @author hugo
 * @version 1.0
 * @description: 文件服务的默认实现
 * @date 2024/10/12 00:30
 */
public class DefaultFileServiceImpl implements FileService {
    @Override
    public BasicFile getFileInfo(String path) {
        BasicFile basicFile = new BasicFile();
        basicFile.setOriginalFileName(FileUtils.generateCompletePath(path));
        basicFile.setName(path);
        return basicFile;
    }

    @Override
    public void saveFile(BasicFile file) {

    }

    @Override
    public void deleteFile(String objectName) {
    }
}
