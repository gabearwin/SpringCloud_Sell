package com.imooc.order.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(value = {StreamClient.class})
@Slf4j
public class StreamReceiver {

    /**
     * 接收消息
     */
    /*@StreamListener(StreamClient.OUTPUT1)
    public void process(Object message) {
        log.info("StreamReceiver 接收消息:{}", message);
    }*/

    /**
     * 接收消息，并返回消息
     */
    @StreamListener(StreamClient.OUTPUT1)
    @SendTo(StreamClient.OUTPUT2)
    public String process1(Object message) {
        log.info("StreamReceiver1 接收消息:{}", message);
        return "我收到消息了";
    }

    /**
     * 这里是模拟接收到process1返回的消息
     */
    @StreamListener(StreamClient.OUTPUT2)
    public void process2(Object message) {
        log.info("StreamReceiver2 接收消息:{}", message);
    }
}
