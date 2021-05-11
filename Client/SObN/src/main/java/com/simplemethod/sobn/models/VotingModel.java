package com.simplemethod.sobn.models;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Resource;
import java.math.BigInteger;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "acceptorID",
        "votingValue",
        "acceptorResponse"
})
@Resource
@Getter
@Setter
@AllArgsConstructor
public class VotingModel {
    BigInteger acceptorID;
    String votingValue;
    Boolean acceptorResponse;
    public VotingModel() {
        super();
    }
}
