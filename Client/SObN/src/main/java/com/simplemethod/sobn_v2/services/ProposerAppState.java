package com.simplemethod.sobn_v2.services;

import com.simplemethod.sobn_v2.model.AcceptorStateSimplifiedModel;
import com.simplemethod.sobn_v2.model.ClientModel;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Getter
@Service
public class ProposerAppState {

    private List<AcceptorStateSimplifiedModel> acceptorsInfo = new ArrayList<>();

    private List<ClientModel> clients = new ArrayList<>();


    public ClientModel getCurrentClient(int clientId) {
        return clients.get(clientId);
    }

    public int getAcceptorsNumber() {
        return acceptorsInfo.size();
    }
}
