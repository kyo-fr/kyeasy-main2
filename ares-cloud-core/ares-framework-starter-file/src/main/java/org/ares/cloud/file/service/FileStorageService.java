package org.ares.cloud.file.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author hugo
 * @version 1.0
 * @description: 文件存储
 * @date 2024/10/11 19:53
 */
public interface FileStorageService {
    /**
     * 上传文件
     * @param file 要上传的文件
     * @return  文件名
     * @throws Exception 上传过程中可能抛出的异常
     */
    String uploadFile(MultipartFile file) throws Exception;

    /**
     * 根据文件名删除文件
     * @param fileName 文件名
     */
    boolean deleteFile(String fileName);
    /**
     * 文件的完整地址
     * @param fileName 文件名
     * @return 完整地址
     */
    String getAccessUrl(String fileName);

    /**
     * 文件下载
     * @param fileName 文件名
     */
    ResponseEntity<Resource> downloadFile(String fileName);

    /**
     * 文件预览
     * @param fileName 文件名
     */
    ResponseEntity<Resource> previewFile(String fileName);

    /**
     * 分段传输
     * @param fileName 文件名
     */
    ResponseEntity<Resource> streamFile(String fileName,String rangeHeader);

    /**
     * 存储名称
     * @return 存储名称
     */
    String getStorageName();
}
