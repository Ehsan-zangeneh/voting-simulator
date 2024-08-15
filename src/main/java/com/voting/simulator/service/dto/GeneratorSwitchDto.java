package com.voting.simulator.service.dto;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class GeneratorSwitchDto {
    long durationSleep;
    int numberOfThreads;
}
