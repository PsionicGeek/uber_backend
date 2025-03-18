package org.psionicgeek.uber_backend.repositories;

import org.psionicgeek.uber_backend.entities.User;
import org.psionicgeek.uber_backend.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet,Long> {
  Optional<Wallet> findByUser(User user);
}
