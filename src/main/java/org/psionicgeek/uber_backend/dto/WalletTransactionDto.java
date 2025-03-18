package org.psionicgeek.uber_backend.dto;


import lombok.Builder;
import lombok.Data;
import org.psionicgeek.uber_backend.entities.enums.TransactionMethod;
import org.psionicgeek.uber_backend.entities.enums.TransactionType;

import java.time.LocalDateTime;

@Data
@Builder
public class WalletTransactionDto {
    private Long id;

    private Double amount;


    private TransactionType transactionType;


    private TransactionMethod transactionMethod;


    private RideDto ride;

    private String transactionId;


    private WalletDto wallet;


    private LocalDateTime timeStamp;
}
