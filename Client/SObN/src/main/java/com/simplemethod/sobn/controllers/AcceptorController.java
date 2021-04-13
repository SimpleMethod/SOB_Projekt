package com.simplemethod.sobn.controllers;

import com.simplemethod.sobn.interfaces.AcceptorInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

@RestController
public class AcceptorController implements AcceptorInterface {

    @Override
    public ResponseEntity<Object> acceptedAcceptor(BigInteger acceptorID, BigInteger seqNumber) {
        return null;
    }
}
