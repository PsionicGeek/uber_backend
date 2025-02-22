package org.psionicgeek.uber_backend.strategies;


import org.psionicgeek.uber_backend.dto.RideRequestDto;
import org.psionicgeek.uber_backend.entities.RideRequest;

public interface RideFareCalculationStrategy {
    final double RIDE_FARE_MULTIPLIER = 10.0;
    double calculateFare(RideRequest rideRequest);
}
