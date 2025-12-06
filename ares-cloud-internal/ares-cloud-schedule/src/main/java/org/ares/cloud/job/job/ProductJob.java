package org.ares.cloud.job.job;

import com.alibaba.fastjson.JSONObject;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.ares.cloud.api.msg_center.NotificationServerClient;
import org.ares.cloud.api.msg_center.command.SendNotificationCommand;
import org.ares.cloud.api.msg_center.enums.NotificationType;
import org.ares.cloud.job.entity.ProductBaseInfoEntity;
import org.ares.cloud.job.mapper.ProductMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
public class ProductJob {
    @Resource
    private NotificationServerClient notificationServerClient;

    @Resource
    private ProductMapper productMapper;


    //定时5分钟扫码商品24小时后过期数据
     @Scheduled(initialDelay = 30000, fixedRate = 60 * 60 * 1000)
    public void checkExpiringProducts() {
        log.info("开始扫描即将过期的商品...");
        // 1. 只查询即将在24小时内过期的商品，减少数据量
        LocalDateTime localDateNow = LocalDateTime.now();
        LocalDateTime localDateTimeThreshold = localDateNow.plusHours(24);
        //将now转换成时间戳
        Long now = localDateNow.toInstant(java.time.ZoneOffset.of("+8")).toEpochMilli();
        Long threshold = localDateTimeThreshold.toInstant(java.time.ZoneOffset.of("+8")).toEpochMilli();
         List<ProductBaseInfoEntity>      expiringProducts = productMapper.getProductsExpiringSoon("enable", now, threshold, 0);
        // 2. 批量处理过期商品
         if (!expiringProducts.isEmpty()){
             expiringProducts.forEach(product -> {
                log.info("商品ID：{}，名称：{} 即将在24小时内过期",
                        product.getId(), product.getName());
                SendNotificationCommand sendNotificationCommand = new SendNotificationCommand();
                sendNotificationCommand.setType(NotificationType.PRODUCT_OVERDUE);
                sendNotificationCommand.setReceiver(product.getTenantId());
                sendNotificationCommand.setTitle("商品即将过期");
                sendNotificationCommand.setContent("商品即将过期,请及时处理");
                sendNotificationCommand.setData(JSONObject.toJSONString(expiringProducts));
                sendNotificationCommand.setTimestamp(System.currentTimeMillis());
                log.info("发送商品即将过期提醒数据：{}", JSONObject.toJSONString(sendNotificationCommand));
                try {
                    //提醒商户
                    notificationServerClient.sendNotification(sendNotificationCommand);
                }catch (Exception e){
                    log.error("发送商品即将过期提醒数据异常：{}", e.getMessage());
                }
            });
        }
        log.info("扫描完成，共发现{}个即将过期的商品", expiringProducts.size());
    }


    //定时5分钟扫码商品库存不足 进行下架操作
    @Scheduled(initialDelay = 30000, fixedRate = 60 * 60 * 1000)
    public void checkInsufficientProducts() {
        log.info("开始扫描库存不足的商品...");
        try {
            List<ProductBaseInfoEntity> insufficientProducts = productMapper.getProductsInsufficient("enable", 0, 0);
            if (!insufficientProducts.isEmpty()) {
                insufficientProducts.forEach(product -> {
                    log.info("商品ID：{}，名称：{} 库存不足，进行下架操作",
                            product.getId(), product.getName());
                    try {
                        productMapper.updateProductByEnable("not_enable", product.getId());
                    } catch (Exception e) {
                        log.error("商品下架失败：{}", e.getMessage());
                    }
                });
            }
            log.info("库存检查完成，共处理{}个库存不足的商品", insufficientProducts.size());
        } catch (Exception e) {
            log.error("库存检查任务执行失败：{}", e.getMessage(), e);
        }
    }
}