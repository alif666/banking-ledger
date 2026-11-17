package com.example.demo_app.account.dto;

import com.example.demo_app.account.entity.AccountType;
import com.example.demo_app.currency.entity.CurrencyEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * DTO for {@link com.example.demo_app.account.entity.Account}
 */
public record AccountResponse(Long version, Long id, String accountNumber, String currencyCode, BigDecimal balance,
                              Instant createdAt, Instant updatedAt, boolean isActive,
                              AccountType accountType) implements Serializable {
}