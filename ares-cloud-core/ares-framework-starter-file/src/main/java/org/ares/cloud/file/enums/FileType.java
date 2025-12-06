package org.ares.cloud.file.enums;

/**
 * @author ares-cloud
 * @description 文件类型枚举
 * @date 2025-01-24
 */
public enum FileType {
    /**
     * 图片文件
     */
    IMAGE("img", "图片"),
    
    /**
     * 视频文件
     */
    VIDEO("video", "视频"),
    
    /**
     * 其他文件
     */
    OTHER("other", "其他");

    private final String code;
    private final String description;

    FileType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    /**
     * 根据MIME类型判断文件类型
     * @param mimeType MIME类型
     * @return 文件类型
     */
    public static FileType fromMimeType(String mimeType) {
        if (mimeType == null) {
            return OTHER;
        }
        
        String lowerMimeType = mimeType.toLowerCase();
        
        if (lowerMimeType.startsWith("image/")) {
            return IMAGE;
        } else if (lowerMimeType.startsWith("video/")) {
            return VIDEO;
        } else {
            return OTHER;
        }
    }

    /**
     * 根据文件扩展名判断文件类型
     * @param fileName 文件名
     * @return 文件类型
     */
    public static FileType fromFileName(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return OTHER;
        }
        
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        
        // 图片扩展名
        if ("jpg".equals(extension) || "jpeg".equals(extension) || "png".equals(extension) || 
            "gif".equals(extension) || "bmp".equals(extension) || "webp".equals(extension) ||
            "svg".equals(extension) || "ico".equals(extension)) {
            return IMAGE;
        }
        
        // 视频扩展名
        if ("mp4".equals(extension) || "avi".equals(extension) || "mov".equals(extension) ||
            "wmv".equals(extension) || "flv".equals(extension) || "webm".equals(extension) ||
            "mkv".equals(extension) || "3gp".equals(extension)) {
            return VIDEO;
        }
        
        return OTHER;
    }
}
