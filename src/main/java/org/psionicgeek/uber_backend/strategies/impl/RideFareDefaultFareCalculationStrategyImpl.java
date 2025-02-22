package org.psionicgeek.uber_backend.strategies.impl;

import lombok.RequiredArgsConstructor;
import org.psionicgeek.uber_backend.dto.RideRequestDto;
import org.psionicgeek.uber_backend.entities.RideRequest;
import org.psionicgeek.uber_backend.services.DistanceService;
import org.psionicgeek.uber_backend.strategies.RideFareCalculationStrategy;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RideFareDefaultFareCalculationStrategyImpl implements RideFareCalculationStrategy {

    private final DistanceService distanceService;


    @Override
    public double calculateFare(RideRequest rideRequest) {
        double distance=distanceService.calculateDistance(rideRequest.getPickupLocation(),rideRequest.getDropLocation());
        return distance*RIDE_FARE_MULTIPLIER;
    }
}
