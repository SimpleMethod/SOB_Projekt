package com.simplemethod.sobn.controllers;

import com.simplemethod.sobn.enums.FaultsEnum;
import com.simplemethod.sobn.interfaces.SimulatorInterface;
import com.simplemethod.sobn.models.AcceptorModel;
import com.simplemethod.sobn.models.PromiseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigInteger;

@RestController
public class SimulatorController implements SimulatorInterface {

    @Override
    public ResponseEntity<AcceptorModel> getAcceptorInfo(BigInteger acceptorID) {

       final short failure= (short) Math.round(Math.random());
       boolean acceptorState=false;
        if(failure==1)
        {
            acceptorState=true;
        }
        AcceptorModel acceptorModel = new AcceptorModel(acceptorID,acceptorID.add(BigInteger.valueOf(1)),"temp",acceptorState);
        return new ResponseEntity<>(acceptorModel, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> updateProposerValue(PromiseModel promiseModel) {
        //TODO: Odebranie wiadomości od frontu i przesłanie jej dalej
        return null;
    }

    @Override
    public ResponseEntity<Object> acceptorFault(BigInteger acceptorID, short typeFault) {
        //TODO: Odebranie wiadomości od frontu i przesłanie jej dalej
        return null;
    }

    @Override
    public ResponseEntity<Object> repairAcceptor(BigInteger acceptorID) {
        //TODO: Odebranie wiadomości od frontu i przesłanie jej dalej
        return null;
    }
}
