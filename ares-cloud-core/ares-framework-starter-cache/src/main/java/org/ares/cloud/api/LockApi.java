package org.ares.cloud.api;

/**
 * @author hugo  tangxkwork@163.com
 * @description 锁
 * @date 2024/01/17/14:35
 **/
public interface LockApi {
    /**
     * 解锁
     * @param key 加锁的key
     */
    void unLock(String key);

    /**
     * 加锁
     * @param key 加锁的key
     * @param seconds 时间
     * @return 是否加锁成功
     */
    boolean lock(String key, int seconds);

    /**
     * 是否在加锁状态
     * @param key 锁key
     * @return 是否上锁
     */
    boolean isLock(String key);
    /**
     * 执行锁
     * @param key 锁的key
     * @param lockExecute
     * @param <T>
     * @return
     */
    <T> T lockExecute(String key, LockExecute<T> lockExecute);

    interface LockExecute<T> {
        T execute();

        T waitTimeOut();
    }
}
