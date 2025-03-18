package org.psionicgeek.uber_backend.strategies;


import lombok.RequiredArgsConstructor;
import org.psionicgeek.uber_backend.strategies.impl.DriverMatchingHighestRatedStrategyImpl;
import org.psionicgeek.uber_backend.strategies.impl.DriverMatchingNearestDriverStrategyImpl;
import org.psionicgeek.uber_backend.strategies.impl.RideFareDefaultFareCalculationStrategy;
import org.psionicgeek.uber_backend.strategies.impl.RideFareSurgePricingFareCalculationStrategy;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class RideStrategyManager {


    private final DriverMatchingNearestDriverStrategyImpl driverMatchingNearestDriverStrategyImpl;
    private final DriverMatchingHighestRatedStrategyImpl driverMatchingHighestRatedStrategyImpl;
    private final RideFareDefaultFareCalculationStrategy rideFareDefaultFareCalculationStrategy;
    private final RideFareSurgePricingFareCalculationStrategy rideFareSurgePricingFareCalculationStrategy;

    public DriverMatchingStrategy driverMatchingStrategy(double rating){
        if(rating>=4.8){
            return driverMatchingHighestRatedStrategyImpl;
        }
        return driverMatchingNearestDriverStrategyImpl;
    }

    public RideFareCalculationStrategy rideFareCalculationStrategy(){
        LocalTime surgeStartTime = LocalTime.of(18, 0);
        LocalTime surgeEndTime = LocalTime.of(21, 0);
        boolean isSurgeTime = LocalTime.now().isAfter(surgeStartTime) && LocalTime.now().isBefore(surgeEndTime);
        if(isSurgeTime){
            return rideFareSurgePricingFareCalculationStrategy;
        }
        return rideFareDefaultFareCalculationStrategy;
    }

}
