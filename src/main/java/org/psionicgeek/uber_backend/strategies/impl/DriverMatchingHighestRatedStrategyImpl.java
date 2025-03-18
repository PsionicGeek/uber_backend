package org.psionicgeek.uber_backend.strategies.impl;

import lombok.RequiredArgsConstructor;
import org.psionicgeek.uber_backend.entities.Driver;
import org.psionicgeek.uber_backend.entities.RideRequest;
import org.psionicgeek.uber_backend.repositories.DriverRepository;
import org.psionicgeek.uber_backend.strategies.DriverMatchingStrategy;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class DriverMatchingHighestRatedStrategyImpl implements DriverMatchingStrategy {

    private final DriverRepository driverRepository;
    @Override
    public List<Driver> findMatchingDrivers(RideRequest rideRequest) {
        return driverRepository.findTenNearbyTopRatedDrivers(rideRequest.getPickupLocation());
    }
}
