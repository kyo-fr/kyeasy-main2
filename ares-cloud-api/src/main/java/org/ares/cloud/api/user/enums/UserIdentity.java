package org.ares.cloud.api.user.enums;

/**
 * @author hugo
 * @version 1.0
 * @description: 用户身份
 * @date 2024/10/16 11:52
 */
public enum UserIdentity {
    Unknown(0),
    NormalUser(1),
    Knight(2),
    Merchants(3),
    PlatformUsers(4);

    private final int value;

    UserIdentity(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static UserIdentity valueOf(int value) {
        return switch (value) {
            case 1 -> NormalUser;
            case 2 -> Knight;
            case 3 -> Merchants;
            case 4 -> PlatformUsers;
            default -> Unknown;
        };
    }

    public boolean equals(int value) {
        return this.value == value;
    }
}
