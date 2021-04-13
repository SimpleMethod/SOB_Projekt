package com.simplemethod.sobn.controllers;

import com.simplemethod.sobn.interfaces.ProposerInterface;
import com.simplemethod.sobn.models.PromiseModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

@RestController
public class ProposerController implements ProposerInterface {

    @Override
    public ResponseEntity<Object> promiseAcceptor(BigInteger acceptorID, PromiseModel promiseModel) {
        return null;
    }
}
