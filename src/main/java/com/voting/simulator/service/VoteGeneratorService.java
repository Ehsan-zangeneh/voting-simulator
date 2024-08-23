package com.voting.simulator.service;

import com.voting.simulator.common.VotingConstant;
import com.voting.simulator.common.stream.StreamPublisher;
import com.voting.simulator.common.stream.model.EventMessage;
import com.voting.simulator.model.VoteEvent;
import com.voting.simulator.service.dto.GeneratorSwitchDto;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "simulator.thread.settings")
@Setter
@Slf4j
public class VoteGeneratorService {

    private final VoteEventRandomGenerator randomGenerator;
    private final StreamPublisher publisher;
    private int minimumNumberOfThread;
    private int maximumNumberOfThread;
    private final GeneratorSwitch generatorSwitch = new GeneratorSwitch();

    public void start(GeneratorSwitchDto switchDto) {
        this.generatorSwitch.setSleepDuration(switchDto.getDurationSleep());
        log.info("event generation period is set to {} ms", switchDto.getDurationSleep());
        if(!this.generatorSwitch.isOn()) {
            log.info("Number of threads must be between {} and {}", minimumNumberOfThread, maximumNumberOfThread);
            setOffTheGenerator(switchDto.getNumberOfThreads());
        }
    }

    public void stop() {
        if(generatorSwitch.isOn()) {
            this.generatorSwitch.setOn(false);
            log.info("generator is switched off");
        }
    }

    private void setOffTheGenerator(int numberOfThread) {
        var numberOfThreads = setNumberOfThreads(numberOfThread);
        this.generatorSwitch.setOn(true);
        for(int i = minimumNumberOfThread; i <= numberOfThreads; i++) {
            new Thread(this::triggerGenerator).start();
        }
        log.info("generator is switched on with {} thread(s)", numberOfThreads);
    }

    @SneakyThrows
    private void triggerGenerator() {
        while(this.generatorSwitch.isOn()) {
            Thread.sleep(this.generatorSwitch.getSleepDuration());
            this.publisher.send(EventMessage.builder()
                            .message(this.getVoteEvent())
                            .channelName(VotingConstant.VOTE_CHANNEL_OUT)
                    .build());
        }
    }

    private VoteEvent getVoteEvent() {
        return VoteEvent.builder()
                .userId(randomGenerator.getUserId())
                .topicId(randomGenerator.getTopicId())
                .voteOptions(randomGenerator.getVote())
                .createdDate(LocalDateTime.now())
                .build();
    }

    private int setNumberOfThreads(int suggestedNumberOfThreads) {
        return suggestedNumberOfThreads < minimumNumberOfThread || suggestedNumberOfThreads > maximumNumberOfThread
                ? minimumNumberOfThread: suggestedNumberOfThreads;
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
