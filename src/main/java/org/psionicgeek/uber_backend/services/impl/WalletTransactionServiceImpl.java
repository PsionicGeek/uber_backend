package org.psionicgeek.uber_backend.services.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.psionicgeek.uber_backend.dto.WalletTransactionDto;
import org.psionicgeek.uber_backend.entities.WalletTransaction;
import org.psionicgeek.uber_backend.repositories.WalletTransactionRepository;
import org.psionicgeek.uber_backend.services.WalletTransactionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletTransactionServiceImpl implements WalletTransactionService {

    private final WalletTransactionRepository walletTransactionRepository;
    private final ModelMapper modelMapper;

    @Override
    public void createNewWalletTransaction(WalletTransaction walletTransaction) {

        walletTransactionRepository.save(walletTransaction);

    }
}
