package org.psionicgeek.uber_backend.services.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.psionicgeek.uber_backend.dto.DriverDto;
import org.psionicgeek.uber_backend.dto.RiderDto;
import org.psionicgeek.uber_backend.entities.Driver;
import org.psionicgeek.uber_backend.entities.Rating;
import org.psionicgeek.uber_backend.entities.Ride;
import org.psionicgeek.uber_backend.entities.Rider;
import org.psionicgeek.uber_backend.exceptions.ResourceNotFoundException;
import org.psionicgeek.uber_backend.repositories.DriverRepository;
import org.psionicgeek.uber_backend.repositories.RatingRepository;
import org.psionicgeek.uber_backend.repositories.RiderRepository;
import org.psionicgeek.uber_backend.services.RatingService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {


    private final RatingRepository ratingRepository;
    private final DriverRepository driverRepository;
    private final RiderRepository riderRepository;
    private final ModelMapper modelMapper;

    @Override
    public DriverDto rateDriver(Ride ride, int rating) {
        Driver driver = ride.getDriver();
        Rating ratingObj = ratingRepository.findRatingByRide(ride).orElseThrow(
                () -> new ResourceNotFoundException("Rating not found")
        );

        if(ratingObj.getDriverRating()!=null){
            throw new RuntimeException("Driver already rated");
        }
        ratingObj.setDriverRating(rating);
        ratingRepository.save(ratingObj);
        Double driverRating = ratingRepository.findRatingByDriver(driver).stream()
                .mapToDouble(Rating::getDriverRating)
                .average().orElse(0.0);
        driver.setRating(driverRating);
       Driver savedDriver= driverRepository.save(driver);
       return modelMapper.map(savedDriver, DriverDto.class);
    }

    @Override
    public RiderDto rateRider(Ride ride, int rating) {
        Rider rider = ride.getRider();
        Rating ratingObj = ratingRepository.findRatingByRide(ride).orElseThrow(
                () -> new ResourceNotFoundException("Rating not found")
        );
        if(ratingObj.getRiderRating()!=null){
            throw new RuntimeException("Rider already rated");
        }
        ratingObj.setRiderRating(rating);
        ratingRepository.save(ratingObj);
        Double riderRating = ratingRepository.findRatingByRider(rider).stream()
                .mapToDouble(Rating::getRiderRating)
                .average().orElse(0.0);
        rider.setRating(riderRating);
      Rider savedRider=  riderRepository.save(rider);
      return modelMapper.map(savedRider, RiderDto.class);

    }

    @Override
    public void createNewRating(Ride savedRide) {
        Rating rating = Rating.builder()
                .ride(savedRide)
                .driver(savedRide.getDriver())
                .rider(savedRide.getRider())
                .build();
        ratingRepository.save(rating);

    }


}
