package com.dego.mq.consumer;

import com.dego.constant.RabbitConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消息消费
 */
@Component
@Slf4j
public class MessageConsumeService {


    /**
     * 消息接收测试
     *
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            key = RabbitConstants.QU_SCS_DB_TS,
            value = @Queue(value = RabbitConstants.QU_SCS_DB_TS, durable = "true"),
            exchange = @Exchange(value = RabbitConstants.MQ_EX_DIRECT, durable = "true")))
    public void receiveMessage(String message) {
        log.info("接收到消息：{}", message);
        log.info("接收到消息完成！");
    }


}
