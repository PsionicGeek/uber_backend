package org.psionicgeek.uber_backend.repositories;

import org.psionicgeek.uber_backend.entities.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideRepository extends JpaRepository<Ride,Long> {
}
