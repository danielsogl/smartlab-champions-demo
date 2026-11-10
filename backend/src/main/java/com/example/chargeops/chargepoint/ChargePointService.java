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
        var chargePoint = chargePoints.findById(chargePointId);
        if (chargePoint.isEmpty()) {
            return RemoteStartResult.refused(RemoteStartError.CHARGE_POINT_NOT_FOUND);
        }
        var refusal = refusalFor(chargePoint.get().status());
        if (refusal != null) {
            return RemoteStartResult.refused(refusal);
        }
        return RemoteStartResult.accepted(commandGateway.sendStart(chargePointId));
    }

    /** DEMO-42: UNKNOWN counts as offline (spec.md, open questions). */
    private static RemoteStartError refusalFor(ChargePointStatus status) {
        return switch (status) {
            case OFFLINE, UNKNOWN -> RemoteStartError.CHARGE_POINT_OFFLINE;
            case IN_MAINTENANCE -> RemoteStartError.IN_MAINTENANCE;
            case AVAILABLE, CHARGING -> null;
        };
    }
}
