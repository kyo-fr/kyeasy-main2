package org.ares.cloud.common.query;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @Author hugo  tangxkwork@163.com
 * @description 公共查询参数
 * @date 2024/01/17/18:11
 **/
public class Query extends PageQuery{
    @Schema(description = "排序字段")
    String order;

    @Schema(description = "是否升序",defaultValue = "true")
    boolean asc =true;

    @Schema(description = "搜索关键字")
    @JsonProperty(value = "keyword")
    private String keyword;

    @Schema(description = "开始时间")
    @JsonProperty(value = "startTime")
    private Long startTime;

    @Schema(description = "结束时间")
    @JsonProperty(value = "endTime")
    private Long endTime;
    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public boolean isAsc() {
        return asc;
    }

    public void setAsc(boolean asc) {
        this.asc = asc;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }
}
