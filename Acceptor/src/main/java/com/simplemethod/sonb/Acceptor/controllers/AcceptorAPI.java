package com.simplemethod.sonb.Acceptor.controllers;


import com.simplemethod.sonb.Acceptor.model.AcceptorModel;
import com.simplemethod.sonb.Acceptor.model.PromiseModel;
import com.simplemethod.sonb.Acceptor.model.StateDto;
import com.simplemethod.sonb.Acceptor.services.PaxosStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/start-new-session")
    public void startNewSession(@RequestParam String newProblem) {
        state.startNewVotingSession(newProblem);
    }
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
    public StateDto getAcceptorById(@PathVariable Integer acceptorId) {
//        //TODO Remove mock
//        return new AcceptorModel(
//                BigInteger.ONE,
//                BigInteger.TEN,
//                "Mocked proposer value",
//                false
//        );


        return state.getStateDto();
    }

    @PutMapping("/{acceptorId}/fault/{faultType}")
    public AcceptorModel putAcceptorFault(@PathVariable Integer acceptorId, @PathVariable Integer faultType) {
        state.setCurrentFault(faultType);
        //TODO po to jest acceptorId
        return new AcceptorModel(
                BigInteger.valueOf(state.getAcceptorId()),
                state.getSequenceNumber(),
                "Enabled fault of type: " + faultType,
                true,
                null
        );
    }

    @DeleteMapping("/{acceptorId}/fault")
    public AcceptorModel deleteAcceptorFault() {
        state.setCurrentFault(null);

        return new AcceptorModel(
                BigInteger.valueOf(state.getAcceptorId()),
                state.getSequenceNumber(),
                "Disabled Fault",
                false,
                null
        );
    }

    @GetMapping("/{acceptorId}/prepare")
//    @PostMapping("/{acceptorId}/prepare")
//    public PromiseModel postPrepare(@RequestBody PromiseModel model) {
    public PromiseModel postPrepare( @RequestParam BigInteger sequenceNumber, @RequestParam String proposerValue) {

        boolean succes = state.tryToAddVote(sequenceNumber, proposerValue);
        //co zwracać?
        return new PromiseModel(
                state.getSequenceNumber(),
                succes ? "Succes" : "Failure"
        );

    }
}
