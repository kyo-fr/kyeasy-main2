package org.ares.cloud.service;

import org.ares.cloud.dto.OperateLogDTO;

import java.util.List;

/**
 * @author hugo
 * @version 1.0
 * @description: 日志接口
 * @date 2024/9/29 15:26
 */
public interface OperateLogService {
    /**
     * 保存日志
     * @param logs 日志对象
     */
    void saveLog(List<OperateLogDTO> logs);
}
