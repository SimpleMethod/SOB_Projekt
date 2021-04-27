package com.simplemethod.sonb.Acceptor.controllers;


import com.simplemethod.sonb.Acceptor.model.AcceptorModel;
import com.simplemethod.sonb.Acceptor.model.PromiseModel;
import com.simplemethod.sonb.Acceptor.services.PaxosStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

@RestController
@RequestMapping("/acceptor")
@RequiredArgsConstructor
public class AcceptorAPI {

    private final PaxosStateService state;

    //TODO Uncomment when/if we decide how to handle acceptor's logic
//    @PostMapping("/sync")
//    public void sync() {
//        //TODO Sync acceptors
//    }
//
//    @PostMapping("/start-new-session")
//    public void startNewSession(@RequestParam String newProblem) {
//        state.startNewVotingSession(newProblem);
//    }
//
//    @GetMapping("/state")
//    public StateDto getState() {
//        return state.getStateDto();
//    }
//
//    @PostMapping("/propose")
//    public void propose(@RequestParam int sequenceId, @RequestParam String message) {
//        state.tryToAddVote(sequenceId, message);
//    }


    @GetMapping("/{acceptorId}")
    public AcceptorModel getAcceptorById(@PathVariable Integer acceptorId) {
        //TODO Remove mock
        return new AcceptorModel(
                BigInteger.ONE,
                BigInteger.TEN,
                "Mocked proposer value",
                false
        );
    }

    @PutMapping("/{acceptorId}/fault/{faultType}")
    public AcceptorModel putAcceptorFault(@PathVariable Integer acceptorId, @PathVariable Integer faultType) {
        state.enableFault(faultType);
        //TODO Remove mock
        return new AcceptorModel(
                BigInteger.valueOf(11),
                BigInteger.valueOf(12),
                "Enabled fault of type: " + faultType,
                true
        );
    }

    @DeleteMapping("/{acceptorId}/fault")
    public AcceptorModel deleteAcceptorFault() {
        state.disableFault();
        //TODO Remove mock
        return new AcceptorModel(
                BigInteger.valueOf(16),
                BigInteger.valueOf(17),
                "Disabled Fault",
                false
        );
    }

    @PostMapping("/{acceptorId}/prepare")
    public PromiseModel postPrepare(@RequestBody PromiseModel model) {
        //TODO Prepare
        //TODO Remove mock
        return new PromiseModel(
                BigInteger.valueOf(145),
                "Mocked Prepare response"
        );
    }
}
