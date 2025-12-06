package org.ares.cloud.platformInfo.enums;

public enum SubscribeStatus {

    SUB("sub"), //订阅中
    OVER("over"),//订阅过期
    USED("used");//订阅过期

    private final String value;

    SubscribeStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
