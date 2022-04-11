package com.dego.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.dego.constant.RabbitConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Map;

/**
 * rabbit失败消息处理
 */
@Component
public class ErrorMessageConsumerService {

    @Autowired
    private MessageConverter messageConverter;

    private final static Logger LOG = LoggerFactory.getLogger(ErrorMessageConsumerService.class);

    @RabbitListener(bindings = @QueueBinding(
            key = RabbitConstants.ERROR_QUEUE,
            value = @Queue(value = RabbitConstants.MQ_EX_DIRECT, durable = "true"),
            exchange = @Exchange(value = RabbitConstants.MQ_EX_DIRECT, durable = "true")))
    public void error(Object messageObj) {
        LOG.error("收到失败消息{}", JSONObject.toJSONString(messageObj));
        Message message = (Message) messageObj;
        // 捕获异常，避免进入死循环
        try {
            MessageProperties properties = message.getMessageProperties();
            Map<String, Object> headers = properties.getHeaders();
            String exchange = (String) headers.get("x-original-exchange");
            String routingKey = (String) headers.get("x-original-routingKey");
            LOG.error("失败消息来源：exchange：{}，routingKey：{}", exchange, routingKey);

            String jsonMessage = JSONObject.toJSONString(messageConverter.fromMessage(message));
            LOG.error("失败消息对象json串：{}", jsonMessage);

            // TODO 业务处理

        } catch (Exception e) {
            LOG.error("消费失败消息出错！", e);
        }
    }
}
