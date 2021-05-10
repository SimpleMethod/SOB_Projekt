package com.simplemethod.sobn.interfaces;

import com.simplemethod.sobn.models.AcceptorModel;
import com.simplemethod.sobn.models.PromiseModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.math.BigInteger;
import java.net.ConnectException;

public interface SimulatorInterface {

    @ApiOperation(value = "Get information about acceptor")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = AcceptorModel.class),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @GetMapping(value = "acceptor/{acceptorID}", produces = "application/json")
    @ResponseBody
    ResponseEntity<AcceptorModel> getAcceptorInfo(@Valid @NotEmpty @PathVariable BigInteger acceptorID) throws ConnectException;

    @ApiOperation(value = "Send new seq number and value to proposer")
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Accepted"),
            @ApiResponse(code = 400, message = "Bad Request"),
    })
    @PatchMapping(value = "proposers", consumes = "application/json",  headers = "Accept=application/json")
    @ResponseBody
    ResponseEntity<Object> updateProposerValue(@Valid @RequestBody PromiseModel promiseModel);

    @ApiOperation(value = "Adding a new failure to acceptor")
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Accepted"),
            @ApiResponse(code = 400, message = "Bad Request"),
    })
    @PostMapping(value = "acceptor/{acceptorID}/{typeFault}")
    ResponseEntity<Object> acceptorFault(@Valid @NotEmpty @PathVariable BigInteger acceptorID, @Valid @NotEmpty @PathVariable short typeFault);

    @ApiOperation(value = "Repair acceptor")
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Accepted"),
            @ApiResponse(code = 400, message = "Bad Request"),
    })
    @DeleteMapping(value = "acceptor/{acceptorID}")
    ResponseEntity<Object> repairAcceptor(@Valid @NotEmpty @PathVariable BigInteger acceptorID);
}
