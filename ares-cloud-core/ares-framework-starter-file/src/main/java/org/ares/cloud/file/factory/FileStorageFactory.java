package org.ares.cloud.file.factory;

import jakarta.annotation.Resource;
import org.ares.cloud.file.constant.ContainerType;
import org.ares.cloud.file.properties.FileProperties;
import org.ares.cloud.file.service.FileStorageService;
import org.ares.cloud.file.service.impl.*;
import org.springframework.stereotype.Service;

/**
 * @author hugo
 * @version 1.0
 * @description: 文件上传工具
 * @date 2024/10/11 20:46
 */
@Service
public class FileStorageFactory {
    @Resource
    private AliyunOssServiceImpl aliyunOssService;
    @Resource
    private TencentCosServiceImpl tencentCosService;
    @Resource
    private QiniuServiceImpl qiniuService;
    @Resource
    private MinioServiceImpl minioService;
    @Resource
    private LocalStorageServiceImpl localStorageService;
    @Resource
    private FileProperties properties;

    /**
     * 获取存储服务
     *
     * @return 存储服务
     */
    public FileStorageService getFileStorageService() {
        return switch (properties.getActive()) {
            case ContainerType.aliyun -> aliyunOssService;
            case ContainerType.tencent -> tencentCosService;
            case ContainerType.qiniu -> qiniuService;
            case ContainerType.minio -> minioService;
            default -> localStorageService;
        };
    }
}
