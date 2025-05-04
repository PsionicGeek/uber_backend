package org.psionicgeek.uber_backend.services;

import org.psionicgeek.uber_backend.dto.DriverDto;
import org.psionicgeek.uber_backend.dto.RiderDto;
import org.psionicgeek.uber_backend.entities.Ride;

public interface RatingService {
    DriverDto rateDriver(Ride ride, int rating);

    RiderDto rateRider(Ride ride, int rating);


    void createNewRating(Ride savedRide);
}
