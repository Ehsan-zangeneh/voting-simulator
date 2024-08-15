package com.voting.simulator.controller;

import lombok.Data;

@Data
public class SwitchSettingParam {
    long durationSleep = 1000;
    int numberOfThreads = 1;
}
