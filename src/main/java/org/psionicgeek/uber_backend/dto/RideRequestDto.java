package org.psionicgeek.uber_backend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.locationtech.jts.geom.Point;
import org.psionicgeek.uber_backend.entities.Rider;
import org.psionicgeek.uber_backend.entities.enums.PaymentMethod;
import org.psionicgeek.uber_backend.entities.enums.RideRequestStatus;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideRequestDto {

    private Long id;


    private PointDto pickupLocation;

    private PointDto dropLocation;


    private LocalDateTime requestedTime;


    private RiderDto rider;


    private PaymentMethod paymentMethod;


    private RideRequestStatus rideRequestStatus;
}
