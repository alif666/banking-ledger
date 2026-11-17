package com.example.demo_app.transfer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.io.Serializable;
import java.math.BigDecimal;


public record MoneyTransferRequest(
        @NotNull
        @Positive
        BigDecimal amountToTransfer,
        @NotBlank
        String fromAccountNumber,
        @NotBlank
        String toAccountNumber
) implements Serializable {
}