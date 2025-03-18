package org.psionicgeek.uber_backend.services;

import org.psionicgeek.uber_backend.entities.Ride;
import org.psionicgeek.uber_backend.entities.User;
import org.psionicgeek.uber_backend.entities.Wallet;
import org.psionicgeek.uber_backend.entities.enums.TransactionMethod;

public interface WalletService {

    Wallet addMoneyToWallet(User user, Double amount, String transactionId, Ride ride, TransactionMethod transactionMethod);

    Wallet deductMoneyFromWallet(User user, Double amount, String transactionId, Ride ride, TransactionMethod transactionMethod);

    void withdrawAllMoneyFromWallet();

    Wallet findWalletById(Long id);

    Wallet createNewWallet(User user);

    Wallet findWalletByUser(User user);

}
