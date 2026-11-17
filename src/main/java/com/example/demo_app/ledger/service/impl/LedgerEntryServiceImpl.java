package com.example.demo_app.ledger.service.impl;

import com.example.demo_app.account.entity.Account;
import com.example.demo_app.account.exception.AccountOwnershipException;
import com.example.demo_app.account.service.AccountService;
import com.example.demo_app.ledger.dto.LedgerEntryResponse;
import com.example.demo_app.ledger.entity.LedgerEntry;
import com.example.demo_app.ledger.repository.LedgerEntryRepository;
import com.example.demo_app.ledger.service.LedgerEntryService;
import com.example.demo_app.transfer.dto.MoneyTransferResponse;
import com.example.demo_app.transfer.service.MoneyTransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LedgerEntryServiceImpl implements LedgerEntryService {
    private final LedgerEntryRepository ledgerEntryRepository;
    private final AccountService accountService;
    private final MoneyTransferService moneyTransferService;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveLedgerEntries(List<LedgerEntry> ledgerEntries) {

        ledgerEntryRepository.saveAll(ledgerEntries);
    }

    @Override
    public LedgerEntryResponse transforLedgerEntryToDto(LedgerEntry ledgerEntry) {
        MoneyTransferResponse moneyTransferResponse = moneyTransferService.transformMoneyTransferToDto(ledgerEntry.getMoneyTransfer());
        return new LedgerEntryResponse(
                ledgerEntry.getId(),
                moneyTransferResponse.transferId(),
                moneyTransferResponse.status().toString(),
                ledgerEntry.getTransferAmount(),
                ledgerEntry.getCreatedAt());
    }

    @Transactional(readOnly = true)
    @Override
    public List<LedgerEntryResponse> findAllLedgerEntriesInTimeInterval(
            String accountNumber,
            Instant startTime,
            Instant endTime,
            int page,
            int size
    ) {
        if(!startTime.isBefore(endTime)){
            throw new IllegalStateException("Start time cannot be after endtime");
        }
        if(size > 20) throw new IllegalStateException("Size must not exceed 20");
        if (!accountService.isAccountOwner(accountNumber)) {
            throw new AccountOwnershipException();
        }
        Account account = accountService.getAccount(accountNumber);

        List<LedgerEntry> ledgerEntries = ledgerEntryRepository.findLedgerEntriesInTimeInterval(
                account.getId(),
                startTime,
                endTime,
                PageRequest.of(page, size)
        );
        return ledgerEntries.stream().map(this::transforLedgerEntryToDto).toList();
    }


}
