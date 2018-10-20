package com.imooc.order.message;

import com.imooc.order.OrderApplicationTests;
import org.junit.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MqReceiverTest extends OrderApplicationTests {

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 发送mq消息测试
     */
    @Test
    public void send() {
        amqpTemplate.convertAndSend("myQueue", "发送一条消息：Hi，你好吗？");
    }


    @Test
    public void sendOrder() {
        amqpTemplate.convertAndSend("myOrder", "computer", "发送一条消息：Hi，你好吗？");
    }
}