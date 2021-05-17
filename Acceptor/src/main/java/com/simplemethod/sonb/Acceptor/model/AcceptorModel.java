package com.simplemethod.sonb.Acceptor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AcceptorModel {

    private Integer currentError;
    private Integer currentSequenceNumber = 1;
    private List<VotingSession> votingSessions = new ArrayList<>();

    private String stagingArea;

    public VotingSession getCurrentVotingSession() {
        return votingSessions.isEmpty() ? null : votingSessions.get(votingSessions.size() - 1);
    }

}