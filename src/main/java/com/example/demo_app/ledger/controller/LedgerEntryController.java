package com.example.demo_app.ledger.controller;

import com.example.demo_app.ledger.dto.LedgerEntryResponse;
import com.example.demo_app.ledger.service.LedgerEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/ledger-entry")
@RequiredArgsConstructor
public class LedgerEntryController {

    private final LedgerEntryService ledgerEntryService;

    @GetMapping("/accounts/{accountNumber}/statement")
    public ResponseEntity<List<LedgerEntryResponse>> getLedgerEntriesInTimeInterval(
            @PathVariable String accountNumber,
            @RequestParam Instant startTime,
            @RequestParam Instant endTime,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {

        List<LedgerEntryResponse> ledgerEntriesResponse = ledgerEntryService.findAllLedgerEntriesInTimeInterval(
                accountNumber,
                startTime,
                endTime,
                page,
                size
        );

        return ResponseEntity.ok(ledgerEntriesResponse);
    }
}
