package org.ares.cloud.file.domain;


import java.io.InputStream;

/**
 * @author hugo
 * @version 1.0
 * @description: 文件对象
 * @date 2024/10/11 23:28
 */
public class BasicFile {
    private String originalFileName;  // 原始文件名
    private String name; // 生成的文件名
    private String fileType;          // 文件类型（MIME 类型）
    private long fileSize;            // 文件大小（字节）
    private String container;  //存储容器名
    private InputStream fileInputStream; // 文件流

    // 构造方法
    public BasicFile(String originalFileName, String name, String fileType, long fileSize, InputStream fileInputStream) {
        this.originalFileName = originalFileName;
        this.name = name;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.fileInputStream = fileInputStream;
    }
    public BasicFile(){

    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public InputStream getFileInputStream() {
        return fileInputStream;
    }

    public void setFileInputStream(InputStream fileInputStream) {
        this.fileInputStream = fileInputStream;
    }
}
