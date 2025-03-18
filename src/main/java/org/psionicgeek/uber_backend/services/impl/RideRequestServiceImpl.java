package org.psionicgeek.uber_backend.services.impl;

import lombok.RequiredArgsConstructor;
import org.psionicgeek.uber_backend.entities.RideRequest;
import org.psionicgeek.uber_backend.exceptions.ResourceNotFoundException;
import org.psionicgeek.uber_backend.repositories.RideRequestRepository;
import org.psionicgeek.uber_backend.services.RideRequestService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideRequestServiceImpl implements RideRequestService {
    private final RideRequestRepository rideRequestRepository;

    @Override
    public RideRequest findRideRequestById(Long id) {
        return rideRequestRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("RideRequest not found with id: " + id)
        );
    }

    @Override
    public void update(RideRequest rideRequest) {
        RideRequest toSave= rideRequestRepository.findById(rideRequest.getId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("RideRequest not found with id: " + rideRequest.getId())
                );

        rideRequestRepository.save(rideRequest);

    }
}
