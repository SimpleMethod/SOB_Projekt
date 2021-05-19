package com.simplemethod.sonb.Acceptor.services;

import com.simplemethod.sonb.Acceptor.model.AcceptedNotificationModel;
import com.simplemethod.sonb.Acceptor.model.AcceptorModel;
import com.simplemethod.sonb.Acceptor.model.AcceptorResponseModel;
import com.simplemethod.sonb.Acceptor.model.ProposeRequestModel;
import com.simplemethod.sonb.Acceptor.model.VotingSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AcceptorPaxosLogicService {

    private final AcceptorAppState acceptorAppState;
    private  BigInteger prevSeqNumber=BigInteger.valueOf(0);
    public AcceptorResponseModel getStateDto(Integer acceptorId) {
        AcceptorModel acceptor = acceptorAppState.getAcceptor(acceptorId);

        //TODO: Store the previous sequence number and return it during a fault. //MM
        if (acceptor.getCurrentError() == 1) {
            //TODO KP&MaMr handle error of type 1
        } else if (acceptor.getCurrentError() == 2) {
            //TODO KP&MaMr handle error of type 2
        }

        VotingSession currentVotingSession = acceptorAppState.getCurrentVotingSession(acceptorId);
        if (acceptor.getCurrentError()!=0 || Objects.isNull(currentVotingSession)) {
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
        prevSeqNumber=BigInteger.valueOf(acceptorAppState.getAcceptor(acceptorId).getCurrentSequenceNumber());
        return acceptorAppState.getAcceptor(acceptorId).getCurrentSequenceNumber() == requestModel.getSequenceNumber();
    }

    public void acceptNewVotingSession(Integer acceptorId, AcceptedNotificationModel accepted) {
        AcceptorModel acceptor = acceptorAppState.getAcceptor(acceptorId);

        //checkAcceptError(accepted, acceptor); //TODO: Verify that the error checking is working correctly //MM

        acceptor.getVotingSessions().add(new VotingSession(accepted.getAcceptedValue()));

        acceptor.setCurrentSequenceNumber(accepted.getNewSequenceId());
    }

    public void acceptNewVote(Integer acceptorId, AcceptedNotificationModel accepted) {
        AcceptorModel acceptor = acceptorAppState.getAcceptor(acceptorId);

        //checkAcceptError(accepted, acceptor);

        acceptor.getCurrentVotingSession().getVotes().add(accepted.getAcceptedValue());

        acceptor.setCurrentSequenceNumber(accepted.getNewSequenceId());
    }

    private void checkAcceptError(AcceptedNotificationModel accepted, AcceptorModel acceptor) {
        if (!acceptor.getStagingArea().equals(accepted.getAcceptedValue())) {
            throw new IllegalStateException("Cannot accept request if proposed value was different");
        }
    }

    public void enableError(Integer acceptorId, ProposeRequestModel propose) {
        AcceptorModel acceptor = acceptorAppState.getAcceptor(acceptorId);
        acceptor.setCurrentError(Integer.valueOf(propose.getMessage()));
    }

    public void disableError(Integer acceptorId) {
        AcceptorModel acceptor = acceptorAppState.getAcceptor(acceptorId);
        acceptor.setCurrentError(0);
    }

    public void addToStaging(Integer acceptorId, String message) {
        AcceptorModel acceptor = acceptorAppState.getAcceptor(acceptorId);
        acceptor.setStagingArea(message);
    }
}
