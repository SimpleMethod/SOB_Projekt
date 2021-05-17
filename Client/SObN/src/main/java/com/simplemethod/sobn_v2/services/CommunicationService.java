package com.simplemethod.sobn_v2.services;

import com.simplemethod.sobn_v2.model.AcceptedRequestModel;
import com.simplemethod.sobn_v2.model.AcceptorResponseModel;
import com.simplemethod.sobn_v2.model.ProposeRequestModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class CommunicationService {

    @Getter
    @AllArgsConstructor
    public enum AcceptorApi { //INFO: All urls given here, hits on endpoints in Acceptor application, class: AcceptorAPI
        ADD_NEW_PROBLEM("localhost:2120/acceptor/:acceptorId/add-new-problem",
                "localhost:2120/acceptor/:acceptorId/accepted-new-problem"),

        ADD_NEW_VOTE("localhost:2120/acceptor/:acceptorId/add-new-vote",
                "localhost:2120/acceptor/:acceptorId/accepted-new-vote"),

        ENABLE_ERROR("localhost:2120/acceptor/:acceptorId/enable-error", null),

        DISABLE_ERROR("localhost:2120/acceptor/:acceptorId/disable-error", null),

        FETCH_ACCEPTOR_STATE("localhost:2120/acceptor/:acceptorId/fetch-acceptor-state", null);

        private String proposeUrl;

        private String acceptedUrl;
    }

    public boolean sendProposeAndAwaitResponse(String effectiveUrl, ProposeRequestModel proposeRequestModel) {
        //TODO KP&MaMr send proposeRequestModel to given effectiveUrl and await response
        //Should return true if acceptor responded with true
        //Should return false if acceptor responded with false

        return false;
    }

    public void sendAccepted(String effectiveUrl, AcceptedRequestModel acceptedRequestModel) {
        //TODO KP&MaMr Send acceptedRequestModel at given effectiveUrl. No need of any additional operations or handling response
    }


    public void sendEnableErrorRequest(Integer acceptorId, Integer errorType) {
        String effectiveUrl = processToEffectiveUrl(AcceptorApi.ENABLE_ERROR.getProposeUrl(), acceptorId);
        ProposeRequestModel proposeRequestModel = new ProposeRequestModel(String.valueOf(errorType), null);

        //TODO KP&MaMr Send proposeRequestModel at given effectiveUrl. No need of any additional operations or handling response
    }

    public void sendDisableErrorRequest(Integer acceptorId) {
        String effectiveUrl = processToEffectiveUrl(AcceptorApi.DISABLE_ERROR.getProposeUrl(), acceptorId);

        //TODO KP&MaMr Send simple request at given effectiveUrl. No need of any additional operations or handling response
    }

    public AcceptorResponseModel readStateOfAcceptor(int acceptorId) {
        String effectiveUrl = processToEffectiveUrl(AcceptorApi.FETCH_ACCEPTOR_STATE.getProposeUrl(), acceptorId);

        //TODO KP&MaMr read data
        return null;
    }

    public String processToEffectiveUrl(String urlWithParam, Integer acceptorId) {
        return urlWithParam.replace(":acceptorId", String.valueOf(acceptorId));
    }
}
