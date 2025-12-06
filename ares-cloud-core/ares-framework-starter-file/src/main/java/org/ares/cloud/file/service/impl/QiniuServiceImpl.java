package org.ares.cloud.file.service.impl;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import io.minio.MinioClient;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.ares.cloud.common.utils.StringUtils;
import org.ares.cloud.file.constant.ContainerType;
import org.ares.cloud.file.domain.BasicFile;
import org.ares.cloud.file.properties.FileProperties;
import org.ares.cloud.file.properties.QiniuProperties;
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
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author hugo
 * @version 1.0
 * @description: 七牛云实现
 * @date 2024/10/11 20:16
 */
@Service
public class QiniuServiceImpl implements FileStorageService {
    private static final Logger log = LoggerFactory.getLogger(QiniuServiceImpl.class);
    @Autowired
    private QiniuProperties qiniuProperties;
    @Autowired
    private FileService fileService;
    @Autowired
    private FileProperties fileProperties;
    private UploadManager uploadManager;
    private BucketManager bucketManager;
    @Override
    public String uploadFile(MultipartFile file) throws Exception {
        Auth auth = Auth.create(qiniuProperties.getAccessKey(), qiniuProperties.getSecretKey());
        String upToken = auth.uploadToken(qiniuProperties.getBucket());
        String fileName = FileUtils.generateCompletePath(file.getOriginalFilename());
        Response response = uploadManager.put(file.getBytes(), fileName, upToken);
        if (response.isOK()) {
            return fileName;
        } else {
            throw new RuntimeException("上传失败");
        }
    }

    @Override
    public boolean deleteFile(String fileName) {
        try {
            // 删除文件
            bucketManager.delete(qiniuProperties.getBucket(), fileName);
            return true;
        } catch (QiniuException e) {
            log.error("删除文件失败，错误码: {}", e.code(), e);
            return false;
        }
    }


    @Override
    public String getAccessUrl(String fileName) {
        return qiniuProperties.getDomain() + "/" + fileName;
    }

    @Override
    public ResponseEntity<Resource> downloadFile(String fileName) {
        try {
            // 获取七牛云上的文件下载URL，若是私有空间需要生成带签名的URL
            String downloadUrl = generateDownloadUrl(fileName);

            // 从远程URL获取文件输入流
            InputStream inputStream = new URL(downloadUrl).openStream();
            Resource resource = new InputStreamResource(inputStream);

            // 获取文件元信息，比如文件大小（可选）
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
            log.error("download qiniu file :{}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<Resource> previewFile(String fileName) {
        try {
            // 获取七牛云上的文件预览URL
            String previewUrl = generateDownloadUrl(fileName);

            // 从远程URL获取文件输入流
            InputStream inputStream = new URL(previewUrl).openStream();
            Resource resource = new InputStreamResource(inputStream);

            // 自动检测文件 MIME 类型
            String mimeType = URLConnection.guessContentTypeFromName(fileName);
            if (mimeType == null) {
                mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            }

            // 获取文件元信息，比如文件大小（可选）
            URLConnection connection = new URL(previewUrl).openConnection();
            long fileSize = connection.getContentLengthLong();

            // 返回文件的预览响应
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(mimeType))
                    .contentLength(fileSize)
                    .body(resource);

        } catch (Exception e) {
            log.error("download preview file :{}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<Resource> streamFile(String fileName, String rangeHeader) {
        try {
            // 获取七牛云上的文件预览URL
            String downloadUrl = generateDownloadUrl(fileName);

            // 获取文件元信息
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

            // 获取文件分段输入流
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
            log.error("download stream file :{}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public String getStorageName() {
        return ContainerType.qiniu;
    }

    private String generateDownloadUrl(String fileName) throws Exception {
        String domainOfBucket = qiniuProperties.getDomain();
        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
        String publicUrl = String.format("%s/%s", domainOfBucket, encodedFileName);

        // 如果是私有空间，生成带有签名的 URL
        Auth auth = Auth.create(qiniuProperties.getAccessKey(), qiniuProperties.getSecretKey());
        long expireInSeconds = 3600; // 1小时有效期
        return auth.privateDownloadUrl(publicUrl, expireInSeconds);
    }

    @PostConstruct
    private void init(){
        if(ContainerType.qiniu.equals(fileProperties.getActive())){
            if(uploadManager == null){
                Configuration cfg = new Configuration(Region.autoRegion());
                uploadManager = new UploadManager(cfg);
            }
            if (bucketManager == null) {
                Configuration cfg = new Configuration(Region.autoRegion());
                // 创建认证实例
                Auth auth = Auth.create(qiniuProperties.getAccessKey(), qiniuProperties.getSecretKey());
                bucketManager = new BucketManager(auth, cfg);
            }
        }
    }
}
