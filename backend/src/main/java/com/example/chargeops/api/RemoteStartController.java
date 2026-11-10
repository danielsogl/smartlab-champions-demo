package com.example.chargeops.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.chargeops.chargepoint.ChargePointService;

@RestController
@RequestMapping("/api/charge-points/{id}/remote-start")
public class RemoteStartController {

    private final ChargePointService service;

    public RemoteStartController(ChargePointService service) {
        this.service = service;
    }

    public record RemoteStartResponse(String sessionId) {
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public RemoteStartResponse start(@PathVariable String id) {
        var result = service.requestRemoteStart(id);
        if (result.error() != null) {
            throw new ApiException(result.error());
        }
        return new RemoteStartResponse(result.sessionId());
    }
}
