package org.psionicgeek.uber_backend.strategies;

import org.psionicgeek.uber_backend.entities.Driver;
import org.psionicgeek.uber_backend.entities.RideRequest;

import java.util.List;

public interface DriverMatchingStrategy {

     List<Driver> findMatchingDrivers(RideRequest rideRequest);
}
