package com.example.chargeops.chargepoint;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

// ponytail: in-memory store, the demo needs no database
@Repository
public class ChargePointRepository {

    private final Map<String, ChargePoint> chargePoints = new ConcurrentHashMap<>();

    public ChargePointRepository() {
        save(new ChargePoint("CP-1", "Hamburg, Depot Nord", ChargePointStatus.AVAILABLE));
        save(new ChargePoint("CP-2", "Hamburg, Depot Nord", ChargePointStatus.CHARGING));
        save(new ChargePoint("CP-3", "Ratingen, Parkhaus West", ChargePointStatus.AVAILABLE));
        save(new ChargePoint("CP-4", "Ratingen, Parkhaus West", ChargePointStatus.OFFLINE));
        save(new ChargePoint("CP-5", "Köln, Autohof A3", ChargePointStatus.AVAILABLE));
        save(new ChargePoint("CP-6", "Köln, Autohof A3", ChargePointStatus.UNKNOWN));
        save(new ChargePoint("CP-7", "München, Messe Ost", ChargePointStatus.IN_MAINTENANCE));
        save(new ChargePoint("CP-8", "München, Messe Ost", ChargePointStatus.AVAILABLE));
    }

    public List<ChargePoint> findAll() {
        return chargePoints.values().stream().sorted(Comparator.comparing(ChargePoint::id)).toList();
    }

    public Optional<ChargePoint> findById(String id) {
        return Optional.ofNullable(chargePoints.get(id));
    }

    public ChargePoint save(ChargePoint chargePoint) {
        chargePoints.put(chargePoint.id(), chargePoint);
        return chargePoint;
    }
}
