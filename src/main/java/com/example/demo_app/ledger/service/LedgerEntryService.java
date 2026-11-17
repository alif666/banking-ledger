package com.example.demo_app.ledger.service;

import com.example.demo_app.ledger.dto.LedgerEntryResponse;
import com.example.demo_app.ledger.entity.LedgerEntry;

import java.time.Instant;
import java.util.List;

public interface LedgerEntryService {
    void saveLedgerEntries(List<LedgerEntry> ledgerEntries);

    List<LedgerEntryResponse> findAllLedgerEntriesInTimeInterval(
            String accountNumber,
            Instant startTime,
            Instant endTime,
            int page,
            int size
    );

    LedgerEntryResponse transforLedgerEntryToDto(LedgerEntry ledgerEntry);
}
