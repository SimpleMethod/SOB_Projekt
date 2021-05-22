package com.simplemethod.sonb.Acceptor.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simplemethod.sonb.Acceptor.model.AcceptorResponseModel;
import com.simplemethod.sonb.Acceptor.model.VoteHistoryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class VoteResultService {

    @Bean
    public RestTemplate restTemplate() {
        final HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(2000);
        requestFactory.setReadTimeout(2000);
        return new RestTemplate(requestFactory);
    }

    @Autowired
    RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    ;


    public VoteHistoryModel getVoteResult() {
        List<String> votes = new ArrayList<>();
        VoteHistoryModel resultModel = new VoteHistoryModel();
        try {
            for (int i = 0; i < 3; i++) {
                final String uri = String.format("http://localhost:2120/acceptor/%d/fetch-acceptor-state", i);
                String resultAsJsonStr = restTemplate.getForObject(uri, String.class);
                AcceptorResponseModel acceptorResponseModel = objectMapper.readValue(resultAsJsonStr, AcceptorResponseModel.class);
                if (!Objects.isNull(acceptorResponseModel.getCurrentProblemVotes())) {
                    votes.addAll(acceptorResponseModel.getCurrentProblemVotes());
                }
                if (!Objects.isNull(acceptorResponseModel.getCurrentProblem())) {
                    resultModel.setCurrentProblem(acceptorResponseModel.getCurrentProblem());
                } else {
                    resultModel.setCurrentProblem("");
                }
            }
            resultModel.setCurrentProblemVotes(votes);
            resultModel.setFinalVoteOfAcceptors(compareQuorumOfVotes(votes));
            return resultModel;
        } catch (JsonProcessingException ignored) {
        }
        return null;
    }

    private String compareQuorumOfVotes(List<String> votes) {
        long voteYes = votes.stream().filter("Tak"::equals).count();
        long voteNo = votes.stream().filter("Nie"::equals).count();

        if (voteYes > voteNo) {
            return "Tak";
        } else if (voteNo > voteYes) {
            return "Nie";
        } else {
            return "Nie rozstrzygnięto";
        }
    }
}
