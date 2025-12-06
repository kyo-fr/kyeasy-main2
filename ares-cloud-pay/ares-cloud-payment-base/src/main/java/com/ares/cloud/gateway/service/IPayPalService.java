package com.ares.cloud.gateway.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author hugo
 * @version 1.0.0
 * @description TODO paypal支付服务
 * @date 2022/10/20 11:16
 */
public interface IPayPalService {
    /**
     * 支付回调
     */
    void callback(JSONObject data);
}
