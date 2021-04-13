package com.simplemethod.sonb.Acceptor.controllers;


import com.simplemethod.sonb.Acceptor.services.PaxosStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/acceptor/")
@RequiredArgsConstructor
public class AcceptorAPI {

    private final PaxosStateService state;

    @PostMapping("/sync")
    public void sync() {
        //TODO Sync acceptors
    }

    @PostMapping("/start-new-session")
    public void startNewSession(@RequestParam String newProblem) {
        state.startNewVotingSession(newProblem);
    }
}
