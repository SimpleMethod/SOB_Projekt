package com.simplemethod.sonb.Acceptor.services;

import com.simplemethod.sonb.Acceptor.model.AcceptorModel;
import com.simplemethod.sonb.Acceptor.model.VotingSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AcceptorAppState {

    private List<AcceptorModel> acceptors = Stream.of(new AcceptorModel(),new AcceptorModel(),new AcceptorModel()).collect(Collectors.toList());


    public VotingSession getCurrentVotingSession(int acceptorId) {
        List<VotingSession> votingSessions = acceptors.get(acceptorId).getVotingSessions();
        return votingSessions.isEmpty() ? null : votingSessions.get(votingSessions.size() - 1);
    }

    public AcceptorModel getAcceptor(int acceptorId) {
        return acceptors.get(acceptorId);
    }



}
