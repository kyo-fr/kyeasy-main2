package org.ares.cloud.file.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.annotation.Resource;
import org.ares.cloud.common.utils.StringUtils;
import org.ares.cloud.file.constant.ContainerType;
import org.ares.cloud.file.domain.BasicFile;
import org.ares.cloud.file.properties.FileProperties;
import org.ares.cloud.file.properties.OssProperties;
import org.ares.cloud.file.service.FileService;
import org.ares.cloud.file.service.FileStorageService;
import org.ares.cloud.file.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2024/10/11 19:54
 */
@Service
public class AliyunOssServiceImpl implements FileStorageService {
    private static final Logger log = LoggerFactory.getLogger(LocalStorageServiceImpl.class);
    @Resource
    private OssProperties ossProperties;
    @Resource
    private FileService fileService;
    @Resource
    private FileProperties fileProperties;
    private OSS ossClient;
    @Override
    public String uploadFile(MultipartFile file) throws Exception {
        // 初始化 OSS 客户端
        String fileName = FileUtils.generateCompletePath(file.getOriginalFilename());
        ossClient.putObject(ossProperties.getBucketName(), fileName, file.getInputStream());
        return fileName;
    }

    @Override
    public boolean deleteFile(String fileName) {
        try {
            ossClient.deleteObject(ossProperties.getBucketName(), fileName);
        } catch (Exception e) {
            log.error("delete oss file err:{}",e.getMessage(), e);
            return false;
        }
        return true;
    }

    @Override
    public String getAccessUrl(String fileName) {
        return ossClient.generatePresignedUrl(ossProperties.getBucketName(), fileName, new Date()).toString();
    }

    @Override
    public ResponseEntity<org.springframework.core.io.Resource> downloadFile(String fileName) {
        try {
            // 从OSS获取文件流
            OSSObject ossObject = ossClient.getObject(ossProperties.getBucketName(), fileName);
            InputStream inputStream = ossObject.getObjectContent();

            // 生成 InputStreamResource
            InputStreamResource resource = new InputStreamResource(inputStream);
            // 获取文件信息
            BasicFile fileInfo = fileService.getFileInfo(fileName);
            if (fileInfo != null && StringUtils.isNotBlank(fileInfo.getOriginalFileName())) {
                fileName = fileInfo.getOriginalFileName();
            }
            // 构建响应头
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
            headers.add(HttpHeaders.CONTENT_TYPE, ossObject.getObjectMetadata().getContentType());

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(ossObject.getObjectMetadata().getContentLength())
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<org.springframework.core.io.Resource> previewFile(String fileName) {
        try {
            // 从OSS获取文件流
            OSSObject ossObject = ossClient.getObject(ossProperties.getBucketName(), fileName);
            InputStream inputStream = ossObject.getObjectContent();

            // 生成 InputStreamResource
            InputStreamResource resource = new InputStreamResource(inputStream);

            // 构建响应头，设置为预览文件
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, ossObject.getObjectMetadata().getContentType());

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(ossObject.getObjectMetadata().getContentLength())
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<org.springframework.core.io.Resource> streamFile(String fileName,String rangeHeader) {
        // 获取 OSS 文件对象的元数据信息
        OSSObject ossObject = ossClient.getObject(ossProperties.getBucketName(), fileName);
        long fileSize = ossObject.getObjectMetadata().getContentLength();

        // 如果没有提供 Range 头，则返回整个文件
        if (rangeHeader == null) {
            InputStream inputStream = ossObject.getObjectContent();
            InputStreamResource resource = new InputStreamResource(inputStream);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(fileSize)
                    .body(resource);
        }

        // 处理分段请求
        HttpRange range = HttpRange.parseRanges(rangeHeader).get(0);
        long start = range.getRangeStart(fileSize);
        long end = range.getRangeEnd(fileSize);
        long rangeLength = end - start + 1;

        // 获取指定字节范围的对象
        GetObjectRequest getObjectRequest = new GetObjectRequest(ossProperties.getBucketName(), fileName)
                .withRange(start, end); // 设置分段范围

        OSSObject partialObject = ossClient.getObject(getObjectRequest);
        InputStream inputStream = partialObject.getObjectContent();
        InputStreamResource resource = new InputStreamResource(inputStream);

        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                .header(HttpHeaders.ACCEPT_RANGES, "bytes")
                .header(HttpHeaders.CONTENT_RANGE, "bytes " + start + "-" + end + "/" + fileSize)
                .contentLength(rangeLength)
                .body(resource);
    }

    @Override
    public String getStorageName() {
        return ContainerType.aliyun;
    }

    @PostConstruct
    private void init() {
        if (ossClient == null && ContainerType.aliyun.equals(fileProperties.getActive())) {
            ossClient = new OSSClientBuilder().build(ossProperties.getEndpoint(), ossProperties.getAccessKeyId(), ossProperties.getAccessKeySecret());
        }
    }

    @PreDestroy
    public void shutdown() {
        if (ossClient != null) {
            // 应用关闭时，释放资源
            ossClient.shutdown();
        }
    }
}
