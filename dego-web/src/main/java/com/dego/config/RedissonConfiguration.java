package com.dego.config;

import com.dego.util.cache.RedisUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Arrays;

/**
 * redisson 配置
 */
@Configuration
@EnableCaching
public class RedissonConfiguration {

    @Value("${spring.redis.sentinel.master}")
    private String masterName;
    @Value("${spring.redis.sentinel.nodes}")
    private String nodes;
    @Value("${spring.redis.database}")
    private int database;
    @Value("${spring.redis.password}")
    private String password;
    private final String protocol = "redis://";
    private final String spliter = ",";

    @Bean
    public RedissonClient redissonClient() {
//        Config config = new Config();
//        config.useSentinelServers()
//                .setMasterName(masterName)
//                .setPingConnectionInterval(60 * 1000)
//                .addSentinelAddress(toSentinelAddress(nodes))
//                .setDatabase(database)
//                .setPassword(password);


        Config config = new Config();
        //单机模式  依次设置redis地址和密码
        config.useSingleServer().
                setAddress(protocol + nodes).
                setPassword(password);
        return Redisson.create(config);
    }

    @Bean
    public CacheManager cacheManager() {
        return new RedissonSpringCacheManager(redissonClient());
    }

    @Bean
    public RedisUtils redisUtils(RedisTemplate redisTemplate, StringRedisTemplate stringRedisTemplate) {
        return new RedisUtils(redisTemplate, stringRedisTemplate);
    }

    private String[] toSentinelAddress(String nodes) {
        return Arrays.stream(nodes.split(spliter))
                .map(node -> protocol + node.trim()).toArray(String[]::new);
    }
}
