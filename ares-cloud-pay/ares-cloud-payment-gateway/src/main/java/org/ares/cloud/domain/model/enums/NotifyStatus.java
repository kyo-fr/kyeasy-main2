package org.ares.cloud.domain.model.enums;

/**
 * 通知状态枚举
 */
public enum NotifyStatus {
    
    /**
     * 待通知
     */
    PENDING("待通知"),
    
    /**
     * 通知成功
     */
    SUCCESS("通知成功"),
    
    /**
     * 通知失败
     */
    FAIL("通知失败");
    
    private final String description;
    
    NotifyStatus(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
} 