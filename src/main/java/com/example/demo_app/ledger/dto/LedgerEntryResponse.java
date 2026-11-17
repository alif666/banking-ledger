package com.example.demo_app.ledger.dto;

import com.example.demo_app.account.dto.AccountResponse;
import com.example.demo_app.transfer.dto.MoneyTransferResponse;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link com.example.demo_app.ledger.entity.LedgerEntry}
 */
public record LedgerEntryResponse(
        Long id,
        UUID transferId ,
        String status,
        BigDecimal signedAmount,
        Instant createdAt) implements Serializable {
}

