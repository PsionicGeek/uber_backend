package org.psionicgeek.uber_backend.strategies.impl;

import lombok.RequiredArgsConstructor;
import org.psionicgeek.uber_backend.entities.RideRequest;
import org.psionicgeek.uber_backend.services.DistanceService;
import org.psionicgeek.uber_backend.strategies.RideFareCalculationStrategy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideFareSurgePricingFareCalculationStrategy implements RideFareCalculationStrategy {

    private final DistanceService distanceService;
    private static final double SURGE_FACTOR=2;

    @Override
    public double calculateFare(RideRequest rideRequest) {
        double distance=distanceService.calculateDistance(rideRequest.getPickupLocation(),rideRequest.getDropLocation());
        return distance*RIDE_FARE_MULTIPLIER*SURGE_FACTOR;
    }
}
