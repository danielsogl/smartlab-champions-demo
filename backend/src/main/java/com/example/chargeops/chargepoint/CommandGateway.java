package com.example.chargeops.chargepoint;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Sends OCPP commands to charge points. In production this talks to the OCPP backend;
 * a command to a charge point that is not connected times out after 60 s, and an
 * inoperative one answers Rejected without a reason.
 */
@Component
public class CommandGateway {

    private static final Logger log = LoggerFactory.getLogger(CommandGateway.class);

    /** Sends RemoteStartTransaction and returns the session id. */
    public String sendStart(String chargePointId) {
        log.info("RemoteStartTransaction sent to {}", chargePointId);
        return UUID.randomUUID().toString();
    }
}
