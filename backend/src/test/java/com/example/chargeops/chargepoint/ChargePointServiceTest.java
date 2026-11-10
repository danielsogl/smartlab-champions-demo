package com.example.chargeops.chargepoint;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChargePointServiceTest {

    private ChargePointRepository chargePoints;
    private CommandGateway commandGateway;
    private ChargePointService service;

    @BeforeEach
    void setUp() {
        chargePoints = new ChargePointRepository();
        commandGateway = mock(CommandGateway.class);
        service = new ChargePointService(chargePoints, commandGateway);
    }

    static ChargePoint chargePoint(String id, ChargePointStatus status) {
        return new ChargePoint(id, "Test site", status);
    }

    @Test
    void startsSessionOnAvailableChargePoint() {
        chargePoints.save(chargePoint("CP-1", ChargePointStatus.AVAILABLE));
        when(commandGateway.sendStart("CP-1")).thenReturn("session-1");

        var result = service.requestRemoteStart("CP-1");

        assertThat(result.sessionId()).isEqualTo("session-1");
        assertThat(result.error()).isNull();
    }

    @Test
    void refusesStartOnUnknownId() {
        var result = service.requestRemoteStart("CP-404");

        assertThat(result.error()).isEqualTo(RemoteStartError.CHARGE_POINT_NOT_FOUND);
        verify(commandGateway, never()).sendStart(any());
    }

    @Test
    void listsChargePointsSortedById() {
        assertThat(service.findAll()).extracting(ChargePoint::id).startsWith("CP-1", "CP-2");
    }
}
