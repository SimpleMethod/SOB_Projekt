package com.simplemethod.sobn_v2.services;

import com.simplemethod.sobn_v2.model.AcceptedRequestModel;
import com.simplemethod.sobn_v2.model.AcceptorResponseModel;
import com.simplemethod.sobn_v2.model.ClientModel;
import com.simplemethod.sobn_v2.model.ProposeRequestModel;
import com.simplemethod.sobn_v2.services.CommunicationService.AcceptorApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProposerPaxosLogicService {

    private final ProposerAppState proposerAppState;
    private final CommunicationService communicationService;

    public void startNewVotingProblem(String problemName, Integer clientId) {
        ProposeRequestModel proposeRequestModel = new ProposeRequestModel(problemName, null);

        sendProposeAndHandlePaxos(clientId, AcceptorApi.ADD_NEW_PROBLEM, proposeRequestModel);
    }

    public void addNewVote(String vote, Integer clientId) {
        ProposeRequestModel proposeRequestModel = new ProposeRequestModel(vote,
                proposerAppState.getCurrentClient(clientId).getSequenceNumber());

        sendProposeAndHandlePaxos(clientId, AcceptorApi.ADD_NEW_VOTE, proposeRequestModel);
    }

    private void sendProposeAndHandlePaxos(Integer clientId, AcceptorApi operation, ProposeRequestModel proposeRequestModel) {
        int acceptedRequests = 0;

        for (int i = 0; i < proposerAppState.getAcceptorsNumber(); ++i) {
            if (sendProposeToAcceptor(i, operation, proposeRequestModel)) {
                ++acceptedRequests;
            }
        }

        if (!isAcceptedByMajority(acceptedRequests)) {
            return;
        }

        for (int i = 0; i < proposerAppState.getAcceptorsNumber(); ++i) {
            proposeRequestsAccepted(i, clientId, operation, proposeRequestModel.getMessage());
        }
    }

    private boolean sendProposeToAcceptor(int acceptorId, AcceptorApi operation, ProposeRequestModel proposeRequestModel) {
        String effectiveUrl = communicationService.processToEffectiveUrl(operation.getProposeUrl(), acceptorId);
        return communicationService.sendProposeAndAwaitResponse(effectiveUrl, proposeRequestModel);
    }

    private boolean isAcceptedByMajority(int acceptedRequests) {
        return (float) acceptedRequests / proposerAppState.getAcceptorsNumber() > 0.5f;
    }

    private void proposeRequestsAccepted(int acceptorId, Integer clientId, AcceptorApi operation, String acceptedMessage) {
        ClientModel currentClient = proposerAppState.getCurrentClient(clientId);
        currentClient.setSequenceNumber(currentClient.getSequenceNumber() + 1);

        String effectiveUrl = communicationService.processToEffectiveUrl(operation.getProposeUrl(), acceptorId);

        AcceptedRequestModel acceptedRequestModel = new AcceptedRequestModel(currentClient.getSequenceNumber(), acceptedMessage);
        communicationService.sendAccepted(effectiveUrl, acceptedRequestModel);
    }

    public ClientModel getClientData(Integer clientId) {
        //TODO KP&MaMr Right now data is loaded only from first acceptor, is is alright ?
        AcceptorResponseModel model = communicationService.readStateOfAcceptor(0);

        ClientModel currentClient = proposerAppState.getCurrentClient(clientId);

        currentClient.setCurrentProblemName(model.getCurrentProblem());
        currentClient.setCurrentProblemVotes(model.getCurrentProblemVotes());

        return currentClient;
    }
}
