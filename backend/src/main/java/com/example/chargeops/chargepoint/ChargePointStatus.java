package com.example.chargeops.chargepoint;

public enum ChargePointStatus {
    AVAILABLE,
    CHARGING,
    OFFLINE,
    /** Taken out of service on purpose, e.g. for maintenance (OCPI: INOPERATIVE). */
    INOPERATIVE,
    /** No heartbeat received yet, or the last status message could not be parsed. */
    UNKNOWN
}
