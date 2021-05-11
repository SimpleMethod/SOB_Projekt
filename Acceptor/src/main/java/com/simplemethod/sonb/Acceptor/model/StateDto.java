package com.simplemethod.sonb.Acceptor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class StateDto {
    private List<String> otherVotes;
    private String problemName;
    private int nextSequenceId;
}
