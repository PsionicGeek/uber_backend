package org.psionicgeek.uber_backend.repositories;

import org.psionicgeek.uber_backend.entities.Payment;
import org.psionicgeek.uber_backend.entities.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
    Optional<Payment> findByRide(Ride ride);
}
