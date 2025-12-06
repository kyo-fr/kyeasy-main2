package org.ares.cloud.database.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2024/9/30 17:15
 */
public class TenantEntity  extends BaseEntity {
    /**
     * 租户id
     */
    @TableField(fill = FieldFill.INSERT)
    protected String tenantId;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
