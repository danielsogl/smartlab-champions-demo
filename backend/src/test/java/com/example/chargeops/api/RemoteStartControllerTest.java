package com.example.chargeops.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import com.example.chargeops.chargepoint.ChargePointService;
import com.example.chargeops.chargepoint.RemoteStartError;
import com.example.chargeops.chargepoint.RemoteStartResult;

@WebMvcTest(RemoteStartController.class)
class RemoteStartControllerTest {

    @Autowired
    MockMvcTester mvc;

    @MockitoBean
    ChargePointService service;

    @Test
    void acceptedStartReturns202WithSessionId() {
        when(service.requestRemoteStart("CP-1")).thenReturn(RemoteStartResult.accepted("session-1"));

        var response = mvc.post().uri("/api/charge-points/CP-1/remote-start").exchange();

        assertThat(response).hasStatus(HttpStatus.ACCEPTED);
        assertThat(response).bodyJson().extractingPath("$.sessionId").isEqualTo("session-1");
    }

    @Test
    void unknownChargePointReturns404WithCode() {
        when(service.requestRemoteStart("CP-404"))
                .thenReturn(RemoteStartResult.refused(RemoteStartError.CHARGE_POINT_NOT_FOUND));

        var response = mvc.post().uri("/api/charge-points/CP-404/remote-start").exchange();

        assertThat(response).hasStatus(HttpStatus.NOT_FOUND);
        assertThat(response).bodyJson().extractingPath("$.code").isEqualTo("CHARGE_POINT_NOT_FOUND");
    }

    @Test
    void refusedStartReturns409WithCode() {
        when(service.requestRemoteStart("CP-7"))
                .thenReturn(RemoteStartResult.refused(RemoteStartError.IN_MAINTENANCE));

        var response = mvc.post().uri("/api/charge-points/CP-7/remote-start").exchange();

        assertThat(response).hasStatus(HttpStatus.CONFLICT);
        assertThat(response).bodyJson().extractingPath("$.code").isEqualTo("IN_MAINTENANCE");
    }
}
