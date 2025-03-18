package org.psionicgeek.uber_backend.services.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.psionicgeek.uber_backend.dto.DriverDto;
import org.psionicgeek.uber_backend.dto.RideDto;
import org.psionicgeek.uber_backend.dto.RiderDto;
import org.psionicgeek.uber_backend.entities.Driver;
import org.psionicgeek.uber_backend.entities.Ride;
import org.psionicgeek.uber_backend.entities.RideRequest;
import org.psionicgeek.uber_backend.entities.enums.RideRequestStatus;
import org.psionicgeek.uber_backend.entities.enums.RideStatus;
import org.psionicgeek.uber_backend.exceptions.ResourceNotFoundException;
import org.psionicgeek.uber_backend.repositories.DriverRepository;
import org.psionicgeek.uber_backend.services.DriverService;
import org.psionicgeek.uber_backend.services.PaymentService;
import org.psionicgeek.uber_backend.services.RideRequestService;
import org.psionicgeek.uber_backend.services.RideService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final RideRequestService rideRequestService;
    private final DriverRepository driverRepository;
    private final RideService rideService;
    private final ModelMapper modelMapper;
    private final PaymentService paymentService;

    @Override
    @Transactional
    public RideDto acceptRide(Long rideRequestId) {
        RideRequest rideRequest = rideRequestService.findRideRequestById(rideRequestId);
        if (!rideRequest.getRideRequestStatus().equals(RideRequestStatus.PENDING)) {
            throw new RuntimeException(
                    "RideRequest is not in PENDING state, status is: " +
                            rideRequest.getRideRequestStatus()
            );
        }
        Driver currentDriver = getCurrentDriver();

        if (currentDriver.getAvailable()) {
            throw new RuntimeException(
                    "Driver is not available"
            );
        }

        Driver savedDriver = updateDriverAvailability(currentDriver, false);
        Ride ride = rideService.createNewRide(rideRequest, savedDriver);


        return modelMapper.map(ride, RideDto.class);
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();
        if (!ride.getDriver().getId().equals(driver.getId())) {
            throw new RuntimeException(
                    "Driver is not authorized to start this ride"
            );
        }
        if (!ride.getRideStatus().equals(RideStatus.CONFIRMED)) {
            throw new RuntimeException(
                    "Ride status is not confirmed yet, status is: " +
                            ride.getRideStatus()
            );
        }
        rideService.updateRideStatus(ride, RideStatus.CANCELLED);
        updateDriverAvailability(driver, true);

        return modelMapper.map(ride, RideDto.class);
    }


    @Override
    public RideDto startRide(Long rideId, String otp) {
        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();
        if (!ride.getDriver().getId().equals(driver.getId())) {
            throw new RuntimeException(
                    "Driver is not authorized to start this ride"
            );
        }
        if (!ride.getOtp().equals(otp)) {
            throw new RuntimeException(
                    "Invalid OTP"
            );
        }
        if (!ride.getRideStatus().equals(RideStatus.CONFIRMED)) {
            throw new RuntimeException(
                    "Ride status is not confirmed yet, status is: " +
                            ride.getRideStatus()
            );
        }
        Ride savedRide = rideService.updateRideStatus(ride, RideStatus.ONGOING);
        paymentService.createNewPayment(savedRide);
        return modelMapper.map(savedRide, RideDto.class);
    }

    @Override
    @Transactional
    public RideDto endRide(Long rideId) {
        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();
        if (!ride.getDriver().getId().equals(driver.getId())) {
            throw new RuntimeException(
                    "Driver is not authorized to start this ride"
            );
        }
        if (!ride.getRideStatus().equals(RideStatus.ONGOING)) {
            throw new RuntimeException(
                    "Ride status is not ONGOING yet, status is: " +
                            ride.getRideStatus()
            );
        }
        ride.setEndedAt(LocalDateTime.now());
        Ride savedRide = rideService.updateRideStatus(ride, RideStatus.ENDED);
        updateDriverAvailability(driver, true);
        paymentService.processPayment(savedRide);

        return modelMapper.map(savedRide, RideDto.class);
    }

    @Override
    public RiderDto rateRider(Long rideId, Integer rating) {
        return null;
    }

    @Override
    public DriverDto getMyProfile() {
        Driver currentDriver = getCurrentDriver();

        return modelMapper.map(currentDriver, DriverDto.class);
    }

    @Override
    public Page<RideDto> getAllMyRides(PageRequest pageRequest) {

        return rideService.getAllRidesOfDriver(getCurrentDriver(), pageRequest)
                .map(ride -> modelMapper.map(ride, RideDto.class));
    }

    @Override
    public Driver getCurrentDriver() {
        return driverRepository.findById(2L).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Driver not found with id: " + 2L
                )
        );
    }

    @Override
    public Driver updateDriverAvailability(Driver driver, Boolean available) {
        driver.setAvailable(available);
        return driverRepository.save(driver);
    }

}
