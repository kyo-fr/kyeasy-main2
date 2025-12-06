package org.ares.cloud.file.service.impl;

import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.http.Method;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.ares.cloud.common.exception.BaseException;
import org.ares.cloud.common.utils.StringUtils;
import org.ares.cloud.file.constant.ContainerType;
import org.ares.cloud.file.domain.BasicFile;
import org.ares.cloud.file.properties.FileProperties;
import org.ares.cloud.file.properties.MinioProperties;
import org.ares.cloud.file.service.FileService;
import org.ares.cloud.file.service.FileStorageService;
import org.ares.cloud.file.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author hugo
 * @version 1.0
 * @description: minio
 * @date 2024/10/11 20:20
 */
@Service
public class MinioServiceImpl implements FileStorageService {
    private static final Logger log = LoggerFactory.getLogger(MinioServiceImpl.class);

    @Autowired
    private MinioProperties minioConfig;
    private MinioClient minioClient;
    @Autowired
    private FileService fileService;
    @Autowired
    private FileProperties fileProperties;
    @Override
    public String uploadFile(MultipartFile file) throws Exception {
        String fileName = FileUtils.generateCompletePath(file.getOriginalFilename());
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(minioConfig.getBucketName())
                .object(fileName)
                .stream(file.getInputStream(), file.getSize(), -1)
                .build());
        return fileName;
    }

    @Override
    public boolean deleteFile(String fileName) {
        try {
            // 删除文件
            minioClient.removeObject(
                    io.minio.RemoveObjectArgs.builder()
                            .bucket(minioConfig.getBucketName())
                            .object(fileName)
                            .build()
            );
            return true;
        } catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
            log.error("删除文件失败: {}", fileName, e);
            return false;
        }
    }

    @Override
    public String getAccessUrl(String fileName) {
        try {
            // 设置 URL 的有效期，单位为秒
            // 请求方法：GET 表示生成用于读取文件的 URL
            // 存储桶名称
            // 文件对象名称
            // 设置过期时间，单位为秒
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)  // 请求方法：GET 表示生成用于读取文件的 URL
                            .bucket(minioConfig.getBucketName())   // 存储桶名称
                            .object(fileName)   // 文件对象名称
                            .expiry(minioConfig.getExpiryIn() * 60) // 设置过期时间，单位为秒
                            .build()
            );
        } catch (Exception e) {
            throw new BaseException("生成临时文件访问 URL 失败", e);
        }
    }

    @Override
    public ResponseEntity<org.springframework.core.io.Resource> downloadFile(String fileName) {
        try {
            // 获取文件对象信息
            InputStream fileStream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(minioConfig.getBucketName())
                            .object(fileName)
                            .build()
            );
            long fileSize = minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(minioConfig.getBucketName())
                            .object(fileName)
                            .build()
            ).size();
            // 获取文件信息
            BasicFile fileInfo = fileService.getFileInfo(fileName);
            if (fileInfo != null && StringUtils.isNotBlank(fileInfo.getOriginalFileName())) {
                fileName = fileInfo.getOriginalFileName();
            }
            Resource resource = new InputStreamResource(fileStream);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .contentLength(fileSize)
                    .body(resource);
        } catch (Exception e) {
            log.error("download minio file :{}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<org.springframework.core.io.Resource> previewFile(String fileName) {
        try {
            // 获取文件对象信息
            InputStream fileStream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(minioConfig.getBucketName())
                            .object(fileName)
                            .build()
            );
            long fileSize = minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(minioConfig.getBucketName())
                            .object(fileName)
                            .build()
            ).size();

            // 自动检测文件 MIME 类型
            String mimeType = URLConnection.guessContentTypeFromName(fileName);
            if (mimeType == null) {
                mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            }

            Resource resource = new InputStreamResource(fileStream);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(mimeType))
                    .contentLength(fileSize)
                    .body(resource);
        } catch (Exception e) {
            log.error("preview minio file :{}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<org.springframework.core.io.Resource> streamFile(String fileName, String rangeHeader) {
        try {
            // 获取文件元信息
            StatObjectResponse stat = minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(minioConfig.getBucketName())
                            .object(fileName)
                            .build()
            );
            long fileLength = stat.size();

            if (rangeHeader == null || !rangeHeader.startsWith("bytes=")) {
                // 如果没有 Range 头部，返回整个文件
                InputStream fileStream = minioClient.getObject(
                        GetObjectArgs.builder()
                                .bucket(minioConfig.getBucketName())
                                .object(fileName)
                                .build()
                );
                Resource resource = new InputStreamResource(fileStream);

                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .contentLength(fileLength)
                        .body(resource);
            }

            // 解析 Range 请求头
            String[] ranges = rangeHeader.substring("bytes=".length()).split("-");
            long rangeStart = Long.parseLong(ranges[0]);
            long rangeEnd = ranges.length > 1 ? Long.parseLong(ranges[1]) : fileLength - 1;
            long rangeLength = rangeEnd - rangeStart + 1;

            // 使用 MinIO 的 SDK 获取部分对象
            InputStream partialStream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(minioConfig.getBucketName())
                            .object(fileName)
                            .offset(rangeStart)
                            .length(rangeLength)
                            .build()
            );

            Resource resource = new InputStreamResource(partialStream);

            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_RANGE, "bytes " + rangeStart + "-" + rangeEnd + "/" + fileLength)
                    .contentLength(rangeLength)
                    .body(resource);

        } catch (Exception e) {
            log.error("stream minio file :{}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public String getStorageName() {
        return ContainerType.minio;
    }

    @PostConstruct
    private void init(){
        if(minioClient == null && ContainerType.minio.equals(fileProperties.getActive())){
            minioClient =MinioClient.builder()
                    .endpoint(minioConfig.getEndpoint())
                    .credentials(minioConfig.getAccessKey(), minioConfig.getSecretKey())
                    .build();
        }
    }
    @PreDestroy
    public void close() {
        if (minioClient != null) {
            // 应用关闭时，释放资源
            try {
                minioClient.close();
            } catch (Exception e) {
                log.error("close minioClient error", e);
            }
        }
    }
}
