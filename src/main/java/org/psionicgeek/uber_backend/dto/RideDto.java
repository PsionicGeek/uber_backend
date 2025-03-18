package org.psionicgeek.uber_backend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.psionicgeek.uber_backend.entities.enums.PaymentMethod;
import org.psionicgeek.uber_backend.entities.enums.RideStatus;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideDto {
    private Long id;


    private PointDto pickupLocation;

    private PointDto dropLocation;


    private LocalDateTime createdTime;


    private RiderDto rider;


    private DriverDto driver;
    private String rideDto;



    private PaymentMethod paymentMethod;


    private RideStatus rideStatus;

    private Double fare;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
}
