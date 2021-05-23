package com.simplemethod.sobn_v2.controllers;

import com.simplemethod.sobn_v2.model.AcceptorResponseModel;
import com.simplemethod.sobn_v2.model.AcceptorStateSimplifiedModel;
import com.simplemethod.sobn_v2.model.ClientModel;
import com.simplemethod.sobn_v2.model.VoteHistoryModel;
import com.simplemethod.sobn_v2.services.CommunicationService;
import com.simplemethod.sobn_v2.services.ProposerAppState;
import com.simplemethod.sobn_v2.services.ProposerPaxosLogicService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/proposer")
@RequiredArgsConstructor
public class ProposerLocalController {

    @Autowired
    private final ProposerPaxosLogicService proposerPaxosLogicService;
    private final CommunicationService communicationService;

    @GetMapping("/vote-history")
    public List<VoteHistoryModel> getVoteHistory() {
        return communicationService.readHistory();
    }

    @GetMapping("/get-single-history")
    public void getSingleHistory() {
        communicationService.readSingleHistory();
    }

    //TODO MM: Always visible and reloaded on UI
    @GetMapping("/client/{clientId}")
    public ClientModel getClientData(@PathVariable Integer clientId) {
        return proposerPaxosLogicService.getClientData(clientId);
    }

    //TODO MM: Always visible and reloaded on UI
    @GetMapping("/state-of-acceptors")
    public List<AcceptorResponseModel> getStateOfAcceptors() {
        return Stream.of(communicationService.readStateOfAcceptor(0),communicationService.readStateOfAcceptor(1),communicationService.readStateOfAcceptor(2)).collect(Collectors.toList());
    }

    //TODO KP&MaMR: Use once after start of application
    @GetMapping("/initialize-clients")
    public void initializeClients() {
        proposerPaxosLogicService.addNewClients();
    }

    //TODO MM: Use by leader to start new voting session
    @PostMapping("/start-new-voting-problem")
    public void startNewVotingProblem(@RequestParam String problemName, @RequestParam Integer clientId) {
        proposerPaxosLogicService.startNewVotingProblem(problemName, clientId);
    }

    //TODO MM: Use by any user to add vote to current voting session
    @PostMapping("/add-new-vote")
    public void addNewVote(@RequestParam String vote, @RequestParam Integer clientId) {
        proposerPaxosLogicService.addNewVote(vote, clientId);
    }

    //TODO MM: User by leader to enable error
    @PostMapping("/enable-acceptor-error")
    public void enableAcceptorError(@RequestParam Integer acceptorId, @RequestParam Integer errorType) {
        communicationService.sendEnableErrorRequest(acceptorId, errorType);
    }

    //TODO MM: User by leader to disable error
    @PostMapping("/disable-acceptor-error")
    public void enableAcceptorError(@RequestParam Integer acceptorId) {
        communicationService.sendDisableErrorRequest(acceptorId);
    }

}
