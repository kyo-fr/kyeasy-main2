package com.ares.cloud.order.infrastructure.service;

import com.ares.cloud.order.domain.enums.OrderError;
import com.ares.cloud.order.domain.enums.OrderType;
import com.ares.cloud.order.domain.service.OrderCodeGenerator;
import org.ares.cloud.common.exception.BusinessException;
import org.ares.cloud.common.exception.RequestBadException;
import org.ares.cloud.common.utils.DateUtils;
import org.ares.cloud.redis.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 订单编号生成器实现
 * 根据不同订单类型生成不同格式的订单编号
 */
@Service
public class OrderCodeGeneratorImpl implements OrderCodeGenerator {
    
    private static final Logger log = LoggerFactory.getLogger(OrderCodeGeneratorImpl.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HHmmss");
    private static final String ORDER_CODE_KEY_PREFIX = "order:code:";
    
    @Autowired
    private RedisUtil redisUtil;
    
    @Override
    public String generateOrderCode(OrderType orderType, String merchantId) {
        try {
            // 获取当前日期
            LocalDateTime now = LocalDateTime.now();
            String dateStr = now.format(DATE_FORMATTER);
            
            // 根据订单类型生成不同前缀的订单编号
            String prefix = switch (orderType) {
                case DINE_IN -> "D";
                case DELIVERY -> "L";
                case PICKUP -> "E";
                default -> "";
            };
            if (prefix.isEmpty()) {
                return null;
            }
            // 生成Redis键，格式为：order:code:merchantId:orderType:date
            String redisKey = ORDER_CODE_KEY_PREFIX + merchantId + ":" + orderType.name() + ":" + dateStr;
            
            // 获取并递增计数器
            long sequence = redisUtil.incrOne(redisKey);
            
            // 设置过期时间（2天后过期，确保跨天也能正常使用）
            redisUtil.expire(redisKey, 48 * 60 * 60);
            
            // 格式化序列号为4位，不足前面补0
            String sequenceStr = String.format("%04d", sequence);
            
            // 拼接订单编号：类型前缀 + 序列号
            return prefix +  sequenceStr;
        } catch (Exception e) {
            log.error("生成订单编号异常", e);
            // 发生异常时，使用备用方案生成编号
            throw new BusinessException(OrderError.ORDER_CHECK_FAILED);
        }
    }


    @Override
    public String generateOrderNumber(String merchantId) {
        // 获取当前日期，格式为yyyyMMdd
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DateUtils.YYYYMMDD));

        // 获取商户ID的后4位，如果商户ID长度不足4位，则用0补齐
        String merchantSuffix = merchantId.length() > 4 ?
                merchantId.substring(merchantId.length() - 4) :
                String.format("%04d", Integer.parseInt(merchantId));

        // 生成6位随机数
        int randomNum = ThreadLocalRandom.current().nextInt(100000, 1000000);

        // 拼接订单号
        return dateStr + merchantSuffix + randomNum;
    }
}