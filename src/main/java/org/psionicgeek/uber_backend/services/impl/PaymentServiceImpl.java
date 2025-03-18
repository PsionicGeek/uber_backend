package org.psionicgeek.uber_backend.services.impl;

import lombok.RequiredArgsConstructor;
import org.psionicgeek.uber_backend.entities.Payment;
import org.psionicgeek.uber_backend.entities.Ride;
import org.psionicgeek.uber_backend.entities.enums.PaymentStatus;
import org.psionicgeek.uber_backend.repositories.PaymentRepository;
import org.psionicgeek.uber_backend.services.PaymentService;
import org.psionicgeek.uber_backend.strategies.PaymentStrategyManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentStrategyManager paymentStrategyManager;

    @Override
    public void processPayment(Ride ride) {
        Payment payment = paymentRepository.findByRide(ride)
                .orElseThrow(() -> new RuntimeException("Payment not found for ride: " + ride.getId()));
        paymentStrategyManager
                .getPaymentStrategy(payment.getPaymentMethod())
                .processPayment(payment);

    }

    @Override
    public Payment createNewPayment(Ride ride) {
        Payment payment = Payment.builder()
                .ride(ride)
                .paymentMethod(ride.getPaymentMethod())
                .amount(ride.getFare())
                .paymentStatus(PaymentStatus.PENDING)
                .build();
        return paymentRepository.save(payment);
    }

    @Override
    public void updatePaymentStatus(Payment payment, PaymentStatus paymentStatus) {
        payment.setPaymentStatus(paymentStatus);
        paymentRepository.save(payment);
    }
}
