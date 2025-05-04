package org.psionicgeek.uber_backend.repositories;

import org.psionicgeek.uber_backend.entities.Driver;
import org.psionicgeek.uber_backend.entities.Rating;
import org.psionicgeek.uber_backend.entities.Ride;
import org.psionicgeek.uber_backend.entities.Rider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface RatingRepository extends JpaRepository<Rating,Long> {

    List<Rating> findRatingByRider(Rider rider);
    List<Rating> findRatingByDriver(Driver driver);

    Optional<Rating> findRatingByRide(Ride ride);
}
