package org.psionicgeek.uber_backend.services.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.psionicgeek.uber_backend.dto.RideDto;
import org.psionicgeek.uber_backend.dto.WalletDto;
import org.psionicgeek.uber_backend.dto.WalletTransactionDto;
import org.psionicgeek.uber_backend.entities.Ride;
import org.psionicgeek.uber_backend.entities.User;
import org.psionicgeek.uber_backend.entities.Wallet;
import org.psionicgeek.uber_backend.entities.WalletTransaction;
import org.psionicgeek.uber_backend.entities.enums.TransactionMethod;
import org.psionicgeek.uber_backend.entities.enums.TransactionType;
import org.psionicgeek.uber_backend.exceptions.ResourceNotFoundException;
import org.psionicgeek.uber_backend.repositories.WalletRepository;
import org.psionicgeek.uber_backend.services.WalletService;
import org.psionicgeek.uber_backend.services.WalletTransactionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;

    private final WalletTransactionService walletTransactionService;

    @Override
    @Transactional
    public Wallet addMoneyToWallet(User user, Double amount, String transactionId, Ride ride, TransactionMethod transactionMethod) {
        Wallet wallet = findWalletByUser(user);
        wallet.setBalance(wallet.getBalance() + amount);

        WalletTransaction walletTransaction = WalletTransaction.builder()
                .transactionId(transactionId)
                .ride(ride)
                .wallet(wallet)
                .transactionType(TransactionType.CREDIT)
                .transactionMethod(transactionMethod)
                .amount(amount)
                .build();

        walletTransactionService.createNewWalletTransaction(walletTransaction);

        return walletRepository.save(wallet);
    }

    @Override
    @Transactional
    public Wallet deductMoneyFromWallet(User user, Double amount, String transactionId, Ride ride, TransactionMethod transactionMethod) {
        Wallet wallet = findWalletByUser(user);
        wallet.setBalance(wallet.getBalance() - amount);
        WalletTransaction walletTransaction = WalletTransaction.builder()
                .transactionId(transactionId)
                .ride(ride)
                .wallet(wallet)
                .transactionType(TransactionType.DEBIT)
                .transactionMethod(transactionMethod)
                .amount(amount)
                .build();

        walletTransactionService.createNewWalletTransaction(walletTransaction);
        //wallet.getWalletTransactions().add(walletTransaction);

        return walletRepository.save(wallet);
    }

    @Override
    public void withdrawAllMoneyFromWallet() {

    }

    @Override
    public Wallet findWalletById(Long id) {
        return walletRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Wallet not found"));
    }

    @Override
    public Wallet createNewWallet(User user) {
        Wallet wallet = new Wallet();

        wallet.setUser(user);
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet findWalletByUser(User user) {
        return walletRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet not found"));
    }
}
