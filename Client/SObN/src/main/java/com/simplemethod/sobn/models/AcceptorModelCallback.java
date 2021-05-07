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
        "failureAcceptor",
        "faultType"
})
@Resource
@Getter
@Setter
@AllArgsConstructor
public class AcceptorModelCallback {

    private BigInteger acceptorID;
    private BigInteger sequenceNumber= BigInteger.valueOf(0);
    private String proposerValue;
    private boolean failureAcceptor=false;
    private BigInteger faultType;

    public AcceptorModelCallback(BigInteger acceptorID, String proposerValue) {
        this.acceptorID = acceptorID;
        this.proposerValue = proposerValue;
    }

    public AcceptorModelCallback() {
        super();
    }
}
