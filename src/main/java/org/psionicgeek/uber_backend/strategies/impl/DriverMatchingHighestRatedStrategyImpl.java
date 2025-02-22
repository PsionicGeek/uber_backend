package org.psionicgeek.uber_backend.strategies.impl;

import org.psionicgeek.uber_backend.dto.RideRequestDto;
import org.psionicgeek.uber_backend.entities.Driver;
import org.psionicgeek.uber_backend.entities.RideRequest;
import org.psionicgeek.uber_backend.strategies.DriverMatchingStrategy;

import java.util.List;

public class DriverMatchingHighestRatedStrategyImpl implements DriverMatchingStrategy {
    @Override
    public List<Driver> findMatchingDrivers(RideRequest rideRequest) {
        return List.of();
    }
}
