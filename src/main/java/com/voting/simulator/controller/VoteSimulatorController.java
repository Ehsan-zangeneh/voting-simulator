package com.voting.simulator.controller;

import com.voting.simulator.controller.dto.SwitchSettingParam;
import com.voting.simulator.service.VoteGeneratorService;
import com.voting.simulator.service.dto.GeneratorSwitchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vote-simulator")
public class VoteSimulatorController {

    private final VoteGeneratorService voteGeneratorService;

    @PostMapping("/start")
    public void start(@RequestBody SwitchSettingParam switchDto) {
        voteGeneratorService.start(GeneratorSwitchDto.builder()
                        .durationSleep(switchDto.getDurationSleep())
                        .numberOfThreads(switchDto.getNumberOfThreads())
                .build());
    }

    @GetMapping("/stop")
    public void stop() {
        voteGeneratorService.stop();
    }


}
