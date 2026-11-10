package com.example.chargeops.chargepoint;

/** Either a session id (accepted) or an error (refused). */
public record RemoteStartResult(String sessionId, RemoteStartError error) {

    public static RemoteStartResult accepted(String sessionId) {
        return new RemoteStartResult(sessionId, null);
    }

    public static RemoteStartResult refused(RemoteStartError error) {
        return new RemoteStartResult(null, error);
    }
}
