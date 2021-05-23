package com.simplemethod.sonb.Acceptor.services;

import com.simplemethod.sonb.Acceptor.model.AcceptedNotificationModel;
import com.simplemethod.sonb.Acceptor.model.AcceptorModel;
import com.simplemethod.sonb.Acceptor.model.AcceptorResponseModel;
import com.simplemethod.sonb.Acceptor.model.ProposeRequestModel;
import com.simplemethod.sonb.Acceptor.model.VotingSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AcceptorPaxosLogicService {

    private final AcceptorAppState acceptorAppState;
    private BigInteger prevSeqNumber = BigInteger.valueOf(0);
    private List<Integer> seqNumberError = new LinkedList<Integer>();

    public AcceptorResponseModel getStateDto(Integer acceptorId) {
        AcceptorModel acceptor = acceptorAppState.getAcceptor(acceptorId);

        prevSeqNumber = BigInteger.valueOf(acceptor.getCurrentSequenceNumber());

        //TODO: Store the previous sequence number and return it during a fault. //MM
        if (acceptor.getCurrentError() == 1) {

            seqNumberError.add(acceptor.getCurrentSequenceNumber());
            if (seqNumberError.size() > 1) {
                seqNumberError.remove(1);
            }
            return new AcceptorResponseModel(
                    true,
                    null,
                    null,
                    seqNumberError.get(0),
                    acceptor.getCurrentError()
            );
        } else if (acceptor.getCurrentError() == 2) {

            Random rand = new Random();
            return new AcceptorResponseModel(
                    true,
                    null,
                    null,
                    rand.nextInt(999),
                    acceptor.getCurrentError()
            );
        }

        VotingSession currentVotingSession = acceptorAppState.getCurrentVotingSession(acceptorId);
        if (/*acceptor.getCurrentError()!=0 ||*/ Objects.isNull(currentVotingSession)) {

            return new AcceptorResponseModel(
                    true,
                    null,
                    null,
                    prevSeqNumber.intValue(),
                    acceptor.getCurrentError()
            );
        }

        return new AcceptorResponseModel(
                true,
                currentVotingSession.getCurrentProblem(),
                currentVotingSession.getVotes(),
                acceptor.getCurrentSequenceNumber(),
                acceptor.getCurrentError()
        );

    }

    public boolean isSequenceCorrect(Integer acceptorId, ProposeRequestModel requestModel) {
        prevSeqNumber = BigInteger.valueOf(acceptorAppState.getAcceptor(acceptorId).getCurrentSequenceNumber());
        return acceptorAppState.getAcceptor(acceptorId).getCurrentSequenceNumber() == requestModel.getSequenceNumber();
    }

    public void acceptNewVotingSession(Integer acceptorId, AcceptedNotificationModel accepted) {
        AcceptorModel acceptor = acceptorAppState.getAcceptor(acceptorId);

        if (shouldAcceptMsg(accepted, acceptor)) {
            acceptor.getVotingSessions().add(new VotingSession(accepted.getAcceptedValue()));
            acceptor.setCurrentSequenceNumber(accepted.getNewSequenceId());
        } //TODO: Verify that the error checking is working correctly //MM
    }

    public void acceptNewVote(Integer acceptorId, AcceptedNotificationModel accepted) {
        AcceptorModel acceptor = acceptorAppState.getAcceptor(acceptorId);

        if (shouldAcceptMsg(accepted, acceptor)) {
            acceptor.getCurrentVotingSession().getVotes().add(accepted.getAcceptedValue());
            acceptor.setCurrentSequenceNumber(accepted.getNewSequenceId());
        }

    }

    private boolean shouldAcceptMsg(AcceptedNotificationModel accepted, AcceptorModel acceptor) {

        //TODO jeśli awaria to nie może przesłać głosu
/*
        if (!acceptor.getStagingArea().equals(accepted.getAcceptedValue())) {

            throw new IllegalStateException("Cannot accept request if proposed value was different");
        }
*/

        return acceptor.getCurrentSequenceNumber().equals(accepted.getNewSequenceId()) && acceptor.getCurrentError() == 0;

    }

    public void enableError(Integer acceptorId, ProposeRequestModel propose) {
        AcceptorModel acceptor = acceptorAppState.getAcceptor(acceptorId);
        acceptor.setCurrentError(Integer.valueOf(propose.getMessage()));
    }

    public void disableError(Integer acceptorId) {
        seqNumberError.clear();
        AcceptorModel acceptor = acceptorAppState.getAcceptor(acceptorId);
        acceptor.setCurrentError(0);
    }

    public void addToStaging(Integer acceptorId, String message) {
        AcceptorModel acceptor = acceptorAppState.getAcceptor(acceptorId);
        acceptor.setStagingArea(message);
    }
}
