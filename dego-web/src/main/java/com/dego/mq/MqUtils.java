package com.dego.mq;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

/**
 * 消息队列工具
 *
 */
public class MqUtils {

    public static <T> Message build(T payload) {
        return MessageBuilder.withPayload(payload).build();
    }
}
