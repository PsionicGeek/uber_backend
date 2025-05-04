package org.psionicgeek.uber_backend.repositories;

import org.psionicgeek.uber_backend.entities.Rider;
import org.psionicgeek.uber_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RiderRepository extends JpaRepository<Rider, Long> {

    Optional<Rider> findByUser(User user);
}
