package com.simplemethod.sonb.Acceptor.services;

import com.simplemethod.sonb.Acceptor.model.AcceptedNotificationModel;
import com.simplemethod.sonb.Acceptor.model.AcceptorModel;
import com.simplemethod.sonb.Acceptor.model.AcceptorResponseModel;
import com.simplemethod.sonb.Acceptor.model.ProposeRequestModel;
import com.simplemethod.sonb.Acceptor.model.VotingSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AcceptorPaxosLogicService {

    private final AcceptorAppState acceptorAppState;

    public AcceptorResponseModel getStateDto(Integer acceptorId) {
        AcceptorModel acceptor = acceptorAppState.getAcceptor(acceptorId);
        if (acceptor.getCurrentError() == 1) {
            //TODO KP&MaMr handle error of type 1
        } else if (acceptor.getCurrentError() == 2) {
            //TODO KP&MaMr handle error of type 2
        }

        VotingSession currentVotingSession = acceptorAppState.getCurrentVotingSession(acceptorId);

        return new AcceptorResponseModel(
                true,
                currentVotingSession.getCurrentProblem(),
                currentVotingSession.getVotes()
        );
    }

    public boolean isSequenceCorrect(Integer acceptorId, ProposeRequestModel requestModel) {
        return acceptorAppState.getAcceptor(acceptorId).getCurrentSequenceNumber() == requestModel.getSequenceNumber();
    }

    public void acceptNewVotingSession(Integer acceptorId, AcceptedNotificationModel accepted) {
        AcceptorModel acceptor = acceptorAppState.getAcceptor(acceptorId);

        checkAcceptError(accepted, acceptor);

        acceptor.getVotingSessions().add(new VotingSession(accepted.getAcceptedValue()));

        acceptor.setCurrentSequenceNumber(accepted.getNewSequenceId());
    }

    public void acceptNewVote(Integer acceptorId, AcceptedNotificationModel accepted) {
        AcceptorModel acceptor = acceptorAppState.getAcceptor(acceptorId);

        checkAcceptError(accepted, acceptor);

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
        acceptor.setCurrentError(null);
    }

    public void addToStaging(Integer acceptorId, String message) {
        AcceptorModel acceptor = acceptorAppState.getAcceptor(acceptorId);
        acceptor.setStagingArea(message);
    }
}
