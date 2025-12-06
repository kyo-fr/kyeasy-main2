package org.ares.cloud.file.properties;

import org.ares.cloud.common.constant.interfaces.AresConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2024/10/11 20:46
 */
@Configuration
@ConfigurationProperties(prefix = FileProperties.PREFIX)
public class FileProperties {
    /**
     * 配置前缀
     */
    public static final String PREFIX = AresConstant.PROPERTIES_PREFIX+".file";
    /**
     * 活跃
     */
    private  String  active="local";
    /**
     * 是否开启默认的控制器
     */
    private  boolean  controllerEnabled = true;
    /**
     * 基础地址
     */
    private  String  base_path ="/api/ares/file";
    /**
     * 是否允许删除文件
     */
    private  boolean deletionAllowed = false;

    public boolean isDeletionAllowed() {
        return deletionAllowed;
    }

    public void setDeletionAllowed(boolean deletionAllowed) {
        this.deletionAllowed = deletionAllowed;
    }

    public String getBase_path() {
        return base_path;
    }

    public void setBase_path(String base_path) {
        this.base_path = base_path;
    }

    public boolean isControllerEnabled() {
        return controllerEnabled;
    }

    public void setControllerEnabled(boolean controllerEnabled) {
        this.controllerEnabled = controllerEnabled;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}
