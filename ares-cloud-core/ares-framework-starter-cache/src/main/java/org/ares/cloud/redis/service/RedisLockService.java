package org.ares.cloud.redis.service;

import org.ares.cloud.api.LockApi;
import org.ares.cloud.redis.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Redis分布式锁服务
 * 基于Redis实现的分布式锁，支持可重入、自动续期、防误删等特性
 * 
 * @Author hugo  tangxkwork@163.com
 * @description redis分布式锁
 * @date 2024/01/17/15:03
 **/
public class RedisLockService implements LockApi {
    
    private static final Logger log = LoggerFactory.getLogger(RedisLockService.class);
    
    /**
     * 锁值前缀，用于标识锁的所有者
     */
    private static final String LOCK_VALUE_PREFIX = "lock:";
    
    /**
     * 默认锁超时时间（秒）
     */
    private static final int DEFAULT_LOCK_TIMEOUT = 30;
    
    /**
     * 默认重试间隔（毫秒）
     */
    private static final long DEFAULT_RETRY_INTERVAL = 100;
    
    /**
     * 默认重试次数
     */
    private static final int DEFAULT_RETRY_TIMES = 50;
    
    /**
     * 锁续期间隔（毫秒），锁过期时间的1/3
     */
    private static final long RENEWAL_INTERVAL_RATIO = 3;
    
    /**
     * 解锁Lua脚本
     */
    private static final String UNLOCK_SCRIPT = 
        "if redis.call('get', KEYS[1]) == ARGV[1] then " +
        "    return redis.call('del', KEYS[1]) " +
        "else " +
        "    return 0 " +
        "end";
    
    /**
     * 续期Lua脚本
     */
    private static final String RENEWAL_SCRIPT = 
        "if redis.call('get', KEYS[1]) == ARGV[1] then " +
        "    return redis.call('expire', KEYS[1], ARGV[2]) " +
        "else " +
        "    return 0 " +
        "end";
    
    /**
     * Redis工具类
     */
    private final RedisUtil redisUtil;
    
    /**
     * Redis模板，用于执行Lua脚本
     */
    private final RedisTemplate<String, Object> redisTemplate;
    
    /**
     * 线程本地存储，用于存储当前线程的锁信息
     */
    private final ThreadLocal<LockInfo> lockInfoThreadLocal = new ThreadLocal<>();
    
    /**
     * 调度器，用于自动续期
     */
    private final ScheduledExecutorService scheduler;
    
    /**
     * 解锁脚本
     */
    private final DefaultRedisScript<Long> unlockScript;
    
    /**
     * 续期脚本
     */
    private final DefaultRedisScript<Long> renewalScript;
    
    /**
     * 构造函数
     */
    private RedisLockService() {
        throw new UnsupportedOperationException("RedisLockService cannot be instantiated without RedisUtil");
    }
    
    /**
     * 构造函数
     * @param redisUtil Redis工具类
     * @param redisTemplate Redis模板
     */
    public RedisLockService(RedisUtil redisUtil, RedisTemplate<String, Object> redisTemplate) {
        if (redisUtil == null) {
            throw new IllegalArgumentException("RedisUtil cannot be null");
        }
        if (redisTemplate == null) {
            throw new IllegalArgumentException("RedisTemplate cannot be null");
        }
        
        this.redisUtil = redisUtil;
        this.redisTemplate = redisTemplate;
        this.scheduler = new ScheduledThreadPoolExecutor(1, r -> {
            Thread t = new Thread(r, "redis-lock-renewal");
            t.setDaemon(true);
            return t;
        });
        
        // 初始化Lua脚本
        this.unlockScript = new DefaultRedisScript<>();
        this.unlockScript.setScriptText(UNLOCK_SCRIPT);
        this.unlockScript.setResultType(Long.class);
        
        this.renewalScript = new DefaultRedisScript<>();
        this.renewalScript.setScriptText(RENEWAL_SCRIPT);
        this.renewalScript.setResultType(Long.class);
    }
    
    /**
     * 构造函数（兼容旧版本）
     * @param redisUtil Redis工具类
     */
    public RedisLockService(RedisUtil redisUtil) {
        this(redisUtil, null);
    }
    
    /**
     * 解锁
     * 使用Lua脚本确保原子性操作，防止误删其他线程的锁
     * 
     * @param key 锁的key
     */
    @Override
    public void unLock(String key) {
        if (!StringUtils.hasText(key)) {
            log.warn("Lock key is null or empty, skip unlock");
            return;
        }
        
        LockInfo lockInfo = lockInfoThreadLocal.get();
        if (lockInfo == null || !key.equals(lockInfo.getKey())) {
            log.warn("No lock info found for key: {}, or key mismatch", key);
            return;
        }
        
        // 减少重入次数
        lockInfo.decrementCount();
        
        if (lockInfo.getCount() > 0) {
            log.debug("Lock {} is still held by current thread, count: {}", key, lockInfo.getCount());
            return;
        }
        
        // 重入次数为0，执行真正的解锁
        String lockValue = lockInfo.getLockValue();
        boolean unlocked = unlockWithLua(key, lockValue);
        
        if (unlocked) {
            log.debug("Successfully unlocked key: {}", key);
            // 停止自动续期
            lockInfo.stopRenewal();
            lockInfoThreadLocal.remove();
        } else {
            log.warn("Failed to unlock key: {}, lock may have expired or been released by other thread", key);
        }
    }
    
    /**
     * 加锁
     * 支持可重入锁，同一线程可以多次获取同一个锁
     * 
     * @param key 锁的key
     * @param seconds 锁超时时间（秒）
     * @return 是否加锁成功
     */
    @Override
    public boolean lock(String key, int seconds) {
        if (!StringUtils.hasText(key)) {
            log.warn("Lock key is null or empty");
            return false;
        }
        
        if (seconds <= 0) {
            seconds = DEFAULT_LOCK_TIMEOUT;
        }
        
        LockInfo lockInfo = lockInfoThreadLocal.get();
        
        // 检查是否是可重入锁
        if (lockInfo != null && key.equals(lockInfo.getKey())) {
            lockInfo.incrementCount();
            log.debug("Reentrant lock acquired for key: {}, count: {}", key, lockInfo.getCount());
            return true;
        }
        
        // 生成唯一的锁值
        String lockValue = generateLockValue();
        
        // 尝试获取锁
        boolean acquired = tryLock(key, lockValue, seconds);
        
        if (acquired) {
            // 创建新的锁信息
            lockInfo = new LockInfo(key, lockValue, seconds);
            lockInfoThreadLocal.set(lockInfo);
            
            // 启动自动续期
            startRenewal(lockInfo);
            
            log.debug("Lock acquired for key: {}, value: {}", key, lockValue);
        } else {
            log.debug("Failed to acquire lock for key: {}", key);
        }
        
        return acquired;
    }
    
    /**
     * 检查是否在加锁状态
     * 
     * @param key 锁的key
     * @return 是否上锁
     */
    @Override
    public boolean isLock(String key) {
        if (!StringUtils.hasText(key)) {
            return false;
        }
        
        LockInfo lockInfo = lockInfoThreadLocal.get();
        if (lockInfo != null && key.equals(lockInfo.getKey())) {
            // 检查当前线程是否持有锁
            return lockInfo.getCount() > 0;
        }
        
        // 检查Redis中是否存在锁
        return redisUtil.hasKey(key);
    }
    
    /**
     * 执行锁
     * 自动加锁、执行、解锁的便捷方法
     * 
     * @param key 锁的key
     * @param lockExecute 执行器
     * @param <T> 返回类型
     * @return 执行结果
     */
    @Override
    public <T> T lockExecute(String key, LockApi.LockExecute<T> lockExecute) {
        return lockExecute(key, DEFAULT_LOCK_TIMEOUT, DEFAULT_RETRY_TIMES, DEFAULT_RETRY_INTERVAL, lockExecute);
    }
    
    /**
     * 执行锁（带重试参数）
     * 
     * @param key 锁的key
     * @param lockTimeout 锁超时时间（秒）
     * @param retryTimes 重试次数
     * @param retryInterval 重试间隔（毫秒）
     * @param lockExecute 执行器
     * @param <T> 返回类型
     * @return 执行结果
     */
    public <T> T lockExecute(String key, int lockTimeout, int retryTimes, long retryInterval, LockApi.LockExecute<T> lockExecute) {
        if (!StringUtils.hasText(key)) {
            log.warn("Lock key is null or empty");
            return lockExecute.waitTimeOut();
        }
        
        boolean acquired = false;
        int attempts = 0;
        
        // 尝试获取锁
        while (!acquired && attempts < retryTimes) {
            acquired = lock(key, lockTimeout);
            
            if (!acquired) {
                attempts++;
                if (attempts < retryTimes) {
                    log.debug("Failed to acquire lock for key: {}, attempt: {}/{}, retrying in {}ms", 
                             key, attempts, retryTimes, retryInterval);
                    
                    try {
                        // 添加随机延迟，避免惊群效应
                        long jitter = ThreadLocalRandom.current().nextLong(retryInterval / 2, retryInterval);
                        Thread.sleep(jitter);
                    } catch (InterruptedException e) {
                        log.warn("Thread interrupted while waiting for lock: {}", e.getMessage());
                        Thread.currentThread().interrupt();
                        return lockExecute.waitTimeOut();
                    }
                }
            }
        }
        
        if (!acquired) {
            log.warn("Failed to acquire lock for key: {} after {} attempts", key, retryTimes);
            return lockExecute.waitTimeOut();
        }
        
        try {
            log.debug("Lock acquired for key: {}, executing business logic", key);
            return lockExecute.execute();
        } catch (Exception e) {
            log.error("Error executing business logic with lock: {}", e.getMessage(), e);
            throw e;
        } finally {
            unLock(key);
            log.debug("Lock released for key: {}", key);
        }
    }
    
    /**
     * 尝试获取锁
     * 
     * @param key 锁的key
     * @param lockValue 锁的值
     * @param seconds 锁超时时间（秒）
     * @return 是否获取成功
     */
    private boolean tryLock(String key, String lockValue, int seconds) {
        try {
            // 使用SET NX EX命令原子性地设置锁
            return redisUtil.set(key, lockValue, seconds);
        } catch (Exception e) {
            log.error("Error acquiring lock for key: {}", key, e);
            return false;
        }
    }
    
    /**
     * 使用Lua脚本解锁，确保原子性
     * 
     * @param key 锁的key
     * @param lockValue 锁的值
     * @return 是否解锁成功
     */
    private boolean unlockWithLua(String key, String lockValue) {
        try {
            if (redisTemplate != null && unlockScript != null) {
                // 使用Lua脚本确保原子性
                Long result = redisTemplate.execute(unlockScript, Collections.singletonList(key), lockValue);
                return result != null && result == 1L;
            } else {
                // 降级到简单删除操作
                Object currentValue = redisUtil.get(key);
                if (lockValue.equals(currentValue)) {
                    return redisUtil.del(key);
                }
                return false;
            }
        } catch (Exception e) {
            log.error("Error unlocking key: {}", key, e);
            return false;
        }
    }
    
    /**
     * 生成唯一的锁值
     * 
     * @return 锁值
     */
    private String generateLockValue() {
        return LOCK_VALUE_PREFIX + UUID.randomUUID().toString() + ":" + Thread.currentThread().getId();
    }
    
    /**
     * 启动自动续期
     * 
     * @param lockInfo 锁信息
     */
    private void startRenewal(LockInfo lockInfo) {
        if (scheduler != null && renewalScript != null) {
            lockInfo.startRenewal(scheduler, renewalScript, redisTemplate);
        } else {
            log.debug("Auto-renewal not available for lock: {}", lockInfo.getKey());
        }
    }
    
    /**
     * 锁信息内部类
     */
    private static class LockInfo {
        private final String key;
        private final String lockValue;
        private final int timeout;
        private int count;
        private long lastRenewalTime;
        private ScheduledFuture<?> renewalTask;
        private final AtomicBoolean renewalActive = new AtomicBoolean(false);
        
        public LockInfo(String key, String lockValue, int timeout) {
            this.key = key;
            this.lockValue = lockValue;
            this.timeout = timeout;
            this.count = 1;
            this.lastRenewalTime = System.currentTimeMillis();
        }
        
        public String getKey() {
            return key;
        }
        
        public String getLockValue() {
            return lockValue;
        }
        
        public int getTimeout() {
            return timeout;
        }
        
        public int getCount() {
            return count;
        }
        
        public void incrementCount() {
            this.count++;
        }
        
        public void decrementCount() {
            if (this.count > 0) {
                this.count--;
            }
        }
        
        public long getLastRenewalTime() {
            return lastRenewalTime;
        }
        
        public void setLastRenewalTime(long lastRenewalTime) {
            this.lastRenewalTime = lastRenewalTime;
        }
        
        /**
         * 启动自动续期
         */
        public void startRenewal(ScheduledExecutorService scheduler, DefaultRedisScript<Long> renewalScript, 
                               RedisTemplate<String, Object> redisTemplate) {
            if (renewalActive.compareAndSet(false, true)) {
                long renewalInterval = (timeout * 1000L) / RENEWAL_INTERVAL_RATIO;
                
                renewalTask = scheduler.scheduleAtFixedRate(() -> {
                    try {
                        if (count > 0) {
                            // 注意：ARGV[2] 应该传递整数而不是字符串，避免序列化问题
                            Long result = redisTemplate.execute(renewalScript, 
                                Collections.singletonList(key), lockValue, timeout);
                            
                            if (result != null && result == 1L) {
                                lastRenewalTime = System.currentTimeMillis();
                                log.debug("Lock renewed for key: {}", key);
                            } else {
                                log.warn("Failed to renew lock for key: {}", key);
                                renewalActive.set(false);
                            }
                        } else {
                            renewalActive.set(false);
                        }
                    } catch (Exception e) {
                        log.error("Error during lock renewal for key: {}", key, e);
                        renewalActive.set(false);
                    }
                }, renewalInterval, renewalInterval, TimeUnit.MILLISECONDS);
                
                log.debug("Auto-renewal started for lock: {}, interval: {}ms", key, renewalInterval);
            }
        }
        
        /**
         * 停止自动续期
         */
        public void stopRenewal() {
            if (renewalActive.compareAndSet(true, false)) {
                if (renewalTask != null && !renewalTask.isCancelled()) {
                    renewalTask.cancel(false);
                    log.debug("Auto-renewal stopped for lock: {}", key);
                }
            }
        }
    }
}
