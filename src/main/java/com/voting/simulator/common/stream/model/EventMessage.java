package com.voting.simulator.common.stream.model;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class EventMessage<T> {
    T message;
    String channelName;
}
