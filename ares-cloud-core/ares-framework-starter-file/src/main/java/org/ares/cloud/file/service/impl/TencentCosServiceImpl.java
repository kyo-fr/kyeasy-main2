package org.ares.cloud.file.service.impl;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.model.DeleteObjectRequest;
import com.qcloud.cos.model.GeneratePresignedUrlRequest;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;
import io.minio.MinioClient;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.ares.cloud.common.utils.StringUtils;
import org.ares.cloud.file.constant.ContainerType;
import org.ares.cloud.file.domain.BasicFile;
import org.ares.cloud.file.properties.CosProperties;
import org.ares.cloud.file.properties.FileProperties;
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

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

/**
 * @author hugo
 * @version 1.0
 * @description: 腾讯云
 * @date 2024/10/11 20:14
 */
@Service
public class TencentCosServiceImpl implements FileStorageService {
    private static final Logger log = LoggerFactory.getLogger(MinioServiceImpl.class);

    @Autowired
    private CosProperties cosProperties;
    @Autowired
    private FileService fileService;
    @Autowired
    private FileProperties fileProperties;
    private COSClient cosClient;

    @Override
    public String uploadFile(MultipartFile file) throws Exception {
        String fileName = FileUtils.generateCompletePath(file.getOriginalFilename());
        PutObjectRequest putObjectRequest = new PutObjectRequest(cosProperties.getBucketName(), fileName, file.getInputStream(), null);
        cosClient.putObject(putObjectRequest);
        return fileName;
    }

    @Override
    public boolean deleteFile(String fileName) {
        try {
            // 创建删除对象请求
            DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(cosProperties.getBucketName(), fileName);
            // 调用 COS 客户端删除对象
            cosClient.deleteObject(deleteObjectRequest);
            return true;
        } catch (CosClientException e) {
            log.error("删除文件失败: {}", fileName, e);
            return false;
        }
    }

    @Override
    public String getAccessUrl(String fileName) {
        return "https://" + cosProperties.getBucketName() + ".cos." + cosProperties.getRegion() + ".myqcloud.com/" + fileName;
    }

    @Override
    public ResponseEntity<Resource> downloadFile(String fileName) {
        try {
            // 获取腾讯云上的文件下载 URL
            String downloadUrl = generateDownloadUrl(fileName);

            // 从 URL 获取文件输入流
            InputStream inputStream = new URL(downloadUrl).openStream();
            Resource resource = new InputStreamResource(inputStream);

            // 获取文件元信息，比如文件大小
            URLConnection connection = new URL(downloadUrl).openConnection();
            long fileSize = connection.getContentLengthLong();
            // 获取文件信息
            BasicFile fileInfo = fileService.getFileInfo(fileName);
            if (fileInfo != null && StringUtils.isNotBlank(fileInfo.getOriginalFileName())) {
                fileName = fileInfo.getOriginalFileName();
            }
            // 返回文件的下载响应
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .contentLength(fileSize)
                    .body(resource);

        } catch (Exception e) {
            log.error("download cos err:{}",e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<Resource> previewFile(String fileName) {
        try {
            // 获取腾讯云上的文件预览 URL
            String previewUrl = generateDownloadUrl(fileName);

            // 获取文件流
            InputStream inputStream = new URL(previewUrl).openStream();
            Resource resource = new InputStreamResource(inputStream);

            // 自动检测文件 MIME 类型
            String mimeType = URLConnection.guessContentTypeFromName(fileName);
            if (mimeType == null) {
                mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            }

            // 获取文件大小
            URLConnection connection = new URL(previewUrl).openConnection();
            long fileSize = connection.getContentLengthLong();

            // 返回文件的预览响应
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(mimeType))
                    .contentLength(fileSize)
                    .body(resource);

        } catch (Exception e) {
            log.error("preview cos err:{}",e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<Resource> streamFile(String fileName, String rangeHeader) {
        try {
            // 获取腾讯云上的文件 URL
            String downloadUrl = generateDownloadUrl(fileName);

            // 获取文件元信息，比如文件大小
            URLConnection connection = new URL(downloadUrl).openConnection();
            long fileLength = connection.getContentLengthLong();

            // 判断是否有 Range 请求
            if (rangeHeader == null || !rangeHeader.startsWith("bytes=")) {
                // 无 Range 请求，返回整个文件
                InputStream inputStream = new URL(downloadUrl).openStream();
                Resource resource = new InputStreamResource(inputStream);

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

            // 获取文件的分段输入流
            URL partialUrl = new URL(downloadUrl + "&range=" + rangeStart + "-" + rangeEnd);
            InputStream partialStream = partialUrl.openStream();
            Resource resource = new InputStreamResource(partialStream);

            // 返回部分内容
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_RANGE, "bytes " + rangeStart + "-" + rangeEnd + "/" + fileLength)
                    .contentLength(rangeLength)
                    .body(resource);

        } catch (Exception e) {
            log.error("stream cos err:{}",e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public String getStorageName() {
        return ContainerType.tencent;
    }

    private String generateDownloadUrl(String fileName) {
        String bucketName = cosProperties.getBucketName();
        String region = cosProperties.getRegion(); // 例如 ap-shanghai
//        String domainOfBucket = String.format("https://%s.cos.%s.myqcloud.com", bucketName, region);
//
//        // 拼接文件的 URL
//        String fileUrl = String.format("%s/%s", domainOfBucket, fileName);

        // 如果是私有空间，生成带签名的 URL
        COSClient cosClient = new COSClient(new BasicCOSCredentials(cosProperties.getSecretId(), cosProperties.getSecretKey()), new ClientConfig(new Region(region)));
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, fileName, HttpMethodName.GET);
        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000L); // 有效期1小时
        request.setExpiration(expiration);
        URL signedUrl = cosClient.generatePresignedUrl(request);
        cosClient.shutdown();

        return signedUrl.toString();
    }


    @PostConstruct
    private void init(){
        if (cosClient == null && ContainerType.tencent.equals(fileProperties.getActive())) {
            COSCredentials cred = new BasicCOSCredentials(cosProperties.getSecretId(), cosProperties.getSecretKey());
            ClientConfig clientConfig = new ClientConfig(new Region(cosProperties.getRegion()));
            cosClient = new COSClient(cred, clientConfig);
        }
    }
    @PreDestroy
    public void close() {
        if (cosClient != null) {
            cosClient.shutdown();
        }
    }
}
