package com.simplemethod.sobn_v2.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simplemethod.sobn.models.PromiseModel;
import com.simplemethod.sobn_v2.model.AcceptedRequestModel;
import com.simplemethod.sobn_v2.model.AcceptorResponseModel;
import com.simplemethod.sobn_v2.model.ProposeRequestModel;
import com.simplemethod.sobn_v2.model.VoteHistoryModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class CommunicationService {

    @Bean
    public RestTemplate restTemplate() {
        final HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(2000);
        requestFactory.setReadTimeout(2000);
        return new RestTemplate(requestFactory);
    }

    @Autowired
    RestTemplate restTemplate = new RestTemplate();

    private final ObjectMapper objectMapper = new ObjectMapper();;
    private final HttpHeaders headers = new HttpHeaders();
    private List<VoteHistoryModel> listOfVoteHistoryModels = new ArrayList<>();

    public List<VoteHistoryModel> readHistory()
    {
        return listOfVoteHistoryModels;
    }

    public void readSingleHistory()
    {
        String effectiveUrl = processToEffectiveUrl(AcceptorApi.VOTE_HISTORY.getProposeUrl(),null);
        VoteHistoryModel voteHistoryModel = restTemplate.getForObject(effectiveUrl, VoteHistoryModel.class);
        if(!Objects.isNull(voteHistoryModel))
        {
            listOfVoteHistoryModels.add(voteHistoryModel);
        }
    }

    @Getter
    @AllArgsConstructor
    public enum AcceptorApi { //INFO: All urls given here, hits on endpoints in Acceptor application, class: AcceptorAPI
        ADD_NEW_PROBLEM("http://localhost:2120/acceptor/:acceptorId/add-new-problem","http://localhost:2120/acceptor/:acceptorId/accepted-new-problem"),
        ADD_NEW_VOTE("http://localhost:2120/acceptor/:acceptorId/add-new-vote",
                "http://localhost:2120/acceptor/:acceptorId/accepted-new-vote"),
        ENABLE_ERROR("http://localhost:2120/acceptor/:acceptorId/enable-error", null),
        DISABLE_ERROR("http://localhost:2120/acceptor/:acceptorId/disable-error", null),
        FETCH_ACCEPTOR_STATE("http://localhost:2120/acceptor/:acceptorId/fetch-acceptor-state", null),
        VOTE_HISTORY("http://localhost:2120/acceptor/vote/history", null);

        private String proposeUrl;

        private String acceptedUrl;
    }

    public boolean sendProposeAndAwaitResponse(String effectiveUrl, ProposeRequestModel proposeRequestModel){
        //TODO KP&MaMr send proposeRequestModel to given effectiveUrl and await response
        //Should return true if acceptor responded with true
        //Should return false if acceptor responded with false

        try {

            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(proposeRequestModel), headers);
            String resultAsJsonStr = restTemplate.postForObject(effectiveUrl, request, String.class);
             AcceptorResponseModel acceptorResponseModel = objectMapper.readValue(resultAsJsonStr, AcceptorResponseModel.class);
             return acceptorResponseModel.getRequestAccepted();
        } catch (JsonProcessingException ignored)
        {

        }

        return false;
    }

    public void sendAccepted(String effectiveUrl, AcceptedRequestModel acceptedRequestModel) {
        try {
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(acceptedRequestModel), headers);
            restTemplate.postForObject(effectiveUrl, request, String.class);
        }
        catch (HttpClientErrorException | JsonProcessingException ignored)
        {
        }
        //TODO KP&MaMr Send acceptedRequestModel at given effectiveUrl. No need of any additional operations or handling response
    }


    public void sendEnableErrorRequest(Integer acceptorId, Integer errorType)  {
        String effectiveUrl = processToEffectiveUrl(AcceptorApi.ENABLE_ERROR.getProposeUrl(), acceptorId);

        try {
            ProposeRequestModel proposeRequestModel = new ProposeRequestModel(String.valueOf(errorType), null);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(proposeRequestModel), headers);
            restTemplate.postForObject(effectiveUrl, request, String.class);
        }
        catch (HttpClientErrorException | JsonProcessingException ignored)
        {
        }
        //TODO KP&MaMr Send proposeRequestModel at given effectiveUrl. No need of any additional operations or handling response
    }

    public void sendDisableErrorRequest(Integer acceptorId) {
        String effectiveUrl = processToEffectiveUrl(AcceptorApi.DISABLE_ERROR.getProposeUrl(), acceptorId);
        try {
            restTemplate.postForObject(effectiveUrl, null, String.class);
        }
        catch (HttpClientErrorException ignored)
        {

        }
        //TODO KP&MaMr Send simple request at given effectiveUrl. No need of any additional operations or handling response
    }

    public AcceptorResponseModel readStateOfAcceptor(int acceptorId) {
        String effectiveUrl = processToEffectiveUrl(AcceptorApi.FETCH_ACCEPTOR_STATE.getProposeUrl(), acceptorId);
        try {
            return restTemplate.getForObject(effectiveUrl, AcceptorResponseModel.class);
        } catch (ResourceAccessException ignored) {
        }
        return null;
    }

    public String processToEffectiveUrl(String urlWithParam, Integer acceptorId) {
        return urlWithParam.replace(":acceptorId", String.valueOf(acceptorId));
    }
}
