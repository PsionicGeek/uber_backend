package org.psionicgeek.uber_backend.repositories;

import org.psionicgeek.uber_backend.entities.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<Driver,Long> {
}
