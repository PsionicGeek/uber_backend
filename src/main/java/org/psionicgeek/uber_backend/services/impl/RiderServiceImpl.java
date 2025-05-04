package org.psionicgeek.uber_backend.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.psionicgeek.uber_backend.dto.DriverDto;
import org.psionicgeek.uber_backend.dto.RideDto;
import org.psionicgeek.uber_backend.dto.RideRequestDto;
import org.psionicgeek.uber_backend.dto.RiderDto;
import org.psionicgeek.uber_backend.entities.*;
import org.psionicgeek.uber_backend.entities.enums.RideRequestStatus;
import org.psionicgeek.uber_backend.entities.enums.RideStatus;
import org.psionicgeek.uber_backend.repositories.RideRequestRepository;
import org.psionicgeek.uber_backend.repositories.RiderRepository;
import org.psionicgeek.uber_backend.services.DriverService;
import org.psionicgeek.uber_backend.services.RatingService;
import org.psionicgeek.uber_backend.services.RideService;
import org.psionicgeek.uber_backend.services.RiderService;
import org.psionicgeek.uber_backend.strategies.RideStrategyManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class RiderServiceImpl implements RiderService {

    private final ModelMapper modelMapper;
    private final RideStrategyManager rideStrategyManager;

    private final RideRequestRepository rideRequestRepository;

    private final RiderRepository riderRepository;
    private final RideService rideService;
    private final DriverService driverService;
    private final RatingService ratingService;

    @Override
    public RideRequestDto requestRide(RideRequestDto rideRequestDto) {
        Rider rider = getCurrentRider();
        RideRequest rideRequest= modelMapper.map(rideRequestDto,RideRequest.class);
        rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);
        rideRequest.setRider(rider);
        Double fare = rideStrategyManager.rideFareCalculationStrategy().calculateFare(rideRequest);
        rideRequest.setFare(fare);
        RideRequest savedRideRequest = rideRequestRepository.save(rideRequest);


       List<Driver> drivers= rideStrategyManager.driverMatchingStrategy(rider.getRating()).findMatchingDrivers(rideRequest);
//TODO: Notify drivers

        return modelMapper.map(savedRideRequest, RideRequestDto.class);
    }

    @Override
    public Rider createRider(User user) {
        Rider rider = Rider.builder()
                .user(user)
                .rating(0.0).build();


        return riderRepository.save(rider);
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        Rider rider = getCurrentRider();
        Ride ride = rideService.getRideById(rideId);
        if(!Objects.equals(rider.getId(), ride.getRider().getId())){
            throw new RuntimeException("You are not authorized to cancel this ride");
        }
        if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)){
            throw new RuntimeException(
                    "Ride status is not confirmed yet, status is: " +
                            ride.getRideStatus()
            );
        }
      Ride savedRide=  rideService.updateRideStatus(ride, RideStatus.CANCELLED);
        driverService.updateDriverAvailability(savedRide.getDriver(), true);

        return modelMapper.map(savedRide, RideDto.class);
    }

    @Override
    public DriverDto rateDriver(Long rideId, Integer rating) {

        Rider rider = getCurrentRider();
        Ride ride = rideService.getRideById(rideId);
        if(!Objects.equals(rider.getId(), ride.getRider().getId())){
            throw new RuntimeException("You are not authorized to rate this ride");
        }
        if(!ride.getRideStatus().equals(RideStatus.ENDED)){
            throw new RuntimeException(
                    "Ride status is not completed yet, status is: " +
                            ride.getRideStatus()
            );
        }
        return ratingService.rateDriver(ride, rating);
    }

    @Override
    public RiderDto getMyProfile() {
        return modelMapper.map(getCurrentRider(), RiderDto.class);
    }

    @Override
    public Page<RideDto> getAllMyRides(PageRequest pageRequest) {
        Rider rider = getCurrentRider();
        return rideService.getAllRidesOfRider(
                rider,
                pageRequest
        ).map(
                ride -> modelMapper.map(ride, RideDto.class)
        );
    }

    @Override
    public Rider getCurrentRider() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return riderRepository.findByUser(user).orElseThrow(
                ()->new RuntimeException("Rider not associated with this user: "+user.getEmail())
        );
    }
}
