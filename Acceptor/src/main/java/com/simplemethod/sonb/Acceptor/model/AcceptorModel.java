package com.simplemethod.sonb.Acceptor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigInteger;

@Getter
@AllArgsConstructor
public class AcceptorModel {
    private BigInteger acceptorID;
    private BigInteger sequenceNumber = BigInteger.ZERO;
    private String proposerValue;
    private boolean failureAcceptor = false;
}