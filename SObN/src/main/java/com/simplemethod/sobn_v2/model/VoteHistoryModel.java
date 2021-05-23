package com.simplemethod.sobn_v2.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({
        "currentProblem",
        "currentProblemVotes",
        "finalVoteOfAcceptors"
})
@Getter
@Setter
@Resource
public class VoteHistoryModel {

    String currentProblem;
    List<String> currentProblemVotes = new ArrayList<>();
    String finalVoteOfAcceptors;

    public VoteHistoryModel() {
        super();
    }

}
