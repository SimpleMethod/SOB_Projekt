package com.simplemethod.sonb.Acceptor.services;

import com.simplemethod.sonb.Acceptor.model.StateDto;
import com.simplemethod.sonb.Acceptor.model.Vote;
import com.simplemethod.sonb.Acceptor.model.VotingContext;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaxosStateService {

    private final AcceptorsSyncService syncService;

    private List<VotingContext> votingSessions = new ArrayList<>();

    @Getter
    private BigInteger sequenceNumber = BigInteger.ONE;

    @Setter
    private Integer currentFault;

    public VotingContext getCurrentVotingSession() {
        return votingSessions.isEmpty() ? null : votingSessions.get(votingSessions.size() - 1);
    }

    public void startNewVotingSession(String newProblem) {
        VotingContext votingContext = new VotingContext();
        votingContext.setCurrentProblem(newProblem);
        votingSessions.add(votingContext);
        syncService.syncAcceptors(votingSessions);
    }

    public StateDto getStateDto() {
        VotingContext currentVotingSession = getCurrentVotingSession();

        List<String> votes = currentVotingSession.getVotes()
                .stream()
                .map(Vote::getMessage)
                .collect(Collectors.toList());

        int nextSequenceNumber = currentVotingSession.getVotes().size() + 1;
        String problem = currentVotingSession.getCurrentProblem();

        return new StateDto(votes, problem, nextSequenceNumber);
    }

    public boolean tryToAddVote(BigInteger sequenceId, String message) {
        VotingContext cvs = getCurrentVotingSession();

        if (syncService.checkIsNextSequenceIdValid(cvs, sequenceId)) {
            cvs.getVotes().add(new Vote(sequenceId, message));
            syncService.syncAcceptors(votingSessions);

            //TODO return Accept
            return true;
        } else {
            //TODO return Reject
            return false;
        }

    }

    public long getAcceptorId() {
        return 0;
    }
}
