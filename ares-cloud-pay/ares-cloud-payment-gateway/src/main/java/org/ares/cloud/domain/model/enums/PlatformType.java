package org.ares.cloud.domain.model.enums;

/**
 * 平台类型枚举
 */
public enum PlatformType {
    
    /**
     * H5平台
     */
    H5("H5平台"),
    
    /**
     * PC平台
     */
    PC("PC平台"),
    
    /**
     * APP平台
     */
    APP("APP平台");
    
    private final String description;
    
    PlatformType(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
} 