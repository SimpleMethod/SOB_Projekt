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
        "sequenceNumber",
        "proposerValue"
})
@Resource
@Getter
@Setter
@AllArgsConstructor
public class PromiseModel {

    private BigInteger sequenceNumber= BigInteger.valueOf(0);
    private String proposerValue;

    public PromiseModel(String proposerValue) {
        this.proposerValue = proposerValue;
    }
}
