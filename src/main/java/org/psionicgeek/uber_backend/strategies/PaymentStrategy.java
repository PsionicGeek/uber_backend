package org.psionicgeek.uber_backend.strategies;

import org.psionicgeek.uber_backend.entities.Payment;

public interface PaymentStrategy {

    Double PLATFORM_COMMISSION = 0.3;

    void processPayment(Payment payment);
}
