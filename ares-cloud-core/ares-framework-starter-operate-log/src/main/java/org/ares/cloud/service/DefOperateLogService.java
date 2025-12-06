package org.ares.cloud.service;

import org.ares.cloud.dto.OperateLogDTO;

/**
 * @author hugo
 * @version 1.0
 * @description: 日志接口
 * @date 2024/9/29 15:25
 */
public interface DefOperateLogService {
    /**
     * 保存日志
     * @param log 日志对象
     */
    void saveLog(OperateLogDTO log);
}
