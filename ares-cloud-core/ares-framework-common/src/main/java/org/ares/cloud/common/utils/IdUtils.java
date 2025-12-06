package org.ares.cloud.common.utils;

import cn.hutool.core.lang.UUID;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author hugo
 * @version 1.0
 * @description: ID生成器工具类
 * @date 2024/9/29 15:40
 */
public class IdUtils {
    // 起始的时间戳
    private final static long START_STMP = 1480166465631L;

    // 每一部分占用的位数
    private final static long SEQUENCE_BIT = 12; // 序列号占用的位数
    private final static long MACHINE_BIT = 5;   // 机器标识占用的位数
    private final static long DATACENTER_BIT = 5;// 数据中心占用的位数

    // 每一部分的最大值
    private final static long MAX_DATACENTER_NUM = -1L ^ (-1L << DATACENTER_BIT);
    private final static long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT);
    private final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);

    // 每一部分向左的位移
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIMESTMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

    private long datacenterId;  // 数据中心
    private long machineId;     // 机器标识
    private long sequence = 0L; // 序列号
    private long lastStmp = -1L;// 上一次时间戳

    // ==================== 16位短雪花ID生成器相关 ====================
    
    /**
     * 机器ID（0-99），根据IP地址最后一位生成
     */
    private static final int SHORT_SNOWFLAKE_MACHINE_ID;
    
    /**
     * 序列号（0-9999）
     */
    private static int shortSnowflakeSequence = 0;
    
    /**
     * 上次生成ID的时间戳（秒级）
     */
    private static long shortSnowflakeLastTimestamp = -1L;
    
    static {
        // 根据IP地址生成机器ID
        int machineId = 12; // 默认值
        try {
            InetAddress ip = InetAddress.getLocalHost();
            byte[] ipAddress = ip.getAddress();
            // 使用IP地址最后一位对100取模
            machineId = Math.abs(ipAddress[ipAddress.length - 1]) % 100;
        } catch (Exception e) {
            // 如果获取IP失败，使用默认值
        }
        SHORT_SNOWFLAKE_MACHINE_ID = machineId;
    }

    public void SnowflakeIdGenerator(long datacenterId, long machineId) {
        if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
            throw new IllegalArgumentException("datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }
        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }

    // 产生下一个ID
    public synchronized long nextId() {
        long currStmp = getNewstmp();
        if (currStmp < lastStmp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }

        if (currStmp == lastStmp) {
            // 相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            // 同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currStmp = getNextMill();
            }
        } else {
            // 不同毫秒内，序列号置为0
            sequence = 0L;
        }

        lastStmp = currStmp;

        return (currStmp - START_STMP) << TIMESTMP_LEFT // 时间戳部分
                | datacenterId << DATACENTER_LEFT       // 数据中心部分
                | machineId << MACHINE_LEFT             // 机器标识部分
                | sequence;                             // 序列号部分
    }

    private long getNextMill() {
        long mill = getNewstmp();
        while (mill <= lastStmp) {
            mill = getNewstmp();
        }
        return mill;
    }

    private long getNewstmp() {
        return System.currentTimeMillis();
    }

    /**
     * 获取随机UUID
     *
     * @return 随机UUID
     */
    public static String randomUUID()
    {
        return UUID.randomUUID().toString();
    }

    /**
     * 简化的UUID，去掉了横线
     *
     * @return 简化的UUID，去掉了横线
     */
    public static String simpleUUID()
    {
        return UUID.randomUUID().toString(true);
    }

    /**
     * 获取随机UUID，使用性能更好的ThreadLocalRandom生成UUID
     *
     * @return 随机UUID
     */
    public static String fastUUID()
    {
        return UUID.fastUUID().toString();
    }

    /**
     * 简化的UUID，去掉了横线，使用性能更好的ThreadLocalRandom生成UUID
     *
     * @return 简化的UUID，去掉了横线
     */
    public static String fastSimpleUUID()
    {
        return UUID.fastUUID().toString(true);
    }

    /**
     * 生产订单号
     *
     * @return
     */
    public static String getOrderNo() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = dateFormat.format(new Date());
        StringBuilder stringBuilder = new StringBuilder(format);
        Random random = new Random();
        //随机生成数字，并添加到字符串
        for (int i = 0; i < 3; i++) {
            stringBuilder.append(random.nextInt(10));
        }
        return stringBuilder.toString();
    }

    /**
     * 生成16位短雪花ID
     * 格式：秒级时间戳(10位) + 机器ID(2位) + 序列号(4位)
     * 示例：1730015724123001
     * 
     * 特点：
     * 1. 使用秒级时间戳，减少位数
     * 2. 支持单机每秒10000个ID
     * 3. 支持100台机器
     * 4. 全局唯一、趋势递增
     * 
     * @return 16位雪花ID字符串
     */
    public static synchronized String generateSnowflakeId() {
        long now = System.currentTimeMillis() / 1000; // 秒级时间戳
        
        if (now == shortSnowflakeLastTimestamp) {
            // 同一秒内，序列号递增
            shortSnowflakeSequence = (shortSnowflakeSequence + 1) % 10000;
            if (shortSnowflakeSequence == 0) {
                // 序列号用完，等待下一秒
                while (now <= shortSnowflakeLastTimestamp) {
                    now = System.currentTimeMillis() / 1000;
                }
            }
        } else {
            // 新的一秒，序列号重置
            shortSnowflakeSequence = 0;
            shortSnowflakeLastTimestamp = now;
        }
        
        // 格式化：10位时间戳 + 2位机器ID + 4位序列号 = 16位
        return String.format("%010d%02d%04d", now, SHORT_SNOWFLAKE_MACHINE_ID, shortSnowflakeSequence);
    }
}
