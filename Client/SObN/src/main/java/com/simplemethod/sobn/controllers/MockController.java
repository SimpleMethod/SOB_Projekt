package com.simplemethod.sobn.controllers;

import com.simplemethod.sobn.models.AcceptorModel;
import com.simplemethod.sobn.models.PromiseModel;
import com.simplemethod.sobn.models.VotingModel;
import org.springframework.stereotype.Controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MockController {


    AcceptorModel acceptorModel1 = new AcceptorModel(BigInteger.valueOf(1), BigInteger.valueOf(1), "1", true, BigInteger.valueOf(-1),true);
    AcceptorModel acceptorModel2 = new AcceptorModel(BigInteger.valueOf(2), BigInteger.valueOf(2), "2", true, BigInteger.valueOf(-1),true);
    AcceptorModel acceptorModel3 = new AcceptorModel(BigInteger.valueOf(3), BigInteger.valueOf(3), "3", true, BigInteger.valueOf(-1),true);

    List<VotingModel> votingModels = new ArrayList<>();


    public AcceptorModel getAcceptorById(Integer acceptorId) {
        switch (acceptorId) {
            case 1:
                return acceptorModel1;
            case 2:
                return acceptorModel2;
            case 3:
                return acceptorModel3;
            default:
                return null;
        }
    }

    public AcceptorModel putAcceptorFault(Integer acceptorId, Integer faultType) {
        switch (acceptorId) {
            case 1:
                acceptorModel1.setFailureAcceptor(false);
                acceptorModel1.setFaultType(BigInteger.valueOf(faultType));
                return acceptorModel1;
            case 2:
                acceptorModel2.setFailureAcceptor(false);
                acceptorModel2.setFaultType(BigInteger.valueOf(faultType));
                return acceptorModel2;
            case 3:
                acceptorModel3.setFailureAcceptor(false);
                acceptorModel3.setFaultType(BigInteger.valueOf(faultType));
                return acceptorModel3;
            default:
                return null;
        }
    }


    public AcceptorModel deleteAcceptorFault(Integer acceptorId) {
        switch (acceptorId) {
            case 1:
                acceptorModel1.setFailureAcceptor(true);
                acceptorModel1.setFaultType(BigInteger.valueOf(-1));
                return acceptorModel1;
            case 2:
                acceptorModel2.setFailureAcceptor(true);
                acceptorModel2.setFaultType(BigInteger.valueOf(-1));
                return acceptorModel2;
            case 3:
                acceptorModel3.setFailureAcceptor(true);
                acceptorModel3.setFaultType(BigInteger.valueOf(-1));
                return acceptorModel3;
            default:
                return null;
        }

    }

    public PromiseModel postPrepare(Integer acceptorId, PromiseModel model) {
        switch (acceptorId) {
            case 1:
                if(acceptorModel1.isFailureAcceptor())
                {
                    acceptorModel1.setProposerValue(model.getProposerValue());
                    acceptorModel1.setSequenceNumber(model.getSequenceNumber());
                    acceptorModel1.setVotingValue(true);
                   // return model;
                }
                else
                {
                    acceptorModel1.setVotingValue(false);
                }
                votingModels.add(new VotingModel(BigInteger.valueOf(1),model.getProposerValue(),acceptorModel1.isVotingValue()));
            case 2:
                if(acceptorModel2.isFailureAcceptor()) {
                    acceptorModel2.setProposerValue(model.getProposerValue());
                    acceptorModel2.setSequenceNumber(model.getSequenceNumber());
                    acceptorModel2.setVotingValue(true);
                   // return model;
                }
                else
                {
                    acceptorModel2.setVotingValue(false);
                }
                votingModels.add(new VotingModel(BigInteger.valueOf(2),model.getProposerValue(),acceptorModel2.isVotingValue()));
            case 3:
                if(acceptorModel3.isFailureAcceptor()) {
                    acceptorModel3.setProposerValue(model.getProposerValue());
                    acceptorModel3.setSequenceNumber(model.getSequenceNumber());
                    acceptorModel3.setVotingValue(true);
                   // return model;
                }
                else
                {
                    acceptorModel3.setVotingValue(false);
                }
                votingModels.add(new VotingModel(BigInteger.valueOf(3),model.getProposerValue(),acceptorModel3.isVotingValue()));
            default:

        }
        return null;
    }

    public void getVoting(Integer acceptorId, boolean model) {

        switch (acceptorId) {
            case 1:
                acceptorModel1.setVotingValue(model);
            case 2:
                acceptorModel2.setVotingValue(model);
            case 3:
                acceptorModel3.setVotingValue(model);
        }
    }

    public List<VotingModel> getVotingModels()
    {
        return  votingModels;
    }
}