package org.ares.cloud.api.user.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author hugo
 * @version 1.0
 * @description: 修改米密码
 * @date 2024/12/7 15:21
 */
@Data
public class ChangePasswordReq implements Serializable {
    /**
     * 用户id
     */
    private String userId;
    /**
     * 旧密码
     */
    private String oldPassword;
    /**
     * 新密码
     */
    private String newPassword;
    /**
     * 确认密码
     */
    private String confirmPassword;
}
