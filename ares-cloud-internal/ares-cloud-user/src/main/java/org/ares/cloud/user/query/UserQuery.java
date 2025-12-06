package org.ares.cloud.user.query;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.query.Query;


/**
* @author hugo tangxkwork@163.com
* @description 用户 查询原型
* @version 1.0.0
* @date 2024-10-07
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "用户查询")
public class UserQuery extends Query {

    @Schema(description = "用户身份(1:普通会员;2:骑士;3:商户;4:平台用户)",requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty(value = "identity")
    private Integer identity;

//    @Schema(description = "搜索关键字")
//    @JsonProperty(value = "keyword")
//    private String keyword;
//
//    @Schema(description = "开始时间")
//    @JsonProperty(value = "startTime")
//    private Long startTime;
//
//    @Schema(description = "结束时间")
//    @JsonProperty(value = "endTime")
//    private Long endTime;

    public Integer getIdentity() {
        return identity;
    }

    public void setIdentity(Integer identity) {
        this.identity = identity;
    }

//    public String getKeyword() {
//        return keyword;
//    }
//
//    public void setKeyword(String keyword) {
//        this.keyword = keyword;
//    }
//
//    public Long getStartTime() {
//        return startTime;
//    }
//
//    public void setStartTime(Long startTime) {
//        this.startTime = startTime;
//    }
//
//    public Long getEndTime() {
//        return endTime;
//    }
//
//    public void setEndTime(Long endTime) {
//        this.endTime = endTime;
//    }
}