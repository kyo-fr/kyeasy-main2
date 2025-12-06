package org.ares.cloud.file.service.impl;

import org.ares.cloud.common.utils.StringUtils;
import org.ares.cloud.file.constant.ContainerType;
import org.ares.cloud.file.domain.BasicFile;
import org.ares.cloud.file.properties.FileProperties;
import org.ares.cloud.file.properties.LocalProperties;
import org.ares.cloud.file.service.FileService;
import org.ares.cloud.file.service.FileStorageService;
import org.ares.cloud.file.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * @author hugo
 * @version 1.0
 * @description: 本地上传
 * @date 2024/10/11 20:23
 */
@Service
public class LocalStorageServiceImpl implements FileStorageService {
    private static final Logger log = LoggerFactory.getLogger(LocalStorageServiceImpl.class);
    @Autowired
    private LocalProperties localProperties;
    @Autowired
    private FileService fileService;

    @Override
    public String uploadFile(MultipartFile file) throws Exception {
        // 生成唯一文件名
        String fileName = FileUtils.generateCompletePath(file.getOriginalFilename());
        // 获取目标目录并检查目录是否存在
        String uploadDir = localProperties.getUploadDir();
        // 创建文件的目标路径
        File destFile = new File(uploadDir, fileName);
        // 如果目录不存在，创建目录
        if (!destFile.getParentFile().exists()) {
            boolean isDirCreated = destFile.getParentFile().mkdirs(); // 创建多层目录
            if (!isDirCreated) {
                throw new Exception("Failed to create directory: " + uploadDir);
            }
        }
        try (InputStream inputStream = file.getInputStream(); OutputStream outputStream = new FileOutputStream(destFile)) {

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

        } catch (IOException e) {
            throw new Exception("Failed to save file: " + fileName, e);
        }
        return  fileName;
    }

    @Override
    public boolean deleteFile(String fileName) {
        Path filePath = Paths.get(localProperties.getUploadDir()).resolve(fileName).normalize();
        File file = new File(filePath.toString());
        // 检查文件是否存在并且是文件
        if (!file.exists()) {
            log.error("文件不存在: {}", filePath);
            return false;
        }
        if (!file.isFile()) {
            log.error("路径不是一个文件: {}", filePath);
            return false;
        }
        // 尝试删除文件
        try {
            if (file.delete()) {
                return true;
            } else {
                log.error("文件删除失败: {}", filePath);
                return false;
            }
        } catch (SecurityException e) {
            log.error("没有权限删除文件: {}", filePath, e);
            return false;
        }
    }

    @Override
    public String getAccessUrl(String fileName) {
        return localProperties.getDomain() + "/" + fileName;
    }

    @Override
    public ResponseEntity<org.springframework.core.io.Resource> downloadFile(String fileName) {
        try {
            // 文件路径
            Path filePath = Paths.get(localProperties.getUploadDir()).resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            // 检查文件是否存在
            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }
            // 获取文件信息
            BasicFile fileInfo = fileService.getFileInfo(fileName);
            if (fileInfo != null && StringUtils.isNotBlank(fileInfo.getOriginalFileName())) {
                fileName = fileInfo.getOriginalFileName();
            }
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"").body(resource);
        } catch (Exception e) {
            log.error("download local file :{}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<org.springframework.core.io.Resource> previewFile(String fileName) {
        try {
            // 文件路径
            Path filePath = Paths.get(localProperties.getUploadDir()).resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            // 检查文件是否存在
            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            // 自动检测文件 MIME 类型
            String mimeType = Files.probeContentType(filePath);
            if (mimeType == null) {
                mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            }

            return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType)).body(resource);
        } catch (Exception e) {
            log.error("preview local file :{}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<org.springframework.core.io.Resource> streamFile(String fileName, String rangeHeader) {
        try {
            // 文件路径
            Path filePath = Paths.get(localProperties.getUploadDir()).resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            // 检查文件是否存在
            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            // 获取文件长度
            long fileLength = Files.size(filePath);

            if (rangeHeader == null || !rangeHeader.startsWith("bytes=")) {
                // 如果没有 Range 头部，返回整个文件
                return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).contentLength(fileLength).body(resource);
            }

            // 解析 Range 请求头
            String[] ranges = rangeHeader.substring("bytes=".length()).split("-");
            long rangeStart = Long.parseLong(ranges[0]);
            long rangeEnd = ranges.length > 1 ? Long.parseLong(ranges[1]) : fileLength - 1;
            long rangeLength = rangeEnd - rangeStart + 1;

            // 创建子文件流
            InputStream inputStream = Files.newInputStream(filePath);
            inputStream.skip(rangeStart);

            InputStreamResource inputStreamResource = new InputStreamResource(inputStream);

            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).contentType(MediaType.APPLICATION_OCTET_STREAM).header(HttpHeaders.CONTENT_RANGE, "bytes " + rangeStart + "-" + rangeEnd + "/" + fileLength).contentLength(rangeLength).body(inputStreamResource);

        } catch (Exception e) {
            log.error("stream local file :{}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public String getStorageName() {
        return ContainerType.local;
    }
}
