package org.psionicgeek.uber_backend.strategies.impl;

import org.psionicgeek.uber_backend.dto.RideRequestDto;
import org.psionicgeek.uber_backend.entities.RideRequest;
import org.psionicgeek.uber_backend.strategies.RideFareCalculationStrategy;

public class RideFareSurgePricingFareCalculationStrategy implements RideFareCalculationStrategy {
    @Override
    public double calculateFare(RideRequest rideRequest) {
        return 0;
    }
}
