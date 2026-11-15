package com.example.chargeops.api;

import com.example.chargeops.chargepoint.RemoteStartError;

/** Thrown by controllers; ApiExceptionHandler turns it into a problem response. */
public class ApiException extends RuntimeException {

    private final RemoteStartError code;

    public ApiException(RemoteStartError code) {
        super(code.name());
        this.code = code;
    }

    public RemoteStartError code() {
        return code;
    }
}
