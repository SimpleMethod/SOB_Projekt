package com.simplemethod.sonb.Acceptor.controllers;


import com.simplemethod.sonb.Acceptor.model.AcceptedNotificationModel;
import com.simplemethod.sonb.Acceptor.model.AcceptorResponseModel;
import com.simplemethod.sonb.Acceptor.model.ProposeRequestModel;
import com.simplemethod.sonb.Acceptor.services.AcceptorPaxosLogicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/acceptor/{acceptorId}")
@RequiredArgsConstructor
public class AcceptorAPI {

    private final AcceptorPaxosLogicService acceptorLogic;

    @PostMapping("/add-new-problem")
    public AcceptorResponseModel addNewProblem(@PathVariable Integer acceptorId, @RequestBody ProposeRequestModel propose) {
        if (acceptorLogic.isSequenceCorrect(acceptorId, propose)) {
            acceptorLogic.addToStaging(acceptorId, propose.getMessage());
            return responseSimpleAccepted();
        }

        return responseSimpleReject();
    }

    @PostMapping("/accepted-new-problem")
    public void acceptedNewProblem(@PathVariable Integer acceptorId, @RequestBody AcceptedNotificationModel accepted) {
        acceptorLogic.acceptNewVotingSession(acceptorId, accepted);
    }

    @PostMapping("/add-new-vote")
    public AcceptorResponseModel addNewVote(@PathVariable Integer acceptorId, @RequestBody ProposeRequestModel propose) {
        if (acceptorLogic.isSequenceCorrect(acceptorId, propose)) {
            acceptorLogic.addToStaging(acceptorId, propose.getMessage());
            return responseSimpleAccepted();
        }

        return responseSimpleReject();
    }

    @PostMapping("/accepted-new-vote")
    public void acceptedNewVote(@PathVariable Integer acceptorId, @RequestBody AcceptedNotificationModel accepted) {
        acceptorLogic.acceptNewVote(acceptorId, accepted);
    }

    @PostMapping("/enable-error")
    public void enableError(@PathVariable Integer acceptorId, @RequestBody ProposeRequestModel propose) {
        acceptorLogic.enableError(acceptorId, propose);
    }

    @PostMapping("/disable-error")
    public void disableError(@PathVariable Integer acceptorId) {
        acceptorLogic.disableError(acceptorId);
    }

    @GetMapping("/fetch-acceptor-state")
    public AcceptorResponseModel fetchState(@PathVariable Integer acceptorId) {
        return acceptorLogic.getStateDto(acceptorId);
    }

    private AcceptorResponseModel responseSimpleAccepted() {
        return new AcceptorResponseModel(true, null, null,null,null);
    }

    private AcceptorResponseModel responseSimpleReject() {
        return new AcceptorResponseModel(false, null, null,null,null);
    }
}
