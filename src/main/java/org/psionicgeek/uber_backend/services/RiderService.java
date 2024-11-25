package org.psionicgeek.uber_backend.services;

import org.psionicgeek.uber_backend.dto.DriverDto;
import org.psionicgeek.uber_backend.dto.RideDto;
import org.psionicgeek.uber_backend.dto.RideRequestDto;
import org.psionicgeek.uber_backend.dto.RiderDto;

import java.util.List;

public interface RiderService {


    RideRequestDto requestRide(RideRequestDto rideRequestDto);

    RideDto cancelRide(Long rideId);


    DriverDto rateDriver(Long rideId, Integer rating);

    RiderDto getMyProfile();

    List<RideDto> getAllMyRides();
}
