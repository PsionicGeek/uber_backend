package org.psionicgeek.uber_backend.services;

import org.psionicgeek.uber_backend.entities.RideRequest;

public interface RideRequestService {

    RideRequest findRideRequestById(Long id);

    void update(RideRequest rideRequest);
}
