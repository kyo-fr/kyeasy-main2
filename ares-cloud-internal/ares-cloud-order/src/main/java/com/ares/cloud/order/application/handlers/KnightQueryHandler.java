package com.ares.cloud.order.application.handlers;

import com.ares.cloud.order.application.dto.KnightStatistics;
import com.ares.cloud.order.application.dto.PaymentChannelStatistics;
import com.ares.cloud.order.application.query.KnightStatisticsQuery;
import com.ares.cloud.order.domain.enums.OrderError;
import com.ares.cloud.order.infrastructure.persistence.entity.KnightStatisticsResult;
import com.ares.cloud.order.infrastructure.persistence.entity.PaymentChannelStatisticsResult;
import com.ares.cloud.order.infrastructure.persistence.mapper.OrderMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.common.exception.BusinessException;
import org.ares.cloud.common.model.Money;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 骑士查询处理器
 * 处理所有骑士相关的查询操作
 * 
 * @author hugo
 * @version 1.0
 * @date 2025/10/27
 */
@Component
@Slf4j
public class KnightQueryHandler {

    @Resource
    private OrderMapper orderMapper;
    
    /**
     * 支付渠道名称映射
     */
    private static final Map<String, String> CHANNEL_NAMES = new HashMap<>();
    
    static {
        CHANNEL_NAMES.put("CREDIT_CARD", "信用卡");
        CHANNEL_NAMES.put("CASH", "现金");
        CHANNEL_NAMES.put("PAYPAL", "paypal");
        CHANNEL_NAMES.put("ALIPAY", "支付宝");
        CHANNEL_NAMES.put("WECHAT", "微信");
        CHANNEL_NAMES.put("CHECK", "支票");
        CHANNEL_NAMES.put("BANK_TRANSFER", "银行转帐");
        CHANNEL_NAMES.put("MEAL_CARD", "饭卡");
    }

    /**
     * 查询骑士统计信息
     */
    public KnightStatistics getKnightStatistics(KnightStatisticsQuery query) {
        try {
            // 验证查询参数
            validateStatisticsQuery(query);
            
            // 获取骑士ID（如果没有传入则使用当前登录用户）
            String knightId = query.getKnightId();
            if (!StringUtils.hasText(knightId)) {
                knightId = ApplicationContext.getUserId();
            }
            
            // 计算时间范围
            Long startTime = null;
            Long endTime = null;
            String dateRange = null;
            
            if ("DAY".equals(query.getQueryType())) {
                // 按天查询
                if (query.getStartTime() == null || query.getEndTime() == null) {
                    startTime = getTodayStartTime();
                    endTime = getTodayEndTime();
                } else {
                    startTime = query.getStartTime();
                    endTime = query.getEndTime();
                }
                dateRange = formatTime(startTime);
            } else if ("WEEK".equals(query.getQueryType())) {
                // 按周查询
                if (StringUtils.hasText(query.getQueryValue())) {
                    // 解析 yyyy-Www 格式
                    String[] parts = query.getQueryValue().split("-W");
                    if (parts.length == 2) {
                        int year = Integer.parseInt(parts[0]);
                        int week = Integer.parseInt(parts[1]);
                        startTime = getWeekStartTime(year, week);
                        endTime = getWeekEndTime(year, week);
                    }
                } else {
                    // 使用当前周
                    LocalDate now = LocalDate.now();
                    startTime = getWeekStartTime(now.getYear(), now.get(java.time.temporal.IsoFields.WEEK_OF_WEEK_BASED_YEAR));
                    endTime = getWeekEndTime(now.getYear(), now.get(java.time.temporal.IsoFields.WEEK_OF_WEEK_BASED_YEAR));
                }
                dateRange = formatTime(startTime) + " - " + formatTime(endTime);
            } else if ("MONTH".equals(query.getQueryType())) {
                // 按月查询
                if (StringUtils.hasText(query.getQueryValue())) {
                    String[] parts = query.getQueryValue().split("-");
                    if (parts.length == 2) {
                        int year = Integer.parseInt(parts[0]);
                        int month = Integer.parseInt(parts[1]);
                        startTime = getMonthStartTime(year, month);
                        endTime = getMonthEndTime(year, month);
                    }
                } else {
                    // 使用当前月份
                    LocalDate now = LocalDate.now();
                    startTime = getMonthStartTime(now.getYear(), now.getMonthValue());
                    endTime = getMonthEndTime(now.getYear(), now.getMonthValue());
                }
                dateRange = formatTime(startTime) + " - " + formatTime(endTime);
            } else if ("YEAR".equals(query.getQueryType())) {
                // 按年查询
                if (StringUtils.hasText(query.getQueryValue())) {
                    int year = Integer.parseInt(query.getQueryValue());
                    startTime = getYearStartTime(year);
                    endTime = getYearEndTime(year);
                    dateRange = String.valueOf(year);
                } else {
                    // 使用当前年份
                    int currentYear = LocalDate.now().getYear();
                    startTime = getYearStartTime(currentYear);
                    endTime = getYearEndTime(currentYear);
                    dateRange = String.valueOf(currentYear);
                }
            }
            
            // 查询基础统计数据
            KnightStatisticsResult result = orderMapper.getKnightStatistics(
                knightId, startTime, endTime, query.getPaymentRegion());
            
            // 查询按支付渠道分组的统计数据
            List<PaymentChannelStatisticsResult> channelResults = orderMapper.getPaymentChannelStatistics(
                knightId, startTime, endTime, query.getPaymentRegion());
            
            // 转换为DTO
            return convertToStatisticsDTO(result, channelResults, query.getPaymentRegion(), startTime, endTime, dateRange);
            
        } catch (Exception e) {
            log.error("查询骑士统计信息失败", e);
            throw new BusinessException(OrderError.SYSTEM_ERROR);
        }
    }
    
    /**
     * 验证统计查询参数
     */
    private void validateStatisticsQuery(KnightStatisticsQuery query) {
        if (query == null) {
            throw new BusinessException(OrderError.INVALID_PARAMETER);
        }
        if (!StringUtils.hasText(query.getQueryType())) {
            throw new BusinessException(OrderError.INVALID_PARAMETER);
        }
        if (!"DAY".equals(query.getQueryType()) 
            && !"WEEK".equals(query.getQueryType())
            && !"MONTH".equals(query.getQueryType()) 
            && !"YEAR".equals(query.getQueryType())) {
            throw new BusinessException(OrderError.INVALID_PARAMETER);
        }
    }
    
    /**
     * 转换为统计DTO
     */
    private KnightStatistics convertToStatisticsDTO(KnightStatisticsResult result,
                                                     List<PaymentChannelStatisticsResult> channelResults,
                                                     String paymentRegion,
                                                     Long startTime,
                                                     Long endTime,
                                                     String dateRange) {
        KnightStatistics dto = new KnightStatistics();
        
        // 设置时间范围
        dto.setStartTime(startTime);
        dto.setEndTime(endTime);
        dto.setPaymentRegion(paymentRegion);
        dto.setDateRange(dateRange);
        
        // 格式化时间字符串
        if (startTime != null) {
            dto.setStartTimeStr(formatTime(startTime));
        }
        if (endTime != null) {
            dto.setEndTimeStr(formatTime(endTime));
        }
        
        String currency = paymentRegion != null ? paymentRegion : "CNY";
        
        if (result == null) {
            // 如果没有数据，返回默认值
            dto.setIncome(Money.zero(currency, 2));
            dto.setOrderCount(0);
            dto.setOverdueOrderCount(0);
            dto.setOverdueTime("00:00:00");
            dto.setPaymentChannelStatistics(new ArrayList<>());
            return dto;
        }
        
        // 设置基础统计数据
        dto.setIncome(Money.create(result.getIncome() != null ? result.getIncome() : 0L, currency, 2));
        dto.setOrderCount(result.getOrderCount() != null ? result.getOrderCount() : 0);
        dto.setOverdueOrderCount(result.getOverdueOrderCount() != null ? result.getOverdueOrderCount() : 0);
        
        // 格式化超时时长
        if (result.getOverdueTotalSeconds() != null && result.getOverdueTotalSeconds() > 0) {
            dto.setOverdueTime(formatDuration(result.getOverdueTotalSeconds()));
        } else {
            dto.setOverdueTime("00:00:00");
        }
        
        // 转换按支付渠道分组的统计
        if (channelResults != null && !channelResults.isEmpty()) {
            List<PaymentChannelStatistics> channelStatsList = channelResults.stream()
                .map(channelResult -> convertToPaymentChannelStatistics(channelResult, currency))
                .collect(Collectors.toList());
            dto.setPaymentChannelStatistics(channelStatsList);
        } else {
            dto.setPaymentChannelStatistics(new ArrayList<>());
        }
        
        return dto;
    }
    
    /**
     * 转换支付渠道统计结果
     */
    private PaymentChannelStatistics convertToPaymentChannelStatistics(PaymentChannelStatisticsResult result, String currency) {
        PaymentChannelStatistics dto = new PaymentChannelStatistics();
        
        dto.setChannelId(result.getChannelId());
        dto.setChannelName(CHANNEL_NAMES.getOrDefault(result.getChannelId(), result.getChannelId()));
        dto.setAmount(Money.create(result.getAmount() != null ? result.getAmount() : 0L, currency, 2));
        dto.setOrderCount(result.getOrderCount() != null ? result.getOrderCount() : 0);
        
        return dto;
    }
    
    /**
     * 格式化时长为 HH:mm:ss 格式
     */
    private String formatDuration(Long seconds) {
        if (seconds == null || seconds <= 0) {
            return "00:00:00";
        }
        
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long secs = seconds % 60;
        
        return String.format("%02d:%02d:%02d", hours, minutes, secs);
    }
    
    /**
     * 获取月份开始时间戳
     */
    private Long getMonthStartTime(int year, int month) {
        LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0, 0);
        return start.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
    
    /**
     * 获取月份结束时间戳
     */
    private Long getMonthEndTime(int year, int month) {
        LocalDateTime end = LocalDateTime.of(year, month, 1, 0, 0, 0)
            .plusMonths(1).minusSeconds(1);
        return end.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
    
    /**
     * 获取年份开始时间戳
     */
    private Long getYearStartTime(int year) {
        LocalDateTime start = LocalDateTime.of(year, 1, 1, 0, 0, 0);
        return start.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
    
    /**
     * 获取年份结束时间戳
     */
    private Long getYearEndTime(int year) {
        LocalDateTime end = LocalDateTime.of(year, 12, 31, 23, 59, 59);
        return end.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
    
    /**
     * 获取周开始时间戳（ISO 8601标准，周一为一周的开始）
     */
    private Long getWeekStartTime(int year, int week) {
        LocalDate weekStart = LocalDate.of(year, 1, 1)
            .with(java.time.temporal.IsoFields.WEEK_OF_WEEK_BASED_YEAR, week)
            .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDateTime start = weekStart.atStartOfDay();
        return start.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
    
    /**
     * 获取周结束时间戳（ISO 8601标准，周日为一周的结束）
     */
    private Long getWeekEndTime(int year, int week) {
        LocalDate weekEnd = LocalDate.of(year, 1, 1)
            .with(java.time.temporal.IsoFields.WEEK_OF_WEEK_BASED_YEAR, week)
            .with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        LocalDateTime end = weekEnd.atTime(23, 59, 59);
        return end.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
    
    /**
     * 获取当天开始时间戳
     */
    private Long getTodayStartTime() {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        return start.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
    
    /**
     * 获取当天结束时间戳
     */
    private Long getTodayEndTime() {
        LocalDateTime end = LocalDate.now().atTime(23, 59, 59);
        return end.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
    
    /**
     * 格式化时间为 dd/MM/yyyy 格式
     */
    private String formatTime(Long timestamp) {
        if (timestamp == null) {
            return null;
        }
        java.time.Instant instant = java.time.Instant.ofEpochMilli(timestamp);
        LocalDate date = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formatter);
    }
}

