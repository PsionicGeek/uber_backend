package org.psionicgeek.uber_backend.dto;



import lombok.*;
import org.locationtech.jts.geom.Point;


import org.psionicgeek.uber_backend.entities.enums.PaymentMethod;
import org.psionicgeek.uber_backend.entities.enums.RideStatus;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideDto {
    private Long id;


    private Point pickupLocation;

    private Point dropLocation;


    private LocalDateTime createdTime;


    private RiderDto rider;


    private DriverDto driver;


    private PaymentMethod paymentMethod;


    private RideStatus rideStatus;

    private Double fare;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
}
