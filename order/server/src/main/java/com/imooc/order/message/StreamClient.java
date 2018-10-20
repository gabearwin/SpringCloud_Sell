package com.imooc.order.message;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

// @Component
public interface StreamClient {

    String INPUT = "myMessageInput";

    // @Input(StreamClient.INPUT)
    // SubscribableChannel input();

    String OUTPUT1 = "myMessageOutput1";

    @Output(StreamClient.OUTPUT1)
    MessageChannel output1();

    String OUTPUT2 = "myMessageOutput";

    @Output(StreamClient.OUTPUT2)
    MessageChannel output2();
}
