package org.psionicgeek.uber_backend.services;

import org.psionicgeek.uber_backend.dto.WalletTransactionDto;
import org.psionicgeek.uber_backend.entities.WalletTransaction;

public interface WalletTransactionService {

    void createNewWalletTransaction(WalletTransaction walletTransaction);
}
