package org.ares.cloud.common.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * @Author hugo  tangxkwork@163.com
 * @description 时间工具
 * @date 2024/01/20/19:56
 **/
public class DateUtils {

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static String YYYYMMDD = "yyyyMMdd";
    /** 时间格式(yyyy-MM-dd) */
    public final static String DATE_PATTERN = "yyyy-MM-dd";
    /** 时间格式(yyyy-MM-dd HH:mm:ss) */
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    // UTC时区ID
    private static final String UTC_ZONE_ID = "UTC";
    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     * @param date  日期
     * @return  返回yyyy-MM-dd格式日期
     */
    public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     * @param date  日期
     * @param pattern  格式，如：DateUtils.DATE_TIME_PATTERN
     * @return  返回yyyy-MM-dd格式日期
     */
    public static String format(Date date, String pattern) {
        if(date != null){
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    /**
     * 日期解析
     * @param date  日期
     * @param pattern  格式，如：DateUtils.DATE_TIME_PATTERN
     * @return  返回Date
     */
    public static Date parse(String date, String pattern) {
        try {
            return new SimpleDateFormat(pattern).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    public static final String dateTimeNow() {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format) {
        return parseDateToStr(format, new Date());
    }

    public static final String parseDateToStr(final String format, final Date date) {
        return new SimpleDateFormat(format).format(date);
    }


    /**
     * 获取UTC零时区的当前时间戳
     *
     * @return UTC时间戳(毫秒)
     */
    public static long getCurrentTimestampInUTC() {
        return ZonedDateTime.now(ZoneId.of(UTC_ZONE_ID))
                .toInstant()
                .toEpochMilli();
    }
    /**
     * 将UTC时间戳转换为指定时区的时间戳
     *
     * @param utcTimestamp UTC时间戳(毫秒)
     * @param timezone 目标时区ID(如: Asia/Shanghai)
     * @return 目标时区的时间戳(毫秒)
     */
    public static long convertUtcToTimezone(long utcTimestamp, String timezone) {
        if (StringUtils.isBlank(timezone)) {
            return utcTimestamp;
        }
        // 如果是 10 位（秒级时间戳），转换为 13 位（毫秒级时间戳）
        if (String.valueOf(utcTimestamp).length() == 10) {
            utcTimestamp *= 1000;
        }
        // 创建UTC时间
        ZonedDateTime utcTime = Instant.ofEpochMilli(utcTimestamp)
                .atZone(ZoneId.of(UTC_ZONE_ID));

        // 转换到目标时区
        ZonedDateTime targetTime = utcTime.withZoneSameInstant(ZoneId.of(timezone));

        return targetTime.toInstant().toEpochMilli();
    }
    /**
     * 将指定时区的时间戳转换为UTC时间戳
     *
     * @param timestamp 源时间戳(毫秒)
     * @param timezone 源时区ID(如: Asia/Shanghai)
     * @return UTC时间戳(毫秒)
     */
    public static long convertTimezoneToUtc(long timestamp, String timezone) {
        // 如果是 10 位（秒级时间戳），转换为 13 位（毫秒级时间戳）
        if (String.valueOf(timestamp).length() == 10) {
            timestamp *= 1000;
        }
        // 创建源时区时间
        ZonedDateTime sourceTime = Instant.ofEpochMilli(timestamp)
                .atZone(ZoneId.of(timezone));

        // 转换到UTC时区
        ZonedDateTime utcTime = sourceTime.withZoneSameInstant(ZoneId.of(UTC_ZONE_ID));

        return utcTime.toInstant().toEpochMilli();
    }
    /**
     * 获取指定时区的当天时间范围
     *
     * @param timezone 时区ID(如: Asia/Shanghai)
     * @return 时间范围对象，包含起始时间和结束时间
     */
    public static TimeRange getDailyTimeRange(String timezone) {
        LocalDate today = LocalDate.now(ZoneId.of(timezone));
        long startTime = today.atStartOfDay(ZoneId.of(timezone)).toInstant().toEpochMilli();
        long endTime = today.plusDays(1).atStartOfDay(ZoneId.of(timezone)).toInstant().toEpochMilli();
        return new TimeRange(startTime, endTime);
    }

    public static class TimeRange {
        private final long startTime;
        private final long endTime;

        public TimeRange(long startTime, long endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public long getStartTime() {
            return startTime;
        }

        public long getEndTime() {
            return endTime;
        }
    }
}
