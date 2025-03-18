package org.psionicgeek.uber_backend.strategies.impl;

import lombok.RequiredArgsConstructor;
import org.psionicgeek.uber_backend.entities.Driver;
import org.psionicgeek.uber_backend.entities.Payment;
import org.psionicgeek.uber_backend.entities.enums.PaymentStatus;
import org.psionicgeek.uber_backend.entities.enums.TransactionMethod;
import org.psionicgeek.uber_backend.repositories.PaymentRepository;
import org.psionicgeek.uber_backend.services.PaymentService;
import org.psionicgeek.uber_backend.services.WalletService;
import org.psionicgeek.uber_backend.strategies.PaymentStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CashPaymentStrategy implements PaymentStrategy {
    private final WalletService walletService;

    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public void processPayment(Payment payment) {
        Driver driver = payment.getRide().getDriver();

        double platformCommission = payment.getAmount() * PLATFORM_COMMISSION;
        walletService.deductMoneyFromWallet(driver.getUser(), platformCommission,null,
                payment.getRide(),
                TransactionMethod.RIDE
                );

        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);



    }
}
