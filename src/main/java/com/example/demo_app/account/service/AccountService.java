package com.example.demo_app.account.service;

import com.example.demo_app.account.dto.AccountResponse;
import com.example.demo_app.account.entity.Account;
import com.example.demo_app.account.entity.AccountType;
import com.example.demo_app.auth.entity.UserEntity;
import com.example.demo_app.currency.entity.CurrencyEntity;

import java.math.BigDecimal;
import java.util.Optional;

public interface AccountService {
    Account getAccount(String accountNumber);
    AccountResponse transformAccountToDto(Account account);
    boolean isAccountOwner(String accountNumber);
}
