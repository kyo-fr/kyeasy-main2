package org.ares.cloud.database.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fhs.core.trans.vo.TransPojo;

/**
 * @author hugo  tangxkwork@163.com
 * @description Entity基类
 * @date 2024/01/17/15:56
 **/

public class BaseEntity  {
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    protected String id;

    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT)
    protected String  creator;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    protected Long createTime;

    /**
     * 更新者
     */
    @TableField(fill = FieldFill.UPDATE)
    protected String  updater;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    protected Long updateTime;

    /**
     * 版本号
     */
    @Version
    @TableField(fill = FieldFill.INSERT)
    protected Integer version;

    /**
     * 删除标记
     */
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    protected Integer deleted;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}


