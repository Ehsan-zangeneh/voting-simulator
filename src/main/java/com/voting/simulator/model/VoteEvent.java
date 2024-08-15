package com.voting.simulator.model;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class VoteEvent {
    String userId;
    String topicId;
    VoteOptions voteOptions;
}
