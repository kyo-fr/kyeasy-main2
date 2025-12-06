package org.ares.cloud.platformInfo.enums;

public enum ApprovalRecordType {
    SEND("send"), //发放
    OVER("over") ,//到期
    USED("used"); //扣除


    private final String value;

    ApprovalRecordType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
