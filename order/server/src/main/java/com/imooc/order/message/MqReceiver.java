package com.imooc.order.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MqReceiver {

    // 1、@RabbitListener(queues = "myQueue")
    // 2、自动创建队列 @RabbitListener(queuesToDeclare = @Queue("myQueue"))
    // 3、自动创建，Exchange和Queue绑定
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange("myExchange"),
            value = @Queue("myQueue")
    ))
    public void process(String message) {
        log.info("接收到消息，内容是：{}", message);
    }


    /**
     * myOrder通过key找到相应的队列computerOrder
     */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange("myOrder"),
            key = "computer",
            value = @Queue("computerOrder")
    ))
    public void processComputer(String message) {
        log.info("computer接收到消息，内容是：{}", message);
    }


    /**
     * myOrder通过key找到相应的队列fruitOrder
     */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange("myOrder"),
            key = "fruit",
            value = @Queue("fruitOrder")
    ))
    public void processFruit(String message) {
        log.info("fruit接收到消息，内容是：{}", message);
    }
}
