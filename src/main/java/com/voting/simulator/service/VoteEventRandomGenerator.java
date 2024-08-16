package com.voting.simulator.service;

import com.voting.simulator.model.VoteOptions;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class VoteEventRandomGenerator {
    private final Random r = new Random();
    private final int numberOfUsers = 5;
    private final int numberOfTopics = 30;

    private final int numberOfOptions = VoteOptions.values().length;

    public String getUserId() {
        return (r.nextInt(numberOfUsers) + 1) + "";
    }

    public String getTopicId() {
        return (r.nextInt(numberOfTopics) + 1) + "";
    }

    public VoteOptions getVote() {
         var ordinal = r.nextInt(numberOfOptions);
         return VoteOptions.values()[ordinal];
    }

}
