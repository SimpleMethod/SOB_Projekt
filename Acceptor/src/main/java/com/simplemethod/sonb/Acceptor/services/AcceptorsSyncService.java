package com.simplemethod.sonb.Acceptor.services;

import com.simplemethod.sonb.Acceptor.model.VotingContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AcceptorsSyncService {
    //TODO Implement sync

    public void syncAcceptors(List<VotingContext> votingSessions) {
    }

    public boolean checkIsNextSequenceIdValid(VotingContext cvs, int sequenceId) {
        return false;
    }
}
