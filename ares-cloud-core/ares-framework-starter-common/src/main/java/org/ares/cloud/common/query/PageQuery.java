package org.ares.cloud.common.query;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2025/3/23 00:54
 */
public class PageQuery implements Serializable {
    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码最小值为 1")
    @Schema(description = "当前页码",type = "integer", format = "int32", requiredMode = Schema.RequiredMode.REQUIRED,defaultValue = "1")
    Integer page;

    @NotNull(message = "每页条数不能为空")
    @Schema(description = "每页条数",type = "integer", format = "int32", requiredMode = Schema.RequiredMode.REQUIRED,defaultValue = "10")
    Integer limit;
    public @NotNull(message = "页码不能为空") @Min(value = 1, message = "页码最小值为 1") Integer getPage() {
        return page;
    }

    public void setPage(@NotNull(message = "页码不能为空") @Min(value = 1, message = "页码最小值为 1") Integer page) {
        this.page = page;
    }

    public @NotNull(message = "每页条数不能为空") Integer getLimit() {
        return limit;
    }

    public void setLimit(@NotNull(message = "每页条数不能为空") Integer limit) {
        this.limit = limit;
    }
}
