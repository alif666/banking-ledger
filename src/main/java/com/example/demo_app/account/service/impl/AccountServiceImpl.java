package com.example.demo_app.account.service.impl;

import com.example.demo_app.account.dto.AccountResponse;
import com.example.demo_app.account.entity.Account;
import com.example.demo_app.account.entity.AccountType;
import com.example.demo_app.account.exception.AccountNotFoundException;
import com.example.demo_app.account.repository.AccountRepository;
import com.example.demo_app.account.service.AccountService;
import com.example.demo_app.auth.entity.UserEntity;
import com.example.demo_app.currency.entity.CurrencyEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    @Transactional(readOnly = true)
    public Account getAccount(String accountNumber) {

        Optional<Account> optionalAccount = accountRepository.findAccountByAccountNumber(accountNumber);
        if(!optionalAccount.isPresent()){
            throw new AccountNotFoundException();
        }
        return optionalAccount.get();
    }

    @Override
    public AccountResponse transformAccountToDto(Account account){
        return new AccountResponse(
                account.getVersion(),
                account.getId(),
                account.getAccountNumber(),
                account.getCurrency().getCurrencyCode(),
                account.getBalance(),
                account.getCreatedAt(),
                account.getUpdatedAt(),
                account.isActive(),
                account.getAccountType()
        );
    }

    @Transactional
    @Override
    public boolean isAccountOwner(String accountNumber){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken){
            return false;
        }
        String authenticatedEmail = authentication.getName();
        Account account = getAccount(accountNumber);
        return account.getUserEntity().getEmail().equals(authenticatedEmail);
    }


//    @Override
//    @Transactional(propagation = Propagation.REQUIRED)
//    public Account debitBalance(BigDecimal amount, String accountNumber){
//        Account account = accountRepository.findAccountByAccountNumber(accountNumber).orElseThrow(()-> new IllegalStateException("Account not found "));
//        account.debit(amount);
//        accountRepository.save(account);
//        return account;
//    }
//
//    @Override
//    @Transactional(propagation = Propagation.REQUIRED)
//    public Account creditBalance(BigDecimal amount, String accountNumber){
//        Account account = accountRepository.findAccountByAccountNumber(accountNumber).orElseThrow(()-> new IllegalStateException("Account not found "));
//        account.credit(amount);
//        accountRepository.save(account);
//        return account;
//    }


}
