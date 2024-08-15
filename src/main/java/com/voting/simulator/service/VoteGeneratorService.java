package com.voting.simulator.service;

import com.voting.simulator.model.VoteEvent;
import com.voting.simulator.service.dto.GeneratorSwitchDto;
import lombok.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoteGeneratorService {

    private final VoteEventRandomGenerator randomGenerator;
    private final GeneratorSwitch generatorSwitch = new GeneratorSwitch();

    public void start(GeneratorSwitchDto switchDto) {
        this.generatorSwitch.setSleepDuration(switchDto.getDurationSleep());
        if(!this.generatorSwitch.isOn()) {
            var numberOfThreads = switchDto.getNumberOfThreads();
            numberOfThreads = numberOfThreads < 1 || numberOfThreads > 10 ? 1: numberOfThreads;
            this.generatorSwitch.setOn(true);
            for(int i = 1; i <= numberOfThreads; i++) {
                new Thread(this::triggerGenerator).start();
            }
        }
    }

    public void stop() {
        this.generatorSwitch.setOn(false);
    }


    @SneakyThrows
    private void triggerGenerator() {
        while(this.generatorSwitch.isOn()) {
            Thread.sleep(this.generatorSwitch.getSleepDuration());
            System.out.println(this.getVoteEvent());
        }
    }

    private VoteEvent getVoteEvent() {
        return VoteEvent.builder()
                .userId(randomGenerator.getUserId())
                .topicId(randomGenerator.getTopicId())
                .voteOptions(randomGenerator.getVote())
                .build();
    }


    @Data
    private static class GeneratorSwitch {
        private boolean on = false;
        private long sleepDuration = 1000;

        public void setSleepDuration(long sleepDuration) {
            if(sleepDuration > 0) {
                this.sleepDuration = sleepDuration;
            }
        }
    }


}
