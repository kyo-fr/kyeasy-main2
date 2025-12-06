package org.ares.cloud.user.properties;

import lombok.Getter;
import lombok.Setter;
import org.ares.cloud.common.constant.interfaces.AresConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author hugo  tangxkwork@163.com
 * @description 超级管理员配置
 * @date 2024/01/17/23:35
 **/
@Component
@ConfigurationProperties(prefix = SuperAdminProperties.PREFIX)
public class SuperAdminProperties {
    /**
     * 配置前缀
     */
    public static final String PREFIX = AresConstant.PROPERTIES_PREFIX+".super-admin";
    /**
     * 用户名
     */
    private String nickname = "";
    /**
     * 账号
     */
    private String account = "";
    /**
     * 密码
     */
    private String password = "";

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
