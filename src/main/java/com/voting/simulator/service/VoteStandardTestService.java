package com.voting.simulator.service;

import com.voting.simulator.common.VotingConstant;
import com.voting.simulator.common.stream.StreamPublisher;
import com.voting.simulator.common.stream.model.EventMessage;
import com.voting.simulator.model.VoteEvent;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Setter
@Slf4j
public class VoteStandardTestService {

    private final VoteEventRandomGenerator randomGenerator;
    private final StreamPublisher publisher;

    public void start() {
        new Thread(this::triggerGenerator).start();
        new Thread(this::triggerGenerator).start();
    }

    @SneakyThrows
    private void triggerGenerator() {
        int counter = 250_000;
        while(counter >= 1) {
            Thread.sleep(1);
            this.publisher.send(EventMessage.builder()
                            .message(this.getVoteEvent())
                            .channelName(VotingConstant.VOTE_CHANNEL_OUT)
                    .build());
            counter--;
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

}
