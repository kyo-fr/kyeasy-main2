package org.ares.cloud.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import org.ares.cloud.common.deserializer.CustomDateDeserializer;
import org.ares.cloud.common.serializer.CustomDateSerializer;

import java.io.Serializable;

/**
 * @Author hugo  tangxkwork@163.com
 * @description 基础数据转换类
 * @date 2024/01/20/19:53
 **/
public class BaseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Schema(description = "主键")
    @JsonProperty(value = "id")
    protected String id;

    @Schema(description = "创建者",hidden = true)
    //@JsonProperty("creator")
    protected String creator;
    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    protected Long createTime;

    @Schema(description = "更新者",hidden = true)
    //@JsonProperty("updater")
    protected String updater;
    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    protected Long updateTime;


    @Schema(description = "版本号",hidden = true)
    //@JsonProperty("version")
    protected Integer version;

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
}
