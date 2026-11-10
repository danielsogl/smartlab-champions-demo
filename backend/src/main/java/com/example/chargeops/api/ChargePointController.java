package com.example.chargeops.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.chargeops.chargepoint.ChargePoint;
import com.example.chargeops.chargepoint.ChargePointService;
import com.example.chargeops.chargepoint.RemoteStartError;

@RestController
@RequestMapping("/api/charge-points")
public class ChargePointController {

    private final ChargePointService service;

    public ChargePointController(ChargePointService service) {
        this.service = service;
    }

    @GetMapping
    public List<ChargePoint> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ChargePoint get(@PathVariable String id) {
        return service.findById(id)
                .orElseThrow(() -> new ApiException(RemoteStartError.CHARGE_POINT_NOT_FOUND));
    }
}
