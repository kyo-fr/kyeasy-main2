package org.ares.cloud.platformInfo.enums;

/**
 * @author hugo
 * @version 1.0
 * @description: 用户身份
 * @date 2024/10/16 11:52
 */
public enum ApprovalType {
    OPEN_MERCHANT("open_merchant"), //开通商户
    ADD_MEMORY("add_memory") ;//增加存储

    private final String value;

    ApprovalType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
