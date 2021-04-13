package com.simplemethod.sobn.interfaces;

import com.simplemethod.sobn.models.PromiseModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.math.BigInteger;

public interface ProposerInterface {

    @PostMapping(value = "promise/{acceptorID}", consumes =  "application/json", headers = "Accept=application/json")
    @ApiOperation(value = "Acceptor return prepare seq number and value from proposer")
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Accepted"),
            @ApiResponse(code = 400, message = "Bad Request"),
    })
    ResponseEntity<Object> promiseAcceptor(@Valid @NotEmpty @PathVariable BigInteger acceptorID, @Valid @RequestBody PromiseModel promiseModel);
}
