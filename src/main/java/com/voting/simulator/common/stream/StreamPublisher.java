package com.voting.simulator.common.stream;

import com.voting.simulator.common.stream.model.EventMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class StreamPublisher {
    private final StreamBridge bridge;

    public <T> void  send(EventMessage<T> message) {

        this.bridge.send(message.getChannelName(),
                MessageBuilder.withPayload(message.getMessage())
                        .setHeader("partitionKey", message.getMessage().hashCode())
                        .build()
        );
        log.info("channel:{}, message:{}",message.getChannelName(), message.getMessage() );
    }

}
