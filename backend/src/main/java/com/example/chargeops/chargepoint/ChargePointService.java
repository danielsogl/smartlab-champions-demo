package com.example.chargeops.chargepoint;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class ChargePointService {

    private final ChargePointRepository chargePoints;
    private final CommandGateway commandGateway;

    public ChargePointService(ChargePointRepository chargePoints, CommandGateway commandGateway) {
        this.chargePoints = chargePoints;
        this.commandGateway = commandGateway;
    }

    public List<ChargePoint> findAll() {
        return chargePoints.findAll();
    }

    public Optional<ChargePoint> findById(String id) {
        return chargePoints.findById(id);
    }

    public RemoteStartResult requestRemoteStart(String chargePointId) {
        if (chargePoints.findById(chargePointId).isEmpty()) {
            return RemoteStartResult.refused(RemoteStartError.CHARGE_POINT_NOT_FOUND);
        }
        return RemoteStartResult.accepted(commandGateway.sendStart(chargePointId));
    }
}
