package com.example.demo_app.transfer.service.impl;

import com.example.demo_app.account.entity.Account;
import com.example.demo_app.account.exception.AccountOwnershipException;
import com.example.demo_app.account.service.AccountService;
import com.example.demo_app.ledger.entity.LedgerEntry;
import com.example.demo_app.ledger.service.LedgerEntryService;
import com.example.demo_app.transfer.dto.MoneyTransferResponse;
import com.example.demo_app.transfer.entity.MoneyTransfer;
import com.example.demo_app.transfer.exception.IdempotencyKeyConflictException;
import com.example.demo_app.transfer.exception.TransactionMismatchException;
import com.example.demo_app.transfer.exception.UnsupportedCurrencyMatchBetweenAccountsException;
import com.example.demo_app.transfer.repository.MoneyTransferRepository;
import com.example.demo_app.transfer.service.MoneyTransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MoneyTransferServiceImpl implements MoneyTransferService {

    private final MoneyTransferRepository moneyTransferRepository;
    private final AccountService accountService;
    private final LedgerEntryService ledgerEntryService;

    @Override
    @Transactional
    public MoneyTransfer transfer(
            String fromAccountNumber,
            String toAccountNumber,
            BigDecimal transferAmount,
            UUID idempotencyKey
    ) {
//        Transfer is owner
        if(!accountService.isAccountOwner(fromAccountNumber)){
            throw new AccountOwnershipException();
        }

        Optional<MoneyTransfer> existing = moneyTransferRepository.findByIdempotencyKey(idempotencyKey);
// if transfer present than show it
        if(existing.isPresent()){
            MoneyTransfer currentMoneyTransfer = existing.get();
            if (currentMoneyTransfer.getFromAccount().getAccountNumber().equals(fromAccountNumber)
                    && currentMoneyTransfer.getToAccount().getAccountNumber().equals(toAccountNumber)
                    && currentMoneyTransfer.getAmountToTransfer().compareTo(transferAmount) == 0) {
                return currentMoneyTransfer;
            } else {
                throw new TransactionMismatchException();
            }
        }

        Account fromAccount = accountService.getAccount(fromAccountNumber);
        Account toAccount = accountService.getAccount(toAccountNumber);
        MoneyTransfer moneyTransfer = new MoneyTransfer();
        moneyTransfer.setFromAccountAndToAccount(fromAccount, toAccount);
        moneyTransfer.setAmountToTransfer(fromAccount, transferAmount);
        moneyTransfer.setIdempotencyKey(idempotencyKey);
        fromAccount.debit(transferAmount);
        toAccount.credit(transferAmount);


//        handle idempotency
        try{
            MoneyTransfer savedMoneyTransfer = moneyTransferRepository.saveAndFlush(moneyTransfer);
            LedgerEntry sourceLedgerEntry = new LedgerEntry(savedMoneyTransfer, fromAccount);
            LedgerEntry destinationLedgerEntry = new LedgerEntry(savedMoneyTransfer, toAccount);
            ledgerEntryService.saveLedgerEntries(List.of(sourceLedgerEntry, destinationLedgerEntry));
            return savedMoneyTransfer;

        } catch(DataIntegrityViolationException ex){
            if(isIdempotencyKeyViolation(ex)){
                throw new IdempotencyKeyConflictException();
            }
            throw ex;
        }
    }

    @Override
    public MoneyTransferResponse transformMoneyTransferToDto(MoneyTransfer moneyTransfer) {
        return new MoneyTransferResponse(
                moneyTransfer.getId(),
                moneyTransfer.getTransferId(),
                moneyTransfer.getIdempotencyKey(),
                accountService.transformAccountToDto(moneyTransfer.getFromAccount()),
                accountService.transformAccountToDto(moneyTransfer.getToAccount()),
                moneyTransfer.getStatus(),
                moneyTransfer.getCreatedAt(),
                moneyTransfer.getAmountToTransfer()
        );
    }

    protected boolean isIdempotencyKeyViolation(Throwable error) {
        for (Throwable cause = error; cause != null; cause =
                cause.getCause()) {
            if (cause instanceof
                    org.hibernate.exception.ConstraintViolationException violation) {
                return "uk_money_transfer_idempotency_key"
                        .equals(violation.getConstraintName());
            }
        }
        return false;
    }

}
