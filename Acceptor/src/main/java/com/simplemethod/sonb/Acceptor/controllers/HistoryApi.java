package com.simplemethod.sonb.Acceptor.controllers;

import com.simplemethod.sonb.Acceptor.model.VoteHistoryModel;

import com.simplemethod.sonb.Acceptor.services.VoteResultService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/acceptor")
public class HistoryApi {


    private final VoteResultService historyService = new VoteResultService();

    @GetMapping("/vote/history")
    public VoteHistoryModel getHistory() {
        return historyService.getVoteResult();
    }

}
