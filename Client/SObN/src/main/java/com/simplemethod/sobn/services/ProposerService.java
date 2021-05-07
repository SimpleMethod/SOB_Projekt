package com.simplemethod.sobn.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simplemethod.sobn.models.PromiseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.util.HashMap;


@Service
public class ProposerService {

    @Autowired
    RestTemplate restTemplate = new RestTemplate();


    private static BigInteger seqNumber = BigInteger.valueOf(0);
    private final  ObjectMapper objectMapper = new ObjectMapper();;
    private final   HttpHeaders headers = new HttpHeaders();

    public HashMap<BigInteger, Boolean> makePaxosCommunication(String newValue) {
        HashMap<BigInteger,Boolean> response = new HashMap<>();
        response.put(BigInteger.valueOf(1), preparePromisePhase(BigInteger.valueOf(1), seqNumber, newValue));
        response.put(BigInteger.valueOf(2), preparePromisePhase(BigInteger.valueOf(2), seqNumber, newValue));
        response.put(BigInteger.valueOf(3),  preparePromisePhase(BigInteger.valueOf(3), seqNumber, newValue));
        seqNumber = seqNumber.add(BigInteger.valueOf(1));
        System.out.println("Numer sekwencyjny:"+seqNumber);
        response.forEach((k,v) -> System.out.println("Acceptor: "+k+" Wartość:"+v));
        return response;
    }

    public boolean preparePromisePhase(BigInteger AcceptorID, BigInteger seqNumber, String value) {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            final PromiseModel promiseModel = new PromiseModel(seqNumber, value);
            final String uri = String.format("http://localhost:2120/acceptor/%2d/prepare", AcceptorID);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(promiseModel), headers);
            String resultAsJsonStr = restTemplate.postForObject(uri, request, String.class);
            PromiseModel promiseModelCallback = objectMapper.readValue(resultAsJsonStr, PromiseModel.class);
            if(seqNumber.equals(promiseModelCallback.getSequenceNumber()))
            {
                return  acceptAcceptedPhase(AcceptorID,promiseModelCallback.getSequenceNumber(),promiseModelCallback.getProposerValue());
            }
            else
            {
                acceptAcceptedPhase(AcceptorID,BigInteger.valueOf(-1),"-1");
                return false;
            }


        } catch (ResourceAccessException | JsonProcessingException ignored ) {
        }
        return false;
    }

    public boolean acceptAcceptedPhase(BigInteger AcceptorID, BigInteger seqNumber, String value) {

        try {
            final PromiseModel promiseModel = new PromiseModel(seqNumber, value);
            final String uri = String.format("http://localhost:2120/acceptor/%2d/accept", AcceptorID);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(promiseModel), headers);
            String resultAsJsonStr = restTemplate.postForObject(uri, request, String.class);
            PromiseModel promiseModelCallback = objectMapper.readValue(resultAsJsonStr, PromiseModel.class);
            return seqNumber.equals(promiseModelCallback.getSequenceNumber());
        } catch (ResourceAccessException | HttpClientErrorException | JsonProcessingException ignored ) {
        }
        return false;
    }

}
