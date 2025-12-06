package org.ares.cloud.service.impl;

import cn.hutool.core.thread.ThreadUtil;

import jakarta.annotation.PostConstruct;
import org.ares.cloud.common.utils.ExceptionUtils;
import org.ares.cloud.common.utils.StaticMethodGetBean;
import org.ares.cloud.dto.OperateLogDTO;
import org.ares.cloud.redis.cache.RedisCache;
import org.ares.cloud.redis.cache.RedisKeys;
import org.ares.cloud.service.DefOperateLogService;
import org.ares.cloud.service.OperateLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Author hugo  tangxkwork@163.com
 * @description 操作日志服务
 * @date 2024/01/18/23:35
 **/
@Service
public class DefOperateLogServiceImpl implements DefOperateLogService {
    //日志
    private static final Logger log = LoggerFactory.getLogger(DefOperateLogServiceImpl.class);
    private final RedisCache redisCache;

    public DefOperateLogServiceImpl(RedisCache redisCache) {
        this.redisCache = redisCache;
    }

    /**
     * 保存日志
     * @param log 日志
     */
    @Async
    public void saveLog(OperateLogDTO log) {
        String key = RedisKeys.getLogKey();
        // 保存到Redis队列
        redisCache.leftPush(key, log, RedisCache.NOT_EXPIRE);
    }
    /**
     * 启动项目时，从Redis队列获取操作日志并保存
     */
    @PostConstruct
    public void saveLog() {
        ScheduledExecutorService scheduledService = ThreadUtil.createScheduledExecutor(1);
        //从容器中获取用户定义的日志服务
        OperateLogService logService = StaticMethodGetBean.getBean(OperateLogService.class);
        // 每隔10秒钟，执行一次
        scheduledService.scheduleWithFixedDelay(() -> {
            try {
                String key = RedisKeys.getLogKey();
                // 每次插入10条
                int count = 10;
                List<OperateLogDTO> dts = new ArrayList<>();
                for (int i = 0; i < count; i++) {
                    OperateLogDTO log = (OperateLogDTO) redisCache.rightPop(key);
                    if (log == null) {
                        return;
                    }
                    //没有实现则不进行保存
                    if (logService == null){
                        return;
                    }
                    dts.add(log);
                }
                logService.saveLog(dts);
            } catch (Exception e) {
                log.error("OperateLogServiceImpl.saveLog Error：{}", ExceptionUtils.getExceptionMessage(e));
            }
        }, 1, 10, TimeUnit.SECONDS);
    }
}
