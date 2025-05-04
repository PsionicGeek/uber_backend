package org.psionicgeek.uber_backend.controllers;

import lombok.RequiredArgsConstructor;
import org.psionicgeek.uber_backend.dto.*;
import org.psionicgeek.uber_backend.services.DriverService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/drivers")
@RequiredArgsConstructor
@Secured("ROLE_DRIVER")
public class DriverController {

    private final DriverService driverService;
    @PostMapping("/acceptRide/{rideRequestId}")
    public ResponseEntity<RideDto> acceptRide(@PathVariable Long rideRequestId) {
        return ResponseEntity.ok(driverService.acceptRide(rideRequestId));
    }

    @PostMapping("startRide/{rideRequestId}")
    public ResponseEntity<RideDto> startRide(@PathVariable Long rideRequestId,
                                             @RequestBody RideStartDto rideStartDto) {
        return ResponseEntity.ok(driverService.startRide(rideRequestId, rideStartDto.getOtp()));
    }

    @PostMapping("endRide/{rideId}")
    public ResponseEntity<RideDto> endRide(@PathVariable Long rideId
                                             ) {
        return ResponseEntity.ok(driverService.endRide(rideId));
    }

    @PostMapping("/cancelRide/{rideId}")
    public ResponseEntity<RideDto> cancelRide(@PathVariable Long rideId) {
        return ResponseEntity.ok(driverService.cancelRide(rideId));
    }

    @PostMapping("/rateDriver")
    public ResponseEntity<RiderDto> rateRider(@RequestBody RateDto rateDto) {
        return ResponseEntity.ok(driverService.rateRider(rateDto.getRideId(), rateDto.getRating()));
    }

    @GetMapping("/getMyProfile")
    public ResponseEntity<DriverDto> getMyProfile() {
        return ResponseEntity.ok(driverService.getMyProfile());
    }

    @PostMapping("/rateRider/{rideId}/{rating}")
    public ResponseEntity<RiderDto> rateDriver(@PathVariable Long rideId, @PathVariable int rating) {
        return ResponseEntity.ok(driverService.rateRider(rideId, rating));
    }

    @GetMapping("/getMyRides")
    public ResponseEntity<Page<RideDto>> getMyRides(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "createdTime") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir
    ) {
        PageRequest pageRequest = PageRequest.of(page, size, sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy,"id").ascending() : Sort.by(sortBy,"id").descending());
        return ResponseEntity.ok(driverService.getAllMyRides(
                pageRequest
        ));
    }
}
