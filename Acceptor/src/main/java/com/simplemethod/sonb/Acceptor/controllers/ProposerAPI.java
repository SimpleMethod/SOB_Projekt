package com.simplemethod.sonb.Acceptor.controllers;

import com.simplemethod.sonb.Acceptor.model.StateDto;
import com.simplemethod.sonb.Acceptor.services.PaxosStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/proposer")
@RequiredArgsConstructor
public class ProposerAPI {

    private final PaxosStateService stateService;

    @GetMapping("/state")
    public StateDto getState() {
        return stateService.getStateDto();
    }

    @PostMapping("/propose")
    public void propose(@RequestParam int sequenceId, @RequestParam String message) {
        stateService.tryToAddVote(sequenceId, message);
    }

}
