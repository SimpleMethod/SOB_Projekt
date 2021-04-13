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
        "sequenceNumber",
        "proposerValue",
        "acceptorState"
})
@Resource
@Getter
@Setter
@AllArgsConstructor
public class AcceptorModel {

    private BigInteger acceptorID;
    private BigInteger sequenceNumber= BigInteger.valueOf(0);
    private String proposerValue;
    private boolean failureAcceptor=false;

    public AcceptorModel(BigInteger acceptorID, String proposerValue) {
        this.acceptorID = acceptorID;
        this.proposerValue = proposerValue;
    }
}
