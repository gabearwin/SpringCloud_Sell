package com.imooc.order.controller;

import com.imooc.order.dto.OrderDTO;
import com.imooc.order.message.StreamClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class SendMessageController {

    @Autowired
    private StreamClient streamClient;

    // 发送string消息
    @GetMapping("/sendMessage")
    public void process() {
        String message = "发送消息，当前时间是：" + new Date();
        streamClient.output1().send(MessageBuilder.withPayload(message).build());
    }

    // 发送消息是一个实体
    @GetMapping("/sendMessage1")
    public void process1() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId("123456");
        streamClient.output1().send(MessageBuilder.withPayload(orderDTO).build());
    }
}
