package com.simplemethod.sonb.Acceptor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromiseModel {
    private BigInteger sequenceNumber = BigInteger.ZERO;
    //wartość z akceptora
    private String proposerValue;
}
