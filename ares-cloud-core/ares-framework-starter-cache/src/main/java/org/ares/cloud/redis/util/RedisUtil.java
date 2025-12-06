package org.ares.cloud.redis.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author hugo  tangxkwork@163.com
 * @description redis 工具
 * @date 2024/01/17/14:58
 **/
public class RedisUtil {
    private static final Logger log = LoggerFactory.getLogger(RedisUtil.class);
    /**
     * redis模板
     */
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 构造函数
     */
    private   RedisUtil(){}
    /**
     * 构造函数
     */
    public  RedisUtil(RedisTemplate<String, Object> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    /**
     * 指定缓存失效时间
     * @param key 键
     * @param time 时间(秒)
     * @return 是否成功
     */
    public boolean expire(String key,long time){
        try {
            if(time>0){
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
           log.error("exception message", e); 
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key){
        Long res = redisTemplate.getExpire(key,TimeUnit.SECONDS);
        if(res==null) return -1;
        return res;
    }

    /**
     * 判断key是否存在
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key){
        try {
            return Boolean.TRUE.equals(redisTemplate.hasKey(key));
        } catch (Exception e) {
           log.error("exception message", e); 
            return false;
        }
    }

    /**
     * 删除缓存
     * @param key 可以传一个值 或多个
     */
    public boolean del(String ... key){
        if(key!=null&&key.length>0){
            if(key.length==1){
               return Boolean.TRUE.equals(redisTemplate.delete(key[0]));
            }else{
                redisTemplate.delete(Arrays.asList(key));
            }
        }
        return false;
    }
    /**
     * 批量的删除key
     * @param keys 删除key
     */
    public Long delKeys (Set<String> keys){
        if(!CollectionUtils.isEmpty(keys)){
            return  redisTemplate.delete(keys);
        }
        return 0L;
    }
    /**
     * 根据前缀删除缓存
     * @param prefix 前置
     */
    public Long delByPrefix (String prefix){
        if(!StringUtils.isBlank(prefix)){
            //根据前缀模糊搜索
            Set<String> keys = scanKeyPrefix(prefix);
            //执行删除
            return  delKeys(keys);
        }
        return 0L;
    }
    //============================String=============================
    /**
     * 普通缓存获取
     * @param key 键
     * @return 值
     */
    public Object get(String key){
        return key==null?null:redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     * @param key 键
     * @param value 值
     * @return true成功 false失败
     */
    public  boolean set(String key,Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
           log.error("exception message", e);
            return false;
        }

    }
    /**
     * 普通缓存放入并设置时间
     * @param key 键
     * @param value 值
     * @param time 过期时间，时间戳
     * @return true成功 false 失败
     */
    public boolean setSettingTime(String key,Object value,long time){
        try {
            if(time>0){
                redisTemplate.opsForValue().set(key, value, time);
            }else{
                set(key, value);
            }
            return true;
        } catch (Exception e) {
           log.error("exception message", e); 
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间
     * @param key 键
     * @param value 值
     * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key,Object value,long time){
        try {
            if(time>0){
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            }else{
                set(key, value);
            }
            return true;
        } catch (Exception e) {
           log.error("exception message", e); 
            return false;
        }
    }

    /**
     * 递增
     * @param key 键
     * @param delta 要增加几(大于0)
     * @return 递增
     */
    public long incr(String key, long delta){
        if(delta<0){
            throw new RuntimeException("递增因子必须大于0");
        }
        Long res = redisTemplate.opsForValue().increment(key, delta);
        if (res == null) return 0L;
        return res;
    }

    /**
     * 根据字符串扫描key
     * @param matchKey 匹配字符串
     * @return  得到的值
     */
    public Set<String> scan(String matchKey) {
        //缓存搜索
        return redisTemplate.keys("*" + matchKey + "*");
    }

    /**
     * 根据前缀和后缀扫描key
     * @param start 前缀
     * @param end 后缀
     * @return 结果
     */
    public Set<String> scanMiddleVague(String start,String end) {
        //缓存搜索
        return redisTemplate.keys(start+"*"+end);
    }
    /**
     * 根据前缀获取key
     * @param prefix 前缀
     * @return 搜索结果
     */
    public Set<String> scanKeyPrefix(String prefix) {
        //缓存搜索
        return redisTemplate.keys(prefix + "*");
    }
    /**
     * 递减
     * @param key 键
     * @param delta 要减少几(小于0)
     * @return 当前值
     */
    public long decr(String key, long delta){
        if(delta<0){
            throw new RuntimeException("递减因子必须大于0");
        }
        Long res = redisTemplate.opsForValue().increment(key, -delta);
        if (res == null) return 0;
        return res;
    }
    /**
     * 递增每次加一
     * @param key 键
     * @return 当前值
     */
    public long incrOne(String key){
        Long res = redisTemplate.opsForValue().increment(key, 1);
        if (res == null) return 0;
        return res;
    }

    /**
     * 递减每次减一
     * @param key 键
     * @return 当前值
     */
    public long decrOne(String key){

        Long res =  redisTemplate.opsForValue().increment(key, -1);
        if (res == null) return 0;
        return res;
    }

    //================================Map=================================
    /**
     * HashGet
     * @param key 键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public Object hget(String key,String item){
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取hashKey对应的所有键值
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object,Object> hmget(String key){
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * HashSet
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public boolean hmset(String key, Map<String,Object> map){
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
           log.error("exception message", e); 
            return false;
        }
    }

    /**
     * HashSet 并设置时间
     * @param key 键
     * @param map 对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public boolean hmset(String key, Map<String,Object> map, long time){
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if(time>0){
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
           log.error("exception message", e); 
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * @param key 键
     * @param item 项
     * @param value 值
     * @return true 成功 false失败
     */
    public boolean hset(String key,String item,Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
           log.error("exception message", e); 
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * @param key 键
     * @param item 项
     * @param value 值
     * @param time 时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public boolean hset(String key,String item,Object value,long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if(time>0){
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
           log.error("exception message", e); 
            return false;
        }
    }

    /**
     * 删除hash表中的值
     * @param key 键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public void hdel(String key, Object... item){
        redisTemplate.opsForHash().delete(key,item);
    }

    /**
     * 判断hash表中是否有该项的值
     * @param key 键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hHasKey(String key, String item){
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     * @param key 键
     * @param item 项
     * @param by 要增加几(大于0)
     * @return 当前值
     */
    public double hincr(String key, String item,double by){
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * hash递减
     * @param key 键
     * @param item 项
     * @param by 要减少记(小于0)
     * @return 当前值
     */
    public double hdecr(String key, String item,double by){
        return redisTemplate.opsForHash().increment(key, item,-by);
    }

    //============================set=============================
    /**
     * 根据key获取Set中的所有值
     * @param key 键
     * @return 缓存中的值
     */
    public Set<Object> sGet(String key){
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
           log.error("exception message", e); 
            return null;
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     * @param key 键
     * @param value 值
     * @return true 存在 false不存在
     */
    public boolean sHasKey(String key,Object value){
        try {
            Boolean b =  redisTemplate.opsForSet().isMember(key, value);
            if (b == null) return false;
            return b;
        } catch (Exception e) {
           log.error("exception message", e); 
            return false;
        }
    }

    /**
     * 将数据放入set缓存
     * @param key 键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSet(String key, Object...values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (count == null) return 0;
            return count;
        } catch (Exception e) {
           log.error("exception message", e); 
            return 0;
        }
    }

    /**
     * 将set数据放入缓存
     * @param key 键
     * @param time 时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSetAndTime(String key,long time,Object...values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (count == null) return 0;
            if(time>0) expire(key, time);
            return count;
        } catch (Exception e) {
           log.error("exception message", e); 
            return 0;
        }
    }

    /**
     * 获取set缓存的长度
     * @param key 键
     * @return 长度
     */
    public long sGetSetSize(String key){
        try {
            Long source =  redisTemplate.opsForSet().size(key);
            if(source == null) return 0;
            return source;
        } catch (Exception e) {
           log.error("exception message", e); 
            return 0;
        }
    }

    /**
     * 移除值为value的
     * @param key 键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public long setRemove(String key, Object ...values) {
        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            if(count == null) return 0;
            return count;
        } catch (Exception e) {
           log.error("exception message", e); 
            return 0;
        }
    }
    //===============================list=================================

    /**
     * 获取list缓存的内容
     * @param key 键
     * @param start 开始
     * @param end 结束  0 到 -1代表所有值
     * @return 缓存的内容
     */
    public List<Object> lGet(String key, long start, long end){
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
           log.error("exception message", e); 
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     * @param key 键
     * @return  集合长度
     */
    public long lGetListSize(String key){
        try {
            Long source =  redisTemplate.opsForList().size(key);
            if (source == null) return 0;
            return source;
        } catch (Exception e) {
           log.error("exception message", e); 
            return 0;
        }
    }

    /**
     * 通过索引 获取list中的值
     * @param key 键
     * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return 获取的值
     */
    public Object lGetIndex(String key,long index){
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
           log.error("exception message", e); 
            return null;
        }
    }

    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     * @return 是否添加成功
     */
    public boolean lSet(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
           log.error("exception message", e); 
            return false;
        }
    }

    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     * @return 是否添加成功
     */
    public boolean lSet(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) expire(key, time);
            return true;
        } catch (Exception e) {
           log.error("exception message", e); 
            return false;
        }
    }

    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     * @return 是否添加成功
     */
    public boolean lSet(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
           log.error("exception message", e); 
            return false;
        }
    }

    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     * @return 是否添加成功
     */
    public boolean lSet(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) expire(key, time);
            return true;
        } catch (Exception e) {
           log.error("exception message", e); 
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     * @param key 键
     * @param index 索引
     * @param value 值
     * @return 是否修改成功
     */
    public boolean lUpdateIndex(String key, long index,Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
           log.error("exception message", e); 
            return false;
        }
    }

    /**
     * 移除N个值为value
     * @param key 键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public long lRemove(String key,long count,Object value) {
        try {
            Long source =  redisTemplate.opsForList().remove(key, count, value);
            if (source == null) return 0;
            return source;
        } catch (Exception e) {
           log.error("exception message", e); 
            return 0;
        }
    }
    /**
     * 有序集合的插入
     * @param key 键
     * @param collectionName 集合的名称
     * @return 成功/失败
     */
    public boolean zSetAdd(String collectionName, String key){
        try {
            ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();
            zSetOperations.add(collectionName,key,System.currentTimeMillis());
            return true;
        } catch (Exception e) {
           log.error("exception message", e); 
            return false;
        }
    }
    /**
     * 有序集合key移除
     * @param key 键
     * @param collectionName 集合的名称
     * @return 成功/失败
     */
    public boolean zSetRemove(String collectionName, String key){
        try {
            ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();
            zSetOperations.remove(collectionName,key);
            return true;
        } catch (Exception e) {
           log.error("exception message", e); 
            return false;
        }
    }
    /**
     * 获取有序集合的元素总数
     * @param collectionName 集合的名称
     * @return 数量
     */
    public Long getZSetCount(String collectionName){
        Long count = 0L;
        try {
            ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();
            count = zSetOperations.zCard(collectionName);
        } catch (Exception e) {
           log.error("exception message", e); 
        }
        return count;
    }
    /**
     * 获取有序集合中某个元素是否存在
     * @param collectionName 集合的名称
     * @param key 需要查询的键值
     * @return Boolean
     */
    public double getZExists(String collectionName,String key){
        if(!StringUtils.isEmpty(collectionName) || !StringUtils.isEmpty(key)){
            return 0;
        }
        double dd = 0;
        try {
            ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();
            Double score = zSetOperations.score(collectionName, key);
            if (score == null) {
                // 处理 score 为 null 的情况
                return 0; // 或者使用默认值
            }
            // 安全拆箱
            return score;
        } catch (Exception e) {
           log.error("exception message", e); 
        }
        return dd;
    }
    /**
     * 获取有序集合,时间间隔大于30分钟则移除
     * @param collectionName 集合的名称
     */
    public void removeMemberFromZset(String collectionName){
        try {
            ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();
            long currentTime = System.currentTimeMillis();
            long outTime = 30*60*1000;
            zSetOperations.removeRangeByScore(collectionName,0,currentTime-outTime);
        } catch (Exception e) {
            log.error("exception message", e);
        }

    }
}
