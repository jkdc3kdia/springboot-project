package com.dego.mq.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * 
 */
public interface OrderChannel {
    String INPUT = "input-order";
    String OUTPUT = "output-order";

    interface In {
        @Input(INPUT)
        SubscribableChannel input();
    }

    interface Out {
        @Output(OUTPUT)
        MessageChannel output();
    }
}
