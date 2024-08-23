package com.voting.simulator.model;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Builder
@Value
public class VoteEvent {
    String userId;
    String topicId;
    VoteOptions voteOptions;
    LocalDateTime createdDate;
}
