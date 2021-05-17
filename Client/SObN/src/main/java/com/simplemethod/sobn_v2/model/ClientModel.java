package com.simplemethod.sobn_v2.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientModel {

    public static String DEFAULT_PROBLEM_DISPLAY_NAME = "No problem has ben specified by a leader";


    private Boolean isLeader;   //TODO MM: Determines visibility of "New Problem" form, and visibility of dropdown for acceptor error enable

    private Integer sequenceNumber;

    private String currentProblemName;

    private List<String> currentProblemVotes;

}
