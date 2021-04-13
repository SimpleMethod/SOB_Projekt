package com.simplemethod.sobn.interfaces;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.math.BigInteger;

public interface AcceptorInterface {


    @PostMapping(value = "accepted/{acceptorID}/{seqNumber}")
    @ApiOperation(value = "Acceptor informs proposer about accepting value")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 400, message = "Bad Request"),
    })
    ResponseEntity<Object> acceptedAcceptor(@Valid @NotEmpty @PathVariable BigInteger acceptorID,@Valid @NotEmpty @PathVariable BigInteger seqNumber);
}
