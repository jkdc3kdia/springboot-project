package com.dego.util.cache;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 在某些跨系统的异步处理的业务场景中，有些处理过程比较长。在短时间内刷新页面，仍能查到刚刚页面上已处理了的数据。
 * 因为这条数据还在处理中，数据的状态还没发生变化。所以通过本工具类做一些处理，算是一种障眼法。但暂时来看这种处理方式成本最小
 * 问题场景 https://ylfood.zentaopm.com/bug-view-15294.html
 * @author chenpeng
 * @since 2020-10-12
 */
@Slf4j
@Component
public class DeceitUtils {
    /**
     * 代表key存在的一个任意的值
     */
    private final static String SOME_VALUE = "1";
    /**
     * key的过期时间，避免业务处理不成功的情况
     */
    private final static Integer TIMEOUT = 1;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 往redis中放入业务编码数据
     * @param list
     */
    public void put(ICacheCode cacheCode,List<String> list) {
        for (String  code: list) {
            redisUtils.set(cacheCode.cacheKey(code), SOME_VALUE, TIMEOUT);
        }
    }

    /**
     * 业务处理完成后清理业务编码
     * @param cacheCode
     * @param list
     */
    public void clear(ICacheCode cacheCode,List<String> list) {
        for (String  code: list) {
            redisUtils.del(cacheCode.cacheKey(code));
        }
    }
    /**
     * 获取正在处理中的数据编码
     * @return
     */
    public List<String> existedCode(ICacheCode cacheCode) {
        Set<String> set = redisUtils.keys(cacheCode.cacheKey("")+"*");
        List<String> result = new ArrayList<>();
        if(set.size()>0) {
            log.info("{} processing:{}",cacheCode, JSON.toJSONString(set));
            for(String key:set) {
                //获取去掉前缀后的业务编码
                String deviceCode = key.substring(key.lastIndexOf(ICacheCode.ID_SPLITTER)+ICacheCode.ID_SPLITTER.length());
                result.add(deviceCode);
            }
        }
        return result;
    }
}
