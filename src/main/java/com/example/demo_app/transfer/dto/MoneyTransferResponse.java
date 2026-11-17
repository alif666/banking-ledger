package com.example.demo_app.transfer.dto;

import com.example.demo_app.account.dto.AccountResponse;
import com.example.demo_app.transfer.entity.MoneyTransferStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link com.example.demo_app.transfer.entity.MoneyTransfer}
 */
public record MoneyTransferResponse(Long id, UUID transferId, UUID idempotencyKey, AccountResponse fromAccount,
                                    AccountResponse toAccount, MoneyTransferStatus status, Instant createdAt,
                                    BigDecimal amountToTransfer) implements Serializable {
}