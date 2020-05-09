package com.bda.bdaqm.rabbitmq;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: 消息队列发送者
 * @Author:
 * @CreateTime:
 */
@Service
public class RabbitmqProducer {

    public RabbitmqProducer() {

    }

    @Autowired
    private AmqpTemplate amqpTemplateReady;

    public void sendQueue(String exchange_key, String queue_key, Object object) {
        // convertAndSend 将Java对象转换为消息发送至匹配key的交换机中Exchange
        amqpTemplateReady.convertAndSend(exchange_key, queue_key, object);

    }

    public void sendQueue(String exchange_key, String queue_key, Object object, int pri) {
        // convertAndSend 将Java对象转换为消息发送至匹配key的交换机中Exchange
        amqpTemplateReady.convertAndSend(exchange_key, queue_key, object, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setPriority(pri);
                return message;
            }
        });
        System.out.println(pri);
    }
}