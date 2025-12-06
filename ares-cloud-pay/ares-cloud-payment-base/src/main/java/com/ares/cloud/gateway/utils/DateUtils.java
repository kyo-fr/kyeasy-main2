package com.ares.cloud.gateway.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

/**
 * @author tangxk
 * @version 1.0
 * @Description
 * @package com.ares.cloud.common.utils.date
 * @reference com.ares.cloud.common.utils.date.DateUtils
 * @date 2021/01/19 16:38
 */
public class DateUtils {

    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String MINUTE_PATTERN = "yyyy-MM-dd HH:mm";
    public static final String HOUR_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String MONTH_PATTERN = "yyyy-MM";
    public static final String YEAR_PATTERN = "yyyy";
    public static final String MINUTE_ONLY_PATTERN = "mm";
    public static final String HOUR_ONLY_PATTERN = "HH";
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String HHMMSS = "HHmmss";

    /**
     * @param date
     * @param pattern
     * @return
     */
    public static String dateFormatStr(Date date, String pattern) {
        if (StringUtils.isEmpty(pattern)) {
            pattern = DateUtils.DATE_TIME_PATTERN;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 字符串转化为时间对象
     *
     * @param dateTimeString
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static Date strToDate(String dateTimeString, String pattern) throws ParseException {
        if (StringUtils.isEmpty(pattern)) {
            pattern = DateUtils.MINUTE_PATTERN;
        }

        if (StringUtils.isBlank(dateTimeString)) return null;

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = sdf.parse(dateTimeString);

        return date;
    }

    // 日期时间格式转化为日期对象
    public static Date dateTimeToDate(Date dateTime) throws Exception {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    /**
     * 小时数量相加
     *
     * @param dateStr
     * @param addHour
     * @return
     */
    public static Date dateAddHours(Date dateStr, int addHour) {

        if (null != dateStr) {
            System.out.println("传入的时间不能为空");
        }
        if (addHour == 0) {
            System.out.println("时间加数不能为0");
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateStr);
        calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + addHour);

        return calendar.getTime();
    }

    /**
     * 天数相加
     *
     * @param dateStr
     * @param days
     * @return
     */
    public static Date dateAddDay(Date dateStr, int days) {

        if (null != dateStr) {
            System.out.println("传入的时间不能为空");
        }

        if ( days == 0) {
            System.out.println("传入的天数不能为");
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateStr);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + days);
        return calendar.getTime();

    }

    /**
     * 分相加
     *
     * @param dateStr
     * @param minutes
     * @return
     */
    public static Date dateMinuteAdd(Date dateStr, int minutes) {

        if (null != dateStr) {
            System.out.println("传入的时间不能为空");
        }
        if (minutes == 0) {
            System.out.println("传入的加数不能为空");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateStr);
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + minutes);
        return calendar.getTime();
    }

    /**
     * 两个时间比较
     *
     * @param mydate
     * @param comparedate
     * @return
     */
    public static int dateCompare(Date mydate, Date comparedate) {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(mydate);
        calendar2.setTime(comparedate);

        return calendar1.compareTo(calendar2);

    }

    /**
     * 获取两个时间中比较小的一个
     *
     * @param date1
     * @param date2
     * @return
     */
    public static Date dateMin(Date date1, Date date2) {

        if (date1 == null) {
            return date2;
        }
        if (date2 == null) {
            return date1;
        }

        if (1 == DateUtils.dateCompare(date1, date2)) {
            return date2;
        } else if (-1 == DateUtils.dateCompare(date1, date2)) {
            return date1;
        }
        return date1;
    }

    /**
     * 获取两个时间中比较小的一个
     *
     * @param date1
     * @param date2
     * @return
     */
    public static Date dateMax(Date date1, Date date2) {

        if (date1 == null) {
            return date2;
        }
        if (date2 == null) {
            return date1;
        }

        if (1 == DateUtils.dateCompare(date1, date2)) {
            return date1;
        } else if (-1 == DateUtils.dateCompare(date1, date2)) {
            return date2;
        }
        return date1;
    }

    /**
     * 获取给定日期的年份
     *
     * @param date
     * @return
     */
    public static int getDateOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取给定日期的月份数
     *
     * @param date
     * @return
     */
    public static int getMonthOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH);
    }
    /**
     * 获取给定日期的天数
     *
     * @param date
     * @return
     */
    public static int getDayOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取日期时间当年的总天数，如201-09-12，返回2018年的总天数
     *
     * @param date
     * @return
     */
    public static int getDaysOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.getActualMaximum(Calendar.DAY_OF_YEAR);
    }

    /**
     * 获取开始时间
     * @param year
     * @param month
     * @return
     */
    public static Date getBeginTime(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate localDate = yearMonth.atDay(1);
        LocalDateTime startOfDay = localDate.atStartOfDay();
        ZonedDateTime zonedDateTime = startOfDay.atZone(ZoneId.of("Asia/Shanghai"));

        return Date.from(zonedDateTime.toInstant());
    }
    /**
     * 获取下一年的开始时间
     * @param year
     * @return
     */
    public static Date getNextYearBeginTime(int year) {
        return DateUtils.getBeginTime(year + 1, 1);
    }
    /**
     * 获取下一年的开始时间
     * @return
     */
    public static Date getNextYearBeginTime() {
        int dateOfYear = DateUtils.getDateOfYear(new Date());
        return getNextYearBeginTime(dateOfYear);
    }
    /**
     * 获取下一个月的开始时间
     * @return
     */
    public static Date getNextMonthBeginTime(int year,int month) {
        if(month < 1){
            //小于一获取一月的
            month = 1;
        }else if (month >= 12){
            year = year+1;
            month = 1;
        }
        return DateUtils.getBeginTime(year + 1, month);
    }
    /**
     * 获取下一个月的开始时间
     * @return
     */
    public static Date getNextMonthBeginTime() {
        Date data = new Date();
        //获取年
        int dateOfYear = DateUtils.getDateOfYear(data);
        //获取月
        int monthOfDate = DateUtils.getMonthOfDate(data);
        return  getNextMonthBeginTime(dateOfYear,monthOfDate);
    }
    /**
     * 获取下一个月的开始时间
     * @return
     */
    public static Date getNextDayBeginTime() {
        //通过日历
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DATE,+1);
        now.set(Calendar.HOUR_OF_DAY,0);
        now.set(Calendar.MINUTE,0);
        now.set(Calendar.SECOND,0);
        now.set(Calendar.MILLISECOND,0);
        return now.getTime();
    }
    /**
     * 获取传入的时间和当前时间的差
     * 单位为秒
     * @return
     */
    public static Long getNowDifference(Date date) {
        if (null == date) {
            return 0L;
        }
        //当前的
        long nowValue = System.currentTimeMillis()/1000;
        //传入的
        long value =  date.getTime()/ 1000;
        //差值
        return nowValue-value;
    }
    /**
     * 获取结束时间
     * @param year
     * @param month
     * @return
     */
    public static Date getEndTime(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate endOfMonth = yearMonth.atEndOfMonth();
        LocalDateTime localDateTime = endOfMonth.atTime(23, 59, 59, 999);
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Shanghai"));
        return Date.from(zonedDateTime.toInstant());
    }
    /**
     * 两个时间是否同年
     * @return
     */
    public static Boolean SameYear(Date date1,Date date2) {
        int daysOfYear1 = DateUtils.getDateOfYear(date1);
        int daysOfYear2 = DateUtils.getDateOfYear(date2);
        return daysOfYear1 == daysOfYear2;
    }
    /**
     * 两个时间是否同月
     * @return
     */
    public static Boolean SameMonth(Date date1,Date date2) {
       if(SameYear(date1,date2)){
           int monthOfDate1 = getMonthOfDate(date1);
           int monthOfDate2 = getMonthOfDate(date2);
           return  monthOfDate1==monthOfDate2;
       }
        return false;
    }
    /**
     * 两个时间是否同月
     * @return
     */
    public static Boolean SameDay(Date date1,Date date2) {
        if(SameMonth(date1,date2)){
            int dayOfDate1 = getDayOfDate(date1);
            int dayOfDate2 = getDayOfDate(date2);
            return  dayOfDate1==dayOfDate2;
        }
        return false;
    }
    /**
     * 和当前时间是否是同一年
     * @return
     */
    public static Boolean SameNowYear(Date date) {
        int daysOfYear1 = DateUtils.getDateOfYear(date);
        int daysOfYear2 = DateUtils.getDateOfYear(new Date());
        return daysOfYear1 == daysOfYear2;
    }
    /**
     * 两个时间是否同月
     * @return
     */
    public static Boolean SameNowMonth(Date date) {
        Date date1 = new Date();
        if(SameYear(date1,date)){
            int monthOfDate1 = getMonthOfDate(date1);
            int monthOfDate = getMonthOfDate(date);
            return  monthOfDate1==monthOfDate;
        }
        return false;
    }
    /**
     * 两个时间是否同月
     * @return
     */
    public static Boolean SameNowDay(Date date) {
        Date date1 = new Date();
        if(SameMonth(date1,date)){
            int dayOfDate1 = getDayOfDate(date1);
            int dayOfDate = getDayOfDate(date);
            return  dayOfDate1==dayOfDate;
        }
        return false;
    }
    /**
     * 获取当前格式化的时间
     * @param pattern
     * @return
     */
    public static String getNowDataFormat(String pattern ){
        return  dateFormatStr(new Date(),pattern);
    }
    /**
     * 判断给定时间段[startX,endX]是否与已知时间段存在交集,不存在返回true
     * @param startX 例：2020-02-18 00:00:00
     * @param endX   例：2020-04-18 24:00:00
     * @param originList 正序存储的时间段集合[{start:A0,end:A1},{start:B0,end:B1}]
     * @return true:表示有相交的，false:表示没有相交
     */
    public static boolean isNormal(String startX,String endX,List<Map<String, Object>> originList){
        if (!isEmpty(startX) && !isEmpty(endX)) {
            startX = formatDate(startX);
            endX   = formatDate(endX);
            if (originList != null && originList.size() > 0) {
                List<String> startList = new ArrayList<String>();
                List<String> endList = new ArrayList<String>();
                for (int i = 0; i < originList.size(); i++) {
                    startList.add(formatDate((String) originList.get(i).get("start")));
                    endList.add(formatDate((String) originList.get(i).get("end")));
                }
                String minStart = startList.get(0);
                String maxEnd   = endList.get(endList.size()-1);
                if (endX.compareTo(minStart) < 0) {
                    return true;
                }
                if (startX.compareTo(maxEnd) > 0) {
                    return true;
                }
                int minStartIndex = 0;
                if (startX.compareTo(minStart) > 0) {
                    if (startX.compareTo(endList.get(minStartIndex)) > 0) {// 即startX>A1
                        for (int i = minStartIndex; i < startList.size(); i++) {
                            if (startX.compareTo(startList.get(i)) < 0) {
                                if (endX.compareTo(startList.get(i)) < 0) {
                                    return true;
                                }
                            } else {
                                minStartIndex += 1;
                                continue;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    public static boolean isEmpty(String object) {
        if (object == null) {
            object = "";
        }
        return object.length() == 0;
    }
    public static String formatDate(String date) {
        if (date != null) {
            date = date.replace("-", "").replace(":", "").replace(" ", "");
        }else {
            date = "";
        }
        return date;
    }
}
