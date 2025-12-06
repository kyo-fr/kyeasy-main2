package com.ares.cloud.order.domain.model.valueobject;

import lombok.Data;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2025/3/24 20:02
 */
@Data
public class KnightInfo {
    /**
     * 骑士id
     */
    private String id;
    /**
     * 骑士姓名
     */
    private String name;
    /**
     * 骑士国家代码
     */
    private String countryCode;
    /**
     * 骑士电话
     */
    private String phone;
}
