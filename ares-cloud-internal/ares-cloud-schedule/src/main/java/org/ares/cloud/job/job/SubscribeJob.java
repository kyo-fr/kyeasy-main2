package org.ares.cloud.job.job;

import com.alibaba.fastjson.JSONObject;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.ares.cloud.api.msg_center.NotificationServerClient;
import org.ares.cloud.api.msg_center.command.SendNotificationCommand;
import org.ares.cloud.api.msg_center.enums.NotificationType;
import org.ares.cloud.job.entity.PlatformApprovalEntity;
import org.ares.cloud.job.enums.SubscribeStatus;
import org.ares.cloud.job.mapper.MerchantSubscribeMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class SubscribeJob {


    @Resource
    private MerchantSubscribeMapper merchantSubscribeMapper;

    @Resource
    private NotificationServerClient notificationServerClient;


    /**
     * 订阅相关的任务
     * 商户端：
     *  1、订阅到期提醒
     *  2、存储不足300M提醒
     *  3、商品过期提醒
     * 管理端：
     *  1、订阅到期提醒
     *  2、存储不足300M提醒
     */


    /**
     * 订阅到期提醒 10分钟执行一次
     */
    @Transactional
    @Scheduled(initialDelay = 0, fixedRate = 10 * 60 * 1000) // 单位：毫秒
    public void subscribeExpiredJob() {
        log.info("订阅到期提醒 30分钟执行一次");
        //定时查询所有商户订阅数据 根据订阅类型为订阅且到期时间小于当前时间
        List<PlatformApprovalEntity> merchantSubscribes = merchantSubscribeMapper.getMerchantSubscribes();
        for (PlatformApprovalEntity merchantSubscribe : merchantSubscribes) {
            if (merchantSubscribe.getEndTime() <= System.currentTimeMillis()) {
                //更新订阅状态为过期
                int i = merchantSubscribeMapper.updateSubStatus(SubscribeStatus.OVER.getValue(), merchantSubscribe.getId());
                if (i > 0) {
                    //先查询下当前这个租户下的
                    //插入一条订阅过期的明细记录
                    //审批id
                    String id = merchantSubscribe.getId();
                    String tenantId = merchantSubscribe.getTenantId();
                    long usedCounts = merchantSubscribeMapper.selectTenantUsedCount(tenantId,id);
                    long sendCounts = merchantSubscribeMapper.selectTenantSendCount(tenantId,id);
                    long subCounts = sendCounts - usedCounts;
                    //计算出剩余的存储在生成一行审批存储记录表状态为over的
                    merchantSubscribeMapper.insertApprovalRecord(UUID.randomUUID().toString().replaceAll("-", ""),tenantId,id,subCounts,System.currentTimeMillis(),"定时过期存储","over");
                    try {
                        //发送订阅到期提醒
                        SendNotificationCommand sendNotificationCommand = new SendNotificationCommand();
                        sendNotificationCommand.setType(NotificationType.MERCHANT_SUBSCRIBE_EXPIRATION);
                        sendNotificationCommand.setReceiver(merchantSubscribe.getUserId());
                        sendNotificationCommand.setTitle("订阅到期");
                        sendNotificationCommand.setContent("订阅到期");
                        sendNotificationCommand.setData(null);
                        sendNotificationCommand.setTimestamp(System.currentTimeMillis());
                        log.info("发送订阅到期提醒数据：{}", JSONObject.toJSONString(sendNotificationCommand));
                        //提醒商户
                        notificationServerClient.sendNotification(sendNotificationCommand);
                        //提醒管理员
                        sendNotificationCommand.setReceiver("platform");
                        notificationServerClient.sendNotification(sendNotificationCommand);
                    } catch (Exception e) {
                        log.error("发送订阅到期提醒失败", e);
                    }
                }

            }
        }
    }


    /**
     * 订阅存储不足300M提醒
     */
    @Scheduled(initialDelay = 0, fixedRate = 30 * 60 * 1000) // 单位：毫秒
    public void subscribeInsufficientJob() {
        log.info("订阅存储不足300M提醒 30分钟执行一次");
        //查询商户数据总存储量

    }
}
