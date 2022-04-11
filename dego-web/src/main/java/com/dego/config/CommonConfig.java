package com.dego.config;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfig {

    /**
     * 全局唯一id生成
     * @return
     */
    @Bean
    public Snowflake snowflake(){
        // todo 不同实例参数必须不一样 目前先写死
        return IdUtil.createSnowflake(1, 1);
    }
}
