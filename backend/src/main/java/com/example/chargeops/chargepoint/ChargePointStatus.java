package com.example.chargeops.chargepoint;

public enum ChargePointStatus {
    AVAILABLE,
    CHARGING,
    OFFLINE,
    IN_MAINTENANCE,
    /** No heartbeat received yet, or the last status message could not be parsed. */
    UNKNOWN
}
