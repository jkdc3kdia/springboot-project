package com.dego.controller;

import cn.hutool.core.lang.Snowflake;
import com.alibaba.fastjson.JSON;
import com.dego.constant.RabbitConstants;
import com.dego.threadlocal.ThreadLocalBack;
import com.dego.threadlocal.cache.EmployeeCache;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Description
 * @Author janus
 * @Date 2021/7/15 11:27
 * @Version 1.0
 */
@RestController
@Slf4j
public class IndexController {

    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private Snowflake snowflake;


    @GetMapping("/")
    public String index() {
        for (int i = 0; i < 10; i++) {
            System.out.println(snowflake.nextIdStr());
        }
        Map<String, String> map = Maps.newHashMap();
        map.put("auth", "Janus");
        map.put("description", "欢迎访问首页信息");
        map.put("nextId", snowflake.nextIdStr());
        // 发送调拨消息至ts系统
//        amqpTemplate.convertAndSend(RabbitConstants.MQ_EX_DIRECT, RabbitConstants.QU_SCS_DB_TS, JSON.toJSONString(map));
        return JSON.toJSONString(map);
    }


    @GetMapping("/userInfo")
    public String userInfo() {
        EmployeeCache currentUser = ThreadLocalBack.getCurrentUser();
        if (currentUser!=null){
            log.debug("当前用户信息:{}",JSON.toJSONString(currentUser));
        }else {
            log.error("当前用户未登录");
        }
        return JSON.toJSONString(currentUser);
    }




}
