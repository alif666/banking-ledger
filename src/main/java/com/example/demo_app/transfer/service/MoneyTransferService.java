package com.example.demo_app.transfer.service;

import com.example.demo_app.account.entity.Account;
import com.example.demo_app.transfer.dto.MoneyTransferResponse;
import com.example.demo_app.transfer.entity.MoneyTransfer;

import java.math.BigDecimal;
import java.util.UUID;

public interface MoneyTransferService {
    MoneyTransfer transfer(String fromAccountNumber, String toAccountNumber, BigDecimal transferAmount, UUID idempotencyKey);
    MoneyTransferResponse transformMoneyTransferToDto(MoneyTransfer moneyTransfer);
}
