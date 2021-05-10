package com.simplemethod.sobn.controllers;

import com.simplemethod.sobn.interfaces.SimulatorInterface;
import com.simplemethod.sobn.models.AcceptorModel;
import com.simplemethod.sobn.models.AcceptorModelCallback;
import com.simplemethod.sobn.models.PromiseModel;
import com.simplemethod.sobn.services.ProposerService;
import com.simplemethod.sobn.services.SimulatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

@RestController
public class SimulatorController implements SimulatorInterface {

    @Autowired
    SimulatorService simulatorService;

    @Autowired
    ProposerService proposerService;

    @Override
    public ResponseEntity<AcceptorModel> getAcceptorInfo(BigInteger acceptorID) {
        AcceptorModelCallback acceptorModel = simulatorService.getAcceptorInfo(acceptorID);
        AcceptorModel acceptorModel1 = new AcceptorModel(acceptorModel.getAcceptorID(),acceptorModel.getSequenceNumber(),acceptorModel.getProposerValue(),acceptorModel.isFailureAcceptor(),acceptorModel.getFaultType());
        return new ResponseEntity<>(acceptorModel1, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> updateProposerValue(PromiseModel promiseModel) {
        proposerService.makePaxosCommunication(promiseModel.getProposerValue());
        return null;
    }

    @Override
    public ResponseEntity<Object> acceptorFault(BigInteger acceptorID, short typeFault) {
        boolean requestStatus = simulatorService.updateFatalError(acceptorID, typeFault);
        if (requestStatus) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Object> repairAcceptor(BigInteger acceptorID) {
        boolean requestStatus = simulatorService.removeFatalError(acceptorID);
        if (requestStatus) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

}
