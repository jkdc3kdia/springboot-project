package com.dego.util.cache;

import com.dego.exception.BusinessException;
import com.dego.exception.web.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis操作工具
 * set()、get()相关方法使用kyro序列化
 * getString()相关方法使用String序列化
 *
 * @author huang.guo
 * @since 2019-12-23
 */
@SuppressWarnings("unchecked")
@Slf4j
public class RedisUtils {

    private final RedisTemplate redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;

    public RedisUtils(RedisTemplate redisTemplate, StringRedisTemplate stringRedisTemplate) {
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public <K> String getString(K k) {
        return stringRedisTemplate.opsForValue().get(k);
    }

    public <K, V> V get(K k) {
        return (V) redisTemplate.opsForValue().get(k);
    }

    public <K, V> void set(K k, V v) {
        redisTemplate.opsForValue().set(k, v);
    }

    public <K, V> void set(K k, V v, long timeout) {
        redisTemplate.opsForValue().set(k, v, timeout, TimeUnit.SECONDS);
    }

    public <K, V> boolean setnx(K k, V v) {
        return Objects.equals(redisTemplate.opsForValue().setIfAbsent(k, v), true);
    }

    public <K> boolean del(K k) {
        return Objects.equals(redisTemplate.delete(k), true);
    }

    /**
     * 按指定数量增加
     *
     * @param k
     * @param v
     * @param <K>
     */
    public <K> long incr(K k, long v) {
        Long increment = redisTemplate.opsForValue().increment(k, v);
        if (Objects.isNull(increment)) {
            throw new BusinessException(ResponseCode.REDIS_OPERATION_FAIL);
        }

        return increment;
    }

    /**
     * 指定数量减少
     *
     * @param k
     * @param v   正整数
     * @param <K>
     * @return
     */
    public <K> long decr(K k, long v) {
        return incr(k, (-1 * v));
    }

    /**
     * 获取自增的值
     * <p>
     * 注：必须通过{@link #getIncr}获取
     *
     * @param k
     * @param <K>
     * @return
     */
    public <K> Long getIncr(K k) {
        String incrNum = stringRedisTemplate.opsForValue().get(k);
        return Objects.isNull(incrNum) ? null : Long.valueOf(incrNum);
    }

    /**
     * 自增
     * <p>
     * 注：必须通过{@link #getIncr}获取
     *
     * @param k
     * @param <K>
     */
    public <K> long incrAuto(K k) {
        return incr(k, 1);
    }

    /**
     * 自减
     *
     * @param k
     * @param <K>
     * @return
     */
    public <K> long decrAuto(K k) {
        return incr(k, -1);
    }

    /**
     * 设置键过期时间，单位{@link TimeUnit#SECONDS}
     *
     * @param k
     * @param timeout
     * @param <K>
     */
    public <K> boolean expire(K k, long timeout) {
        return Objects.equals(redisTemplate.expire(k, timeout, TimeUnit.SECONDS), true);
    }

    /**
     * 获取键的剩余过期时间，单位{@link TimeUnit#SECONDS}
     *
     * @param k
     * @param <K>
     * @return
     */
    public <K> Long getExpire(K k) {
        return redisTemplate.getExpire(k);
    }


    public <K> Long getExpire(K k, TimeUnit timeUnit) {
        return redisTemplate.getExpire(k, timeUnit);
    }

    /**
     * @param key
     * @param seconds 秒
     * @param value
     */
    public void setString(String key, final int seconds, final String value) {
        if (StringUtils.isEmpty(key)) {
            log.error("Key can not be empty!");
        }
        if (StringUtils.isEmpty(value)) {
            log.info("Value can not be empty!");
        }
        stringRedisTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
    }

    /**
     * @param key
     * @param value
     */
    public void setString(String key, final String value) {
        if (StringUtils.isEmpty(key)) {
            log.error("Key can not be empty!");
        }
        if (StringUtils.isEmpty(value)) {
            log.info("Value can not be empty!");
        }
        stringRedisTemplate.opsForValue().set(key, value);
    }


    /**
     * 获取Key对应的所有键值
     *
     * @param key
     * @return
     */
    public Map<String, Object> hgetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 设置map
     *
     * @param key
     * @param map
     * @return
     */
    public boolean hmset(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            log.error("RedisUtils hmset error.");
            return false;
        }
    }

    /**
     * 获取map
     *
     * @param key
     * @param hashKeys
     * @return
     */
    public List<Object> hmget(String key, List<String> hashKeys) {
        return redisTemplate.opsForHash().multiGet(key, hashKeys);

    }

    public void hdel(String key, List<String> hashKeys) {
        redisTemplate.opsForHash().delete(key, hashKeys.toArray());
    }


    public void hset(String key, String field, Object value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    public <T> T hget(String key, String field) {
        return (T) redisTemplate.opsForHash().get(key, field);
    }

    public Set<String> keys(String pattern) {
        Set<String> keys = stringRedisTemplate.keys(pattern);
        return keys;
    }

    public Long lpush(String k, String message) {
        return redisTemplate.opsForList().leftPush(k, message);
    }
}
