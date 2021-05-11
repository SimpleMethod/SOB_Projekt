package com.simplemethod.sobn.services;

import com.simplemethod.sobn.controllers.MockController;
import com.simplemethod.sobn.models.AcceptorModel;
import com.simplemethod.sobn.models.AcceptorModelCallback;
import com.simplemethod.sobn.models.PromiseModel;
import com.simplemethod.sobn.models.VotingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.util.List;

@Service
public class SimulatorService {

    //TODO: Ustawić odpowiedni port dla drugiej aplikacji.

    @Bean
    public RestTemplate restTemplate() {
        final HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(2000);
        requestFactory.setReadTimeout(2000);
        return new RestTemplate(requestFactory);
    }

    @Autowired
    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    MockController mockController;

    private static BigInteger seqNumber = BigInteger.valueOf(1);

    public AcceptorModel getAcceptorInfo(BigInteger AcceptorID) {
       /*
        try {
            final String uri = String.format("http://localhost:2120/acceptor/%2d", AcceptorID);
            final AcceptorModelCallback response =  restTemplate.getForObject(uri, AcceptorModelCallback.class);
            return response;
        } catch (ResourceAccessException ignored) {
        }
        return new AcceptorModelCallback(AcceptorID, BigInteger.valueOf(-1), "-1", true, BigInteger.valueOf(-1));
        */
       return mockController.getAcceptorById(AcceptorID.intValue());
    }

    public boolean updateFatalError(BigInteger AcceptorID, short faultType) {
       /*
        try {
            final String uri = String.format("http://localhost:2120/acceptor/%2d/fault/%2d", AcceptorID, faultType);
            restTemplate.put(uri, String.class);
            return true;
        } catch (ResourceAccessException ignored) {
            ignored.printStackTrace();
        }
        */
        mockController.putAcceptorFault(AcceptorID.intValue(), (int) faultType);
        return false;
    }

    public boolean removeFatalError(BigInteger AcceptorID) {
       /*
        try {
            final String uri = String.format("http://localhost:2120/acceptor/%2d/fault", AcceptorID);
            restTemplate.delete(uri, String.class);
            return true;
        } catch (ResourceAccessException ignored) {
        }

        */
        mockController.deleteAcceptorFault(AcceptorID.intValue());
        return false;
    }


    public void updateValue(String getNewValue) {
        PromiseModel promiseModel = new PromiseModel(seqNumber,getNewValue);
        mockController.postPrepare(1,promiseModel);
      //  mockController.postPrepare(2,promiseModel);
      //  mockController.postPrepare(3,promiseModel);
        seqNumber = seqNumber.add(BigInteger.valueOf(1));
    }

    public List<VotingModel> getsVotingModels()
    {
        return mockController.getVotingModels();
    }
}
