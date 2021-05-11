package com.simplemethod.sobn.models;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import org.springframework.context.annotation.Scope;

import javax.annotation.Resource;
import java.math.BigInteger;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "acceptorID",
        "sequenceNumber",
        "proposerValue",
        "acceptorState",
        "faultType",
        "votingValue"
})
@Resource
@Getter
@Setter
@AllArgsConstructor
@Scope("singleton")
public class AcceptorModel {

    private BigInteger acceptorID;
    private BigInteger sequenceNumber= BigInteger.valueOf(0);
    private String proposerValue;
    private boolean failureAcceptor=false;
    private BigInteger faultType;
    private boolean votingValue = false;
    public AcceptorModel(BigInteger acceptorID, String proposerValue) {
        this.acceptorID = acceptorID;
        this.proposerValue = proposerValue;
    }
}
